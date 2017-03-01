package br.com.tiagohs.popmovies.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar c = Calendar.getInstance();

    public static int getYearByDate(String dateString) {
        Date date = null;

        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static String getDateDayWeek(int dayWeek) {
        c.set(Calendar.DAY_OF_WEEK, dayWeek);
        return formatter.format(c.getTime());
    }

    public static String getDateToday() {
        Calendar c = Calendar.getInstance();
        return formatter.format(c.getTime());
    }

    public static String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return String.valueOf(c.get(Calendar.YEAR));
    }

    public static String getDateBefore(int numDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -numDays);
        return formatter.format(c.getTime());
    }

    public static String getDateAfter(int numDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, numDays);
        return formatter.format(c.getTime());
    }

    public static String formateDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            return dateString;
        }

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(date);
    }

    public static Calendar formateStringToCalendar(String dateString) {
        Calendar cal = Calendar.getInstance();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            return Calendar.getInstance();
        }

        return cal;
    }
}
