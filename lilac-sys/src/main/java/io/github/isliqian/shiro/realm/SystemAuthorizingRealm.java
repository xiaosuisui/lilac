package io.github.isliqian.shiro.realm;

import java.util.*;
import javax.annotation.Resource;
import io.github.isliqian.sys.bean.SysRoleMenu;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.bean.SysUserRole;
import io.github.isliqian.sys.service.SysRoleMenuService;
import io.github.isliqian.sys.service.SysUserRoleService;
import io.github.isliqian.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SystemAuthorizingRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysUserRoleService sysUserRoleService;
    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("sys————身份认证方法————");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从数据库获取对应用户名密码的用户
        SysUser sysUser = sysUserService.getByLoginName(token.getUsername());
        if (null == sysUser) {
            throw new AccountException("用户名不正确");
        } else if (!sysUser.getPassword().equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), sysUser.getPassword(), getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("sys————权限认证————");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SysUser user = sysUserService.getByLoginName(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        SysUserRole sysUserRole  = new SysUserRole();
        sysUserRole.setUser(user);
        List<SysUserRole> sysUserRoles = sysUserRoleService.findList(sysUserRole);
        Set<String> roleSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();
        SysRoleMenu sysRoleMenu =null;
        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        for (SysUserRole userRole: sysUserRoles) {
            roleSet.add(userRole.getRole().getEnname());
            sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRole(userRole.getRole());

            List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.findList(sysRoleMenu);
            for (SysRoleMenu roleMenu:sysRoleMenus){
                permissionSet.add(roleMenu.getMenu().getPermission());
            }
        }
        for (String s:permissionSet){
            log.info("role:"+s);
        }
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }
}
