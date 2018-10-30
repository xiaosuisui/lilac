package io.github.isliqian.mail.bean;

import lombok.Data;

/**
 * @author wxt.liqian
 * @version 2018/10/30
 * @desc 邮件模板
 */
@Data
public class MailTemple  {

    String subject; //主题

    String to; //收件人

    String content; //内容

    String filePath;//附件上传

    String rscId;

    String imgPath;


}
