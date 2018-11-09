package io.github.isliqian.splider.service;

import io.github.isliqian.core.CrudService;
import io.github.isliqian.splider.bean.College;
import io.github.isliqian.splider.mapper.CollegeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CollegeService extends CrudService<CollegeMapper, College> {

    public List<College> findAll(){
        return dao.findAllList();
    }

    @Transactional(readOnly = false)
    public void save(College college) {
        super.save(college);
    }

    @Transactional(readOnly = false)
    public void delete(College college) {
        super.delete(college);
    }
}
