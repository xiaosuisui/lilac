package io.github.isliqian.sys.utils;

import java.util.Iterator;
import java.util.List;

import io.github.isliqian.utils.Reflections;
import io.github.isliqian.utils.StringUtils;
import io.github.isliqian.utils.base.ServiceException;
import io.github.isliqian.utils.base.TreeDao;
import io.github.isliqian.utils.base.TreeEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional(
        readOnly = true
)
public abstract class TreeService<D extends TreeDao<T>, T extends TreeEntity<T>> extends CrudService<D, T> {
    public TreeService() {
    }

    @Transactional(
            readOnly = false
    )
    public void save(T entity) {
        Class<T> entityClass = Reflections.getClassGenricType(this.getClass(), 1);
        if (entity.getParent() != null && !StringUtils.isBlank(entity.getParentId()) && !"0".equals(entity.getParentId())) {
            entity.setParent(super.get(entity.getParentId()));
        } else {
            entity.setParent((T) null);
        }

        if (entity.getParent() == null) {
            TreeEntity parentEntity = null;

            try {
                parentEntity = (TreeEntity)entityClass.getConstructor(String.class).newInstance("0");
            } catch (Exception var9) {
                throw new ServiceException(var9);
            }

            entity.setParent((T) parentEntity);
            ((TreeEntity)entity.getParent()).setParentIds("");
        }

        String oldParentIds = entity.getParentIds();
        entity.setParentIds(((TreeEntity)entity.getParent()).getParentIds() + ((TreeEntity)entity.getParent()).getId() + ",");
        super.save(entity);
        TreeEntity o = null;

        try {
            o = (TreeEntity)entityClass.newInstance();
        } catch (Exception var8) {
            throw new ServiceException(var8);
        }

        o.setParentIds("%," + entity.getId() + ",%");
        List<T> list = ((TreeDao)this.dao).findByParentIdsLike(o);
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            T e = (T) i$.next();
            if (e.getParentIds() != null && oldParentIds != null) {
                e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
                this.preUpdateChild(entity, e);
                ((TreeDao)this.dao).updateParentIds(e);
            }
        }

    }

    protected void preUpdateChild(T entity, T childEntity) {
    }
}
