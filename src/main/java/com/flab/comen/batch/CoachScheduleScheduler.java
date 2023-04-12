package com.flab.comen.batch;

import com.flab.comen.coachSchedule.service.CoachScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.flab.comen.global.utils.DateUtils.getNextMondayDate;

@Component
@Slf4j
public class CoachScheduleScheduler {

    CoachScheduleService coachScheduleService;

    public CoachScheduleScheduler(CoachScheduleService coachScheduleService) {
        this.coachScheduleService = coachScheduleService;
    }

//    @Scheduled(cron = "0 30 21 * * FRI")
    @Scheduled(fixedRate = 5000)
    public void checkCoachScheduleNextWeek() {
        String nextMondayDate = getNextMondayDate();
        int result = coachScheduleService.updateMenteesCoachExpireDate(nextMondayDate);
        log.info("[checkCoachScheduleNextWeek 수행] {}건의 expiredDate가 연장되었습니다.", result);
    }
}
