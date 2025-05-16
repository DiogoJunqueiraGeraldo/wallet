package com.wallet.wallets_command_service.services;

import com.wallet.wallets_command_service.errors.LockingTimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class DistributedLockService {
    @Value("${application.lock-timeout-ms}")
    int lockTimeoutMs;

    private final RedisTemplate<String, String> redisTemplate;

    public DistributedLockService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static String getLockKey(String resource, UUID id) {
        return resource + ":lock:" + id;
    }

    public String tryLock(String resource, UUID id) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = UUID.randomUUID().toString();
        String key = getLockKey(resource, id);
        Boolean success = ops.setIfAbsent(key, value, lockTimeoutMs, TimeUnit.MILLISECONDS);

        if (Boolean.TRUE.equals(success)) {
            return value;
        }

        throw new LockingTimeoutException("Timeout - Try again later.");
    }

    public void releaseLock(String resource, UUID id, String value) {
        String key = getLockKey(resource, id);
        String currentValue = redisTemplate.opsForValue().get(key);

        if (value.equals(currentValue)) {
            redisTemplate.delete(key);
        }
    }
}
