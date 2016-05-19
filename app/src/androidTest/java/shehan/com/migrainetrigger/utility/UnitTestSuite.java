package shehan.com.migrainetrigger.utility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import shehan.com.migrainetrigger.utility.database.DatabaseHandlerTest;

/**
 * Created by Shehan on 19/05/2016.
 */
// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({AppUtil.class,
        DatabaseHandlerTest.class})
public class UnitTestSuite {
}
