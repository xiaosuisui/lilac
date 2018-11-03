package io.github.isliqian.splider.bean;


import io.github.isliqian.core.DataEntity;
import lombok.Data;


/**
 * @author wxt.liqian
 * @version 2018/8/3
 * 学校类
 */
@Data
public class BasicCollege extends DataEntity<BasicCollege> {


    private String  name; //名称

    private String location;//所在地

    private String baikeUrl;//百度百科学校详情网址

    private String gaokaoUrl;//阳光高考学校详情网址

    private String subject;//隶属

    private String type;//类型

    private String level;//学历层次

    private String feature;//特性

    private String postgraduate;//是否是研究生院

    private String satisfaction;//满意度


    private String ext1;//扩展字段1

    private String ext2;//扩展字段2

    private String ext3;//扩展字段3



}
