package io.github.isliqian.log.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.*;
/**
 * 系统日志记录类
 */
@Data
public class SysLog implements Serializable {

    private String id;

    private String username; //用户名

    private String operation; //操作

    private String method; //方法名

    private String params; //参数

    private String ip; //ip地址

    private Date createDate; //操作时间
}
