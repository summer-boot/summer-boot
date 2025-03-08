package io.github.summer.boot.quartz;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 任务
 *
 * @author changebooks@qq.com
 */
public final class QuartzJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJob.class);

    private QuartzJob() {
    }

    /**
     * 获取任务组列表
     *
     * @param scheduler Quartz Scheduler
     * @return [ GroupName ]
     */
    public static List<String> getGroupNames(Scheduler scheduler) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("getGroupNames failed, scheduler must not be null");
            return null;
        }

        List<String> groupNames = scheduler.getJobGroupNames();
        if (groupNames != null) {
            return groupNames.stream()
                    .filter(Objects::nonNull)
                    .filter(x -> !x.isEmpty())
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 获取任务名列表
     *
     * @param scheduler Quartz Scheduler
     * @param groupName 任务组
     * @return [ JobName ]
     */
    public static List<String> getJobNames(Scheduler scheduler, String groupName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("getJobNames failed, scheduler must not be null, groupName: {}", groupName);
            return null;
        }

        if (groupName == null) {
            LOGGER.error("getJobNames failed, groupName must not be null");
            return null;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("getJobNames failed, groupName must not be empty");
            return null;
        }

        GroupMatcher<JobKey> groupMatcher = GroupMatcher.groupEquals(groupName);
        Set<JobKey> keySet = scheduler.getJobKeys(groupMatcher);
        if (keySet != null) {
            return keySet.stream()
                    .filter(Objects::nonNull)
                    .map(JobKey::getName)
                    .filter(Objects::nonNull)
                    .filter(x -> !x.isEmpty())
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 获取任务详情
     *
     * @param scheduler Quartz Scheduler
     * @param groupName 任务组
     * @param jobName   任务名
     * @return Job Detail
     */
    public static JobDetail getDetail(Scheduler scheduler, String groupName, String jobName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("getDetail failed, scheduler must not be null, groupName: {}, jobName: {}", groupName, jobName);
            return null;
        }

        if (groupName == null) {
            LOGGER.error("getDetail failed, groupName must not be null, jobName: {}", jobName);
            return null;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("getDetail failed, groupName must not be empty, jobName: {}", jobName);
            return null;
        }

        if (jobName == null) {
            LOGGER.error("getDetail failed, jobName must not be null, groupName: {}", groupName);
            return null;
        }

        if (jobName.isEmpty()) {
            LOGGER.error("getDetail failed, jobName must not be empty, groupName: {}", groupName);
            return null;
        }

        JobKey jobKey = new JobKey(jobName, groupName);
        return scheduler.getJobDetail(jobKey);
    }

    /**
     * 获取构建类
     *
     * @param scheduler Quartz Scheduler
     * @param groupName 任务组
     * @param jobName   任务名
     * @param jobClass  任务执行类
     * @param dataMap   执行数据
     * @return Job Builder
     */
    public static JobBuilder getBuilder(Scheduler scheduler, String groupName, String jobName, Class<? extends Job> jobClass, JobDataMap dataMap) {
        if (scheduler == null) {
            LOGGER.error("getBuilder failed, scheduler must not be null, groupName: {}, jobName: {}", groupName, jobName);
            return null;
        }

        if (groupName == null) {
            LOGGER.error("getBuilder failed, groupName must not be null, jobName: {}", jobName);
            return null;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("getBuilder failed, groupName must not be empty, jobName: {}", jobName);
            return null;
        }

        if (jobName == null) {
            LOGGER.error("getBuilder failed, jobName must not be null, groupName: {}", groupName);
            return null;
        }

        if (jobName.isEmpty()) {
            LOGGER.error("getBuilder failed, jobName must not be empty, groupName: {}", groupName);
            return null;
        }

        if (jobClass == null) {
            LOGGER.error("getBuilder failed, jobClass must not be null, groupName: {}, jobName: {}", groupName, jobName);
            return null;
        }

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobName, groupName)
                .setJobData(dataMap)
                .storeDurably();
    }

    /**
     * 恢复任务
     *
     * @param scheduler Quartz Scheduler
     * @param groupName 任务组
     * @param jobName   任务名
     */
    public static void resume(Scheduler scheduler, String groupName, String jobName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("resume failed, scheduler must not be null, groupName: {}, jobName: {}", groupName, jobName);
            return;
        }

        if (groupName == null) {
            LOGGER.error("resume failed, groupName must not be null, jobName: {}", jobName);
            return;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("resume failed, groupName must not be empty, jobName: {}", jobName);
            return;
        }

        if (jobName == null) {
            LOGGER.error("resume failed, jobName must not be null, groupName: {}", groupName);
            return;
        }

        if (jobName.isEmpty()) {
            LOGGER.error("resume failed, jobName must not be empty, groupName: {}", groupName);
            return;
        }

        JobKey jobKey = new JobKey(jobName, groupName);
        scheduler.resumeJob(jobKey);
    }

    /**
     * 暂停任务
     *
     * @param scheduler Quartz Scheduler
     * @param groupName 任务组
     * @param jobName   任务名
     */
    public static void pause(Scheduler scheduler, String groupName, String jobName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("pause failed, scheduler must not be null, groupName: {}, jobName: {}", groupName, jobName);
            return;
        }

        if (groupName == null) {
            LOGGER.error("pause failed, groupName must not be null, jobName: {}", jobName);
            return;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("pause failed, groupName must not be empty, jobName: {}", jobName);
            return;
        }

        if (jobName == null) {
            LOGGER.error("pause failed, jobName must not be null, groupName: {}", groupName);
            return;
        }

        if (jobName.isEmpty()) {
            LOGGER.error("pause failed, jobName must not be empty, groupName: {}", groupName);
            return;
        }

        JobKey jobKey = new JobKey(jobName, groupName);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 删除任务
     *
     * @param scheduler Quartz Scheduler
     * @param groupName 任务组
     * @param jobName   任务名
     * @return success ?
     */
    public static boolean delete(Scheduler scheduler, String groupName, String jobName) throws SchedulerException {
        if (scheduler == null) {
            LOGGER.error("delete failed, scheduler must not be null, groupName: {}, jobName: {}", groupName, jobName);
            return false;
        }

        if (groupName == null) {
            LOGGER.error("delete failed, groupName must not be null, jobName: {}", jobName);
            return false;
        }

        if (groupName.isEmpty()) {
            LOGGER.error("delete failed, groupName must not be empty, jobName: {}", jobName);
            return false;
        }

        if (jobName == null) {
            LOGGER.error("delete failed, jobName must not be null, groupName: {}", groupName);
            return false;
        }

        if (jobName.isEmpty()) {
            LOGGER.error("delete failed, jobName must not be empty, groupName: {}", groupName);
            return false;
        }

        JobKey jobKey = new JobKey(jobName, groupName);
        return scheduler.deleteJob(jobKey);
    }

}
