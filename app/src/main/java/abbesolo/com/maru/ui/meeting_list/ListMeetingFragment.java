package abbesolo.com.maru.ui.meeting_list;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import abbesolo.com.maru.di.DI;
import abbesolo.com.maru.model.Meeting;
import abbesolo.com.maru.service.MeetingApiService;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Dictionary;
import java.util.List;

import abbesolo.com.maru.R;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMeetingFragment extends Fragment {

    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
   // private RecyclerView mRecyclerView;

  //  private FloatingActionButton fab;

    public ListMeetingFragment() {
        // Required empty public constructor
    }



    public static ListMeetingFragment newInstance() {
        ListMeetingFragment fragment = new ListMeetingFragment ();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
       mApiService = DI.getMeetingApiService ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate (R.layout.fragment_list_meeting, container, false);
  /**      mRecyclerView = (RecyclerView)view.findViewById (R.id.recyclerView);
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration (getContext(), DividerItemDecoration.VERTICAL));**/

       // fab = (FloatingActionButton)view.findViewById (R.id.ajout);



        iniList ();

        return view;

    }


    private void iniList(){
/**
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
             public void onClick(View view) {
                Intent intent = new Intent(getContext (), FormActivity.class);
              startActivity(intent);


            //Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //       .setAction ("Action", null).show ();
             }
             });
 **/


        mMeetings = mApiService.getMeetings ();
       // mRecyclerView.setAdapter (new MeetingAdapter (mMeetings));
    }




    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
