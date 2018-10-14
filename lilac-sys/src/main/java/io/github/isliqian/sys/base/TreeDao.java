package io.github.isliqian.sys.base;

import java.util.List;

public interface TreeDao<T extends TreeEntity<T>> extends CrudDao<T> {
    List<T> findByParentIdsLike(T var1);

    int updateParentIds(T var1);
}