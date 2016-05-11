package shehan.com.migrainetrigger.utility;

import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shehan on 4/17/2016.
 */
public class AppUtil {

    public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;

    /**
     * Convert ime from date picker to 12 hour time
     *
     * @param hourOfDay Range :0-23
     * @param minute    Range 0:59
     * @return String FormattedTime
     */
    public static String getFormattedTime(int hourOfDay,
                                          int minute) {

        String suffix = "am";
        if (hourOfDay > 12) {
            hourOfDay -= 12;
            suffix = "pm";
        }
        return String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute, suffix);
    }

    /**
     * Convert Timestamp object value to string
     * string format : yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp timestamp to convert to string
     * @ string object with time
     */
    public static String getStringDate(Timestamp timestamp) {
        Log.d("AppUtil", "getStringDate timestamp value : " + timestamp.toString());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(timestamp);
    }

    /**
     * Convert Timestamp object value to string
     * string format : dd/MM/yyyy h:mm a
     *
     * @param timestamp timestamp to convert to string
     * @ string object with time
     */
    public static String getFriendlyStringDate(Timestamp timestamp) {
        Log.d("AppUtil", "getFriendlyStringDate timestamp value : " + timestamp.toString());
        return new SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.getDefault()).format(timestamp);
    }

    /**
     * Convert long duration to human readable format
     *
     * @param duration time difference as a long
     * @return string duration
     */
    @NonNull
    public static String getFriendlyDuration(long duration) {

        duration *= 1000;

        Log.d("AppUtil", "getFriendlyDuration seconds value : " + duration);
        int hours = (int) duration / 3600;
        int minutes = (int) (duration % 3600) / 60;

        StringBuilder res = new StringBuilder();

        long temp;
        if (duration >= ONE_SECOND) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp).append(" day").append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" hour").append(temp > 1 ? "s" : "");
//                        .append(duration >= ONE_MINUTE ? " " : "");
            }

            if (!res.toString().equals("") && duration >= ONE_MINUTE) {
                res.append(" and ");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
//                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
            }

//            if (!res.toString().equals("") && duration >= ONE_SECOND) {
//                res.append(" and ");
//            }
//
//            temp = duration / ONE_SECOND;
//            if (temp > 0) {
//                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
//            }
            return res.toString();
        } else {
            return "0 second";
        }

    }

    /**
     * Convert Timestamp object value to string
     * string format : yyyy-MM-dd HH:mm:ss  2016-04-18T22:30:45
     *
     * @param timestamp timestamp to convert to string
     * @ string object with time
     */
    public static String getStringWeatherDate(Timestamp timestamp) {
        Log.d("AppUtil", "getStringDate timestamp value : " + timestamp.toString());
//        String result= timestamp.toString().replace(" ","T");
//        result=result.substring(0,result.indexOf('T')+9);
//        return result;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(timestamp).replace(" ", "T");
    }

    /**
     * Convert String object value to timestamp
     * string format : yyyy-MM-dd HH:mm:ss
     *
     * @param str string to convert to timestamp
     * @return timestamp object with time .nullable
     */
    public static Timestamp getTimeStampDate(String str) {
        Log.d("AppUtil", "getTimeStampDate str value : " + str);
        Timestamp timestamp = null;
        try {
            //str = str.replace("/", "-");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date parsedDate = dateFormat.parse(str);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {//this generic but you can control another types of exception
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * Convert String object value to timestamp
     * string format : dd/MM/yyyy
     *
     * @param str string to convert to timestamp
     * @return timestamp object with time .nullable
     */
    public static Timestamp getTimeStampDay(String str) {
        Log.d("AppUtil", "getTimeStampDay str value : " + str);
        Timestamp timestamp = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date parsedDate = dateFormat.parse(str);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {//this generic but you can control another types of exception
            e.printStackTrace();
        }
        return timestamp;
    }


}
