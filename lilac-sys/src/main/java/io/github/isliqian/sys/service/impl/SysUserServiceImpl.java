package io.github.isliqian.sys.service.impl;



import com.github.pagehelper.PageHelper;


import com.github.pagehelper.PageInfo;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.mapper.SysUserMapper;
import io.github.isliqian.sys.mapper.SysUserRoleMapper;
import io.github.isliqian.sys.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by LiQian_Nice on 2018/6/9
 */
@Component
@Slf4j
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    private List<SysUser> sysUserList;

    @Override
    public List<SysUser> findAll() {
        PageHelper.startPage(1,10); //pageNum=2, pageSize=3 ,表示每页的大小为3，查询第二页的结果
        PageHelper.orderBy("id ASC "); //进行分页结果的排序，name为字段名，排序规则DESC/ASC
        sysUserList = sysUserMapper.findAll();
        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(sysUserList);
        return pageInfo.getList();
    }

    @Override
    public void add(SysUser sysUser) {
        log.info("UserImpl add");
        sysUserMapper.add(sysUser);
    }

    @Override
    public String getPassword(String username) {
        return sysUserMapper.getPassword(username);
    }

    @Override
    public void banUser(String username) {

    }

    @Override
    public void updatePassword(String username, String newPassword) {

    }

    @Override
    public List<String> getRoles(String username) {
        return sysUserRoleMapper.getRoles(username);
    }

    @Override
    public int checkUserBanStatus(String username) {
        return 0;
    }
}
