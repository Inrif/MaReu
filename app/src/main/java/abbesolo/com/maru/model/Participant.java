package abbesolo.com.maru.model;

public class Participant {

    private String mEmail;
    private Integer mparticipantId;

    public Participant(Integer mparticipantId, String email) {
        mEmail = email;
        this.mparticipantId = mparticipantId;
    }

    public Integer getMparticipantId() {
        return mparticipantId;
    }


    public void setMparticipantId(Integer mparticipantId) {
        this.mparticipantId = mparticipantId;
    }



    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }






}
