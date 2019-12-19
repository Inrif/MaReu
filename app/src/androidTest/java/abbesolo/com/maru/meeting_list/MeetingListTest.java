package abbesolo.com.maru.meeting_list;

import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)

public class MeetingListTest {

    // This is fixed
    private static int ITEMS_COUNT = 3;

    private String[] salles = {"Salle 1 - 8-12-2019 9:30 - New App", "Salle 2 - 10-12-2019 10:30 - zo App", "Salle 7 - 11-12-2019 12:28 - New App"};

    private ListMeetingActivity mActivity;


    public static final String SALLE = "Salle I";

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.rec))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withId(R.id.rec)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.rec))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction ()));
        // Then : the number of element is 2
        onView(withId(R.id.rec)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void myMeetingList_byDefaultIsSortedByDate_shouldDisplayTheRightItemAtTheFirstPlace() {
        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.rec);
        TextView textView1 = recyclerView.getChildAt(0).findViewById(R.id.meeting);
        Assert.assertEquals(salles[0],textView1.getText());
        TextView textView2 = recyclerView.getChildAt(1).findViewById(R.id.meeting);
        Assert.assertEquals(salles[1],textView2.getText());
        TextView textView3 = recyclerView.getChildAt(2).findViewById(R.id.meeting);
        Assert.assertEquals(salles[2],textView3.getText());

    }


    @Test
    public void myMeetingList_clickAction_shouldDisplayFormActivity()
    {
        // When : we click on the fab button
        onView(ViewMatchers.withId(R.id.ajout)).perform(click());

        // Then : Go to the Add meeting in FormActivity
        onView(ViewMatchers.withId(R.id.form)).check(matches(isDisplayed()));

    }

    @Test
    public void myMeetingList_shouldDisplay_myCreatedMeeting()
        {
            // When : check if meetinglist isDisplayed
            onView(withId(R.id.rec)).check(matches(isDisplayed()));
            // Given :
            onView(withId(R.id.rec)).check(withItemCount(ITEMS_COUNT));
            //  When : we click on the fab button
           onView(withId(R.id.ajout)).perform(click());
            // Then : Go to the Add meeting in FormActivity
            onView(withId(R.id.form)).check(matches(isDisplayed()));

            //Enter meeting Topic
            onView(withId(R.id.meeting_topic)).perform(replaceText("New App"));
//
////            // Click on the spinner
//            onView(withId(R.id.spinner)).perform(click());
//            onData(allOf(is(instanceOf(String.class)), is("Salle"))).perform(click());
//            onView(withId(textView))
//                    .check(matches(withText(containsString("Salle"))));



            // Click on the Spinner
            onView(withId(R.id.spinner)).perform(click());

            // Select a ROOM from the list
            onData(allOf(is(instanceOf(String.class)), is(SALLE))).perform(click());

            // Check that the Room label is updated with selected room
            onView(withId(textView)).check(matches(withText(SALLE)));




////            ViewInteraction customTextView = onView
////                    allOf(withId(R.id.textView), withText("Salle I"), isDisplayed()));
////            customTextView.perform(click());
//
//            //Create a ViewInteraction to click on the spinner
//            ViewInteraction appCompatSpinner = onView(allOf(withId(R.id.spinner),
//                    childAtPosition(allOf(withId(R.id.textView),
//                            childAtPosition(withId(android.R.id.), 0)), 0),
//                    isDisplayed()));
//            appCompatSpinner.perform(click());
//            onData(anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(2).perform(click());
//
//
//
//            onView(withId(R.id.spinner)).perform(replaceText (SALLE));
//            onData(anything()).atPosition(1).perform(click());
//            onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Salle I"))));
//

            //Enter meeting members
            onView(withId(R.id.member)).perform(replaceText("romulus@yahoo.fr, romulus@yahoo.fr,romulus@yahoo.fr"));

            //Enter meeting date
   //         onView(withId(R.id.btn_date)).perform(replaceText("8-12-2019"));

            onView(withId(R.id.btn_date)).perform(PickerActions.setDate(2017, 6, 30));

//            onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate(2020, 10, 22));
//            onView(withId(R.id.btn_date)).perform(click());
//
//            //Enter meeting time
//     //       onView(withId(R.id.btn_time)).perform(replaceText("9:30"));
//            onView(isAssignableFrom(TimePicker.class)).perform(PickerActions.setTime (9,  22));

            onView(withId(R.id.btn_time)).perform(PickerActions.setTime (7, 30));





            onView(withId(R.id.envoie)).perform(click());

            onView(withId(R.id.rec)).check(matches(isDisplayed()));


            // Then : the number of element is
            onView(withId(R.id.rec)).check(withItemCount(ITEMS_COUNT +1));



        }













//            onView(withId(R.id.meeting_room)).perform(click());
//            onData(allOf(is(instanceOf(String.class)), is("Hello")).perform(click()));
//            onView(withId(com.android.example.spinner.R.id.Spinner01)).perform(click());
//
//
//            ViewInteraction myspinner = onView(
//                    allOf(with






//            meeting_topic = (EditText) findViewById (R.id.meeting_topic);
//            meeting_members = (EditText) findViewById (R.id.member);
//
//            btnDatePicker = (Button) findViewById (R.id.btn_date);
//            btnTimePicker = (Button) findViewById (R.id.btn_time);
//            btnValider = (Button) findViewById (R.id.envoie);
//            btnAnnuler = (Button) findViewById (R.id.annuler);
//            txtDate = (EditText) findViewById (R.id.in_date);
//            txtTime = (EditText) findViewById (R.id.in_time);



//
//       // Then : Go to the Add meeting in FormActivity and add a meeting
//            onView(ViewMatchers.withId(R.id.form)).    check(matches(isDisplayed()));
//
//
//       // then : we click on the fab button
//           onView(ViewMatchers.withId(R.id.envoie));
//
//            RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.rec);
//            TextView textView1 = recyclerView.getChildAt(0).findViewById(R.id.meeting);
//           Assert.assertEquals(salles[0],textView1.getText());
//
//        // then : We check that we have 3 Reunions
//
//       // onView(ViewMatchers.withId(R.id.rec)).check(withItemCount(0));
//


// // When : We click on the toolbar and put a Date to change the list
//        onView(withId(R.id.menu_button)).perform(click());
//        onView(ViewMatchers.withText("Filtre par Date")).perform(click());
//        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate(2020, 10, 22));
//        onView(withId(android.R.id.button1)).perform(click());
//        onView(ViewMatchers.withId(R.id.fragment_list_reunions)).check(withItemCount(2));
//
//        // When : We click on the toolbar and choose All the Reunions
//        onView(withId(R.id.menu_button)).perform(click());
//        onView(ViewMatchers.withText("Toutes les Réunions")).perform(click());
//
//        // Then : The list has all the reunions
//        onView(ViewMatchers.withId(R.id.fragment_list_reunions)).check(withItemCount(ITEMS_COUNT));




}


