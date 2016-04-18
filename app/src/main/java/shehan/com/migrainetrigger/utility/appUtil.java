package shehan.com.migrainetrigger.utility;

import android.net.ConnectivityManager;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Shehan on 4/17/2016.
 */
public class appUtil {

    /**
     * Convert ime from daate picker to 12 hour time
     *
     * @param hourOfDay Range :0-23
     * @param minute    Range 0:59
     * @return
     */
    public static String getFormattedTime(int hourOfDay,
                                          int minute) {

        String suffix = "am";
        if (hourOfDay > 12) {
            hourOfDay -= 12;
            suffix = "pm";
        }
        return String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + " " + suffix;
    }

    /**
     * Convert Timestamp object value to string
     * string format : dd/MM/yyyy HH:mm:ss
     *
     * @param timestamp timestamp to convert to string
     * @ string object with time
     */
    public static String getStringDate(Timestamp timestamp) {
        Log.d("appUtil", "getStringDate timestamp value : " + timestamp.toString());
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp);
    }

    /**
     * Convert Timestamp object value to string
     * string format : yyyy-MM-dd HH:mm:ss  2016-04-18T22:30:45
     *
     * @param timestamp timestamp to convert to string
     * @ string object with time
     */
    public static String getStringWeatherDate(Timestamp timestamp) {
        Log.d("appUtil", "getStringDate timestamp value : " + timestamp.toString());
//        String result= timestamp.toString().replace(" ","T");
//        result=result.substring(0,result.indexOf('T')+9);
//        return result;
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp)).replace(" ","T");
    }

    /**
     * Convert String object value to timestamp
     * string format : dd/MM/yyyy HH:mm:ss
     *
     * @param str string to convert to timestamp
     * @return timestamp object with time .nullable
     */
    public static Timestamp getTimeStampDate(String str) {
        Log.d("appUtil", "getTimeStampDate str value : " + str);
        Timestamp timestamp = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date parsedDate = dateFormat.parse(str);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {//this generic but you can control another types of exception
            e.printStackTrace();
        }
        return timestamp;
    }



}
