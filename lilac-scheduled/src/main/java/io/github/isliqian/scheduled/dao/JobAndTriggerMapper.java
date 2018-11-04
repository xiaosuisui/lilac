package io.github.isliqian.scheduled.dao;


import io.github.isliqian.scheduled.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface JobAndTriggerMapper {
	public List<JobAndTrigger> getJobAndTriggerDetails();
}
