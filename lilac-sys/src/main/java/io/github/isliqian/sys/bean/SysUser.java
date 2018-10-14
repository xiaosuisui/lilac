package io.github.isliqian.sys.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by LiQian_Nice on 2018/6/9
 */
@Data
public class SysUser implements Serializable{

    private String id;

    @NotEmpty
    private String username;

    private String password;


}
