package ijbh.com.allinoneassistant;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class PhoneActivity extends AppCompatActivity {

    EditText phoneNumberEt;

    final int REQUEST_CODE_SPEECH = 10;
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        //setting phone number from previous window
        phoneNumberEt = findViewById(R.id.phone_number_ed);
        phoneNumberEt.setText(getIntent().getStringExtra("phoneNumber"));

        //buttons
        Button contactsBtn = findViewById(R.id.contacts_btn);
        Button callBtn = findViewById(R.id.call_btn);
        Button smsBtn = findViewById(R.id.sms_btn);

        //listeners
        phoneNumberEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechToText = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechToText.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechToText.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if(speechToText.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(speechToText, REQUEST_CODE_SPEECH);
                }
                else{
                    Toast.makeText(PhoneActivity.this, getResources().getString(R.string.toast_spt_not_support), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TODO works only after allowing permission through the settings menu.
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(phoneNumberEt)){
                    Toast.makeText(PhoneActivity.this, getResources().getString(R.string.toast_number_call), Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+phoneNumberEt.getText().toString()));
                    if (callIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(callIntent);
                    }
                }
            }
        });

        smsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(phoneNumberEt)){
                    Toast.makeText(PhoneActivity.this, getResources().getString(R.string.toast_number_conact), Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                    smsIntent.setData(Uri.parse("smsto:"+phoneNumberEt.getText().toString()));
                    if (smsIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(smsIntent); //TODO maybe add this to all actions
                    }
                }
            }
        });

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(phoneNumberEt)){
                    Toast.makeText(PhoneActivity.this, getResources().getString(R.string.toast_number_conact), Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent contactsIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    contactsIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    contactsIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumberEt.getText());
                    if (contactsIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(contactsIntent);
                    }
                }
            }
        });
    }

    //extra functions
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> input = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String sentence = extractNumber(input.get(0));

                    phoneNumberEt.setText(sentence);
                }
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public static String extractNumber(final String str) {

        if(str == null || str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
                found = true;
            } else if(found){
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return sb.toString();
    }
}
