package com.flab.comen.global.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class DateUtils {

    /***
     * 입력된 날짜 문자열을 이용하여 해당 년도의 몇 번째 주인지 반환하는 메소드
     * @param dateString 날짜 문자열 (yyyy-MM-dd HH:mm:ss)
     * @return 파라미터가 해당 년도의 몇 번째 주인지
     */
    public static int calculateWeekOfYear(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate
                .parse(dateString, formatter)
                .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1L);

        return date.get(WeekFields.of(Locale.getDefault()).weekOfYear());
    }

    /***
     * 오눌이 올해 몇 번째 주인지 반환하는 메소드
     * @return 오늘이 몇 번째 주인지
     */
    public static int getWeekOfYearFromToday(LocalDateTime now){
        return now.get(WeekFields.of(Locale.getDefault()).weekOfYear());
    }

    /***
     * 현재 시점이 금요일 21시가 지났는지에 대해 반환하는 메소드
     * @return T : 현재 금요일 21시가 지남, F : 현재 금요일 21시가 지나지 않음
     */
    public static boolean isDeadlinePassed(LocalDateTime now) {
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        LocalDateTime deadline = LocalDateTime.of(now.toLocalDate(), LocalTime.of(21, 0));

        if(dayOfWeek == DayOfWeek.FRIDAY){
            return now.isAfter(deadline);
        } else if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }

        return false;
    }

    /***
     * 차주 월요일을 yyyy-mm-dd 꼴의 String으로 출력한다
     * @return T : 현재 금요일 21시가 지남, F : 현재 금요일 21시가 지나지 않음
     */
    public static String getNextMondayDate(){
        LocalDate nextMondayDate = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        return nextMondayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


}
