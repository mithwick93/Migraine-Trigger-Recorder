package shehan.com.migrainetrigger.view.activity;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import shehan.com.migrainetrigger.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class FAQActivityTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(FAQActivity.class);

    @Test
    public void testGenerated() {
        // Used to provide time delays between actions, see details at http://droidtestlab.com/delay.html
        IdlingResource idlingResource;


        // Click at RelativeLayout with child index 0 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(0));
        onView(nthChildOf(withId(R.id.topics_recycler_view), 0)).perform(click());

        pressBack();

        // Click at RelativeLayout with child index 1 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(1));
        onView(nthChildOf(withId(R.id.topics_recycler_view), 1)).perform(click());

        pressBack();

        // Click at RelativeLayout with child index 2 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(2));
        onView(nthChildOf(withId(R.id.topics_recycler_view), 2)).perform(click());

        pressBack();

        // Click at RelativeLayout with child index 3 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(3));
        onView(nthChildOf(withId(R.id.topics_recycler_view), 3)).perform(click());

        pressBack();

        // Click at RelativeLayout with child index 4 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(4));
        onView(nthChildOf(withId(R.id.topics_recycler_view), 4)).perform(click());

        pressBack();


        // Click at RelativeLayout with child index 5 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(5));
        onView(nthChildOf(withId(R.id.topics_recycler_view), 5)).perform(click());

        pressBack();


        // Click at RelativeLayout with child index 6 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(6));
        onView(nthChildOf(withId(R.id.topics_recycler_view), 6)).perform(click());

        pressBack();


        // Click at RelativeLayout with child index 7 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(7));
        onView(nthChildOf(withId(R.id.topics_recycler_view), 7)).perform(click());

        pressBack();

        // Swipe up at ScrollView with id R.id.full_record_scroll_view
        onView(withId(R.id.topics_recycler_view)).perform(swipeUp());

        // Click at RelativeLayout with child index 8 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(8));
        onView(withId(R.id.topics_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(8, click()));

        pressBack();

        // Click at RelativeLayout with child index 9 of parent with id R.id.topics_recycler_view
        onView(withId(R.id.topics_recycler_view)).perform(scrollToPosition(9));
        onView(withId(R.id.topics_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(9, click()));



    }

    public static ViewAction scrollToPosition(final int pos) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(android.support.v7.widget.RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "scroll to position";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((android.support.v7.widget.RecyclerView) view).scrollToPosition(pos);
            }
        };
    }

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return false;
                }
                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(group) && view.equals(group.getChildAt(childPosition));
            }
        };
    }

}