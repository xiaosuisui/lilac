package io.github.isliqian.mail.controller;
import io.github.isliqian.mail.service.MailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author wxt.liqian
 * @version 2018/10/26
 * @desc 发送邮件
 */
@Controller
@RequestMapping("/sys/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    /*注入模板引擎*/
    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/")
    @ApiOperation(value="跳转到发送邮件界面")
    public ModelAndView findAll(ModelAndView modelAndView){
        sendTemplateMail();
        modelAndView.setViewName("/sys/email.html");
        return modelAndView;
    }
    /**
     * @author wxt.liqian
     * @version 2018/10/26
     * @desc 发送简单邮件
     */
    public void sendSimpleMail() throws Exception {
        mailService.sendSimpleEmail("821300801@qq.com","this is simple mail"," hello LingDu");
    }

    /**
     * @author wxt.liqian
     * @version 2018/10/26
     * @desc 发送带html邮件
     */
    public void sendHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlEmail("821300801@qq.com","this is html mail",content);
    }

    /**
     * @author wxt.liqian
     * @version 2018/10/26
     * @desc 发送带附件邮件
     */
    public void sendAttachmentsMail() {
        String filePath="f:\\pikaqiu.png";
        mailService.sendAttachmentsEmail("821300801@qq.com", "主题：带附件的邮件", "收到附件，请查收！", filePath);

    }

    /**
     * @author wxt.liqian
     * @version 2018/10/26
     * @desc 发送带静态资源邮件
     */
    public void sendInlineResourceMail() {
        String rscId = "001";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "f:\\pikaqiu.png";

        mailService.sendInlineResourceEmail("821300801@qq.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }


    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("username", "LingDu");
        String emailContent = templateEngine.process("email/emailExample1", context);

        System.out.println(emailContent);
        mailService.sendHtmlEmail("51103942@qq.com","主题：这是模板邮件",emailContent);
    }

}
