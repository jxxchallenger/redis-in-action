package io.jxxchallenger.redis;

import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.sync.RedisCommands;

public class Chapter04 {

    public boolean listItem(RedisCommands<String, String> commands, String itemId, String sellerId, double price) {
        String inventory = "inventory:" + sellerId;
        String item = itemId + "." + sellerId;
        long end = System.currentTimeMillis() + 5000;
        while(System.currentTimeMillis() < end) {
            commands.watch(inventory);
            if(!commands.sismember(inventory, itemId)) {
                commands.unwatch();
                return false;
            }
            commands.multi();
            commands.zadd("market:", price, item);
            commands.srem("inventory:" + sellerId, itemId);
            TransactionResult results = commands.exec();
            if(results == null || results.isEmpty()) {
                continue;
            }
            return true;
        }
        return false;
    }

    public boolean purchaseItem(RedisCommands<String, String> commands, String buyerId, String sellerId, String itemId, double lprice) {
        String buyer = "user:" + buyerId;
        String seller = "user:" + sellerId;
        String item = itemId + "." + sellerId;
        String inventory = "inventory:" + buyerId;

        long end = System.currentTimeMillis() + 10000;

        while(System.currentTimeMillis() < end) {
            commands.watch("market:", buyer);
            
            double price = commands.zscore("market:", item);
            double funds = Double.parseDouble(commands.hget(buyer, "funds"));
            if(price != lprice || price > funds) {
                commands.unwatch();
                return false;
            }
            
            commands.multi();
            commands.hincrbyfloat(seller, "funds", price);
            commands.hincrbyfloat(buyer, "funds", -price);
            commands.sadd(inventory, itemId);
            commands.zrem("market", item);
            TransactionResult results = commands.exec();
            if(results == null || results.isEmpty()) {
                continue;
            }
            return true;
            
        }
        
        return false;
    }
}
