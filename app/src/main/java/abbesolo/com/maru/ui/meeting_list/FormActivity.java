package abbesolo.com.maru.ui.meeting_list;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import abbesolo.com.maru.R;
import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText meeting_topic;
    private EditText meeting_room;
    //   private String meeting_time;
    private EditText meeting_members;
    Button btnDatePicker, btnTimePicker, btnValider, btnAnnuler;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String[] roomNames = {"Salle 1", "Salle 2", "Salle 3", "Salle 4", "Salle 5", "Salle 6"};
    String selectedRoom;
    int selectedRoomPicture;

    int roomPicture[] = {R.drawable.re1, R.drawable.re2, R.drawable.re3, R.drawable.re4, R.drawable.re5, R.drawable.re6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_form);
        Intent intent = getIntent ();

        meeting_topic = (EditText) findViewById (R.id.meeting_topic);
        meeting_members = (EditText) findViewById (R.id.member);

        btnDatePicker = (Button) findViewById (R.id.btn_date);
        btnTimePicker = (Button) findViewById (R.id.btn_time);
        btnValider = (Button) findViewById (R.id.envoie);
        btnAnnuler = (Button) findViewById (R.id.annuler);
        txtDate = (EditText) findViewById (R.id.in_date);
        txtTime = (EditText) findViewById (R.id.in_time);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner meeting_room = (Spinner) findViewById (R.id.meeting_room);
        meeting_room.setOnItemSelectedListener (this);

        CustomAdapter customAdapter = new CustomAdapter (getApplicationContext (), roomPicture, roomNames);
        meeting_room.setAdapter (customAdapter);

        btnDatePicker.setOnClickListener (this);
        btnTimePicker.setOnClickListener (this);
        btnValider.setOnClickListener (this);
        btnAnnuler.setOnClickListener (this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance ();
            mYear = c.get (Calendar.YEAR);
            mMonth = c.get (Calendar.MONTH);
            mDay = c.get (Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog (this,
                    new DatePickerDialog.OnDateSetListener () {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText (dayOfMonth + "-" + ( monthOfYear + 1 ) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show ();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance ();
            mHour = c.get (Calendar.HOUR_OF_DAY);
            mMinute = c.get (Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog (this,
                    new TimePickerDialog.OnTimeSetListener () {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText (hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show ();
        }
        if (v == btnValider) {

            //Appel de la seconde activité
            Intent resultIntent = new Intent ();

            resultIntent.putExtra ("MeetingTopic", meeting_topic.getText ().toString ());
            resultIntent.putExtra ("MeetingRoom", selectedRoom);
            resultIntent.putExtra ("MeetingRoomPicture", selectedRoomPicture);
            resultIntent.putExtra ("MeetingMember", meeting_members.getText ().toString ());
            resultIntent.putExtra ("MeetingTime", txtDate.getText () + " " + txtTime.getText ());
            resultIntent.putExtra ("MeetingDate", txtDate.getText ());

            setResult (Activity.RESULT_OK, resultIntent);
            finish ();

        }
        if (v == btnAnnuler) {
            finish();

        }

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        Toast.makeText (getApplicationContext (), roomNames[position], Toast.LENGTH_LONG).show ();
        selectedRoom = roomNames[position];
        selectedRoomPicture = roomPicture[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}




