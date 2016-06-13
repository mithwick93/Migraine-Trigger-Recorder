package shehan.com.migrainetrigger.view.activity;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationDrawerTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testGenerated() {
        // Used to provide time delays between actions, see details at http://droidtestlab.com/delay.html


        // Click at ImageButton with child index 1 of parent with id R.id.main_toolbar
        onView(nthChildOf(withId(R.id.main_toolbar), 1)).perform(click());

        // Click at ImageButton with child index 1 of parent with id R.id.main_toolbar
        onView(nthChildOf(withId(R.id.main_toolbar), 1)).perform(click());


        // Click at NavigationMenuItemView with child index 1 of parent with id R.id.design_navigation_view
        onView(withId(R.id.design_navigation_view)).perform(scrollToPosition(1));
        onView(nthChildOf(withId(R.id.design_navigation_view), 1)).perform(click());


        // Click at ImageButton with child index 1 of parent with id R.id.main_toolbar
        onView(nthChildOf(withId(R.id.main_toolbar), 1)).perform(click());


        // Click at NavigationMenuItemView with child index 2 of parent with id R.id.design_navigation_view
        onView(withId(R.id.design_navigation_view)).perform(scrollToPosition(2));
        onView(nthChildOf(withId(R.id.design_navigation_view), 2)).perform(click());


        // Click at ImageButton with child index 1 of parent with id R.id.main_toolbar
        onView(nthChildOf(withId(R.id.main_toolbar), 1)).perform(click());


        // Click at NavigationMenuItemView with child index 3 of parent with id R.id.design_navigation_view
        onView(withId(R.id.design_navigation_view)).perform(scrollToPosition(3));
        onView(nthChildOf(withId(R.id.design_navigation_view), 3)).perform(click());


        // Click at ImageButton with child index 1 of parent with id R.id.main_toolbar
        onView(nthChildOf(withId(R.id.main_toolbar), 1)).perform(click());


        // Click at NavigationMenuItemView with child index 9 of parent with id R.id.design_navigation_view
        onView(withId(R.id.design_navigation_view)).perform(scrollToPosition(9));
        onView(nthChildOf(withId(R.id.design_navigation_view), 9)).perform(click());


        // Click at ImageButton with child index 1 of parent with id R.id.settings_toolbar
        onView(nthChildOf(withId(R.id.settings_toolbar), 1)).perform(click());


        // Click at ImageButton with child index 1 of parent with id R.id.main_toolbar
        onView(nthChildOf(withId(R.id.main_toolbar), 1)).perform(click());


        // Click at NavigationMenuItemView with child index 10 of parent with id R.id.design_navigation_view
        onView(withId(R.id.design_navigation_view)).perform(scrollToPosition(10));
        onView(nthChildOf(withId(R.id.design_navigation_view), 10)).perform(click());


        // Click at ImageButton with child index 1 of parent with id R.id.main_toolbar
        onView(nthChildOf(withId(R.id.main_toolbar), 1)).perform(click());


        // Click at NavigationMenuItemView with child index 1 of parent with id R.id.design_navigation_view
        onView(withId(R.id.design_navigation_view)).perform(scrollToPosition(1));
        onView(nthChildOf(withId(R.id.design_navigation_view), 1)).perform(click());


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

}