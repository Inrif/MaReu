package abbesolo.com.maru.model;

import java.util.List;

public class Meeting {


    private Integer id;
    private Room mMeetingRoom;
    private String mMeetingTime;
    private String mMeetingTopic;
    private String mMeetingDate;
    private List<Participant> mMeetingParticipantList;





    public Meeting(Integer id, Room meetingRoom,String meetingDate, String meetingTime, String meetingTopic, List<Participant> meetingParticipant) {
        this.id = id;
        mMeetingRoom = meetingRoom;
        mMeetingTime = meetingTime;
        mMeetingDate = meetingDate;
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

    public String getMeetingDate() {
        return mMeetingDate;
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

    public void setMeetingDate(String meetingDate) {
        mMeetingDate = meetingDate;
    }


    public List<Participant> getMeetingParticipantList() {
        return mMeetingParticipantList;
    }

    public void setMeetingParticipantList(List<Participant> meetingParticipantList) {
        mMeetingParticipantList = meetingParticipantList;
    }





}
