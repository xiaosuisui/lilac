package io.github.isliqian.parameter.entity;

import io.github.isliqian.utils.base.DataEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ParametersBean extends DataEntity<ParametersBean> {
    private String label;
    private String type;
    private String bond;
    private String value;
    private String valueType;
    private String displayType;
    private String valueRange;
    private String canDel;
    private String description;
    private String nowApply;
    private int sort;
    private Map<String,String> valueRangeMap=new HashMap<String,String>();

    public ParametersBean(){
    }

    public ParametersBean(String id){
        super(id);
    }


}
