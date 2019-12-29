package abbesolo.com.maru.service;

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


        List<Meeting> filter(String text);





    }


