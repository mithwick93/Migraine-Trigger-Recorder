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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class InfoLevelChooseTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    public void stopTiming(IdlingResource idlingResource) {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void testGenerated() {
        // Click at SheetFab with id R.id.fab
        onView(withId(R.id.fab)).perform(click());

        // Click at AppCompatTextView with id R.id.fab_sheet_item_basic
        onView(withId(R.id.fab_sheet_item_basic)).perform(click());

        pressBack();

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        // Click at SheetFab with id R.id.fab
        onView(withId(R.id.fab)).perform(click());

        // Click at AppCompatTextView with id R.id.fab_sheet_item_intermediate
        onView(withId(R.id.fab_sheet_item_intermediate)).perform(click());

        pressBack();

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

        // Click at SheetFab with id R.id.fab
        onView(withId(R.id.fab)).perform(click());

        // Click at AppCompatTextView with id R.id.fab_sheet_item_full
        onView(withId(R.id.fab_sheet_item_full)).perform(click());

        pressBack();

        // Click at MDButton with id R.id.buttonDefaultPositive
        onView(withId(R.id.buttonDefaultPositive)).perform(click());

    }
}