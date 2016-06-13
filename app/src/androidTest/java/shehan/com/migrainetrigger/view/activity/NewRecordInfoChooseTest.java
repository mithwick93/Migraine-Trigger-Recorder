package shehan.com.migrainetrigger.view.activity;


import android.support.test.espresso.ViewInteraction;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NewRecordInfoChooseTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> mActivityTestRule = new ActivityTestRule<>(SplashScreenActivity.class);

    @Test
    public void newRecordInfoChooseTest() {
        ViewInteraction sheetFab = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        sheetFab.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.fab_sheet_item_basic), withText("Basic"), isDisplayed()));
        textView.check(matches(withText("Basic")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.fab_sheet_item_intermediate), withText("Intermediate"), isDisplayed()));
        textView2.check(matches(withText("Intermediate")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.fab_sheet_item_full), withText("Full"), isDisplayed()));
        textView3.check(matches(withText("Full")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.fab_sheet_item_basic), withText("Basic"), isDisplayed()));
        appCompatTextView.perform(click());

        pressBack();

        ViewInteraction mDButton = onView(
                allOf(withId(R.id.buttonDefaultPositive), withText("Discard"), isDisplayed()));
        mDButton.perform(click());

        ViewInteraction sheetFab2 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        sheetFab2.perform(click());

        ViewInteraction textView20 = onView(
                allOf(withId(R.id.fab_sheet_item_basic), withText("Basic"), isDisplayed()));
        textView20.check(matches(withText("Basic")));

        ViewInteraction textView21 = onView(
                allOf(withId(R.id.fab_sheet_item_intermediate), withText("Intermediate"), isDisplayed()));
        textView21.check(matches(withText("Intermediate")));

        ViewInteraction textView22 = onView(
                allOf(withId(R.id.fab_sheet_item_full), withText("Full"), isDisplayed()));
        textView22.check(matches(withText("Full")));

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.fab_sheet_item_intermediate), withText("Intermediate"), isDisplayed()));
        appCompatTextView2.perform(click());

        pressBack();

        ViewInteraction mDButton2 = onView(
                allOf(withId(R.id.buttonDefaultPositive), withText("Discard"), isDisplayed()));
        mDButton2.perform(click());

        ViewInteraction sheetFab3 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        sheetFab3.perform(click());

        ViewInteraction textView30 = onView(
                allOf(withId(R.id.fab_sheet_item_basic), withText("Basic"), isDisplayed()));
        textView30.check(matches(withText("Basic")));

        ViewInteraction textView31 = onView(
                allOf(withId(R.id.fab_sheet_item_intermediate), withText("Intermediate"), isDisplayed()));
        textView31.check(matches(withText("Intermediate")));

        ViewInteraction textView32 = onView(
                allOf(withId(R.id.fab_sheet_item_full), withText("Full"), isDisplayed()));
        textView32.check(matches(withText("Full")));

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.fab_sheet_item_full), withText("Full"), isDisplayed()));
        appCompatTextView3.perform(click());

        pressBack();

        ViewInteraction mDButton3 = onView(
                allOf(withId(R.id.buttonDefaultPositive), withText("Discard"), isDisplayed()));
        mDButton3.perform(click());

    }
}
