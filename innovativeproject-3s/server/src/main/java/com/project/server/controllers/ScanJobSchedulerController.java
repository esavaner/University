package com.project.server.controllers;

import com.project.server.Application;
import com.project.server.job.ScanJob;
import com.project.server.model.ScheduleScanRequest;
import com.project.server.model.ScheduleScanResponse;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
public class ScanJobSchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(ScanJobSchedulerController.class);

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/scheduleScan")
    public ResponseEntity<ScheduleScanResponse> scheduleScan(@Valid @RequestBody ScheduleScanRequest scheduleScanRequest) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(scheduleScanRequest.getDateTime(), scheduleScanRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())) {
                ScheduleScanResponse scheduleScanResponse = new ScheduleScanResponse(false,
                        "dateTime must be after current time");
                return ResponseEntity.badRequest().body(scheduleScanResponse);
            }

            JobDetail jobDetail = buildJobDetail(scheduleScanRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            ScheduleScanResponse scheduleScanResponse = new ScheduleScanResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Scan Scheduled Successfully!");
            return ResponseEntity.ok(scheduleScanResponse);
        } catch (SchedulerException ex) {
            logger.error("Error scheduling scan", ex);

            ScheduleScanResponse scheduleScanResponse = new ScheduleScanResponse(false,
                    "Error scheduling scan. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleScanResponse);
        }
    }

    private JobDetail buildJobDetail(ScheduleScanRequest scheduleScanRequest) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", scheduleScanRequest.getEmail());
        jobDataMap.put("subject", scheduleScanRequest.getSubject());
        jobDataMap.put("body", scheduleScanRequest.getBody());

        return JobBuilder.newJob(ScanJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Scan Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Scan Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
