package io.github.isliqian.log.service.impl;



import io.github.isliqian.log.bean.SysLog;
import io.github.isliqian.log.mapper.SysLogMapper;
import io.github.isliqian.log.service.ISysLogService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void save(SysLog sysLog) {
        sysLogMapper.save(sysLog);
    }
}
