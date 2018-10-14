package io.github.isliqian.async.bean;
import io.github.isliqian.async.eum.TaskStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * 异步执行任务信息
 */
@Data
public class TaskInfo {

    private String taskId;
    private TaskStatusEnum status;
    private Date startTime;
    private Date endTime;
    private String totalTime;

}
