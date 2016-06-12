package shehan.com.migrainetrigger.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.controller.ReportController;

/**
 * Created by Shehan on 4/17/2016.
 */
public class AppUtil {

    public final static long SECONDS = 60;
    public final static long MINUTES = 60;
    public final static long HOURS = 24;
    private final static long ONE_SECOND = 1000;
    private final static long ONE_MINUTE = ONE_SECOND * 60;
    private final static long ONE_HOUR = ONE_MINUTE * 60;
    private final static long ONE_DAY = ONE_HOUR * 24;

    private static Toast mToast;

    /**
     * Convert ime from date picker to 12 hour time
     *
     * @param hourOfDay Range :0-23
     * @param minute    Range 0-59
     * @return String FormattedTime
     */
    public static String getFormattedTime(int hourOfDay,
                                          int minute) {

        String suffix = "am";
        if (hourOfDay > 12) {
            hourOfDay -= 12;
            suffix = "pm";
        }
        if (hourOfDay == 12) {
            suffix = "pm";
        }
        return String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute, suffix);
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
                res.append(temp).append(" hour").append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
            }

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                res.append(" and ");
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
            }
            return res.toString();
        } else {
            return "0 second";
        }

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
     * string format : yyyy-MM-dd HH:mm:ss  2016-04-18T22:30:45
     *
     * @param timestamp timestamp to convert to string
     * @ string object with time
     */
    public static String getStringWeatherDate(Timestamp timestamp) {
        Log.d("AppUtil", "getStringDate timestamp value : " + timestamp.toString());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(timestamp).replace(" ", "T");
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

    /**
     * Get Top list
     *
     * @param type class of model (LifeActivity,Medicine,Relief,Symptom,Trigger)
     * @return list of top factors
     */
    @Nullable
    public static ArrayList<String> getTopList(String type) {
        //get first record day
        Log.d("AppUtil", "getTopList :" + type);
        long sTimestamp = RecordController.getFirstRecordStartTimestamp().getTime();
        Calendar sCal = Calendar.getInstance();
        sCal.setTimeInMillis(sTimestamp);
        int sYear = sCal.get(Calendar.YEAR);
        int sMonth = sCal.get(Calendar.MONTH) + 1;
        int sDay = sCal.get(Calendar.DAY_OF_MONTH);

        Timestamp fromTimestamp = getTimeStampDate(String.format(Locale.getDefault(), "%02d-%02d-%d 00:00:00", sYear, sMonth, sDay));

        //get today
        java.util.Date date = new java.util.Date();
        long eTimestamp = new Timestamp(date.getTime()).getTime();
        Calendar eCal = Calendar.getInstance();
        eCal.setTimeInMillis(eTimestamp);
        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH) + 1;
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Timestamp toTimestamp = getTimeStampDate((String.format(Locale.getDefault(), "%02d-%02d-%d 00:00:00", eYear, eMonth, eDay)));

        switch (type) {
            case "BodyArea":
                return ReportController.getTopBodyAreas(fromTimestamp, toTimestamp, 3);
            case "LifeActivity":
                return ReportController.getTopActivities(fromTimestamp, toTimestamp, 3);
            case "Location":
                return ReportController.getTopLocations(fromTimestamp, toTimestamp, 3);
            case "Medicine":
                return ReportController.getTopMedicines(fromTimestamp, toTimestamp, 3);
            case "Relief":
                return ReportController.getTopReliefs(fromTimestamp, toTimestamp, 3);
            case "Symptom":
                return ReportController.getTopSymptoms(fromTimestamp, toTimestamp, 3);
            case "Trigger":
                return ReportController.getTopTriggers(fromTimestamp, toTimestamp, 3);
            default:
                return null;
        }
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date parsedDate = dateFormat.parse(str);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {//this generic but you can control another types of exception
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * Show dialog box msg
     *
     * @param context context to show text
     * @param msg     string msg
     */
    public static void showMsg(Context context, String msg) {
        new MaterialDialog.Builder(context)
                .content(msg)
                .negativeText(R.string.cancelButtonDialog)
                .show();
    }

    /**
     * @param context context to show text
     * @param msg     string msg
     * @param title   title of msg dialog
     */
    public static void showMsg(Context context, String msg, String title) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .negativeText(R.string.cancelButtonDialog)
                .show();
    }

    /**
     * show toast messages
     *
     * @param context context
     * @param message msg to show
     */
    public static void showToast(Context context, String message) {

        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

}
