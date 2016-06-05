package shehan.com.migrainetrigger.view.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import shehan.com.migrainetrigger.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewRecordsActivityTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(ViewRecordsActivity.class);

    @Test
    public void testGenerated() {
        // Used to provide time delays between actions, see details at http://droidtestlab.com/delay.html
        IdlingResource idlingResource;

        idlingResource = startTiming(2000);
        // Select page 1 in NonSwipeableViewPager with id R.id.pager
        onView(withId(R.id.pager)).perform(selectViewPagerPage(1));
        stopTiming(idlingResource);

        idlingResource = startTiming(1100);
        // Select page 0 in NonSwipeableViewPager with id R.id.pager
        onView(withId(R.id.pager)).perform(selectViewPagerPage(0));
        stopTiming(idlingResource);

        idlingResource = startTiming(2000);
        // Select page 1 in NonSwipeableViewPager with id R.id.pager
        onView(withId(R.id.pager)).perform(selectViewPagerPage(1));
        stopTiming(idlingResource);

        idlingResource = startTiming(2000);
        // Swipe right at CompactCalendarView with id R.id.calender_view
        onView(withId(R.id.calender_view)).perform(swipeRight());
        stopTiming(idlingResource);

        idlingResource = startTiming(800);
        // Swipe left at CompactCalendarView with id R.id.calender_view
        onView(withId(R.id.calender_view)).perform(swipeLeft());
        stopTiming(idlingResource);

        idlingResource = startTiming(700);
        // Swipe left at CompactCalendarView with id R.id.calender_view
        onView(withId(R.id.calender_view)).perform(swipeLeft());
        stopTiming(idlingResource);

        idlingResource = startTiming(800);
        // Swipe right at CompactCalendarView with id R.id.calender_view
        onView(withId(R.id.calender_view)).perform(swipeRight());
        stopTiming(idlingResource);

        idlingResource = startTiming(1100);
        // Swipe left at CompactCalendarView with id R.id.calender_view
        onView(withId(R.id.calender_view)).perform(swipeLeft());
        stopTiming(idlingResource);

        idlingResource = startTiming(400);
        // Swipe left at CompactCalendarView with id R.id.calender_view
        onView(withId(R.id.calender_view)).perform(swipeLeft());
        stopTiming(idlingResource);


        idlingResource = startTiming(700);
        // Swipe right at CompactCalendarView with id R.id.calender_view
        onView(withId(R.id.calender_view)).perform(swipeRight());
        stopTiming(idlingResource);

        idlingResource = startTiming(300);
        // Swipe right at CompactCalendarView with id R.id.calender_view
        onView(withId(R.id.calender_view)).perform(swipeRight());
        stopTiming(idlingResource);

    }

    // See details at http://droidtestlab.com/delay.html
    public IdlingResource startTiming(long time) {
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(time);
        Espresso.registerIdlingResources(idlingResource);
        return idlingResource;
    }

    public static ViewAction selectViewPagerPage(final int pos) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(android.support.v4.view.ViewPager.class);
            }

            @Override
            public String getDescription() {
                return "select page in ViewPager";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((android.support.v4.view.ViewPager) view).setCurrentItem(pos);
            }
        };
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