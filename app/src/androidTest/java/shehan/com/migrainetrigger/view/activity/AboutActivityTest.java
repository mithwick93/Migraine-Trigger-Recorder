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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AboutActivityTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> mActivityTestRule = new ActivityTestRule<>(SplashScreenActivity.class);

    @Test
    public void aboutActivityTest() {
        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("About"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txt_about_licenses), withText("Open source licenses"), isDisplayed()));
        textView.check(matches(withText("Open source licenses")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.txt_about_licenses), withText("Open source licenses"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction mDButton = onView(
                allOf(withId(R.id.buttonDefaultPositive), withText("Ok"),
                        withParent(allOf(withId(R.id.root),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        mDButton.perform(click());

    }
}
