package shehan.com.migrainetrigger.utility;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Shehan on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class AppUtilTest {

    @Test
    public void testGetFormattedTimeAM() throws Exception {
        String expected = "03:15 am";
        String result = AppUtil.getFormattedTime(3, 15);

        assertEquals(expected, result);

    }

    @Test
    public void testGetFormattedTimePM() throws Exception {
        String expected = "03:15 pm";
        String result = AppUtil.getFormattedTime(15, 15);

        assertEquals(expected, result);
    }

    @Test
    public void testGetStringDate() throws Exception {
        Timestamp testTimestamp = new Timestamp(new Date().getTime());
        String expected = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(testTimestamp);
        String result = AppUtil.getStringDate(testTimestamp);

        assertEquals(expected, result);
    }

    @Test
    public void testGetFriendlyStringDate() throws Exception {
        Timestamp testTimestamp = new Timestamp(new Date().getTime());
        String expected = new SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.getDefault()).format(testTimestamp);
        String result = AppUtil.getFriendlyStringDate(testTimestamp);

        assertEquals(expected, result);
    }

    @Test
    public void testGetFriendlyDuration() throws Exception {
        String expected = "2 days, 21 hours and 26 minutes";
        String result = AppUtil.getFriendlyDuration(250000);

        assertEquals(expected, result);
    }

    @Test
    public void testGetStringWeatherDate() throws Exception {
        Timestamp testTimestamp = new Timestamp(new Date().getTime());
        String expected = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(testTimestamp).replace(" ", "T");
        String result = AppUtil.getStringWeatherDate(testTimestamp);

        assertEquals(expected, result);
    }

    @Test
    public void testGetTimeStampDate() throws Exception {
        Timestamp testTimestamp = new Timestamp(new Date().getTime());
        String expected = AppUtil.getStringDate(testTimestamp);
        Timestamp result = AppUtil.getTimeStampDate(expected);

        assertNotNull(result);
        assertTrue(result.getTime() > 0);
    }
}