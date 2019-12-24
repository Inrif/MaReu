package abbesolo.com.maru.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import abbesolo.com.maru.model.Meeting;

import static abbesolo.com.maru.ui.meeting_list.ListMeetingActivity.FILTER_BY_DATE;

public class DummyMeetingApiService implements MeetingApiService {

    public List<Meeting> meetings = DummyMeetingGenerator.generateMeetings ();
    private String mText;

    // List<Meeting> filtered = new ArrayList<> ();


    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove (meeting);

    }

    @Override
    public List<Meeting> getFiltredMeetings(int filter) {

        Collections.sort (meetings, new Comparator<Meeting> () {
            @Override
            public int compare(Meeting t1, Meeting t2) {
                if (filter == FILTER_BY_DATE) {
                    return t2.getMeetingTime ().compareTo (t1.getMeetingTime ());
                }

                return t2.getMeetingRoom ().getRoomName ().compareTo (t1.getMeetingRoom ().getRoomName ());

            }

        });


        return meetings;

    }

    @Override
    public ArrayList<Meeting> filterDate(String date){

        ArrayList<Meeting> MeetingByDateSorted = new ArrayList<>();
        for (Meeting m : meetings) {
            if (m.getMeetingTime ().equalsIgnoreCase (date)) {
                MeetingByDateSorted.add(m);
            }
        }
        return MeetingByDateSorted;
    }



    @Override
    public ArrayList<Meeting> filterRoom(String room){

        ArrayList<Meeting> MeetingByRoomSorted = new ArrayList<>();
        for (Meeting m : meetings) {
            if (m.getMeetingRoom ().getRoomName().equalsIgnoreCase (room)){
                MeetingByRoomSorted.add(m);
            }
        }
        return MeetingByRoomSorted;
    }

    @Override

    public ArrayList<Meeting> filter(String text){
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


