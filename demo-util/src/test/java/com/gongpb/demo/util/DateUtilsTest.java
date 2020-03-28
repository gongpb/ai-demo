package com.gongpb.demo.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.*;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class DateUtilsTest {

    @Test
    public void toDate() throws Exception{
        int dateInt = 20200329;
        Date dateInt2Date = DateUtils.DATE_FORMATE_ENUM.FORMATE2yyyyMMdd.toDate(dateInt);
        System.out.println(dateInt2Date);
        int now2Int = DateUtils.DATE_FORMATE_ENUM.FORMATE2yyyyMMdd.toInt(dateInt2Date);
        assertEquals(now2Int,20200329);

        String dateStr = "2020-03-29";
        Date dateStr2Date = DateUtils.DATE_FORMATE_ENUM.FORMATE2yyyy_MM_dd.toDate(dateStr);
        System.out.println(dateStr2Date);
        String now2Str = DateUtils.DATE_FORMATE_ENUM.FORMATE2yyyy_MM_dd.toString(dateStr2Date);
        assertEquals(now2Str,now2Str);
    }
    @Test
    public void toDateInt() {
    }

    @Test
    public void toInt() {
    }
    @Test
    public void formatUnSafe() {
    }

    @Test
    public void dateAddDay() {
    }

    @Test
    public void addMonths() {
    }

    @Test
    public void addDays() {
    }

    @Test
    public void addHHs() {
    }

    @Test
    public void dateAddMinutes() {
    }

    @Test
    public void dateAddSecond() {
    }

    @Test
    public void addMillsecond() {
    }

    @Test
    public void monthsBetweenYYYYMM() {
    }

    @Test
    public void dateAddMonths() {
    }

    @Test
    public void getDateBeforeWeek() {
    }

    @Test
    public void daysBetween() {
    }
}