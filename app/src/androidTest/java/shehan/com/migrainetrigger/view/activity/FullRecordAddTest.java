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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class FullRecordAddTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGenerated() {
        IdlingResource idlingResource;

        // Click at FloatingActionButton with id R.id.fab
        onView(withId(R.id.fab)).perform(click());

        // Click at item with value 'Full' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Full"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at AppCompatEditText with id R.id.txt_record_start_date
        onView(withId(R.id.txt_record_start_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_date)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.txt_record_start_date)).perform(replaceText("15-05-2016"));
        // Set text to '15-05-2016' in AppCompatEditText with id R.id.txt_record_start_date
        onView(withId(R.id.txt_record_start_date)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_start_time
        onView(withId(R.id.txt_record_start_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_time)).perform(click());

        // Click at AppCompatCheckedTextView with id android.R.id.am_label
        onView(withText("PM")).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        // Set text to '06:45 am' in AppCompatEditText with id R.id.txt_record_start_time
        onView(withId(R.id.txt_record_start_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_time)).perform(replaceText("06:45 am"));

        // Click at AppCompatEditText with id R.id.txt_record_end_date
        onView(withId(R.id.txt_record_end_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_date)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        // Set text to '15-05-2016' in AppCompatEditText with id R.id.txt_record_end_date
        onView(withId(R.id.txt_record_end_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_date)).perform(replaceText("15-05-2016"));

        // Click at AppCompatEditText with id R.id.txt_record_end_time
        onView(withId(R.id.txt_record_end_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_time)).perform(click());

        // Click at AppCompatCheckedTextView with id android.R.id.pm_label
        onView(withText("PM")).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.txt_record_end_time)).perform(replaceText("12:20 pm"));
        // Set text to '12:20 pm' in AppCompatEditText with id R.id.txt_record_end_time
        onView(withId(R.id.txt_record_end_time)).perform(scrollTo());

        // Click at AppCompatTextView with id R.id.txt_record_intensity
        onView(withId(R.id.txt_record_intensity)).perform(scrollTo());
        onView(withId(R.id.txt_record_intensity)).perform(click());

        // Click at item with value '2 - Hurts a little' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("2 - Hurts a little"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at AppCompatTextView with id R.id.txt_record_intensity
        onView(withId(R.id.txt_record_intensity)).perform(scrollTo());
        onView(withId(R.id.txt_record_intensity)).perform(click());

        // Click at item with value '3 - Hurts little more' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("3 - Hurts little more"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at AppCompatEditText with id R.id.txt_record_triggers
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());
        onView(withId(R.id.txt_record_triggers)).perform(click());

        // Click at item with value 'Irregular sleep' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Irregular sleep"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Sinus' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Sinus"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        // onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Odd smell' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Odd smell"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        // onView(withId(R.id.control)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_triggers)).perform(replaceText(" Odd smell, Sinus, Irregular sleep"));
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_symptoms
        onView(withId(R.id.txt_record_symptoms)).perform(scrollTo());
        onView(withId(R.id.txt_record_symptoms)).perform(click());

        // Click at item with value 'Sensitive to light' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Sensitive to light"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Confused' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Confused"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        // onView(withId(R.id.control)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_symptoms)).perform(scrollTo());
        onView(withId(R.id.txt_record_symptoms)).perform(replaceText(" Sensitive to light, Confused"));

        // Click at AppCompatEditText with id R.id.txt_record_activities
        onView(withId(R.id.txt_record_activities)).perform(scrollTo());
        onView(withId(R.id.txt_record_activities)).perform(click());

        // Click at item with value 'Slow to work' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Slow to work"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        // onView(withId(R.id.control)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        // Set text to ' Slow to work' in AppCompatEditText with id R.id.txt_record_activities
        onView(withId(R.id.txt_record_activities)).perform(scrollTo());
        onView(withId(R.id.txt_record_activities)).perform(replaceText(" Slow to work"));

        // Click at AppCompatEditText with id R.id.txt_record_location
        onView(withId(R.id.txt_record_location)).perform(scrollTo());
        onView(withId(R.id.txt_record_location)).perform(click());

        // Click at item with value 'Bed' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Bed"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        onView(withId(R.id.txt_record_location)).perform(replaceText("Bed"));
        // Set text to 'Bed' in AppCompatEditText with id R.id.txt_record_location
        onView(withId(R.id.txt_record_location)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_location
        onView(withId(R.id.txt_record_location)).perform(scrollTo());
        onView(withId(R.id.txt_record_location)).perform(click());

        // Click at item with value 'In transit' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("In transit"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        onView(withId(R.id.txt_record_location)).perform(replaceText("In transit"));
        // Set text to 'In transit' in AppCompatEditText with id R.id.txt_record_location
        onView(withId(R.id.txt_record_location)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_affected_areas
        onView(withId(R.id.txt_record_affected_areas)).perform(scrollTo());
        onView(withId(R.id.txt_record_affected_areas)).perform(click());

        // Swipe up at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeUp());

        // Swipe down at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeDown());

        // Click at item with value 'Left eye' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Left eye"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Right eye' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Right eye"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Right eye' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Right eye"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Uncheck AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Between eyes' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Between eyes"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_affected_areas)).perform(replaceText(" Left eye, Between eyes"));
        onView(withId(R.id.txt_record_affected_areas)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_medicine
        onView(withId(R.id.txt_record_medicine)).perform(scrollTo());
        onView(withId(R.id.txt_record_medicine)).perform(click());

        // Click at item with value 'Paracetamol' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Paracetamol"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Tylenol' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Tylenol"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        // Swipe up at ScrollView with id R.id.full_record_scroll_view
        onView(withId(R.id.full_record_scroll_view)).perform(swipeUp());

        idlingResource = startTiming(3000);
        onView(withId(R.id.txt_record_effective_medicine)).perform(replaceText(""));
        stopTiming(idlingResource);

        onView(withId(R.id.txt_record_medicine)).perform(scrollTo());
        onView(withId(R.id.txt_record_medicine)).perform(replaceText(" Paracetamol, Tylenol"));

        // Set text to '' in AppCompatEditText with id R.id.txt_record_effective_medicine
        onView(withId(R.id.txt_record_effective_medicine)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_effective_medicine
        onView(withId(R.id.txt_record_effective_medicine)).perform(scrollTo());
        onView(withId(R.id.txt_record_effective_medicine)).perform(click());

        // Click at item with value 'Paracetamol' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Paracetamol"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_effective_medicine)).perform(replaceText(" Paracetamol"));
        // Set text to ' Paracetamol' in AppCompatEditText with id R.id.txt_record_effective_medicine
        onView(withId(R.id.txt_record_effective_medicine)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_relief
        onView(withId(R.id.txt_record_relief)).perform(scrollTo());
        onView(withId(R.id.txt_record_relief)).perform(click());

        // Click at item with value 'Sleep' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Sleep"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Dark room rest' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Dark room rest"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Swipe up at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeUp());

        // Click at item with value 'Stay indoor' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Stay indoor"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_effective_reliefs)).perform(replaceText(""));
        // Set text to '' in AppCompatEditText with id R.id.txt_record_effective_reliefs
        onView(withId(R.id.txt_record_effective_reliefs)).perform(scrollTo());
        onView(withId(R.id.txt_record_relief)).perform(replaceText(" Dark room rest, Sleep, Stay indoor"));
        onView(withId(R.id.txt_record_relief)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_effective_reliefs
        onView(withId(R.id.txt_record_effective_reliefs)).perform(scrollTo());
        onView(withId(R.id.txt_record_effective_reliefs)).perform(click());

        // Click at item with value 'Dark room rest' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Dark room rest"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        //onView(withId(R.id.control)).perform(click());

        // Click at item with value 'Sleep' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Sleep"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Check AppCompatCheckBox with id R.id.control
        // onView(withId(R.id.control)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_effective_reliefs)).perform(replaceText(" Dark room rest, Sleep"));
        onView(withId(R.id.txt_record_effective_reliefs)).perform(scrollTo());

        // Click at ActionMenuItemView with id R.id.action_refresh
        //onView(withId(R.id.action_refresh)).perform(click());


        // Click at FloatingActionButton with id R.id.fab_add
        //onView(withId(R.id.fab_add)).perform(click());

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