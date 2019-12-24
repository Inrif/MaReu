package abbesolo.com.maru.ui.meeting_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import abbesolo.com.maru.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int mRes[];
    String[] mRoomName;
    LayoutInflater inflter;



    public CustomAdapter(Context applicationContext, int[] mRes, String[] mRoomName) {
        this.context = applicationContext;
        this.mRes = mRes;
        this.mRoomName = mRoomName;
        inflter = ( LayoutInflater.from (applicationContext) );
    }

    @Override
    public int getCount() {
        return mRes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate (R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById (R.id.imageView);
        TextView names = (TextView) view.findViewById (R.id.textView);
        icon.setImageResource (mRes[i]);
        names.setText (mRoomName[i]);
        return view;
    }
}