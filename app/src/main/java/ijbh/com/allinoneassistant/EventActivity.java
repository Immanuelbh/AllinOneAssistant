package ijbh.com.allinoneassistant;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    //TODO:rearrange the file.
    TextView eventDateTv;
    TextView eventTimeTv;
    EditText eventTitleEt;

    int eventMinute;
    int eventHour;
    int eventDay;
    int eventMonth;
    int eventYear;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        //
        eventTitleEt = findViewById(R.id.event_title_et);
        eventDateTv = findViewById(R.id.event_date_tv);
        eventTimeTv = findViewById(R.id.event_time_tv);
        final TextView eventInfoTv = findViewById(R.id.add_info_tv);
        final EditText eventInfoEt = findViewById(R.id.add_info_et);
        Button alarmBtn = findViewById(R.id.alarm_btn);
        final Button calendarBtn = findViewById(R.id.calendar_btn);

        //get current time
        Calendar time = Calendar.getInstance();
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);
        if(minute < 10){
            eventTimeTv.setText(hour + ":" + "0" + minute);
        }
        else{
            eventTimeTv.setText(hour + ":" + minute);
        }

        eventHour = hour;
        eventMinute = minute;

        //listeners
        //current date
        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);

        //get date from previous intent or default
        if(getIntent().getStringExtra("eventDate") != null){
            eventDateTv.setText(getIntent().getStringExtra("eventDate"));
            eventYear = getIntent().getIntExtra("eventYear", 0);
            eventMonth = getIntent().getIntExtra("eventMonth", 0);
            eventDay = getIntent().getIntExtra("eventDay", 0);
        }
        else{
            eventDateTv.setText(day + "/" + (month + 1) + "/" + year);
        }

        eventDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                eventDateTv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                eventDay = dayOfMonth;
                                eventMonth = monthOfYear;
                                eventYear = year;
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        eventTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current time
                final Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if(minute < 10){
                                    eventTimeTv.setText(hourOfDay + ":" + "0" + minute);
                                }
                                else{
                                    eventTimeTv.setText(hourOfDay + ":" + minute);
                                }
                                eventHour = hourOfDay;
                                eventMinute = minute;
                            }
                        }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        //add info
        eventInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventInfoEt.getVisibility() == View.GONE){
                    eventInfoEt.setVisibility(View.VISIBLE);
                    eventInfoTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_remove_black_24dp, 0, 0, 0);
                }
                else{
                    eventInfoEt.setVisibility(View.GONE);
                    eventInfoEt.setText("");
                    eventInfoTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_add_black_24dp, 0, 0, 0);
                }
            }
        });

        //setting an alarm
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, eventHour);
                alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, eventMinute);
                alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, eventTitleEt.getText().toString());
                if (alarmIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(alarmIntent);
                }
            }
        });

        //setting calendar event
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar beginCal = Calendar.getInstance();
                beginCal.set(eventYear, eventMonth, eventDay, eventHour, eventMinute);

                Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
                calendarIntent.setType("vnd.android.cursor.item/event");

                calendarIntent.putExtra(CalendarContract.Events.TITLE, eventTitleEt.getText().toString());
                calendarIntent.putExtra("beginTime", beginCal.getTimeInMillis());
                calendarIntent.putExtra("endTime", beginCal.getTimeInMillis()+ 60 * 60 * 1000);
                calendarIntent.putExtra("title", eventTitleEt.getText().toString());
                calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, eventInfoEt.getText().toString());
                if (calendarIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(calendarIntent);
                }
            }
        });
    }
}
