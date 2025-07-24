package com.gexw.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 唯一ID生成器
 * 使用时间戳 + 序列号的方式生成唯一ID
 * 时间戳部分：从2000年1月1日开始计算的秒数，左移32位
 * 序列号部分：当天Redis递增的序列号，32位
 */
@Component
public class UniqueIdGenerator {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 基准时间：2000年1月1日 00:00:00
    private static final LocalDateTime BASE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
    
    // 日期格式化器
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy:MM:dd");

    /**
     * 生成唯一ID
     * @return 唯一ID
     */
    public long generateUniqueId() {
        try {
            LocalDateTime now = LocalDateTime.now();
            
            // 计算从基准时间到现在的秒数
            long baseEpochSecond = BASE_TIME.toEpochSecond(ZoneOffset.UTC);
            long nowEpochSecond = now.toEpochSecond(ZoneOffset.UTC);
            long epoch = nowEpochSecond - baseEpochSecond;
            
            // 格式化日期作为Redis key
            String dateKey = now.format(DATE_FORMATTER);
            String redisKey = "accountrecord:" + dateKey;
            
            // 获取当天的序列号
            Long sequence = stringRedisTemplate.opsForValue().increment(redisKey);
            
            // 生成唯一ID：时间戳左移32位 + 序列号
            return (epoch << 32) | sequence;
            
        } catch (Exception e) {
            throw new RuntimeException("生成唯一ID失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成唯一ID（带前缀）
     * @param prefix 前缀
     * @return 带前缀的唯一ID字符串
     */
    public String generateUniqueIdWithPrefix(String prefix) {
        long id = generateUniqueId();
        return prefix + id;
    }

    /**
     * 从唯一ID中解析时间信息
     * @param uniqueId 唯一ID
     * @return 创建时间
     */
    public LocalDateTime parseTimeFromId(long uniqueId) {
        // 提取时间戳部分（右移32位）
        long epoch = uniqueId >> 32;
        
        // 计算实际时间
        long baseEpochSecond = BASE_TIME.toEpochSecond(ZoneOffset.UTC);
        long actualEpochSecond = baseEpochSecond + epoch;
        
        return LocalDateTime.ofEpochSecond(actualEpochSecond, 0, ZoneOffset.UTC);
    }

    /**
     * 从唯一ID中解析序列号
     * @param uniqueId 唯一ID
     * @return 序列号
     */
    public long parseSequenceFromId(long uniqueId) {
        // 提取序列号部分（取低32位）
        return uniqueId & 0xFFFFFFFFL;
    }

    /**
     * 验证ID是否有效
     * @param uniqueId 唯一ID
     * @return 是否有效
     */
    public boolean isValidId(long uniqueId) {
        try {
            LocalDateTime time = parseTimeFromId(uniqueId);
            LocalDateTime now = LocalDateTime.now();
            
            // 检查时间是否在合理范围内（不能是未来时间，也不能太早）
            return !time.isAfter(now) && time.isAfter(BASE_TIME);
        } catch (Exception e) {
            return false;
        }
    }
} 