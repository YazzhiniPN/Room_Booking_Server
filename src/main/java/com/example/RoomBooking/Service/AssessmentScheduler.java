package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.Classes;
import com.example.RoomBooking.Repository.ClassRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Nightly CRON job that auto-expires assessment periods.
 *
 * When a faculty sets an assessment period (isAssess=true), it releases their
 * permanent classroom for others to book during that window. If the faculty
 * forgets to manually delete the period after exams are over, this scheduler
 * automatically resets it at midnight every day.
 *
 * When a faculty manually deletes their assessment period (isAssess → false),
 * this job simply skips that class since it queries only where isAssess=true.
 */
@Component
public class AssessmentScheduler {

    @Autowired
    private ClassRepo classRepo;

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    @Transactional
    public void expireAssessmentPeriods() {
        LocalDate today = LocalDate.now();

        // Find all classes whose assessment toDate is in the past
        List<Classes> expiredClasses = classRepo.findByIsAssessTrueAndToDateBefore(today);

        for (Classes cls : expiredClasses) {
            cls.setAssess(false);
            cls.setFromDate(null);
            cls.setToDate(null);
            cls.setPeriods(null);
            classRepo.save(cls);
        }
    }
}
