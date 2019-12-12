package abbesolo.com.maru.ui.meeting_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

public class ListMeetingActivity extends AppCompatActivity {

    private List<Meeting> mMeetings;
    private   List<Meeting> mFilteredList;
    private MeetingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int REQUEST_FOR_ACTIVITY_CODE = 100;
    private MeetingApiService mApiService;

    public static final int FILTER_BY_DATE = 0, FILTER_BY_PLACE = 1;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();


        if (id == R.id.meeting_filtre ) {

           mFilteredList = mApiService.getFiltredMeetings(FILTER_BY_DATE);
            initList ();
          return true;


        }else {

            mFilteredList = mApiService.getFiltredMeetings(FILTER_BY_PLACE);
            initList ();

        }


        return super.onOptionsItemSelected (item);


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

//            Log.e ("resultat", "MeetingMember: " + MeetingMember );
//            Log.e ("resultat", "MeetingRoom: " + MeetingRoom );
//            Log.e ("resultat", "MeetingRoomPicture: " + MeetingRoomPicture );
//            Log.e ("resultat", "MeetingTime: " + MeetingTime );
//            Log.e ("resultat", "MeetingTopic: " + MeetingTopic );

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