package io.github.summer.boot.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 调度
 *
 * @author changebooks@qq.com
 */
public final class QuartzScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzScheduler.class);

    private QuartzScheduler() {
    }

    /**
     * 配置调度
     *
     * @param scheduler Quartz Scheduler
     * @param jobDetail 任务
     * @param trigger   触发器
     * @return 首次调度时间
     */
    public static Date schedule(Scheduler scheduler, JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("schedule failed, scheduler must not be null");
            return null;
        }

        if (jobDetail == null) {
            LOGGER.error("schedule failed, jobDetail must not be null");
            return null;
        }

        if (trigger == null) {
            LOGGER.error("schedule failed, trigger must not be null");
            return null;
        }

        return scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 恢复调度
     *
     * @param scheduler     Quartz Scheduler
     * @param groupName     调度组
     * @param schedulerName 调度名
     */
    public static void resume(Scheduler scheduler, String groupName, String schedulerName) throws SchedulerException {
        QuartzTrigger.resume(scheduler, groupName, schedulerName);
        QuartzJob.resume(scheduler, groupName, schedulerName);
    }

    /**
     * 暂停调度
     *
     * @param scheduler     Quartz Scheduler
     * @param groupName     调度组
     * @param schedulerName 调度名
     */
    public static void pause(Scheduler scheduler, String groupName, String schedulerName) throws SchedulerException {
        QuartzTrigger.pause(scheduler, groupName, schedulerName);
        QuartzJob.pause(scheduler, groupName, schedulerName);
    }

    /**
     * 删除调度
     *
     * @param scheduler     Quartz Scheduler
     * @param groupName     调度组
     * @param schedulerName 调度名
     * @return success ?
     */
    public static boolean delete(Scheduler scheduler, String groupName, String schedulerName) throws SchedulerException {
        try {
            pause(scheduler, groupName, schedulerName);
        } catch (SchedulerException ex) {
            LOGGER.error("delete failed, pause failed, groupName: {}, schedulerName: {}, throwable: ", groupName, schedulerName, ex);
        }

        boolean deleteTrigger = QuartzTrigger.delete(scheduler, groupName, schedulerName);
        if (!deleteTrigger) {
            LOGGER.error("delete failed, delete trigger failed, groupName: {}, schedulerName: {}", groupName, schedulerName);
        }

        boolean deleteJob = QuartzJob.delete(scheduler, groupName, schedulerName);
        if (!deleteJob) {
            LOGGER.error("delete failed, delete job failed, groupName: {}, schedulerName: {}", groupName, schedulerName);
        }

        return deleteTrigger && deleteJob;
    }

}
