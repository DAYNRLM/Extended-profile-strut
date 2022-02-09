package com.example.aadharscanner.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFactory {

    public static DateFactory dateFactory;
    private Locale locale;

    private DateFactory() {
        locale = Locale.US; // set locale
    }

    public static DateFactory getInstance() {
        if (dateFactory == null) {
            dateFactory = new DateFactory();
        }
        return dateFactory;
    }

    public boolean isTimeZoneAutomatic(@NonNull Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME_ZONE, 0) == 1;
        }
    }
    public boolean isAutoDateEnable(@NonNull Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    @NonNull
    public String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        return date;
    }
    @Nullable
    public Date getDateFromString(@NonNull String date){
        Date conDate=null;
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        try {
            if (conDate==null)
              conDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return conDate;
    }

    @NonNull
    public String getTodayDate() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        // String dateoftodayis = day + "-" + (month + 1) + "-" + year;
        String dateoftodayis = day + "-" + (month + 1) + "-" + year;
        return dateoftodayis;
    }

    @NonNull
    public String getDateInOtherFormate() {
        //SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String dateoftodayis = day + "-" + (month + 1) + "-" + year;
        return dateoftodayis;


    }

    @NonNull
    public String getCurrentTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(Calendar.getInstance().getTime());

    }

    @NonNull
    public String getDiffInTime(@NonNull String currentTime, @NonNull String lastTime ) throws ParseException {
        String diff="";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date startDate = simpleDateFormat.parse(currentTime);
        Date endDate = simpleDateFormat.parse(lastTime);

        long difference = endDate.getTime() - startDate.getTime();
        if(difference<0)
        {
            Date dateMax = simpleDateFormat.parse("24:00");
            Date dateMin = simpleDateFormat.parse("00:00");
            difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
        }
        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        int sec = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)-(1000*60*min)) / (1000);
        diff=""+hours+":"+min+":"+sec;
        return diff;
    }

    @NonNull
    public String getPreviousDate(@NonNull String dateString, int numOfDays, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateString);
        } catch (ParseException e) {
            AppUtils.getInstance().showLog("Date Parse Exception" + e, DateFactory.class);

        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, -numOfDays);
        Date previousDate = calendar.getTime();
        return sdf.format(previousDate);
    }

    @NonNull
    public String getNextDate(@NonNull String dateString, int noOfDays, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateString);
        } catch (ParseException e) {
            AppUtils.getInstance().showLog("ParseException"+e,DateFactory.class);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, +noOfDays);
        Date previousDate = calendar.getTime();
        return sdf.format(previousDate);
    }

    public long getStartTimeOfTodayInMillis() {
        Calendar today = Calendar.getInstance();
        today.setTimeZone(TimeZone.getTimeZone("UTC"));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTimeInMillis();
    }

    public long getStartTimeOfGivenDay(long millis) {
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(millis);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);

        return day.getTimeInMillis();
    }

    public long getEndTimeOfTodayInMillis() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTimeInMillis();
    }

    public long getEndTimeOfGivenDay(long millis) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTimeInMillis(millis);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTimeInMillis();
    }

    @NonNull
    public String changeDateFormat(@NonNull String date, String oldDateFormat, String newDateFormat) throws ParseException {

        Date initDate = new SimpleDateFormat(oldDateFormat).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(newDateFormat);
        String parsedDate = formatter.format(initDate);

        return parsedDate;
    }

    @NonNull
    public String changeDateValue(String date) {
        String changedValue = "";
        Locale locale = new Locale("ENGLISH");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", locale);
        changedValue = sdf.format(new Date());
        return changedValue;
    }

    @NonNull
    public String changeDateValueFordatePicker(@NonNull String date) {
        String changedValue = "";
        Locale locale = new Locale("ENGLISH");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", locale);
        // Date date1 = new Date(date);
        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        changedValue = sdf.format(date1);
        return changedValue;
    }

    @Nullable
    public Date getDateFormate(@NonNull String date){
        Date convertedDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            convertedDate = sdf.parse(date);
            sdf.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public String geteDateFromTimeStamp(@NonNull String evaluationdate) {
        String getdate = "";
        String date[] = evaluationdate.split(" ");
        for (int i = 0; i < date.length; i++) {
            getdate = date[0];
            AppUtils.getInstance().showLog("dateIs" + getdate, DateFactory.class);
        }
        try {
            getdate = changeDateFormat(getdate, "yyyy-MM-dd", "dd-MM-yyyy");
            AppUtils.getInstance().showLog("dateIsAfterChange" + getdate, DateFactory.class);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getdate;
    }

    @NonNull
    public String getCountOfDays(@NonNull Date evaluationDate, @NonNull Date todayDate) {

        int eYear = 0, eMonth = 0, eDay = 0;

        //chech if today date is greater then evaluation given date and get the celender object
        if (evaluationDate.after(todayDate)) {
            Calendar eCal = Calendar.getInstance();
            eCal.setTime(evaluationDate);
            eYear = eCal.get(Calendar.YEAR);
            eMonth = eCal.get(Calendar.MONTH);
            eDay = eCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar eCal = Calendar.getInstance();
            eCal.setTime(todayDate);
            eYear = eCal.get(Calendar.YEAR);
            eMonth = eCal.get(Calendar.MONTH);
            eDay = eCal.get(Calendar.DAY_OF_MONTH);
        }

        //get the today date calender object
        Calendar tCal = Calendar.getInstance();
        tCal.setTime(todayDate);
        int tYear = tCal.get(Calendar.YEAR);
        int tMonth = tCal.get(Calendar.MONTH);
        int tDay = tCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        //set evaluation date in calender object
        date1.clear();
        date1.set(eYear, eMonth, eDay);

        //set today date in calender object
        date2.clear();
        date2.set(tYear, tMonth, tDay);
       AppUtils.getInstance().showLog("Todaydatetime"+date2.getTimeInMillis()+"  "+"Evaluationdatetime"+date1.getTimeInMillis(),DateFactory.class);
        //get the total difference b/w two calender objects
        long diff =date2.getTimeInMillis()-date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ("" + (int) dayCount );
    }
}
