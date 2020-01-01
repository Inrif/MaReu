package abbesolo.com.maru.ui.meeting_list;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import abbesolo.com.maru.model.Meeting;
import abbesolo.com.maru.model.Room;

import static abbesolo.com.maru.service.DummyMeetingGenerator.Rooms;

public abstract class FilterDialog {


    private static ArrayList<Room> mRooms;

    public static void initListSpinner(){

       mRooms = new ArrayList<> ();

        for (Room item : Rooms){
            mRooms.add(new Room (item.getRoomName (),item.getRes ()));
        }

    }

    public static void initRoomSpinner(View view, Spinner spinner){
        initListSpinner();
        RoomAdapter mRoomAdapter = new RoomAdapter(view.getContext(), mRooms);
        spinner.setAdapter(mRoomAdapter);
    }

    public static List<String> initSpinnerDate(List<Meeting> meetings){
        List<String> arrayList = new ArrayList<>();
        Set<String> set = new HashSet<> ();
        for (Meeting m : meetings){
            set.add(m.getMeetingDate ());
        }
        for (String s : set){
            arrayList.add(s);
        }
        return arrayList;
    }

//    public static boolean checkRoomAndDate(String room, String date,String hour, List<Meeting> meetings){
//        for (Meeting m : meetings){
//            if (room.equals(m.getMeetingRoom ()) && date.equalsIgnoreCase(m.getMeetingTime () && hour.equalsIgnoreCase(m..getTime())){
//                return false;
//            }
//        }
//        return true;
//    }

    public static String checkDate(String date){
        if (date == null){
            Date date1 = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            date = simpleDateFormat.format(date1);
            return date;
        }else {
            return date;
        }
    }
//    public static String checkHourNull(String hour){
//        if (hour == null){
//            Date date1 = new Date();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);
//            hour = simpleDateFormat.format(date1);
//            return hour;
//        }else {
//            return hour;
//        }
//    }
//
//    public static boolean checkHour(int hour, int minute){
//        if (hour < 9 || (hour >= 19 && minute >= 1)){
//            return false;
//        }else {
//            return true;
//        }
//    }

    public static boolean checkInputText(EditText textView){
        if (textView.getText().toString().equals("")){
            return false;
        }else {
            return true;
        }
    }

//    public static String makeMailString(String mail){
//        String str = "";
//        String[] arrayString = mail.toLowerCase().split("[,;.:!§/$@?&#|]+");
//
//        for (String a : arrayString){
//            a += "@lamzone.com, ";
//            str += a;
//        }
//        return str;
//    }
}
