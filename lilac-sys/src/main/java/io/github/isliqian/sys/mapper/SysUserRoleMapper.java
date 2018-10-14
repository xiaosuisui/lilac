package io.github.isliqian.sys.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by LiQian_Nice on 2018/6/9
 */
@Component
@Mapper
public interface SysUserRoleMapper {


    List<String> getRoles(String username);
}
