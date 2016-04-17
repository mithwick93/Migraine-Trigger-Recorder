package shehan.com.migrainetrigger.view.utility;

/**
 * Created by Shehan on 4/17/2016.
 */
public class viewUtilities {

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

}
