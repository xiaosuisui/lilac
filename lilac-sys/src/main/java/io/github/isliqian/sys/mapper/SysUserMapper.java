package io.github.isliqian.sys.mapper;




import io.github.isliqian.sys.base.CrudDao;
import io.github.isliqian.sys.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by LiQian_Nice on 2018/6/9
 */
@Component
@Mapper
public interface SysUserMapper extends CrudDao<SysUser> {

    /**
     * 根据登录名称查询用户
     * @param loginName
     * @return
     */
    SysUser getByLoginName(SysUser user);

    /**
     * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
     * @param user
     * @return
     */
    List<SysUser> findUserByOfficeId(SysUser user);

    /**
     * 查询全部用户数目
     * @return
     */
    long findAllCount(SysUser user);

    /**
     * 更新用户密码
     * @param user
     * @return
     */
    int updatePasswordById(SysUser user);

    /**
     * 更新登录信息，如：登录IP、登录时间
     * @param user
     * @return
     */
    int updateLoginInfo(SysUser user);

    /**
     * 删除用户角色关联数据
     * @param user
     * @return
     */
    int deleteUserRole(SysUser user);

    /**
     * 插入用户角色关联数据
     * @param user
     * @return
     */
    int insertUserRole(SysUser user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUserInfo(SysUser user);

    /**
     * 批量删除用户权限
     * @param users
     * @author wxt.touxin
     */
    void deleteUserRoles(List<SysUser> users);

    /**
     * 批量插入用户权限
     * @param users
     * @author wxt.touxin
     */
    void insertUserRoles(List<SysUser> users);

    /**
     * 根据起始更新时间查询
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     * @author wxt.touxin
     */
    List<SysUser> findListByUpdateDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
