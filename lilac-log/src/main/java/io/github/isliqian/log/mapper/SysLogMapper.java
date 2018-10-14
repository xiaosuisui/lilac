package io.github.isliqian.log.mapper;



import io.github.isliqian.log.bean.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 日志记录
 */
@Component
@Mapper
public interface SysLogMapper {


    void save(SysLog sysLog);
}
