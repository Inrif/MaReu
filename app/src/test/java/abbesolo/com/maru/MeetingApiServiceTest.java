package abbesolo.com.maru;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import abbesolo.com.maru.di.DI;
import abbesolo.com.maru.model.Meeting;
import abbesolo.com.maru.service.DummyMeetingGenerator;
import abbesolo.com.maru.service.MeetingApiService;

import static abbesolo.com.maru.ui.meeting_list.ListMeetingActivity.FILTER_BY_DATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)

public class MeetingApiServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService ();
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings ();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DummyMeetings;
        assertThat (meetings, containsInAnyOrder (expectedMeetings.toArray ()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings ().get (0);
        service.deleteMeeting (meetingToDelete);
        assertFalse (service.getMeetings ().contains (meetingToDelete));

    }

    @Test
    public void getFilteredListWithSuccess() {

        int filter = FILTER_BY_DATE, FILTER_BY_PLACE;

        List<Meeting> meetings = service.getMeetings ();
        List<Meeting> sorted = service.getFiltredMeetings (FILTER_BY_DATE);

        Collections.sort (meetings, new Comparator<Meeting> () {
            @Override
            public int compare(Meeting t1, Meeting t2) {
                if (filter == FILTER_BY_DATE) {
                    return t2.getMeetingTime ().compareTo (t1.getMeetingTime ());
                } else {
                    return t2.getMeetingRoom ().getRoomName ().compareTo (t1.getMeetingRoom ().getRoomName ());
                }
            }
        });


        assertThat (meetings, containsInAnyOrder (sorted.toArray ()));
    }


}





