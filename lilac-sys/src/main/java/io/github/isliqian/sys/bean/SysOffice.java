package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.TreeEntity;

import java.util.List;


/**
 * 职位
 */
public class SysOffice extends TreeEntity<SysOffice> {


    private static final long serialVersionUID = 1L;
    //	private Office parent;	// 父级编号
//	private String parentIds; // 所有父级编号
    private SysArea area;		// 归属区域
    private String code; 	// 机构编码
    //	private String name; 	// 机构名称
//	private Integer sort;		// 排序
    private String type; 	// 机构类型（1：公司；2：部门；3：小组）
    private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
    private String address; // 联系地址
    //	private String zipCode; // 邮政编码
//	private String master; 	// 负责人
//	private String phone; 	// 电话
//	private String fax; 	// 传真
//	private String email; 	// 邮箱
    private String useable;//是否可用
    private SysUser primaryPerson;//主负责人
    private SysUser deputyPerson;//副负责人
    private List<String> childDeptList;//快速添加子部门
    protected SysUser createBy;	// 创建者
    protected SysUser updateBy;	// 更新者
    private String developers;  // 开发商
    private String buildingYear;  // 建筑年代
    private String buildingType;  // 建筑类型
    private Integer buildingNumber;  // 楼栋数
    private String volumeRatio;  // 容积率
    private String greenRatio;  // 绿化率
    private String emailPassword;  // 邮箱账户授权码
    private String emailUsername;  // 邮箱账户名
    private String aesKey;  // AES秘钥
    private String appId;  // 工行appid
    private String merId;  // 工行提供的商户id
    private String emailPopport;  // POP服务器地址端口
    private String publicKey;  // 网关公钥
    private String privateKey;  // 密钥对认证私钥
    private String emailPophost;  // POP服务器地址
    private Integer isautoFee;	//是否每月自动生成
    private String emailTo;		//社区对账收件邮箱



    @Override
    public void doPreInsert() {

    }

    @Override
    public void doPreUpdate() {

    }

    @Override
    public SysOffice getParent() {
        return null;
    }

    @Override
    public void setParent(SysOffice var1) {

    }
}
