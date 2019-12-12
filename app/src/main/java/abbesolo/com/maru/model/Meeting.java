package abbesolo.com.maru.model;

import java.util.List;

public class Meeting {


    private Integer id;
    private Room mMeetingRoom;
    private String mMeetingTime;
    private String mMeetingTopic;
    private List<Participant> mMeetingParticipantList;

    public Meeting(Integer id, Room meetingRoom, String meetingTime, String meetingTopic, List<Participant> meetingParticipant) {
        this.id = id;
        mMeetingRoom = meetingRoom;
        mMeetingTime = meetingTime;
        mMeetingTopic = meetingTopic;
        mMeetingParticipantList = meetingParticipant;

    }


    public Integer getId() {
        return id;
    }

    public Room getMeetingRoom() {
        return mMeetingRoom;
    }

    public String getMeetingTime() {
        return mMeetingTime;
    }

    public String getMeetingTopic() {
        return mMeetingTopic;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setMeetingRoom(Room meetingRoom) {
        mMeetingRoom = meetingRoom;
    }

    public void setMeetingTime(String meetingTime) {
        mMeetingTime = meetingTime;
    }

    public void setMeetingTopic(String meetingTopic) {
        mMeetingTopic = meetingTopic;
    }

    public List<Participant> getMeetingParticipantList() {
        return mMeetingParticipantList;
    }

    public void setMeetingParticipantList(List<Participant> meetingParticipantList) {
        mMeetingParticipantList = meetingParticipantList;
    }





}
