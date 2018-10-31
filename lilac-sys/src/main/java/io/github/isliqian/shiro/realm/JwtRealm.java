package io.github.isliqian.shiro.realm;

import io.github.isliqian.shiro.token.JWTToken;
import io.github.isliqian.shiro.utils.JWTUtil;
import io.github.isliqian.sys.bean.SysRoleMenu;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.bean.SysUserRole;
import io.github.isliqian.sys.service.SysRoleMenuService;
import io.github.isliqian.sys.service.SysUserRoleService;
import io.github.isliqian.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysUserRoleService sysUserRoleService;
    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        log.info("jwt————身份认证方法————");
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        SysUser user = sysUserService.getByLoginName(username);
        if (user == null) {
            throw new AuthenticationException("该用户不存在！");
        }
        int ban = sysUserService.checkUserBanStatus(username);
        if (ban == 1) {
            throw new AuthenticationException("该用户已被封号！");
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("jwt————权限认证————");
        String username = JWTUtil.getUsername(principals.toString());
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