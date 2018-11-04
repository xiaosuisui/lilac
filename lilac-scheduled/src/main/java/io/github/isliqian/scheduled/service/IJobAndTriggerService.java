package io.github.isliqian.scheduled.service;


import com.github.pagehelper.PageInfo;
import io.github.isliqian.scheduled.entity.JobAndTrigger;

public interface IJobAndTriggerService {
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}
