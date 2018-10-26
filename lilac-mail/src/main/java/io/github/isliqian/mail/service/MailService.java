package io.github.isliqian.mail.service;

/**
 * @author wxt.liqian
 * @version 2018/10/26
 * @desc 发送邮件服务
 */
public interface MailService { /**
 * 发送简单邮件
 * @param to
 * @param subject
 * @param content
 */
public void sendSimpleEmail(String to, String subject, String content);
    /**
     * 发送html格式邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlEmail(String to, String subject, String content);
    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsEmail(String to, String subject, String content, String filePath);
    /**
     * 发送带静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendInlineResourceEmail(String to, String subject, String content, String rscPath, String rscId);
}