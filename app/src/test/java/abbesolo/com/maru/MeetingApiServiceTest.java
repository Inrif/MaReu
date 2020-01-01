package abbesolo.com.maru;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import abbesolo.com.maru.di.DI;
import abbesolo.com.maru.model.Meeting;
import abbesolo.com.maru.service.DummyMeetingGenerator;
import abbesolo.com.maru.service.MeetingApiService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
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
    public void filterWithSuccess() {
        String text = "Salle 1";
        Meeting m1 = service.getMeetings ().get (0);

      List<Meeting> meetingSorted = service.filter (text);

        assertEquals(1, meetingSorted.size());
    }


}





