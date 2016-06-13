package shehan.com.migrainetrigger.view.activity;

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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class InterRecordAddTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGenerated() {

        // Click at FloatingActionButton with id R.id.fab
        onView(withId(R.id.fab)).perform(click());

        // Click at AppCompatTextView with id R.id.fab_sheet_item_intermediate
        onView(withId(R.id.fab_sheet_item_intermediate)).perform(click());

        // Click at AppCompatEditText with id R.id.txt_record_start_date
        onView(withId(R.id.txt_record_start_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_date)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.txt_record_start_date)).perform(replaceText("03-05-2016"));
        // Set text to '03-05-2016' in AppCompatEditText with id R.id.txt_record_start_date
        onView(withId(R.id.txt_record_start_date)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_start_time
        onView(withId(R.id.txt_record_start_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_time)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        // Set text to '02:36 pm' in AppCompatEditText with id R.id.txt_record_start_time
        onView(withId(R.id.txt_record_start_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_time)).perform(replaceText("02:36 pm"));

        // Click at AppCompatEditText with id R.id.txt_record_end_date
        onView(withId(R.id.txt_record_end_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_date)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        // Set text to '03-05-2016' in AppCompatEditText with id R.id.txt_record_end_date
        onView(withId(R.id.txt_record_end_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_date)).perform(replaceText("03-05-2016"));

        // Click at AppCompatEditText with id R.id.txt_record_end_time
        onView(withId(R.id.txt_record_end_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_time)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.txt_record_end_time)).perform(replaceText("10:55 pm"));
        // Set text to '10:55 pm' in AppCompatEditText with id R.id.txt_record_end_time
        onView(withId(R.id.txt_record_end_time)).perform(scrollTo());

        // Click at AppCompatTextView with id R.id.txt_record_intensity
        onView(withId(R.id.txt_record_intensity)).perform(scrollTo());
        onView(withId(R.id.txt_record_intensity)).perform(click());

        // Click at item with value '4- Mild' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("4 - Mild"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at AppCompatEditText with id R.id.txt_record_triggers
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());
        onView(withId(R.id.txt_record_triggers)).perform(click());

        // Click at item with value 'Allergy' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Allergy"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Weather' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Weather"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Odd smell' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Odd smell"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_triggers)).perform(replaceText(" Odd smell, Weather, Allergy"));
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_symptoms
        onView(withId(R.id.txt_record_symptoms)).perform(scrollTo());
        onView(withId(R.id.txt_record_symptoms)).perform(click());

        // Click at item with value 'Blurred vision' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Blurred vision"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Sensitive to smell' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Sensitive to smell"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Sensitive to noise' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Sensitive to noise"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Sensitive to light' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Sensitive to light"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_symptoms)).perform(scrollTo());
        onView(withId(R.id.txt_record_symptoms)).perform(replaceText(" Sensitive to light, Sensitive to noise, Sensitive to smell, Blurred vision"));

        // Click at AppCompatEditText with id R.id.txt_record_activities
        onView(withId(R.id.txt_record_activities)).perform(scrollTo());
        onView(withId(R.id.txt_record_activities)).perform(click());

        // Click at item with value 'Slow to work' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Slow to work"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Could not fall asleep' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Could not fall asleep"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Missed social activity' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Missed social activity"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_activities)).perform(scrollTo());
        onView(withId(R.id.txt_record_activities)).perform(replaceText(" Could not fall asleep, Slow to work, Missed social activity"));

        // Click at AppCompatEditText with id R.id.txt_record_triggers
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());
        onView(withId(R.id.txt_record_triggers)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultNeutral
        onView(withId(R.id.buttonDefaultNeutral)).perform(click());

        onView(withId(R.id.txt_record_triggers)).perform(replaceText(""));
        // Set text to '' in AppCompatEditText with id R.id.txt_record_triggers
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_triggers
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());
        onView(withId(R.id.txt_record_triggers)).perform(click());

        // Click at item with value 'Irregular sleep' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Irregular sleep"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Odd smell' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Odd smell"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Swipe up at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeUp());

        // Swipe down at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeDown());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_triggers)).perform(replaceText(" Odd smell, Irregular sleep"));
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_symptoms
        onView(withId(R.id.txt_record_symptoms)).perform(scrollTo());
        onView(withId(R.id.txt_record_symptoms)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_symptoms)).perform(scrollTo());
        onView(withId(R.id.txt_record_symptoms)).perform(replaceText(" Sensitive to light, Sensitive to noise, Sensitive to smell, Blurred vision"));

        // Click at AppCompatEditText with id R.id.txt_record_activities
        onView(withId(R.id.txt_record_activities)).perform(scrollTo());
        onView(withId(R.id.txt_record_activities)).perform(click());

        // Click at item with value 'Missed social activity' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Missed social activity"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_activities)).perform(scrollTo());
        onView(withId(R.id.txt_record_activities)).perform(replaceText(" Could not fall asleep, Slow to work"));



    }


}