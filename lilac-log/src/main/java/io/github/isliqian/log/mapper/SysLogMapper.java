package io.github.isliqian.log.mapper;



import io.github.isliqian.log.bean.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日志记录
 */
@Component
@Mapper
public interface SysLogMapper {


    void save(SysLog sysLog);

    List<SysLog> findList(SysLog sysLog);
}
