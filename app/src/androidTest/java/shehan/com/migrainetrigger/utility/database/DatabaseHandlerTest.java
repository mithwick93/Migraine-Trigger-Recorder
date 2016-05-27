package shehan.com.migrainetrigger.utility.database;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by Shehan on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseHandlerTest {

    @Test
    public void getReadableDBTest() {
        assertTrue(DatabaseHandler.getReadableDatabase() != null);
    }

    @Test
    public void getWritableDBTest() {
        assertTrue(DatabaseHandler.getWritableDatabase() != null);
    }

    @Test
    public void testDatabaseTest() {
        DatabaseHandler.testDatabase();
    }
}