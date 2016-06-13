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
        // Click at SheetFab with id R.id.fab
        onView(withId(R.id.fab)).perform(click());

        // Click at AppCompatTextView with id R.id.fab_sheet_item_full
        onView(withId(R.id.fab_sheet_item_full)).perform(click());

        // Click at AppCompatEditText with id R.id.txt_record_start_date
        onView(withId(R.id.txt_record_start_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_date)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.txt_record_start_date)).perform(replaceText("04-06-2016"));
        // Set text to '04-06-2016' in AppCompatEditText with id R.id.txt_record_start_date
        onView(withId(R.id.txt_record_start_date)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_start_time
        onView(withId(R.id.txt_record_start_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_time)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        // Set text to '01:35 pm' in AppCompatEditText with id R.id.txt_record_start_time
        onView(withId(R.id.txt_record_start_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_time)).perform(replaceText("01:35 pm"));

        // Click at AppCompatEditText with id R.id.txt_record_end_date
        onView(withId(R.id.txt_record_end_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_date)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        // Set text to '04-06-2016' in AppCompatEditText with id R.id.txt_record_end_date
        onView(withId(R.id.txt_record_end_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_date)).perform(replaceText("04-06-2016"));

        // Click at AppCompatEditText with id R.id.txt_record_end_time
        onView(withId(R.id.txt_record_end_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_time)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.txt_record_end_time)).perform(replaceText("07:16 pm"));
        // Set text to '07:16 pm' in AppCompatEditText with id R.id.txt_record_end_time
        onView(withId(R.id.txt_record_end_time)).perform(scrollTo());

        // Click at AppCompatTextView with id R.id.txt_record_intensity
        onView(withId(R.id.txt_record_intensity)).perform(scrollTo());
        onView(withId(R.id.txt_record_intensity)).perform(click());

        // Click at item with value '7 - Hurts a lot' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("7 - Hurts a lot"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at AppCompatEditText with id R.id.txt_record_triggers
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());
        onView(withId(R.id.txt_record_triggers)).perform(click());

        // Click at item with value 'Weather' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Weather"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Headache' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Headache"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Caffeine' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Caffeine"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Swipe down at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeDown());

        // Click at item with value 'Bright light' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Bright light"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_triggers)).perform(replaceText(" Headache, Bright light, Weather, Caffeine"));
        onView(withId(R.id.txt_record_triggers)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_symptoms
        onView(withId(R.id.txt_record_symptoms)).perform(scrollTo());
        onView(withId(R.id.txt_record_symptoms)).perform(click());

        // Click at item with value 'Sensitive to light' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Sensitive to light"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Swipe up at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeUp());

        // Click at item with value 'Vomiting' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Vomiting"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Swipe down at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeDown());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_symptoms)).perform(scrollTo());
        onView(withId(R.id.txt_record_symptoms)).perform(replaceText(" Sensitive to light, Vomiting"));

        // Click at AppCompatEditText with id R.id.txt_record_activities
        onView(withId(R.id.txt_record_activities)).perform(scrollTo());
        onView(withId(R.id.txt_record_activities)).perform(click());

        // Click at item with value 'Could not use device' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Could not use device"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Missed social activity' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Missed social activity"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Missed social activity' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Missed social activity"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Could not use device' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Could not use device"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Slow to work' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Slow to work"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        // Set text to ' Slow to work' in AppCompatEditText with id R.id.txt_record_activities
        onView(withId(R.id.txt_record_activities)).perform(scrollTo());
        onView(withId(R.id.txt_record_activities)).perform(replaceText(" Slow to work"));

        // Swipe up at ScrollView with id R.id.full_record_scroll_view
        onView(withId(R.id.full_record_scroll_view)).perform(swipeUp());

        // Click at AppCompatEditText with id R.id.txt_record_location
        onView(withId(R.id.txt_record_location)).perform(scrollTo());
        onView(withId(R.id.txt_record_location)).perform(click());

        // Click at item with value 'Home' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Home"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        onView(withId(R.id.txt_record_location)).perform(replaceText("Home"));
        // Set text to 'Home' in AppCompatEditText with id R.id.txt_record_location
        onView(withId(R.id.txt_record_location)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_affected_areas
        onView(withId(R.id.txt_record_affected_areas)).perform(scrollTo());
        onView(withId(R.id.txt_record_affected_areas)).perform(click());

        // Swipe up at ListView with id R.id.contentListView
        onView(withId(R.id.contentListView)).perform(swipeUp());

        // Click at item with value 'Left eye' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Left eye"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Right eye' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Right eye"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Between eyes' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Between eyes"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_affected_areas)).perform(replaceText(" Right eye, Left eye, Between eyes"));
        onView(withId(R.id.txt_record_affected_areas)).perform(scrollTo());

        // Swipe up at ScrollView with id R.id.full_record_scroll_view
        onView(withId(R.id.full_record_scroll_view)).perform(swipeUp());

        // Click at AppCompatEditText with id R.id.txt_record_medicine
        onView(withId(R.id.txt_record_medicine)).perform(scrollTo());
        onView(withId(R.id.txt_record_medicine)).perform(click());

        // Click at item with value 'Paracetamol' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Paracetamol"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Tylenol' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Tylenol"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_effective_medicine)).perform(replaceText(""));
        onView(withId(R.id.txt_record_medicine)).perform(scrollTo());
        onView(withId(R.id.txt_record_medicine)).perform(replaceText(" Paracetamol, Tylenol"));
        // Set text to '' in AppCompatEditText with id R.id.txt_record_effective_medicine
        onView(withId(R.id.txt_record_effective_medicine)).perform(scrollTo());

        // Swipe up at ScrollView with id R.id.full_record_scroll_view
        onView(withId(R.id.full_record_scroll_view)).perform(swipeUp());

        // Click at AppCompatEditText with id R.id.txt_record_effective_medicine
        onView(withId(R.id.txt_record_effective_medicine)).perform(scrollTo());
        onView(withId(R.id.txt_record_effective_medicine)).perform(click());

        // Click at item with value 'Tylenol' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Tylenol"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Paracetamol' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Paracetamol"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_effective_medicine)).perform(replaceText(" Paracetamol, Tylenol"));
        onView(withId(R.id.txt_record_effective_medicine)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_relief
        onView(withId(R.id.txt_record_relief)).perform(scrollTo());
        onView(withId(R.id.txt_record_relief)).perform(click());

        // Click at item with value 'Caffeine' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Caffeine"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Drink water' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Drink water"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Dark room rest' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Dark room rest"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        // Swipe up at ScrollView with id R.id.full_record_scroll_view
        onView(withId(R.id.full_record_scroll_view)).perform(swipeUp());

        onView(withId(R.id.txt_record_effective_reliefs)).perform(replaceText(""));
        // Set text to '' in AppCompatEditText with id R.id.txt_record_effective_reliefs
        onView(withId(R.id.txt_record_effective_reliefs)).perform(scrollTo());
        onView(withId(R.id.txt_record_relief)).perform(replaceText(" Drink water, Caffeine, Dark room rest"));
        onView(withId(R.id.txt_record_relief)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_effective_reliefs
        onView(withId(R.id.txt_record_effective_reliefs)).perform(scrollTo());
        onView(withId(R.id.txt_record_effective_reliefs)).perform(click());

        // Click at item with value 'Dark room rest' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Dark room rest"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at item with value 'Sleep' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("Drink water"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        onView(withId(R.id.txt_record_effective_reliefs)).perform(replaceText(" Drink water, Dark room rest"));
        onView(withId(R.id.txt_record_effective_reliefs)).perform(scrollTo());

    }

}