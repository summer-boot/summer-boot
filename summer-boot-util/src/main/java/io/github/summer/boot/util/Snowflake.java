package io.github.summer.boot.util;

import io.github.summer.boot.base.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Random;

/**
 * 雪花算法
 * 共64位
 * 0       - 0000000000 0000000000 0000000000 0000000000 0 - 0000000000 - 000000000000
 * 正号|1位 - 毫秒|41位                                     - 机器id|10位 - 序列|12位
 *
 * @author changebooks@qq.com
 */
public final class Snowflake {

    private static final Logger LOGGER = LoggerFactory.getLogger(Snowflake.class);

    /**
     * java.util.Random
     */
    private static final Random RANDOM = new Random();

    /**
     * 起始年
     */
    private static final int YEAR = 2025;

    /**
     * 起始时间戳，毫秒
     */
    private static final long EPOCH;

    static {
        EPOCH = newCalendar(YEAR).getTimeInMillis();
    }

    /**
     * 机器id占位
     */
    private static final long WORKER_ID_BITS = 10L;

    /**
     * 最大机器id
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * 序列占位
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 时间戳，毫秒，左移22位
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 机器id，左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 序列掩码
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 新时间戳时，开始序列用随机数，默认的最大随机数
     */
    private static final int MAX_RAND_SEQUENCE = 64;

    /**
     * 时钟回拨时，默认最大等待时长，毫秒
     */
    private static final int MAX_CLOCK_BACKWARD = 10;

    /**
     * 机器id
     */
    private final long workerId;

    /**
     * 新时间戳时，开始序列用随机数
     * 最大的随机数，随机数不包含此数
     * 2的幂次方性能最优
     */
    private final int maxRandSequence;

    /**
     * 时钟回拨时，最大等待时长，毫秒
     */
    private final long maxClockBackward;

    /**
     * 上次毫秒
     */
    private long lastTimestamp;

    /**
     * 序列
     */
    private long sequence;

    /**
     * 初始化，上次毫秒、机器id、开始序列
     */
    public Snowflake() {
        this(0L);
    }

    /**
     * 初始化，上次毫秒、机器id、开始序列
     *
     * @param workerId 机器id
     */
    public Snowflake(long workerId) {
        this(workerId, MAX_RAND_SEQUENCE, MAX_CLOCK_BACKWARD);
    }

    /**
     * 初始化，上次毫秒、机器id、开始序列
     *
     * @param workerId         机器id
     * @param maxRandSequence  新时间戳时，开始序列用随机数，设置最大随机数，随机数不包含此数，2的幂次方性能最优
     * @param maxClockBackward 时钟回拨时，最大等待时长，毫秒
     */
    public Snowflake(long workerId, int maxRandSequence, int maxClockBackward) {
        Assert.checkArgument((workerId >= 0L && workerId <= MAX_WORKER_ID),
                String.format("workerId must not be less than 0 or greater than %d", MAX_WORKER_ID));
        AssertUtils.nonNegative(maxRandSequence, "maxRandSequence");
        AssertUtils.nonNegative(maxClockBackward, "maxClockBackward");

        this.lastTimestamp = -1L;
        this.workerId = workerId;
        this.sequence = 0L;
        this.maxRandSequence = maxRandSequence;
        this.maxClockBackward = maxClockBackward;

        LOGGER.info("snowflake trace, workerId: {}, maxRandSequence: {}, maxClockBackward: {}", this.workerId, this.maxRandSequence, this.maxClockBackward);
    }

    /**
     * 日历，某年的起始时间，如：2024-01-01 00:00:00 000
     *
     * @param year 起始年，如：2024
     * @return 日历，如：2024-01-01 00:00:00 000
     */
    public static Calendar newCalendar(int year) {
        Calendar r = Calendar.getInstance();
        r.set(year, Calendar.JANUARY, 1);
        r.set(Calendar.HOUR_OF_DAY, 0);
        r.set(Calendar.MINUTE, 0);
        r.set(Calendar.SECOND, 0);
        r.set(Calendar.MILLISECOND, 0);
        return r;
    }

    /**
     * 生成新id
     *
     * @return id
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            tilClockForward(timestamp);
            timestamp = timeGen();
        }

        Assert.checkArgument(timestamp >= lastTimestamp,
                String.format("clock moved backwards. refusing to generate id for %d milliseconds", lastTimestamp - timestamp));

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1L) & SEQUENCE_MASK;
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            if (maxRandSequence > 0) {
                sequence = RANDOM.nextInt(maxRandSequence);
            } else {
                sequence = 0L;
            }
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence;
    }

    /**
     * 阻塞，等下次时间，单位：毫秒
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 阻塞，等时钟恢复，单位：毫秒
     */
    private void tilClockForward(final long timestamp) {
        if (timestamp < lastTimestamp) {
            long waitDifference = lastTimestamp - timestamp;
            Assert.checkArgument(waitDifference < maxClockBackward,
                    String.format("clock moved backwards. refusing to clock backward: %d, max clock backward: %d", waitDifference, maxClockBackward));

            try {
                Thread.sleep(waitDifference);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 当前时间，单位：毫秒
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public long getWorkerId() {
        return workerId;
    }

    public int getMaxRandSequence() {
        return maxRandSequence;
    }

    public int getMaxClockBackward() {
        return (int) maxClockBackward;
    }

}
