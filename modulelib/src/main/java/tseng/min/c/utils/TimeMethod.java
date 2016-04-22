package tseng.min.c.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Neo on 2016/1/22 0022.
 */
public class TimeMethod {
    private static final String TAG = TimeMethod.class.getSimpleName();
    public static final SimpleDateFormat sFull = new SimpleDateFormat("yyyy MMMdd hh:mm:ss a", StringToLocale(Locale.getDefault().getLanguage()));
    public static final SimpleDateFormat sMedium = new SimpleDateFormat("MMMdd hh:mm, a", StringToLocale(Locale.getDefault().getLanguage()));
    public static final SimpleDateFormat sSimple = new SimpleDateFormat("hh:mm, a", StringToLocale(Locale.getDefault().getLanguage()));

    public static String ShowLogTime(long logtime) {
        final long now = System.currentTimeMillis();
        final Calendar calendar_now = Calendar.getInstance();
        final Calendar calendar_log = Calendar.getInstance();
        calendar_now.setTimeInMillis(now);
        calendar_log.setTimeInMillis(logtime);

        if (calendar_now.get(Calendar.YEAR) != calendar_log.get(Calendar.YEAR)) {
            return sFull.format(logtime);
        } else if (calendar_now.get(Calendar.MONTH) != calendar_log.get(Calendar.MONTH)) {
            return sMedium.format(logtime);
        } else {
            return sSimple.format(logtime);
        }
    }

    public static Locale StringToLocale(String inputString) {
        final StringTokenizer tempStringTokenizer = new StringTokenizer(inputString, ",");
        String lang = "en", country = "US";
        if (tempStringTokenizer.hasMoreTokens()) {
            lang = (String) tempStringTokenizer.nextElement();
        }
        if (tempStringTokenizer.hasMoreTokens()) {
            country = (String) tempStringTokenizer.nextElement();
        }
        return new Locale(lang, country);
    }

    public static Date StringToDate(String stringdate) throws ParseException {
        return sFull.parse(stringdate);
    }

    public static long StringTolong(String stringdate) throws ParseException {
        return sFull.parse(stringdate).getTime();
    }
}
