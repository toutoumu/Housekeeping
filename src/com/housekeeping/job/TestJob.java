package com.housekeeping.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("我靠我来了");
	}

	public void test() {
		System.out.println("你来干嘛");
	}
}
