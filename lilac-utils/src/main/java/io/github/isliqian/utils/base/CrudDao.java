package io.github.isliqian.utils.base;

import java.util.List;

public interface CrudDao<T> extends BaseDao {
    T get(String var1);

    T get(T var1);

    List<T> findList(T var1);

    List<T> findAllList(T var1);

    /** @deprecated */
    @Deprecated
    List<T> findAllList();

    int insert(T var1);

    int update(T var1);

    /** @deprecated */
    @Deprecated
    int delete(String var1);

    int delete(T var1);

    int insertList(List<T> var1);

    int updateList(List<T> var1);

    int deleteList(List<T> var1);
}