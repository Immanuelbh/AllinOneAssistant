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

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    EditText eventDateEt;
    EditText eventTimeEt;
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

        eventTitleEt = findViewById(R.id.event_title_et);

        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);

        //get date
        eventDateEt = findViewById(R.id.event_date_et);
        if(getIntent().getStringExtra("eventDate") != null){
            eventDateEt.setText(getIntent().getStringExtra("eventDate"));
        }
        else{
            eventDateEt.setText(day + "/" + (month + 1) + "/" + year);
        }

        //eventDate.setText(current.DAY_OF_MONTH + "/" + (current.MONTH + 1) + "/" + current.YEAR);

        eventDateEt.setOnClickListener(new View.OnClickListener() {
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

                                eventDateEt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                eventDay = dayOfMonth;
                                eventMonth = monthOfYear;
                                eventYear = year;
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        /*eventDateEt.setOnClickListener(new View.OnClickListener() {
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

                                eventDateEt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                eventDay = dayOfMonth;
                                eventMonth = monthOfYear;
                                eventYear = year;
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });*/

        //get time
        eventTimeEt = findViewById(R.id.event_time_et);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if(minute < 10){
            eventTimeEt.setText(hour + ":" + "0" + minute);
        }
        else{
            eventTimeEt.setText(hour + ":" + minute);
        }

        eventHour = hour;
        eventMinute = minute;

        //eventTime.setText(current.HOUR_OF_DAY + ":" + current.MINUTE);

        eventTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current time
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if(minute < 10){
                                    eventTimeEt.setText(hourOfDay + ":" + "0" + minute);
                                }
                                else{
                                    eventTimeEt.setText(hourOfDay + ":" + minute);
                                }
                                eventHour = hourOfDay;
                                eventMinute = minute;
                            }
                        }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        //add info
        final TextView eventInfoTv = findViewById(R.id.add_info_tv);
        final EditText eventInfoEt = findViewById(R.id.add_info_et);

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
        Button alarmBtn = findViewById(R.id.alarm_btn);
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, eventHour);
                alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, eventMinute);
                alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, eventTitleEt.getText().toString());

                startActivity(alarmIntent);

            }
        });

        //setting calendar event
        final Button calendarBtn = findViewById(R.id.calendar_btn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
                //calendarIntent.setData(CalendarContract.Events.CONTENT_URI);
                calendarIntent.setType("vnd.android.cursor.item/event");
                calendarIntent.putExtra(CalendarContract.Events.TITLE, eventTitleEt.getText().toString());
                //calendarIntent.putExtra(CalendarContract.Events.DTSTART, );
                //calendarIntent.putExtra(CalendarContract.Events., );
                calendarIntent.putExtra("beginTime", c.getTimeInMillis());
                calendarIntent.putExtra("endTime", c.getTimeInMillis()+ 60 * 60 * 1000);
                calendarIntent.putExtra("title", eventTitleEt.getText().toString());
                calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, eventInfoEt.getText().toString());
                //calendarIntent.putExtra("allDay", true);



                startActivity(calendarIntent);
            }
        });

    }

}
