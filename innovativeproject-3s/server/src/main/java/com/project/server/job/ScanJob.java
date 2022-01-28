package com.project.server.job;

import org.springframework.boot.CommandLineRunner;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import com.project.server.repository.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

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

@Component
public class ScanJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(ScanJob.class);

    @Autowired
    ScanService scanService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;
    @Autowired
    public ScanRepository scanRepo;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        String s4 = "";
        StringBuilder sb = new StringBuilder();
        try {
            String authentication = "-e TRIVY_AUTH_URL=https://registry.hub.docker.com -e TRIVY_USERNAME="+ jobDataMap.getString("username") +" -e TRIVY_PASSWORD=" + jobDataMap.getString("password");
            String cmd4="docker run --rm " +  (jobDataMap.getBoolean("private") == equals(true) ? authentication : "")  +  " aquasec/trivy -f json --light -q "+ jobDataMap.getString("tool");
            Process proc4 = Runtime.getRuntime().exec(cmd4);
            BufferedReader stdInput4 = new BufferedReader(new InputStreamReader(proc4.getInputStream()));

            while ((s4 = stdInput4.readLine()) != null) {
                System.out.println(s4);
                sb.append(s4);
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        long scanId = jobDataMap.getLong("id");
        //sendMail(mailProperties.getUsername(), jobDataMap.getString("email"), "Scan", sb.toString());
        sendMail("nokiascaner3s@gmail.com", "rurakf@gmail.com", "Scan", sb.toString());

        
        Optional<Scan> optionalScanToUpdate = scanRepo.findById((long) scanId);
        Scan scanToUpdate = optionalScanToUpdate.get();
        scanToUpdate.setLog(sb.toString());
        scanToUpdate.setStatus("positive");
        scanRepo.save(scanToUpdate);
        
    }

    private void sendMail(String fromEmail, String toEmail, String subject, String body) {
        try {
            logger.info("Sending Email to {}", toEmail);
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);

            mailSender.send(message);
        } catch (MessagingException ex) {
            logger.error("Failed to send email to {}", toEmail);
        }
    }
}
