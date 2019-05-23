package ijbh.com.allinoneassistant;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends Activity {

    int eventDay;
    int eventMonth;
    int eventYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView phoneTv = findViewById(R.id.phone_tv);
        TextView emailTv = findViewById(R.id.email_tv);
        TextView eventTv = findViewById(R.id.event_tv);
        final TextView eventInputTv = findViewById(R.id.event_input_tv);

        final EditText phoneEt = findViewById(R.id.phone_et);
        phoneEt.clearFocus();
        final EditText emailEt = findViewById(R.id.email_et);
        final EditText eventEt = findViewById(R.id.event_et);
        phoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent phoneActivity = new Intent(MainActivity.this, PhoneActivity.class);
                phoneActivity.putExtra("phoneNumber", phoneEt.getText().toString());
                startActivity(phoneActivity);
            }
        });

        eventEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneActivity = new Intent(MainActivity.this, PhoneActivity.class);
                phoneActivity.putExtra("phoneNumber", phoneEt.getText().toString());
                startActivity(phoneActivity);
            }
        });
        emailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailActivity = new Intent(MainActivity.this, EmailActivity.class);
                emailActivity.putExtra("emailAddress", emailEt.getText().toString());
                startActivity(emailActivity);
            }
        });

        /*eventInputTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eventActivity = new Intent(MainActivity.this, EventActivity.class);
                eventActivity.putExtra("eventDate", eventEt.getText().toString());

                startActivity(eventActivity);
            }
        });*/

        eventTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eventActivity = new Intent(MainActivity.this, EventActivity.class);
                eventActivity.putExtra("eventDate", eventEt.getText().toString());

                startActivity(eventActivity);
            }
        });



        //get date
        //eventEt.setText(day + "/" + (month + 1) + "/" + year);

        //eventDate.setText(current.DAY_OF_MONTH + "/" + (current.MONTH + 1) + "/" + current.YEAR);

        eventInputTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                eventInputTv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);


                                eventDay = dayOfMonth;
                                eventMonth = monthOfYear;
                                eventYear = year;
                            }
                        }, year, month, day);
                datePickerDialog.show();

                //Intent eventActivity = new Intent(MainActivity.this, EventActivity.class);
                //eventActivity.putExtra("eventDate", eventInputTv.getText().toString());
                //or create it inside the text change.
                eventInputTv.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Intent eventActivity = new Intent(MainActivity.this, EventActivity.class);
                        eventActivity.putExtra("eventDate", eventInputTv.getText().toString());

                        startActivity(eventActivity);

                    }
                });
            }
        });

        eventEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                eventEt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);


                                eventDay = dayOfMonth;
                                eventMonth = monthOfYear;
                                eventYear = year;
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

}
