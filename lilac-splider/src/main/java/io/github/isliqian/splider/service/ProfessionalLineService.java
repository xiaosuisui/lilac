package io.github.isliqian.splider.service;


import io.github.isliqian.core.CrudService;
import io.github.isliqian.splider.bean.ProfessionalLine;
import io.github.isliqian.splider.mapper.ProfessionalLineMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 各专业录取分数线
 */
@Service
@Transactional(readOnly = false)
public class ProfessionalLineService extends CrudService<ProfessionalLineMapper,ProfessionalLine> {

    public List<ProfessionalLine> findAll(){
        return dao.findAllList();
    }

    @Transactional(readOnly = false)
    public void save(ProfessionalLine professionalLine) {
        super.save(professionalLine);
    }

    @Transactional(readOnly = false)
    public void delete(ProfessionalLine professionalLine) {
        super.delete(professionalLine);
    }

}
