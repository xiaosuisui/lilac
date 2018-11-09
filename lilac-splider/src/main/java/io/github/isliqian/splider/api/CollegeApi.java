package io.github.isliqian.splider.api;

import io.github.isliqian.splider.bean.College;
import io.github.isliqian.splider.mapper.CollegeMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("college")
public class CollegeApi {
    @Resource
    private CollegeMapper collegeMapper;
    @GetMapping("/")
    public List<College> list(){
        return collegeMapper.findList(new College());
    }
}
