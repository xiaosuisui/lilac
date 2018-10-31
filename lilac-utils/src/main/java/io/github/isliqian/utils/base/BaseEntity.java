package io.github.isliqian.utils.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import io.github.isliqian.utils.Global;
import io.github.isliqian.utils.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Map;

public abstract class BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected Page<T> page;
    protected Map<String, String> sqlMap;
    protected boolean isNewRecord;
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";

    public BaseEntity() {
        this.isNewRecord = false;
    }

    public BaseEntity(String id) {
        this();
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    @XmlTransient
    public Page<T> getPage() {
        if (this.page == null) {
            this.page = new Page();
        }

        return this.page;
    }

    public Page<T> setPage(Page<T> page) {
        this.page = page;
        return page;
    }

    @JsonIgnore
    @XmlTransient
    public Map<String, String> getSqlMap() {
        if (this.sqlMap == null) {
            this.sqlMap = Maps.newHashMap();
        }

        return this.sqlMap;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }


    public boolean getIsNewRecord() {
        return this.isNewRecord || StringUtils.isBlank(this.getId());
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    @JsonIgnore
    public Global getGlobal() {
        return Global.getInstance();
    }

    @JsonIgnore
    public String getDbName() {
        return Global.getConfig("jdbc.type");
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!this.getClass().equals(obj.getClass())) {
            return false;
        } else {
            BaseEntity<?> that = (BaseEntity) obj;
            return null != this.getId() && this.getId().equals(that.getId());
        }
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}