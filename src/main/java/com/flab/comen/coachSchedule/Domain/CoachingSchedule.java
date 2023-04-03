package com.flab.comen.coachSchedule.Domain;

public class CoachingSchedule {

    private long tid;
    private long coachId;
    private long menteeId;
    String possibleDt;
    int weekNumber;
    String cancelYn;
    String feedback;
    String feedbackCreatedDt;
    String createdDt;
    String updatedDt;
    long updatedBy;

    public CoachingSchedule(long tid, long coachId, long menteeId, String possibleDt, int weekNumber, String cancelYn,
                            String feedback, String feedbackCreatedDt, String createdDt, String updatedDt, long updatedBy) {
        this.tid = tid;
        this.coachId = coachId;
        this.menteeId = menteeId;
        this.possibleDt = possibleDt;
        this.weekNumber = weekNumber;
        this.cancelYn = cancelYn;
        this.feedback = feedback;
        this.feedbackCreatedDt = feedbackCreatedDt;
        this.createdDt = createdDt;
        this.updatedDt = updatedDt;
        this.updatedBy = updatedBy;
    }
}
