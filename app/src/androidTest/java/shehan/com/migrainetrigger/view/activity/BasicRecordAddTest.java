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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class BasicRecordAddTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGenerated() {

        // Click at FloatingActionButton with id R.id.fab
        onView(withId(R.id.fab)).perform(click());

        // Click at AppCompatTextView with id R.id.fab_sheet_item_basic
        onView(withId(R.id.fab_sheet_item_basic)).perform(click());

        // Click at AppCompatEditText with id R.id.txt_record_start_date
        onView(withId(R.id.txt_record_start_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_date)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.txt_record_start_date)).perform(replaceText("07-05-2016"));
        // Set text to '07-05-2016' in AppCompatEditText with id R.id.txt_record_start_date
        onView(withId(R.id.txt_record_start_date)).perform(scrollTo());

        // Click at AppCompatEditText with id R.id.txt_record_start_time
        onView(withId(R.id.txt_record_start_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_time)).perform(click());

        // Click at AppCompatCheckedTextView with id android.R.id.pm_label
        onView(withText("PM")).perform(click());

        // Click at AppCompatCheckedTextView with id android.R.id.am_label
        onView(withText("AM")).perform(click());

        // Click at AppCompatCheckedTextView with id android.R.id.pm_label
        onView(withText("PM")).perform(click());

        // Click at AppCompatTextView with id android.R.id.hours
        //onView(withId(android.R.id.hours)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        // Set text to '03:20 pm' in AppCompatEditText with id R.id.txt_record_start_time
        onView(withId(R.id.txt_record_start_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_start_time)).perform(replaceText("03:20 pm"));

        // Click at AppCompatEditText with id R.id.txt_record_end_date
        onView(withId(R.id.txt_record_end_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_date)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        // Set text to '07-05-2016' in AppCompatEditText with id R.id.txt_record_end_date
        onView(withId(R.id.txt_record_end_date)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_date)).perform(replaceText("07-05-2016"));

        // Click at AppCompatEditText with id R.id.txt_record_end_time
        onView(withId(R.id.txt_record_end_time)).perform(scrollTo());
        onView(withId(R.id.txt_record_end_time)).perform(click());

        // Click at AppCompatButton with id android.R.id.button1
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.txt_record_end_time)).perform(replaceText("10:30 pm"));
        // Set text to '10:30 pm' in AppCompatEditText with id R.id.txt_record_end_time
        onView(withId(R.id.txt_record_end_time)).perform(scrollTo());

        // Click at AppCompatTextView with id R.id.txt_record_intensity
        onView(withId(R.id.txt_record_intensity)).perform(scrollTo());
        onView(withId(R.id.txt_record_intensity)).perform(click());

        // Click at item with value '6 - Moderate' in ListView
        onData(allOf(is(instanceOf(java.lang.String.class)), is("6 - Moderate"))).inAdapterView(withId(R.id.contentListView)).perform(click());

        // Click at ActionMenuItemView with id R.id.action_refresh
        onView(withId(R.id.action_refresh)).perform(click());

        // Click at FloatingActionButton with id R.id.fab_add
        //onView(withId(R.id.fab_add)).perform(click());

    }


}