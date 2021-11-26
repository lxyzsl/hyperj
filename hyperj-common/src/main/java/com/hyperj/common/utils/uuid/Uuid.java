package com.hyperj.common.utils.uuid;

/**
 * 自定义uuid封装类
 */
public class Uuid {
    private static Uuid ourInstance = new Uuid();

    public static Uuid getInstance() {
        return ourInstance;
    }

    private Uuid() {
    }

    /**
     * 根据机房ID和机柜ID生成一个uuid
     * @param centerId
     * @param workerId
     */
    public void init(long centerId, long workerId) {
        idWorker = new SnowflakeIdWorker(workerId, centerId);
    }

    private SnowflakeIdWorker idWorker;

    public long getUUID() {
        return idWorker.nextId();
    }

}
