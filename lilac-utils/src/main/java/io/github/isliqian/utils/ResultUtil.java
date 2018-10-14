package io.github.isliqian.utils;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResultUtil {

    private int code;

    private String msg;

    private Object data;

    public ResultUtil(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultUtil success(){
        return new ResultUtil(200,"success",null);
    }


    public static ResultUtil success(Object data){
        return new ResultUtil(200,"success",data);
    }

    public static ResultUtil error(int code,String msg){
        return new ResultUtil(code,msg,null);
    }
    public static ResultUtil error(int code,String msg,Object data){
        return new ResultUtil(code,msg,data);
    }

    public static ResultUtil badRequest(){
        return new ResultUtil(400,"bad request",null);
    }

    public static ResultUtil internalServerError(){
        return new ResultUtil(500,"internatl server error",null);
    }


}
