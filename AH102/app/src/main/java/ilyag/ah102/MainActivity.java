package ilyag.ah102;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


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

        tvUser = (TextView) findViewById(R.id.tvUser);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        et = (EditText) findViewById(R.id.editText);
        bSend = (Button) findViewById(R.id.bSend);
        tbSignInOut = (ToggleButton) findViewById(R.id.tbSignIn);

        bSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();
                if (user == null) {
                    tvMessage.setText("no user");
                }else{
                    tvMessage.setText(user.getObjectId() + " / " + user.getUsername());
                }
            }
        });

        tbSignInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tbSignInOut.isChecked()){
                    ParseUser.logOut();
                    tvUser.setText("no user");
                }else{
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment fragment = getFragmentManager().findFragmentByTag("ah102dialog");

                    try {
                        ParseUser.logIn("user1", "123");
                        tvUser.setText("user1");
                    } catch (ParseException e) {
                        Log.e("ilyag1", "no such user");
                        tvMessage.setText("no such user");
                    }

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
