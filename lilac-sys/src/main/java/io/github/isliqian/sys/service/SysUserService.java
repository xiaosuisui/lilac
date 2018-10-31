package io.github.isliqian.sys.service;







import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.isliqian.utils.base.CrudService;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.mapper.SysUserMapper;
import io.github.isliqian.sys.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by LiQian_Nice on 2018/6/9
 */
@Component
@Slf4j
public class SysUserService extends CrudService<SysUserMapper, SysUser> {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    private List<SysUser> sysUserList;


    public List<SysUser> findAll() {
        PageHelper.startPage(1,10); //pageNum=2, pageSize=3 ,表示每页的大小为3，查询第二页的结果
        PageHelper.orderBy("id ASC "); //进行分页结果的排序，name为字段名，排序规则DESC/ASC
        sysUserList = sysUserMapper.findList(new SysUser());
        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(sysUserList);
        return pageInfo.getList();
    }


    public void add(SysUser sysUser) {
        log.info("UserImpl add");
        sysUserMapper.insert(sysUser);
    }
    @Cacheable(cacheNames = "userCache")
    public SysUser getByLoginName(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(username);
        return sysUserMapper.getByLoginName(sysUser);
    }


    public void banUser(String username) {

    }


    public void updatePassword(String username, String newPassword) {

    }





    public int checkUserBanStatus(String username) {
        return 0;
    }
}
