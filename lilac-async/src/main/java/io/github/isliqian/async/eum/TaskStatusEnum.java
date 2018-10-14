package io.github.isliqian.async.eum;

/**
 * 任务状态枚举
 */
public enum TaskStatusEnum {
    STARTED(1, "任务已经启动"),
    RUNNING(0, "任务正在运行"),
    SUCCESS(2, "任务执行成功"),
    FAILED(-2, "任务执行失败");
    private int state;
    private String stateInfo;

    TaskStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }


}
