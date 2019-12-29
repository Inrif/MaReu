package abbesolo.com.maru.service;

import java.util.ArrayList;
import java.util.List;

import abbesolo.com.maru.model.Meeting;

public class DummyMeetingApiService implements MeetingApiService {

    public List<Meeting> meetings = DummyMeetingGenerator.generateMeetings ();
    private String mText;




    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove (meeting);

    }

    @Override

    public List<Meeting> filter(String text){
        mText = text;
        ArrayList<Meeting> reunionSorted = new ArrayList<>();
        for (Meeting m : meetings) {
            if (m.getMeetingRoom ().getRoomName ().equalsIgnoreCase(text) || m.getMeetingTime ().equals(text)){
                reunionSorted.add(m);
            }
        }
        return reunionSorted;
    }



}


