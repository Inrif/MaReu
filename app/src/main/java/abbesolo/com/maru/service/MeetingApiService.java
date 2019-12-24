package abbesolo.com.maru.service;

import java.util.ArrayList;
import java.util.List;

import abbesolo.com.maru.model.Meeting;


/**
 * Neighbour API client
 */

public interface MeetingApiService {


        /**
         * Get all my Neighbours
         * @return {@link List}
         */
        List<Meeting> getMeetings();

        /**
         * Deletes a neighbour
         * @param meeting
         */
        void deleteMeeting(Meeting meeting);

        List<Meeting> getFiltredMeetings( int filter);

        public ArrayList<Meeting> filterDate(String date);

        public ArrayList<Meeting> filterRoom(String room);
        ArrayList<Meeting> filter(String text);





    }


