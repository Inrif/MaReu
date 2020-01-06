package abbesolo.com.maru.model;

public class Meeting {


    private Integer id;
    private Room mMeetingRoom;
    private String mMeetingTime;
    private String mMeetingTopic;
    private String mMeetingDate;
    private String mParticpant;





    public Meeting( Room meetingRoom,String meetingDate, String meetingTime, String meetingTopic, String participant ) {

        mMeetingRoom = meetingRoom;
        mMeetingTime = meetingTime;
        mMeetingDate = meetingDate;
        mMeetingTopic = meetingTopic;
        mParticpant = participant;

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
    public String getParticpant() {
        return mParticpant;
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

    public void setParticpant(String particpant) {
        mParticpant = particpant;
    }



}
