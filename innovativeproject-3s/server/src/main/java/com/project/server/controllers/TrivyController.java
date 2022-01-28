package com.project.server.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.project.server.model.Scan;
import com.project.server.model.ScanData;
import com.project.server.repository.ScanRepository;
import com.project.server.services.ScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

public class TrivyController {
    private static final Logger logger = LoggerFactory.getLogger(TrivyController.class);

    @Autowired
    ScanService scanService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;

    @PostMapping("/trivy")
    public void privRepoauthentication( @RequestBody ScanData scandata ) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        String authentication = "-e TRIVY_AUTH_URL=https://registry.hub.docker.com -e TRIVY_USERNAME="+ scandata.getUsername() +" -e TRIVY_PASSWORD=" + scandata.getPassword();
        String cmd4="docker run --rm " +  (scandata.getisPrivate().equals("true") ? authentication : "")  +  " aquasec/trivy -f json --light -q "+ scandata.getName();
        Process proc4 = Runtime.getRuntime().exec(cmd4);

        BufferedReader stdInput4 = new BufferedReader(new 
        InputStreamReader(proc4.getInputStream()));    
        String s4="";
        StringBuilder sb=new StringBuilder();
        while ((s4 = stdInput4.readLine()) != null) {
            System.out.println(s4);
            sb.append(s4);
        }
        
        sendMail(mailProperties.getUsername(), scandata.getEmail(),"scan", sb.toString());

        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(date);

        System.out.println(formattedDate);

        scanService.createScanAndAdd(formattedDate, "positive", scandata.getLogin(), scandata.getEmail(), scandata.getToolName(), scandata.getProjectName(), sb.toString().replaceAll("\\s",""));
    }


  private void sendMail(String fromEmail, String toEmail, String subject, String body) {
                   try {
                       logger.info("Sending Email to {}", toEmail);
                       MimeMessage message = mailSender.createMimeMessage();
                       logger.info("Sending Email to {}", toEmail);

                       MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
                       logger.info("Sending Email to {}", toEmail);

                       messageHelper.setSubject(subject);
                       messageHelper.setText(body, false);
                       messageHelper.setFrom(fromEmail);
                       messageHelper.setTo(toEmail);
            
                       mailSender.send(message); 
                   } catch (MessagingException ex) {
                       logger.error("Failed to send email to {}", toEmail);
                   }
               }
 
}
