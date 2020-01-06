package abbesolo.com.maru.ui.meeting_list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import abbesolo.com.maru.R;
import abbesolo.com.maru.events.DeleteMeetingEvent;
import abbesolo.com.maru.model.Meeting;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;
    private final Context context;


    public MeetingAdapter(List<Meeting> items, Activity context) {
        mMeetings = items;
        this.context = context;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list_avatar)
        public ImageView mImageMeeting;
        @BindView(R.id.meeting)
        public TextView mMeeting;
        @BindView(R.id.participant)
        public TextView mparticipant;
        @BindView(R.id.item_list_delete_button)
        public ImageView delebutton;


        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            ButterKnife.bind (this, itemView);

        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.recyclerview_item, parent, false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Meeting meeting = mMeetings.get (position);

        holder.mImageMeeting.setImageResource (meeting.getMeetingRoom ().getRes ());

        holder.mMeeting.setText (meeting.getMeetingRoom ().getRoomName () + " - " + meeting.getMeetingDate ()+ " - "
                + meeting.getMeetingTime () + " - " + meeting.getMeetingTopic ());

//        List<Participant> participants = meeting.getMeetingParticipantList ();
//
//        String memberList = "";
//
//        for (Participant member : participants) {
//
//            memberList = memberList + " " + member.getEmail () + ", ";
//
//        }

        holder.mparticipant.setText (meeting.getParticpant().toString ());

        holder.delebutton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                EventBus.getDefault ().post (new DeleteMeetingEvent (meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size ();
    }


}
