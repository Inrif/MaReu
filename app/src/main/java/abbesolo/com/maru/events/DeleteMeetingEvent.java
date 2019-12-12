package abbesolo.com.maru.events;

import abbesolo.com.maru.model.Meeting;

public class DeleteMeetingEvent {

    /**
     * Meeting to delete
     */
    public Meeting meeting;

    /**
     * Constructor.
     * @param meeting
     */
    public DeleteMeetingEvent(Meeting meeting){
        this.meeting = meeting;
    }


}


