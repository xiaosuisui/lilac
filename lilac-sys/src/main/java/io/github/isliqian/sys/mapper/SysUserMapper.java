package io.github.isliqian.sys.mapper;




import io.github.isliqian.sys.bean.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by LiQian_Nice on 2018/6/9
 */
@Component
@Mapper
public interface SysUserMapper {

    @Select("SELECT * FROM sys_user")
    List<SysUser> findAll();

    @Insert("INSERT INTO sys_user (id,name) VALUES(#{id},#{name})")
    void add(SysUser sysUser);

    String getPassword(String username);

    String getRole(String username);

    int checkUserBanStatus(String username);
}
