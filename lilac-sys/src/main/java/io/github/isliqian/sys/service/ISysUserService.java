package io.github.isliqian.sys.service;







import io.github.isliqian.sys.bean.SysUser;

import java.util.List;

/**
 * Created by LiQian_Nice on 2018/6/9
 */
public interface ISysUserService {

    List<SysUser> findAll();


    void add(SysUser sysUser);

    String getPassword(String username);

    void banUser(String username);

    void updatePassword(String username, String newPassword);

    List<String> getRoles(String username);

    int checkUserBanStatus(String username);
}
