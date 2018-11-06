package io.github.isliqian.splider.service;

import io.github.isliqian.core.CrudService;
import io.github.isliqian.splider.bean.HistoricalLine;
import io.github.isliqian.splider.mapper.HistoricalLineMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HistoricalLineService extends CrudService<HistoricalLineMapper, HistoricalLine> {


    public List<HistoricalLine> findAll(){
        return dao.findAllList();
    }

    @Transactional(readOnly = false)
    public void save(HistoricalLine historicalLine) {
        super.save(historicalLine);
    }

    @Transactional(readOnly = false)
    public void delete(HistoricalLine historicalLine) {
        super.delete(historicalLine);
    }
}
