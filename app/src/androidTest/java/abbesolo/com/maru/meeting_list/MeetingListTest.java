package abbesolo.com.maru.meeting_list;

import android.widget.DatePicker;
import android.widget.TextView;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import abbesolo.com.maru.R;
import abbesolo.com.maru.ui.meeting_list.ListMeetingActivity;
import abbesolo.com.maru.utils.DeleteViewAction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static abbesolo.com.maru.R.id.textView;
import static abbesolo.com.maru.utils.RecyclerViewItemCountAssertion.withItemCount;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
    private String[] salles = {"Salle 1 - 8-12-2019 9:30 - New App", "Salle 2 - 10-12-2019 10:30 - zo App", "Salle 7 - 11-12-2019 12:28 - New App"};
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

    @Test
    public void myMeetingList_byDefaultIsSortedByDateOrRoom() {
        RecyclerView recyclerView = mActivityRule.getActivity ().findViewById (R.id.rec);
        TextView textView1 = recyclerView.getChildAt (0).findViewById (R.id.meeting);
        Assert.assertEquals (salles[0], textView1.getText ());
        TextView textView2 = recyclerView.getChildAt (1).findViewById (R.id.meeting);
        Assert.assertEquals (salles[1], textView2.getText ());
        TextView textView3 = recyclerView.getChildAt (2).findViewById (R.id.meeting);
        Assert.assertEquals (salles[2], textView3.getText ());
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


}


