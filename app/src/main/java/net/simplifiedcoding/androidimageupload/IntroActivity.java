package net.simplifiedcoding.androidimageupload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {

    PrefManager pref;
    EditText mobile;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mobile = (EditText) findViewById(R.id.mobile_number);
        b = (Button) findViewById(R.id.but);
        pref = new PrefManager(this);

        if(pref.getmobile() != null)
        {
            Intent i = new Intent(IntroActivity.this,MapsActivity.class);
            startActivity(i);
            finish();
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobile.getText().toString()!=null && !mobile.getText().toString().equals("") && mobile.getText().toString().length()==10) {
                    pref.setmobile(mobile.getText().toString());
                    Intent i = new Intent(IntroActivity.this, MapsActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(IntroActivity.this,"Please Enter Valid Mobile Number",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
