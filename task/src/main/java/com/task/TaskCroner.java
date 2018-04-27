package com.task;

import com.task.entity.TaskContact;
import com.task.entity.TaskCronerConfig;
import com.task.entity.TaskCronerLog;

/**
 * 定时任务类。 使用Cron表达式来运行定时任务。
 *
 * @author axeon
 */
public abstract class TaskCroner {

	/**
	 * 运行任务。
	 */
	public abstract String runTask(TaskCronerLog taskCronerLog) throws Exception;

	/**
	 * 初始化配置信息。
	 */
	public abstract TaskCronerConfig initConfig();

	/**
	 * 初始化联系人信息。
	 *
	 * @return
	 */
	public abstract TaskContact initContact();

}
