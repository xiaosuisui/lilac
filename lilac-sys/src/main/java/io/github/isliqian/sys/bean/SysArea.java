
package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;
/**
 * 区域Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class SysArea extends DataEntity<SysArea> {

	private static final long serialVersionUID = 1L;
	private SysArea parent;	// 父级编号
	private String parentIds; // 所有父级编号
	private String code; 	// 区域编码
	private String name; 	// 区域名称
	private Integer sort;		// 排序
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）

	private SysUser updateBy;
	private SysUser createBy;

	@Override
	public void doPreInsert() {

	}

	@Override
	public void doPreUpdate() {

	}

	public SysArea getParent() {
		return parent;
	}

	public void setParent(SysArea parent) {
		this.parent = parent;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SysUser getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(SysUser updateBy) {
		this.updateBy = updateBy;
	}

	public SysUser getCreateBy() {
		return createBy;
	}

	public void setCreateBy(SysUser createBy) {
		this.createBy = createBy;
	}
}