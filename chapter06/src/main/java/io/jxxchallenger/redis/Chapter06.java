package io.jxxchallenger.redis;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.sync.RedisCommands;

public class Chapter06 {

    /**
     * 
     * @param commands
     * @param lockName
     * @return
     */
    public String acquireLock(RedisCommands<String, String> commands, String lockName) {
        return this.acquireLock(commands, lockName, 1000);
    }
    
    /**
     * 
     * @param commands
     * @param lockName
     * @param timeout
     * @return
     */
    public String acquireLock(RedisCommands<String, String> commands, String lockName, long timeout) {
        String identifier = UUID.randomUUID().toString();
        long end = System.currentTimeMillis() + timeout;
        while(System.currentTimeMillis() < end) {
            if(commands.setnx("lock:" + lockName, identifier)) {
                return identifier;
            }
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return "";
    }
    
    /**
     * 
     * @param commands
     * @param lockName
     * @param identifier
     * @return
     */
    public boolean releaseLock(RedisCommands<String, String> commands, String lockName, String identifier) {
        String lock = "lock:" + lockName;
        while(true) {
            commands.watch(lock);
            if(identifier.equals(commands.get(lock))) {
                commands.multi();
                commands.del(lock);
                TransactionResult result = commands.exec();
                if(null == result || result.isEmpty()) {
                    continue;
                }
                
                return true;
            }
            commands.unwatch();
            break;
        }
        return false;
    }
    
    /**
     * 
     * @param commands
     * @param sellerId
     * @param buyerId
     * @param itemId
     * @param lprice
     * @return
     */
    public boolean purchaseItem(RedisCommands<String, String> commands, String sellerId, String buyerId, String itemId, double lprice) {
        String seller = "user:" + sellerId;
        String buyer = "user:" + buyerId;
        String item = itemId + "." + sellerId;
        String inventory = "inventory:" + buyerId;
        
        String locked = acquireLock(commands, "market");
        if(StringUtils.isBlank(locked)) {
            return false;
        }
        
        double price = commands.zscore("market", item);
        double founds = Double.valueOf(commands.hget(buyer, "founds"));
        if(lprice != price || price > founds) {
            releaseLock(commands, "market", locked);
            return false;
        }
        
        commands.hincrbyfloat(buyer, "founds", - price);
        commands.hincrbyfloat(seller, "founds", price);
        commands.zrem("market", item);
        commands.sadd(inventory, itemId);
        releaseLock(commands, "market", locked);
        return true;
        
    }
}
