package abbesolo.com.maru.ui.meeting_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import abbesolo.com.maru.R;
import abbesolo.com.maru.di.DI;
import abbesolo.com.maru.events.DeleteMeetingEvent;
import abbesolo.com.maru.model.Meeting;
import abbesolo.com.maru.model.Participant;
import abbesolo.com.maru.model.Room;
import abbesolo.com.maru.service.MeetingApiService;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static abbesolo.com.maru.ui.meeting_list.FilterDialog.initRoomSpinner;
import static abbesolo.com.maru.ui.meeting_list.FilterDialog.initSpinnerDate;

public class ListMeetingActivity extends AppCompatActivity {

    private List<Meeting> mMeetings;
    private   List<Meeting> mFilteredList;
    private MeetingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int REQUEST_FOR_ACTIVITY_CODE = 100;
    private MeetingApiService mApiService;
    private   List<Meeting> SortedList;
    String itemName = "";
    private CustomAdapter mCustomAdapter;

    public static final int FILTER_BY_DATE = 0, FILTER_BY_PLACE = 1;

    private static final String FILT_BY_DATE ="MeetingDate", FILT_BY_ROOM ="MeetingRoom";


    // UI Components
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rec)
    RecyclerView mRecyclerView;
    @BindView(R.id.ajout)
    FloatingActionButton fab;
    @BindView(R.id.mConstraint)
    ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_list_meeting);
        mApiService = DI.getMeetingApiService ();

        ButterKnife.bind (this);

        setSupportActionBar (toolbar);

        initList ();


        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListMeetingActivity.this, FormActivity.class);
                startActivityForResult (intent, REQUEST_FOR_ACTIVITY_CODE);

//                Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction ("Action", null).show ();
            }
        });
    }


    private void initList() {

        mMeetings = mApiService.getMeetings ();
    //mFilteredList = mApiService.getFiltredMeetings (int filter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager (this);
        mAdapter = new MeetingAdapter (mMeetings, this);
        mRecyclerView.setLayoutManager (mLayoutManager);
        mRecyclerView.setAdapter (mAdapter);


    }

    private void initListAdapter(List<Meeting> meetings) {
        mRecyclerView.setAdapter(new MeetingAdapter (meetings, this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.filtre_by_place: {
                configureAndShowAlertDialogPlace();
                return true;
            }

            case R.id.filtre_by_date: {
                configureAndShowAlertDialogDate();
                return true;
            }

            case R.id.action_no_filter:{
                initList ();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }




    private void configureAndShowAlertDialogPlace() {

        AlertDialog.Builder builder = new AlertDialog.Builder (this);

        View view = LayoutInflater.from (this).inflate (R.layout.filter_list_dialog, null);
        Spinner spinner = view.findViewById(R.id.spinner_choice);
       initRoomSpinner(view, spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Room rom = (Room) spinner.getSelectedItem();
                itemName = rom.getRoomName();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        builder.setTitle("Sélectionnez une valeur")
                .setView(view)
                .setPositiveButton("Filtrer",
                        (dialog, which) -> {
                            List<Meeting> meetings = mApiService.filter (itemName);
                           initListAdapter (meetings);
                        })
                .setNegativeButton("Annuler",
                        (dialog, which) -> {});

        builder.create().show();


    }


    private List<Meeting> configureAndShowAlertDialogDate(){
        AlertDialog.Builder builder = new AlertDialog.Builder (this);

        View view = LayoutInflater.from (this).inflate (R.layout.filter_list_dialog, null);

        List<String> arrayList= initSpinnerDate(mMeetings);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = view.findViewById(R.id.spinner_choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemName = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        builder.setTitle("Sélectionnez une valeur")
                .setView(view)
                .setPositiveButton("Filtrer",
                        (dialog, which) -> {
                           List<Meeting> meetings = mApiService.filter(itemName);
                            initListAdapter (meetings);
                        })
                .setNegativeButton("Annuler",
                        (dialog, which) -> {});

        builder.create().show();
       return mMeetings;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);


        // Recuperer les donnees du formulaire
        // Log.d ("Code", "onActivityResult: " +requestCode);
        if (resultCode == Activity.RESULT_OK) {

            String MeetingTopic = data.getExtras ().getString ("MeetingTopic");
            String MeetingRoom = data.getExtras ().getString ("MeetingRoom");
            int MeetingRoomPicture = data.getExtras ().getInt ("MeetingRoomPicture");
            String MeetingTime = data.getExtras ().getString ("MeetingTime");
            String MeetingMember = data.getExtras ().getString ("MeetingMember");
            String MeetingDate = data.getExtras ().getString ("MeetingDate");
//
////            Log.e ("resultat", "MeetingMember: " + MeetingMember );
//            Log.e ("resultat", "MeetingRoom: " + MeetingRoom );
////            Log.e ("resultat", "MeetingRoomPicture: " + MeetingRoomPicture );
//           Log.e ("resultat", "MeetingTime: " + MeetingTime );
////            Log.e ("resultat", "MeetingTopic: " + MeetingTopic );
//            Log.e ("resultat", "MeetingDate: " + MeetingDate );

            Room room = new Room (MeetingRoom, MeetingRoomPicture);
            List<Participant> participantList = new ArrayList<> ();

            Participant participant = new Participant (1, MeetingMember);
            participantList.add (participant);

            mMeetings.add (new Meeting (1, room, MeetingTime, MeetingTopic, participantList));

            mAdapter.notifyDataSetChanged ();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mApiService.deleteMeeting (event.meeting);
        initList ();

    }


}