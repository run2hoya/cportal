package com.castis.cportal.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

public class RedundantExecutionCheckListener implements TriggerListener {
	Logger logger = Logger.getLogger(RedundantExecutionCheckListener.class);
	
	@Override
	public String getName() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
		// do nothing.
		//log.info("trigger complete:{}", triggerInstructionCode);
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		// do nothing.
		//log.info("trigger fired");
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		// do nothing.
		//log.info("trigger misfired");
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			for (JobExecutionContext job : jobs) {
				if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
					logger.info(String.format("Scheduler - %s is already running. Skip this job trigger.", context.getJobDetail().getKey().getName()));
					return true;
				}
			}
		} catch (SchedulerException e) {
			logger.error("error : ", e);
		}
		return false;
	}

}
