package io.github.isliqian.sys.aspect;

import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.log.bean.SysLog;

import io.github.isliqian.log.service.SysLogService;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.utils.IDUtils;
import io.github.isliqian.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private RedisService redisService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( io.github.isliqian.log.ann.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        SysUser sysUser = (SysUser) redisService.get("user");
        if (sysUser !=null){

            SysLog sysLog = new SysLog();

            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();

            //获取操作
            MyLog myLog = method.getAnnotation(MyLog.class);
            if (myLog != null) {
                String value = myLog.value();
                sysLog.setOperation(value);//保存获取的操作
            }
            sysLog.setId(IDUtils.getId());
            //获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = method.getName();
            sysLog.setMethod(className + "." + methodName);
            sysLog.setCreateDate(new Date());
            //获取用户名
            sysLog.setUsername(sysUser.getLoginName());
            //获取用户ip地址

            sysLog.setIp(IPUtils.INTERNET_IP);
            //调用service保存SysLog实体类到数据库
            sysLogService.save(sysLog);
            logger.info("执行保存用户操作成功......");
        }


//        System.out.println("切面。。。。。");
        //保存日志

    }
}
