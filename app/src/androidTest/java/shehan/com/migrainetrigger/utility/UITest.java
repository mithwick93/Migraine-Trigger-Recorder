package shehan.com.migrainetrigger.utility;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;

/**
 * Created by Shehan on 19/05/2016.
 */
public class UITest extends InstrumentationTestCase {
    private UiDevice device;

    @Override
    public void setUp() throws Exception {
        device = UiDevice.getInstance(getInstrumentation());
        device.pressHome();

        // Wait for the Apps icon to show up on the screen
        device.wait(Until.hasObject(By.desc("Apps")), 3000);

        UiObject2 appsButton = device.findObject(By.desc("Apps"));
        appsButton.click();

        // Wait for the Calculator icon to show up on the screen
        device.wait(Until.hasObject(By.text("Migraine Trigger")), 3000);

        UiObject2 migraineTriggerApp = device.findObject(By.text("Migraine Trigger"));
        migraineTriggerApp.click();

    }

    public void testAdd() throws Exception {

    }
}
