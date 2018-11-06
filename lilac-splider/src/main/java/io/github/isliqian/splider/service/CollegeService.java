package io.github.isliqian.splider.service;

import io.github.isliqian.core.CrudService;
import io.github.isliqian.splider.bean.BasicCollege;
import io.github.isliqian.splider.mapper.CollegeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CollegeService extends CrudService<CollegeMapper, BasicCollege> {

    public List<BasicCollege> findAll(){
        return dao.findAllList();
    }

    @Transactional(readOnly = false)
    public void save(BasicCollege basicCollege) {
        super.save(basicCollege);
    }

    @Transactional(readOnly = false)
    public void delete(BasicCollege basicCollege) {
        super.delete(basicCollege);
    }
}
