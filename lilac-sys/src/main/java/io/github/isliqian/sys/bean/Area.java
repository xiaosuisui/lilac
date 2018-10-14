
package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;
/**
 * 区域Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Area extends DataEntity<Area> {

	private static final long serialVersionUID = 1L;
//	private Area parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	private String code; 	// 区域编码
//	private String name; 	// 区域名称
//	private Integer sort;		// 排序
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）

	private SysUser updateBy;
	private SysUser createBy;

	@Override
	public void doPreInsert() {

	}

	@Override
	public void doPreUpdate() {

	}
}