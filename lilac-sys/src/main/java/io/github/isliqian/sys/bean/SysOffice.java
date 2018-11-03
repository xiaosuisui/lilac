package io.github.isliqian.sys.bean;

import io.github.isliqian.core.DataEntity;
import lombok.Data;


/**
 * 职位
 */
@Data
public class SysOffice extends DataEntity<SysOffice> {


    private static final long serialVersionUID = 1L;
    private SysArea area;		// 归属区域
    private String code; 	// 机构编码
    private String name; 	// 机构名称
	private Integer sort;		// 排序
//    private String type; 	// 机构类型（1：公司；2：部门；3：小组）
//    private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
    private String address; // 联系地址
    private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱



}
