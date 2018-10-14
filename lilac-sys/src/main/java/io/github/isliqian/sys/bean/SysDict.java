package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;



/**
 * 字典表
 */
public class SysDict extends DataEntity<SysDict> {
    private static final long serialVersionUID = 1L;
    private String value;	// 数据值
    private String label;	// 标签名
    private String type;	// 类型
    private String description;// 描述
    private Integer sort;	// 排序
    private String parentId;//父Id
    private SysUser updateBy;
    private SysUser createBy;

    public SysDict() {
        super();
    }

    public SysDict(String id){
        super(id);
    }

    public SysDict(String value, String label){
        this.value = value;
        this.label = label;
    }
    @Override
    public void doPreInsert() {

    }

    @Override
    public void doPreUpdate() {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
