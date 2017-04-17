package main;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Sendmail {

     public static void main(String[] args) throws Exception {
    	 
           Properties prop = new Properties();
           prop.setProperty("mail.host", "smtp.qiye.163.com");
           prop.setProperty("mail.transport.protocol", "smtp");//简单邮件传输协议
           prop.setProperty("mail.smtp.auth", "true");
           //使用JavaMail发送邮件的 个步骤
           // 、创建session
           Session session = Session.getInstance(prop);
          //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
           session.setDebug(true);
           // 、通过session得到transport对象
           Transport ts = session.getTransport();
           // 、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
           ts.connect("smtp.qiye.163.com", "lsx@ultimobi.com", "woaiwojia405");
           // 、创建邮件  包含发送者 接收者 主题 内容 
          // Message message = createSimpleMail(session);
          // Message message = createImageMail(session);
           Message message = createMixedMail(session);
           
           // 、发送邮件
           ts.sendMessage(message, message.getAllRecipients());
           //关闭连接
           ts.close();
     }
     
     /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     * @Anthor:孤傲苍狼

     */ 
       public static MimeMessage createSimpleMail(Session session)
               throws Exception {
           //创建邮件对象
           MimeMessage message = new MimeMessage(session);
           //指明邮件的发件人
           message.setFrom(new InternetAddress("lsx@ultimobi.com"));
           //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
           message.setRecipient(Message.RecipientType.TO, new InternetAddress("1014426766@qq.com"));
           //邮件的标题
           message.setSubject("只包含文本的简单邮件");
           //邮件的文本内容
           message.setContent("你好啊！", "text/html;charset=UTF-8");
           //返回创建好的邮件对象
           return message;
       }
       //com.sun.mail.smtp.SMTPSendFailedException: [EOF]
       public static MimeMessage createImageMail(Session session) throws Exception {
	            //创建邮件
	            MimeMessage message = new MimeMessage(session);
	            // 设置邮件的基本信息
	            //发件人
	            message.setFrom(new InternetAddress("lsx@ultimobi.com"));
	            //收件人
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress("1014426766@qq.com"));
	            //邮件标题
	            message.setSubject("带图片的邮件");
	    
	            // 准备邮件数据
	            // 准备邮件正文数据
	            MimeBodyPart text = new MimeBodyPart();
	            text.setContent("这是一封邮件正文带图片<img src='cid:xxx.jpg'>的邮件", "text/html;charset=UTF-8");
	            // 准备图片数据
	            MimeBodyPart image = new MimeBodyPart();
	            DataHandler dh = new DataHandler(new FileDataSource("src\\1.jpg"));
	            image.setDataHandler(dh);
	            image.setContentID("xxx.jpg");
	            // 描述数据关系
	            MimeMultipart mm = new MimeMultipart();
	            mm.addBodyPart(text);
	            mm.addBodyPart(image);
	            mm.setSubType("related");
	    
	            message.setContent(mm);//这次的content是个对象：含有图片和文字
	            message.saveChanges();
	            //将创建好的邮件写入到E盘以文件的形式进行保存
	            message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
	            //返回创建好的邮件
	            return message;
       }
       public static MimeMessage createMixedMail(Session session) throws Exception {
    	            //创建邮件
    	            MimeMessage message = new MimeMessage(session);
    	            
    	            //设置邮件的基本信息
    	            message.setFrom(new InternetAddress("lsx@ultimobi.com"));
    	            message.setRecipient(Message.RecipientType.TO, new InternetAddress("1014426766@qq.com"));
    	            message.setSubject("带附件和带图片的的邮件");
    	            
    	            //正文
    	            MimeBodyPart text = new MimeBodyPart();
    	            text.setContent("xxx这是女的xxxx<br/><img src='cid:aaa.jpg'>","text/html;charset=UTF-8");
    	            
    	            //图片
    	            MimeBodyPart image = new MimeBodyPart();
    	            image.setDataHandler(new DataHandler(new FileDataSource("src\\3.jpg")));
    	            image.setContentID("aaa.jpg");
    	            
    	            //附件1
    	            MimeBodyPart attach = new MimeBodyPart();
    	            DataHandler dh = new DataHandler(new FileDataSource("src\\4.jar"));
    	            attach.setDataHandler(dh);
    	            attach.setFileName(dh.getName());
    	            
    	            //附件2
    	            MimeBodyPart attach2 = new MimeBodyPart();
    	            DataHandler dh2 = new DataHandler(new FileDataSource("src\\ofbiz.zip"));
    	            attach2.setDataHandler(dh2);
    	            //名字为中文的时候需要处理
    	            attach2.setFileName(MimeUtility.encodeText(dh2.getName()));
    	            
    	            //描述关系:正文和图片
    	            MimeMultipart mp1 = new MimeMultipart();
    	            mp1.addBodyPart(text);
    	            mp1.addBodyPart(image);
    	            mp1.setSubType("related");
    	            
    	          //代表正文的bodypart
     	           MimeBodyPart content = new MimeBodyPart();
     	           content.setContent(mp1);
    	            
    	            //描述关系:正文和附件
    	            MimeMultipart mp2 = new MimeMultipart();
    	            mp2.addBodyPart(attach);
    	            mp2.addBodyPart(attach2);
    	           
    	           
    	           mp2.addBodyPart(content);
    	           mp2.setSubType("mixed");
    	           
    	           message.setContent(mp2);
    	           message.saveChanges();
    	           
    	           message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
    	           //返回创建好的的邮件
    	           return message;
    	       }
   }
/**
 * DEBUG: setDebug: JavaMail version 1.5.6
DEBUG: getProvider() returning javax.mail.Provider[TRANSPORT,smtp,com.sun.mail.smtp.SMTPTransport,Oracle]
DEBUG SMTP: useEhlo true, useAuth true
DEBUG SMTP: trying to connect to host "smtp.qiye.163.com", port 25, isSSL false
220 qiye.163.com Anti-spam GT for Coremail System (163-hosting[20140416])
DEBUG SMTP: connected to host "smtp.qiye.163.com", port: 25

EHLO lsx
250-mail
250-PIPELINING
250-AUTH LOGIN PLAIN
250-AUTH=LOGIN PLAIN
250-coremail 1Uxr2xKj7kG0xkI17xGrUDI0s8FY2U3Uj8Cz28x1UUUUU7Ic2I0Y2UrnaNs0UCa0xDrUUUUj
250-STARTTLS
250 8BITMIME
DEBUG SMTP: Found extension "PIPELINING", arg ""
DEBUG SMTP: Found extension "AUTH", arg "LOGIN PLAIN"
DEBUG SMTP: Found extension "AUTH=LOGIN", arg "PLAIN"
DEBUG SMTP: Found extension "coremail", arg "1Uxr2xKj7kG0xkI17xGrUDI0s8FY2U3Uj8Cz28x1UUUUU7Ic2I0Y2UrnaNs0UCa0xDrUUUUj"
DEBUG SMTP: Found extension "STARTTLS", arg ""
DEBUG SMTP: Found extension "8BITMIME", arg ""
DEBUG SMTP: protocolConnect login, host=smtp.qiye.163.com, user=lsx@ultimobi.com, password=<non-null>
DEBUG SMTP: Attempt to authenticate using mechanisms: LOGIN PLAIN DIGEST-MD5 NTLM XOAUTH2 
DEBUG SMTP: Using mechanism LOGIN
DEBUG SMTP: AUTH LOGIN command trace suppressed
DEBUG SMTP: AUTH LOGIN succeeded
DEBUG SMTP: use8bit false
MAIL FROM:<lsx@ultimobi.com>
250 Mail OK
RCPT TO:<1014426766@qq.com>
250 Mail OK
DEBUG SMTP: Verified Addresses
DEBUG SMTP:   1014426766@qq.com
DATA
354 End data with <CR><LF>.<CR><LF>
From: lsx@ultimobi.com
To: 1014426766@qq.com
Message-ID: <664687665.0.1474705096936@smtp.qiye.163.com>
Subject: =?GBK?B?1ruw/LqszsSxvrXEvPK1pdPKvP4=?=
MIME-Version: 1.0
Content-Type: text/html;charset=UTF-8
Content-Transfer-Encoding: base64

5L2g5aW95ZWK77yB
.
250 Mail OK queued as smtp5,huCowAC3nTLENuZXEvP2Bg--.6930S2 1474705100
DEBUG SMTP: message successfully delivered to mail server
QUIT
221 Bye
 */
