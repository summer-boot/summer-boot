package io.github.summer.boot.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 触发器
 *
 * @author changebooks@qq.com
 */
public final class QuartzTrigger {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzTrigger.class);

    private QuartzTrigger() {
    }

    /**
     * 获取触发器
     *
     * @param scheduler   Quartz Scheduler
     * @param groupName   触发器组
     * @param triggerName 触发器名
     * @return Trigger
     */
    public static Trigger getTrigger(Scheduler scheduler, String groupName, String triggerName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("getTrigger failed, scheduler must not be null, groupName: {}, triggerName: {}", groupName, triggerName);
            return null;
        }

        if (groupName == null) {
            LOGGER.error("getTrigger failed, groupName must not be null, triggerName: {}", triggerName);
            return null;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("getTrigger failed, groupName must not be empty, triggerName: {}", triggerName);
            return null;
        }

        if (triggerName == null) {
            LOGGER.error("getTrigger failed, triggerName must not be null, groupName: {}", groupName);
            return null;
        }

        if (triggerName.isEmpty()) {
            LOGGER.error("getTrigger failed, triggerName must not be empty, groupName: {}", groupName);
            return null;
        }

        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
        return scheduler.getTrigger(triggerKey);
    }

    /**
     * 获取触发器状态
     *
     * @param scheduler   Quartz Scheduler
     * @param groupName   触发器组
     * @param triggerName 触发器名
     * @return Trigger State
     */
    public static Trigger.TriggerState getTriggerState(Scheduler scheduler, String groupName, String triggerName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("getTriggerState failed, scheduler must not be null, groupName: {}, triggerName: {}", groupName, triggerName);
            return null;
        }

        if (groupName == null) {
            LOGGER.error("getTriggerState failed, groupName must not be null, triggerName: {}", triggerName);
            return null;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("getTriggerState failed, groupName must not be empty, triggerName: {}", triggerName);
            return null;
        }

        if (triggerName == null) {
            LOGGER.error("getTriggerState failed, triggerName must not be null, groupName: {}", groupName);
            return null;
        }

        if (triggerName.isEmpty()) {
            LOGGER.error("getTriggerState failed, triggerName must not be empty, groupName: {}", groupName);
            return null;
        }

        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
        return scheduler.getTriggerState(triggerKey);
    }

    /**
     * 获取构建类
     * CronTrigger TriggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
     * Start At    TriggerBuilder.startAt(Date);
     *
     * @param scheduler   Quartz Scheduler
     * @param groupName   触发器组
     * @param triggerName 触发器名
     * @return Trigger Builder
     */
    public static TriggerBuilder<Trigger> getBuilder(Scheduler scheduler, String groupName, String triggerName) {
        if (scheduler == null) {
            LOGGER.error("getBuilder failed, scheduler must not be null, groupName: {}, triggerName: {}", groupName, triggerName);
            return null;
        }

        if (groupName == null) {
            LOGGER.error("getBuilder failed, groupName must not be null, triggerName: {}", triggerName);
            return null;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("getBuilder failed, groupName must not be empty, triggerName: {}", triggerName);
            return null;
        }

        if (triggerName == null) {
            LOGGER.error("getBuilder failed, triggerName must not be null, groupName: {}", groupName);
            return null;
        }

        if (triggerName.isEmpty()) {
            LOGGER.error("getBuilder failed, triggerName must not be empty, groupName: {}", groupName);
            return null;
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName, groupName);
    }

    /**
     * 恢复触发器
     *
     * @param scheduler   Quartz Scheduler
     * @param groupName   触发器组
     * @param triggerName 触发器名
     */
    public static void resume(Scheduler scheduler, String groupName, String triggerName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("resume failed, scheduler must not be null, groupName: {}, triggerName: {}", groupName, triggerName);
            return;
        }

        if (groupName == null) {
            LOGGER.error("resume failed, groupName must not be null, triggerName: {}", triggerName);
            return;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("resume failed, groupName must not be empty, triggerName: {}", triggerName);
            return;
        }

        if (triggerName == null) {
            LOGGER.error("resume failed, triggerName must not be null, groupName: {}", groupName);
            return;
        }

        if (triggerName.isEmpty()) {
            LOGGER.error("resume failed, triggerName must not be empty, groupName: {}", groupName);
            return;
        }

        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
        scheduler.resumeTrigger(triggerKey);
    }

    /**
     * 暂停触发器
     *
     * @param scheduler   Quartz Scheduler
     * @param groupName   触发器组
     * @param triggerName 触发器名
     */
    public static void pause(Scheduler scheduler, String groupName, String triggerName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("pause failed, scheduler must not be null, groupName: {}, triggerName: {}", groupName, triggerName);
            return;
        }

        if (groupName == null) {
            LOGGER.error("pause failed, groupName must not be null, triggerName: {}", triggerName);
            return;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("pause failed, groupName must not be empty, triggerName: {}", triggerName);
            return;
        }

        if (triggerName == null) {
            LOGGER.error("pause failed, triggerName must not be null, groupName: {}", groupName);
            return;
        }

        if (triggerName.isEmpty()) {
            LOGGER.error("pause failed, triggerName must not be empty, groupName: {}", groupName);
            return;
        }

        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
        scheduler.pauseTrigger(triggerKey);
    }

    /**
     * 解除触发器
     *
     * @param scheduler   Quartz Scheduler
     * @param groupName   触发器组
     * @param triggerName 触发器名
     * @return success ?
     */
    public static boolean delete(Scheduler scheduler, String groupName, String triggerName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("delete failed, scheduler must not be null, groupName: {}, triggerName: {}", groupName, triggerName);
            return false;
        }

        if (groupName == null) {
            LOGGER.error("delete failed, groupName must not be null, triggerName: {}", triggerName);
            return false;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("delete failed, groupName must not be empty, triggerName: {}", triggerName);
            return false;
        }

        if (triggerName == null) {
            LOGGER.error("delete failed, triggerName must not be null, groupName: {}", groupName);
            return false;
        }

        if (triggerName.isEmpty()) {
            LOGGER.error("delete failed, triggerName must not be empty, groupName: {}", groupName);
            return false;
        }

        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
        return scheduler.unscheduleJob(triggerKey);
    }

}
