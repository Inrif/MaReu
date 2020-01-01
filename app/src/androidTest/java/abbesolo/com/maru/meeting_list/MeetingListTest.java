package abbesolo.com.maru.meeting_list;

import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import abbesolo.com.maru.R;
import abbesolo.com.maru.ui.meeting_list.ListMeetingActivity;
import abbesolo.com.maru.utils.DeleteViewAction;
import abbesolo.com.maru.utils.RecyclerViewItemCountAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static abbesolo.com.maru.R.id.textView;
import static abbesolo.com.maru.utils.RecyclerViewItemCountAssertion.withItemCount;
import static abbesolo.com.maru.utils.ToolbarMatcher.childAtPosition;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class MeetingListTest {

    // This is fixed
    private static int ITEMS_COUNT = 3;
    private ListMeetingActivity mActivity;
    public static final String SALLE = "Salle I";

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule (ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity ();
        assertThat (mActivity, notNullValue ());

    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView (withId (R.id.rec)).check (matches (hasMinimumChildCount (1)));
    }

    @Test
    public void myMeetingList_clickAction_shouldDisplayFormActivity() {
        // When : we click on the fab button
        onView (ViewMatchers.withId (R.id.ajout)).perform (click ());

        // Then : Go to the Add meeting in FormActivity
        onView (ViewMatchers.withId (R.id.form)).check (matches (isDisplayed ()));

    }

    @Test
    public void myMeetingList_shouldDisplay_myCreatedMeeting() {

        // Given :  We check if ListMeeting isDisplayed and if it contains ITEMS_COUNT
        onView (withId (R.id.rec)).check (matches (isDisplayed ()));
        onView (withId (R.id.rec)).check (withItemCount (ITEMS_COUNT));
        // When: We remove and add a meeting
        onView (withId (R.id.rec)).perform (actionOnItemAtPosition (0, new DeleteViewAction ()));
        onView (withId (R.id.rec)).check (withItemCount (ITEMS_COUNT - 1));
        onView (withId (R.id.ajout)).perform (click ());
        onView (withId (R.id.form)).check (matches (isDisplayed ()));
        onView (withId (R.id.meeting_topic)).perform (replaceText ("New App"));
        onView (withId (R.id.spinner)).perform (click ());
        onView (allOf (withId (textView), withText ("Salle 1"))).perform (click ());
        onView (withId (R.id.member)).perform (replaceText ("romulus@yahoo.fr, romulus@yahoo.fr,romulus@yahoo.fr"));
        onView (withId (R.id.btn_date)).perform (click ()); //Edit Text
        onView (withClassName (Matchers.equalTo (DatePicker.class.getName ()))).perform (PickerActions.setDate (2019, 12, 23));
        onView (withText ("OK")).perform (click ());
        onView (withId (R.id.envoie)).perform (click ());
        // Then : We check if ListMeeting isDisplayed and if it contains ITEMS_COUNT
        onView (withId (R.id.rec)).check (matches (isDisplayed ()));
        onView (withId (R.id.rec)).check (withItemCount (ITEMS_COUNT));

    }


    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView (withId (R.id.rec)).check (withItemCount (ITEMS_COUNT));
        // When perform a click on a delete icon
        onView (withId (R.id.rec)).perform (actionOnItemAtPosition (1, new DeleteViewAction ()));
        // Then : the number of element is 2
        onView (withId (R.id.rec)).check (withItemCount (ITEMS_COUNT - 1));
    }



    /**
     * check if the filter function in the menu filtered well and return good list
     */
    @Test
    public void meetingList_clickOnFilterSalle_thenAttentFilterList(){
        onView(withId(R.id.rec)).check(matches(isDisplayed()));

        //Create a ViewInteraction to click on the toolbar hamburger
        ViewInteraction actionMenuItemView = onView(Matchers.allOf(withContentDescription("Info"),
                childAtPosition(childAtPosition(withId(R.id.toolbar), 1), 0),
                isDisplayed()));
        actionMenuItemView.perform(click());

        // Create a ViewInteraction to click on the item menu
        ViewInteraction appCompatTextView = onView(Matchers.allOf(withId(R.id.title),
                withText("Filtrer par salle"),
                childAtPosition(childAtPosition(withId(R.id.content), 0), 0),
                isDisplayed()));

        appCompatTextView.perform(click());
        //Create a ViewInteraction to click on the spinner
        ViewInteraction appCompatSpinner = onView(Matchers.allOf(withId(R.id.spinner_choice),
                childAtPosition(Matchers.allOf(withId(R.id.layout_spinner_filter),
                        childAtPosition(withId(android.R.id.custom), 0)), 0),
                isDisplayed()));

        appCompatSpinner.perform(click());

        //Click on the fourth position in the spinner list (salle 1)
        onData(anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(0).perform(click());
        //Create a ViewInteraction to click on the button to accept the filter choice
        ViewInteraction appCompatButton = onView(allOf(withId(android.R.id.button1), withText("Filtrer"),
                childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0),
                        3)));
        appCompatButton.perform(scrollTo(), click());
        // then attent to show only the filtered list with1 element
        onView(withId(R.id.rec)).check(RecyclerViewItemCountAssertion.withItemCount(1));
    }

    @Test
    public void meetingList_clickOnFilterDate_thenAttentFilterList(){
        onView(withId(R.id.rec)).check(matches(isDisplayed()));

        //Create a ViewInteraction to click on the toolbar hamburger
        ViewInteraction actionMenuItemView = onView(Matchers.allOf(withContentDescription("Info"),
                childAtPosition(childAtPosition(withId(R.id.toolbar), 1), 0),
                isDisplayed()));

        actionMenuItemView.perform(click());
        // Create a ViewInteraction to click on the item menu
        ViewInteraction appCompatTextView = onView(Matchers.allOf(withId(R.id.title),
                withText("Filtrer par date"),
                childAtPosition(childAtPosition(withId(R.id.content), 0), 0),
                isDisplayed()));

        appCompatTextView.perform(click());
        //Create a ViewInteraction to click on the spinner
        ViewInteraction appCompatSpinner = onView(Matchers.allOf(withId(R.id.spinner_choice),
                childAtPosition(Matchers.allOf(withId(R.id.layout_spinner_filter),
                        childAtPosition(withId(android.R.id.custom), 0)), 0),
                isDisplayed()));

        appCompatSpinner.perform(click());
        //Click on the first position in the spinner list (28/09/19)
        onData(anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(0).perform(click());
        //Create a ViewInteraction to click on the button to accept the filter choice
        ViewInteraction appCompatButton = onView(Matchers.allOf(withId(android.R.id.button1), withText("Filtrer"),
                childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0),
                        3)));
        appCompatButton.perform(scrollTo(), click());
        // then attent to show only the filtered list with 1 reunion
        onView(withId(R.id.rec)).check(RecyclerViewItemCountAssertion.withItemCount(1));
    }

}




