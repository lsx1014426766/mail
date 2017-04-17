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
           prop.setProperty("mail.transport.protocol", "smtp");//���ʼ�����Э��
           prop.setProperty("mail.smtp.auth", "true");
           //ʹ��JavaMail�����ʼ��� ������
           // ������session
           Session session = Session.getInstance(prop);
          //����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
           session.setDebug(true);
           // ��ͨ��session�õ�transport����
           Transport ts = session.getTransport();
           // ��ʹ��������û��������������ʼ��������������ʼ�ʱ����������Ҫ�ύ������û����������smtp���������û��������붼ͨ����֤֮����ܹ����������ʼ����ռ��ˡ�
           ts.connect("smtp.qiye.163.com", "lsx@ultimobi.com", "woaiwojia405");
           // �������ʼ�  ���������� ������ ���� ���� 
          // Message message = createSimpleMail(session);
          // Message message = createImageMail(session);
           Message message = createMixedMail(session);
           
           // �������ʼ�
           ts.sendMessage(message, message.getAllRecipients());
           //�ر�����
           ts.close();
     }
     
     /**
     * @Method: createSimpleMail
     * @Description: ����һ��ֻ�����ı����ʼ�
     * @Anthor:�°�����

     */ 
       public static MimeMessage createSimpleMail(Session session)
               throws Exception {
           //�����ʼ�����
           MimeMessage message = new MimeMessage(session);
           //ָ���ʼ��ķ�����
           message.setFrom(new InternetAddress("lsx@ultimobi.com"));
           //ָ���ʼ����ռ��ˣ����ڷ����˺��ռ�����һ���ģ��Ǿ����Լ����Լ���
           message.setRecipient(Message.RecipientType.TO, new InternetAddress("1014426766@qq.com"));
           //�ʼ��ı���
           message.setSubject("ֻ�����ı��ļ��ʼ�");
           //�ʼ����ı�����
           message.setContent("��ð���", "text/html;charset=UTF-8");
           //���ش����õ��ʼ�����
           return message;
       }
       //com.sun.mail.smtp.SMTPSendFailedException: [EOF]
       public static MimeMessage createImageMail(Session session) throws Exception {
	            //�����ʼ�
	            MimeMessage message = new MimeMessage(session);
	            // �����ʼ��Ļ�����Ϣ
	            //������
	            message.setFrom(new InternetAddress("lsx@ultimobi.com"));
	            //�ռ���
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress("1014426766@qq.com"));
	            //�ʼ�����
	            message.setSubject("��ͼƬ���ʼ�");
	    
	            // ׼���ʼ�����
	            // ׼���ʼ���������
	            MimeBodyPart text = new MimeBodyPart();
	            text.setContent("����һ���ʼ����Ĵ�ͼƬ<img src='cid:xxx.jpg'>���ʼ�", "text/html;charset=UTF-8");
	            // ׼��ͼƬ����
	            MimeBodyPart image = new MimeBodyPart();
	            DataHandler dh = new DataHandler(new FileDataSource("src\\1.jpg"));
	            image.setDataHandler(dh);
	            image.setContentID("xxx.jpg");
	            // �������ݹ�ϵ
	            MimeMultipart mm = new MimeMultipart();
	            mm.addBodyPart(text);
	            mm.addBodyPart(image);
	            mm.setSubType("related");
	    
	            message.setContent(mm);//��ε�content�Ǹ����󣺺���ͼƬ������
	            message.saveChanges();
	            //�������õ��ʼ�д�뵽E�����ļ�����ʽ���б���
	            message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
	            //���ش����õ��ʼ�
	            return message;
       }
       public static MimeMessage createMixedMail(Session session) throws Exception {
    	            //�����ʼ�
    	            MimeMessage message = new MimeMessage(session);
    	            
    	            //�����ʼ��Ļ�����Ϣ
    	            message.setFrom(new InternetAddress("lsx@ultimobi.com"));
    	            message.setRecipient(Message.RecipientType.TO, new InternetAddress("1014426766@qq.com"));
    	            message.setSubject("�������ʹ�ͼƬ�ĵ��ʼ�");
    	            
    	            //����
    	            MimeBodyPart text = new MimeBodyPart();
    	            text.setContent("xxx����Ů��xxxx<br/><img src='cid:aaa.jpg'>","text/html;charset=UTF-8");
    	            
    	            //ͼƬ
    	            MimeBodyPart image = new MimeBodyPart();
    	            image.setDataHandler(new DataHandler(new FileDataSource("src\\3.jpg")));
    	            image.setContentID("aaa.jpg");
    	            
    	            //����1
    	            MimeBodyPart attach = new MimeBodyPart();
    	            DataHandler dh = new DataHandler(new FileDataSource("src\\4.jar"));
    	            attach.setDataHandler(dh);
    	            attach.setFileName(dh.getName());
    	            
    	            //����2
    	            MimeBodyPart attach2 = new MimeBodyPart();
    	            DataHandler dh2 = new DataHandler(new FileDataSource("src\\ofbiz.zip"));
    	            attach2.setDataHandler(dh2);
    	            //����Ϊ���ĵ�ʱ����Ҫ����
    	            attach2.setFileName(MimeUtility.encodeText(dh2.getName()));
    	            
    	            //������ϵ:���ĺ�ͼƬ
    	            MimeMultipart mp1 = new MimeMultipart();
    	            mp1.addBodyPart(text);
    	            mp1.addBodyPart(image);
    	            mp1.setSubType("related");
    	            
    	          //�������ĵ�bodypart
     	           MimeBodyPart content = new MimeBodyPart();
     	           content.setContent(mp1);
    	            
    	            //������ϵ:���ĺ͸���
    	            MimeMultipart mp2 = new MimeMultipart();
    	            mp2.addBodyPart(attach);
    	            mp2.addBodyPart(attach2);
    	           
    	           
    	           mp2.addBodyPart(content);
    	           mp2.setSubType("mixed");
    	           
    	           message.setContent(mp2);
    	           message.saveChanges();
    	           
    	           message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
    	           //���ش����õĵ��ʼ�
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
