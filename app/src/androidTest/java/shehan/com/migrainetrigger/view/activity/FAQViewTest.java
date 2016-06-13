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
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FAQViewTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> mActivityTestRule = new ActivityTestRule<>(SplashScreenActivity.class);

    @Test
    public void fAQViewTest() {
        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("F.A.Q."), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txt_definition), withText("Definition"), isDisplayed()));
        textView.check(matches(withText("Definition")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.txt_definition), withText("Symptoms"), isDisplayed()));
        textView2.check(matches(withText("Symptoms")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.txt_definition), withText("Causes"), isDisplayed()));
        textView3.check(matches(withText("Causes")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.txt_definition), withText("Risk factors"), isDisplayed()));
        textView4.check(matches(withText("Risk factors")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.txt_definition), withText("Complications"), isDisplayed()));
        textView5.check(matches(withText("Complications")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.txt_definition), withText("Tests and diagnosis"), isDisplayed()));
        textView6.check(matches(withText("Tests and diagnosis")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.txt_definition), withText("Treatments and drugs"), isDisplayed()));
        textView7.check(matches(withText("Treatments and drugs")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.txt_definition), withText("Treatments and drugs"), isDisplayed()));
        textView8.check(matches(withText("Treatments and drugs")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(2, click()));

        pressBack();

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView4.perform(actionOnItemAtPosition(3, click()));

        pressBack();

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView5.perform(actionOnItemAtPosition(4, click()));

        pressBack();

        ViewInteraction recyclerView6 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView6.perform(actionOnItemAtPosition(5, click()));

        pressBack();

        ViewInteraction recyclerView7 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView7.perform(actionOnItemAtPosition(6, click()));

        pressBack();

        ViewInteraction recyclerView8 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView8.perform(actionOnItemAtPosition(7, click()));

        pressBack();

        ViewInteraction recyclerView9 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView9.perform(actionOnItemAtPosition(8, click()));

        pressBack();

        ViewInteraction recyclerView10 = onView(
                allOf(withId(R.id.topics_recycler_view), isDisplayed()));
        recyclerView10.perform(actionOnItemAtPosition(9, click()));

        pressBack();

    }
}
