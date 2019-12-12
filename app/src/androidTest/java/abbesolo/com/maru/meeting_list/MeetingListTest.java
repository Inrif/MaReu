package abbesolo.com.maru.meeting_list;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import abbesolo.com.maru.R;
import abbesolo.com.maru.ui.meeting_list.ListMeetingActivity;
import abbesolo.com.maru.utils.DeleteViewAction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static abbesolo.com.maru.utils.RecyclerViewItemCountAssertion.withItemCount;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.util.EnumSet.allOf;
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
    public TypeSafeMatcher<View> myMeetingList_shouldDisplay_myCreatedMeeting()
        {
       //  When : we click on the fab button
           onView(ViewMatchers.withId(R.id.ajout)).perform(click());


            ViewInteraction edittext1 = onView(allOf(withId(R.id.meeting_topic), withText("New App"),
                            childAtPosition(childAtPosition(withId(R.id.rec), 0), 0), isDisplayed()));
            edittext1.check(matches(withText("New App")));


            //Create a ViewInteraction to click on the spinner
            ViewInteraction appCompatSpinner = onView(allOf(withId(R.id.meeting_room),
                    childAtPosition(allOf(withId(R.id.spinner),
                            childAtPosition(withId(android.R.id.custom), 0)), 0),
                    isDisplayed()));
            appCompatSpinner.perform(click());



            ViewInteraction edittext2 = onView(
                    allOf(withId(R.id.member), withText("romulus@yahoo.fr, romulus@yahoo.fr,romulus@yahoo.fr"),
                            childAtPosition(childAtPosition(withId(R.id.rec), 0), 0), isDisplayed()));
            edittext2.check(matches(withText("romulus@yahoo.fr,romulus@yahoo.fr,romulus@yahoo.fr")));


            ViewInteraction timeButton = onView(allOf(withId(R.id.btn_time), withText("12H30"),
                            childAtPosition(childAtPosition(withId(R.id.rec), 0), 0),
                            isDisplayed()));
            timeButton.check(matches(withText("12H30")));

            ViewInteraction dateButton = onView(allOf(withId(R.id.btn_date), withText("9:30"),
                    childAtPosition(childAtPosition(withId(R.id.rec), 0), 0),
                            isDisplayed()));
            dateButton.check(matches(withText("8-12-2019")));


            onView(ViewMatchers.withId(R.id.envoie)).perform(click());


            // Then : the number of element is 2
            onView(withId(R.id.rec)).check(withItemCount(ITEMS_COUNT=1));




           private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

         return new TypeSafeMatcher<View>() {
                @Override
               public void describeTo(org.hamcrest.Description description) {
                    description.appendText ("Child at position" + position + "in parent");
                    parentMatcher.describeTo (description);
                }



                @Override
                public boolean matchesSafely(View view) {
                    ViewParent parent = view.getParent();
                    return parent instanceof ViewGroup && parentMatcher.matches(parent)
                            && view.equals(((ViewGroup) parent).getChildAt(position));
                }
            };
        }


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


}


