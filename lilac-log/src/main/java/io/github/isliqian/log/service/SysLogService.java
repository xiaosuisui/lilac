package io.github.isliqian.log.service;



import io.github.isliqian.log.bean.SysLog;
import io.github.isliqian.log.mapper.SysLogMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SysLogService  {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Async
    public void save(SysLog sysLog) {
        sysLogMapper.save(sysLog);
    }

    public List<SysLog> findList(SysLog sysLog) {
        return sysLogMapper.findList(sysLog);
    }
}
