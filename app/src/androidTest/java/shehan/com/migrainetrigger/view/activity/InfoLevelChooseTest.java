package shehan.com.migrainetrigger.view.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import shehan.com.migrainetrigger.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class InfoLevelChooseTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGenerated() {
        // Used to provide time delays between actions, see details at http://droidtestlab.com/delay.html
        IdlingResource idlingResource;

        idlingResource = startTiming(3000);
        // Click at FloatingActionButton with id R.id.fab
        onView(withId(R.id.fab)).perform(click());
        stopTiming(idlingResource);

        idlingResource = startTiming(1700);
        // Click at item with value 'Basic' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Basic"))).inAdapterView(withId(R.id.contentListView)).perform(click());
        stopTiming(idlingResource);

        pressBack();

        idlingResource = startTiming(3000);
        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());
        stopTiming(idlingResource);

        idlingResource = startTiming(1300);
        // Click at FloatingActionButton with id R.id.fab
        onView(withId(R.id.fab)).perform(click());
        stopTiming(idlingResource);

        idlingResource = startTiming(1400);
        // Click at item with value 'Intermediate' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Intermediate"))).inAdapterView(withId(R.id.contentListView)).perform(click());
        stopTiming(idlingResource);

        pressBack();

        idlingResource = startTiming(3000);
        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());
        stopTiming(idlingResource);

        idlingResource = startTiming(1100);
        // Click at FloatingActionButton with id R.id.fab
        onView(withId(R.id.fab)).perform(click());
        stopTiming(idlingResource);

        idlingResource = startTiming(1200);
        // Click at item with value 'Full' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Full"))).inAdapterView(withId(R.id.contentListView)).perform(click());
        stopTiming(idlingResource);

        pressBack();

        idlingResource = startTiming(2600);
        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());
        stopTiming(idlingResource);

    }


    // See details at http://droidtestlab.com/delay.html
    public IdlingResource startTiming(long time) {
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(time);
        Espresso.registerIdlingResources(idlingResource);
        return idlingResource;
    }

    public void stopTiming(IdlingResource idlingResource) {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    public class ElapsedTimeIdlingResource implements IdlingResource {
        private final long waitingTime;
        private ResourceCallback resourceCallback;
        private long startTime;

        public ElapsedTimeIdlingResource(long waitingTime) {
            this.startTime = System.currentTimeMillis();
            this.waitingTime = waitingTime;
        }

        @Override
        public String getName() {
            return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
        }

        @Override
        public boolean isIdleNow() {
            long elapsed = System.currentTimeMillis() - startTime;
            boolean idle = (elapsed >= waitingTime);
            if (idle) {
                resourceCallback.onTransitionToIdle();
            }
            return idle;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }
    }
}