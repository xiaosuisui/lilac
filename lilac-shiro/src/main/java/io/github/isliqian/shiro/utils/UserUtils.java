package io.github.isliqian.shiro.utils;

import io.github.isliqian.sys.bean.*;

import java.util.List;

public class UserUtils {
/*
    private static UserDao userDao = MicAppContext.getBean(UserDao.class);
    private static RoleDao roleDao = MicAppContext.getBean(RoleDao.class);
    private static MenuDao menuDao = MicAppContext.getBean(MenuDao.class);
    private static AreaDao areaDao = MicAppContext.getBean(AreaDao.class);
    private static OfficeDao officeDao = MicAppContext.getBean(OfficeDao.class);

    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

    *//**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     *//*
    public static User get(String id) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userDao.get(id);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    *//**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return 取不到返回null
     *//*
    public static User getByLoginName(String loginName) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
        if (user == null) {
            user = userDao.getByLoginName(new User(null, loginName));
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    *//**
     * 清除当前用户缓存
     *//*
    public static void clearCache() {
        AuthUtils.removeCache(CACHE_ROLE_LIST);
        AuthUtils.removeCache(CACHE_MENU_LIST);
        AuthUtils.removeCache(CACHE_AREA_LIST);
        AuthUtils.removeCache(CACHE_OFFICE_LIST);
        AuthUtils.removeCache(CACHE_OFFICE_ALL_LIST);
        UserUtils.clearCache(getUser());
    }

    *//**
     * 清除指定用户缓存
     *
     * @param user
     *//*
    public static void clearCache(User user) {
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
        if (user.getOffice() != null && user.getOffice().getId() != null) {
            CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
        }
    }

    *//**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     *//*
    public static SysUser getUser() {
        SystemAuthorizingRealm.Principal principal = AuthUtils.getPrincipal();
        if (principal != null) {
            SysUser user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new SysUser();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new SysUser();
    }

    *//**
     * 获取当前用户角色列表
     *
     * @return
     *//*
    public static List<SysRole> getRoleList() {
        @SuppressWarnings("unchecked")
        List<SysRole> roleList = (List<SysRole>) getCache(CACHE_ROLE_LIST);
        if (roleList == null) {
            SysUser user = getUser();
            if (user.isAdmin()) {
                roleList = roleDao.findAllList(new SysRole());
            } else {
                SysRole role = new SysRole();
                // XXX 数据过滤
                //role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
                roleList = roleDao.findList(role);
            }
            AuthUtils.putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }

    *//**
     * 获取当前用户授权菜单
     *
     * @return
     *//*
    public static List<SysMenu> getMenuList() {
        @SuppressWarnings("unchecked")
        List<SysMenu> menuList = (List<SysMenu>) getCache(CACHE_MENU_LIST);
        if (menuList == null) {
            SysUser user = getUser();
            if (user.isAdmin()) {
                menuList = menuDao.findAllList(new SysMenu());
            } else {
                SysMenu m = new SysMenu();
                m.setUserId(user.getId());
                menuList = menuDao.findByUserId(m);
            }
            AuthUtils.putCache(CACHE_MENU_LIST, menuList);
        }
        return menuList;
    }

    *//**
     * 获取当前用户授权的区域
     *
     * @return
     *//*
    public static List<SysArea> getAreaList() {
        @SuppressWarnings("unchecked")
        List<SysArea> areaList = (List<SysArea>) getCache(CACHE_AREA_LIST);
        if (areaList == null) {
            areaList = areaDao.findAllList(new SysArea());
            AuthUtils.putCache(CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }

    *//**
     * 获取当前用户有权限访问的部门
     *
     * @return
     *//*
    public static List<SysOffice> getOfficeList() {
        @SuppressWarnings("unchecked")
        List<SysOffice> officeList = (List<SysOffice>) getCache(CACHE_OFFICE_LIST);
        if (officeList == null) {
            SysUser user = getUser();
            if (user.isAdmin()) {
                officeList = officeDao.findAllList(new SysOffice());
            } else {
                SysOffice office = new SysOffice();
                // XXX 数据过滤
                //office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
                officeList = officeDao.findList(office);
            }
            AuthUtils.putCache(CACHE_OFFICE_LIST, officeList);
        }
        return officeList;
    }

    *//**
     * 获取当前用户有权限访问的部门
     *
     * @return
     *//*
    public static List<SysOffice> getOfficeAllList() {
        @SuppressWarnings("unchecked")
        List<SysOffice> officeList = (List<SysOffice>) getCache(CACHE_OFFICE_ALL_LIST);
        if (officeList == null) {
            officeList = officeDao.findAllList(new SysOffice());
        }
        return officeList;
    }*/
}
