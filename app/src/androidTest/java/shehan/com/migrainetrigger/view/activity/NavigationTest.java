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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> mActivityTestRule = new ActivityTestRule<>(SplashScreenActivity.class);

    @Test
    public void navigationTest() {
        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Home"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction imageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Severity"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction imageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton3.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Answers"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction imageButton4 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton4.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("F.A.Q."), isDisplayed()));
        appCompatCheckedTextView4.perform(click());

        ViewInteraction imageButton5 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(withId(R.id.faq_toolbar)),
                        isDisplayed()));
        imageButton5.perform(click());

        /*
        *Comment below section if no records (initial)
        */
        //---------------------------------------------------------------------
//        ViewInteraction imageButton6 = onView(
//                allOf(withContentDescription("Open navigation drawer"),
//                        withParent(withId(R.id.main_toolbar)),
//                        isDisplayed()));
//        imageButton6.perform(click());
//
//        ViewInteraction appCompatCheckedTextView5 = onView(
//                allOf(withId(R.id.design_menu_item_text), withText("Records"), isDisplayed()));
//        appCompatCheckedTextView5.perform(click());
//
//        ViewInteraction imageButton12 = onView(
//                allOf(withContentDescription("Navigate up"),
//                        withParent(withId(R.id.view_record_toolbar)),
//                        isDisplayed()));
//        imageButton12.perform(click());
//
//        ViewInteraction imageButton7 = onView(
//                allOf(withContentDescription("Open navigation drawer"),
//                        withParent(withId(R.id.main_toolbar)),
//                        isDisplayed()));
//        imageButton7.perform(click());
//
//        ViewInteraction appCompatCheckedTextView6 = onView(
//                allOf(withId(R.id.design_menu_item_text), withText("Report"), isDisplayed()));
//        appCompatCheckedTextView6.perform(click());
//
//        ViewInteraction imageButton13 = onView(
//                allOf(withContentDescription("Navigate up"),
//                        withParent(withId(R.id.report_toolbar)),
//                        isDisplayed()));
//        imageButton13.perform(click());

        //----------------------------------------------

        ViewInteraction imageButton8 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton8.perform(click());

        ViewInteraction appCompatCheckedTextView7 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Settings"), isDisplayed()));
        appCompatCheckedTextView7.perform(click());

        ViewInteraction imageButton9 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(withId(R.id.settings_toolbar)),
                        isDisplayed()));
        imageButton9.perform(click());

        ViewInteraction imageButton10 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton10.perform(click());

        ViewInteraction appCompatCheckedTextView8 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("About"), isDisplayed()));
        appCompatCheckedTextView8.perform(click());

        ViewInteraction imageButton11 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.main_toolbar)),
                        isDisplayed()));
        imageButton11.perform(click());

        ViewInteraction appCompatCheckedTextView9 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Home"), isDisplayed()));
        appCompatCheckedTextView9.perform(click());

        ViewInteraction sheetFab = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        sheetFab.perform(click());

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

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.fab_sheet_item_full), withText("Full"), isDisplayed()));
        appCompatTextView3.perform(click());

        pressBack();

        ViewInteraction mDButton3 = onView(
                allOf(withId(R.id.buttonDefaultPositive), withText("Discard"), isDisplayed()));
        mDButton3.perform(click());

    }
}
