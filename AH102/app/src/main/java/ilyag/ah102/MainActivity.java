package ilyag.ah102;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.parse.Parse;


public class MainActivity extends Activity {
    TextView tvUser, tvMessage;
    EditText et;
    Button bSend;
    Spinner spinner;
    ToggleButton tbSignInOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "mrunUCoGzjABLEkCIDFWlWEHCOWW0Yfp3QCFJfp5", "nvzAPqJ7ePMlwxhmVss73OsYMuGVbDpWz4mMBUKN");

        tvUser = (TextView)findViewById(R.id.tvUser);
        tvMessage = (TextView)findViewById(R.id.tvMessage);
        et = (EditText)findViewById(R.id.editText);
        bSend = (Button)findViewById(R.id.bSend);
        tbSignInOut = (ToggleButton)findViewById(R.id.tbSignIn);

        bSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        tbSignInOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }


}
