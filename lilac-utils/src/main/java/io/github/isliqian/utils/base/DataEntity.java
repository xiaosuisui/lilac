package io.github.isliqian.utils.base;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

public abstract class DataEntity<T> extends BaseEntity<T> {
    private static final long serialVersionUID = 1L;
    protected String remarks;
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    protected Date createDate;
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    protected Date updateDate;
    protected String delFlag;

    public DataEntity() {
        this.delFlag = "0";
    }

    public DataEntity(String id) {
        super(id);
    }

    @Length(
            min = 0,
            max = 255
    )
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @JsonIgnore
    @Length(
            min = 1,
            max = 1
    )
    public String getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


}
