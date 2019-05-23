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


        phoneNumberEt = findViewById(R.id.phone_number_ed);
        phoneNumberEt.setText(getIntent().getStringExtra("phoneNumber"));

        phoneNumberEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechToText = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechToText.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechToText.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if(speechToText.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(speechToText, 10);
                }
                else{
                    Toast.makeText(PhoneActivity.this, getResources().getString(R.string.toast_spt_not_support), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button contactsBtn = findViewById(R.id.contacts_btn);
        Button callBtn = findViewById(R.id.call_btn);

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

                    startActivity(contactsIntent);
                }


            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(phoneNumberEt)){
                    Toast.makeText(PhoneActivity.this, getResources().getString(R.string.toast_number_call), Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+phoneNumberEt.getText().toString()));
                    startActivity(callIntent);

                }
            }
        });

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> input = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //if(input)
                    boolean hasChar = false;
                    int j = 0;
                    for(int i = 0; i < input.toString().length(); i++){
                        if(Character.isLetter(input.toString().charAt(j))){
                            hasChar = true;
                        }
                    }
                    //not working correctly
                    //String test = input.toString();
                    if(hasChar){
                        Toast.makeText(this, "Can only read numbers", Toast.LENGTH_SHORT).show();
                    }else{
                        phoneNumberEt.setText(input.get(0));
                    }
                }
        }
    }
}