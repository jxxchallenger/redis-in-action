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
            commands.zaddincr("market:", price, item);
            commands.srem("inventory:" + sellerId, itemId);
            TransactionResult results = commands.exec();
            if(results == null || results.isEmpty()) {
                continue;
            }
            return true;
        }
        return false;
    }
}
