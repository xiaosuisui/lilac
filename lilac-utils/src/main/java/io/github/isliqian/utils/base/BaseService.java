package io.github.isliqian.utils.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(
        readOnly = true
)
public abstract class BaseService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseService() {
    }

    public static String dataScopeFilter(String officeAlias, String userAlias) {
        new StringBuilder();
        return "";
    }

    public static void dataScopeFilter(BaseEntity<?> entity, String sqlMapKey, String officeWheres, String userWheres) {
    }
}
