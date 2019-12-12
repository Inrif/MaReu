package abbesolo.com.maru.model;

public class Room {
    private String mRoomName;
    private int mRes;

    public Room(String roomName, int res) {
        mRoomName = roomName;
        mRes = res;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String roomName) {
        mRoomName = roomName;
    }

    public int getRes() {
        return mRes;
    }

    public void setRes(int res) {
        mRes = res;
    }
}
