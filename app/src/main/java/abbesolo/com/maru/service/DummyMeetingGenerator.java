package abbesolo.com.maru.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abbesolo.com.maru.R;
import abbesolo.com.maru.model.Meeting;
import abbesolo.com.maru.model.Participant;
import abbesolo.com.maru.model.Room;

public abstract class DummyMeetingGenerator {

    public static List<Room> Rooms = Arrays.asList (
            new Room ("Salle 1", R.drawable.re1),
            new Room ("Salle 2", R.drawable.re2),
            new Room ("Salle 7", R.drawable.re3),
            new Room ("Salle 4", R.drawable.ic_brightness_1_black_24dp),
            new Room ("Salle 5", R.drawable.ic_brightness_1_black_24dp),
            new Room ("Salle 6", R.drawable.ic_brightness_1_black_24dp),
            new Room ("Salle 3", R.drawable.re3),
            new Room ( "Salle 8", R.drawable.re8),
            new Room ("Salle 9", R.drawable.ic_brightness_1_black_24dp),
            new Room ( "Salle 10", R.drawable.ic_brightness_1_black_24dp)
    );


    public static List<Participant> Participants = Arrays.asList (
            new Participant (1,"romulus@yahoo.fr"),
            new Participant (2,"romulus@yahoo.fr"),
            new Participant (3,"romulus@yahoo.fr"),
            new Participant (4,"romulus@yahoo.fr")

    );


    public static List<Meeting> DummyMeetings = Arrays.asList (
            new Meeting (0, Rooms.get (0), "8-12-2019 9:30", "New App", Participants),
            new Meeting (1, Rooms.get (1), "10-12-2019 10:30", "zo App", Participants),
            new Meeting (2, Rooms.get (2), "11-12-2019 12:28", "New App", Participants)
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<> (DummyMeetings);
    }
}
