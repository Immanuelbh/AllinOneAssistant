package ijbh.com.allinoneassistant;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    int eventDay;
    int eventMonth;
    int eventYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //backgrounds
        LinearLayout phoneBg = findViewById(R.id.phone_bg);
        LinearLayout emailBg = findViewById(R.id.email_bg);
        LinearLayout eventBg = findViewById(R.id.event_bg);

        //titles
        TextView phoneTv = findViewById(R.id.phone_tv);
        TextView emailTv = findViewById(R.id.email_tv);
        TextView eventTv = findViewById(R.id.event_tv);

        //input
        final EditText phoneEt = findViewById(R.id.phone_et);
        final EditText emailEt = findViewById(R.id.email_et);
        final TextView eventInputTv = findViewById(R.id.event_input_tv);

        //get rid of initial focus when openning the app.
        phoneEt.clearFocus();

        //background listeners
        //TODO: put the repeating code into a separate function.
        phoneBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(phoneEt)){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_phone), Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent phoneActivity = new Intent(MainActivity.this, PhoneActivity.class);
                    phoneActivity.putExtra("phoneNumber", phoneEt.getText().toString());
                    startActivity(phoneActivity);
                }

            }
        });

        emailBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(emailEt)){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent emailActivity = new Intent(MainActivity.this, EmailActivity.class);
                    emailActivity.putExtra("emailAddress", emailEt.getText().toString());
                    startActivity(emailActivity);
                }

            }
        });

        eventBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(emailEt)){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_date), Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent eventActivity = new Intent(MainActivity.this, EventActivity.class);
                    eventActivity.putExtra("eventDate", eventInputTv.getText().toString());

                    startActivity(eventActivity);
                }

            }
        });

        //title listeners
        phoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(phoneEt)){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_phone), Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent phoneActivity = new Intent(MainActivity.this, PhoneActivity.class);
                    phoneActivity.putExtra("phoneNumber", phoneEt.getText().toString());
                    startActivity(phoneActivity);
                }
            }
        });

        emailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(emailEt)){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent emailActivity = new Intent(MainActivity.this, EmailActivity.class);
                    emailActivity.putExtra("emailAddress", emailEt.getText().toString());
                    startActivity(emailActivity);
                }
            }
        });

        //TODO:check if working
        eventTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventInputTv.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_date), Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent eventActivity = new Intent(MainActivity.this, EventActivity.class);
                    eventActivity.putExtra("eventDate", eventInputTv.getText().toString());

                    startActivity(eventActivity);
                }
            }
        });


        //enter key listener on edittext
        phoneEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)){
                    Intent phoneActivity = new Intent(MainActivity.this, PhoneActivity.class);
                    phoneActivity.putExtra("phoneNumber", phoneEt.getText().toString());
                    startActivity(phoneActivity);

                    return true;
                }

                return false;
            }
        });

        emailEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)){
                    Intent emailActivity = new Intent(MainActivity.this, EmailActivity.class);
                    emailActivity.putExtra("emailAddress", emailEt.getText().toString());
                    startActivity(emailActivity);

                    return true;
                }

                return false;
            }
        });

        //date textview listener
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

                //user entered a date - move to next screen
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

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
