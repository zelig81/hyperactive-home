package ilyag.ah102;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    static TextView tvUser;
    static TextView tvMessage;
    EditText et;
    Button bSend;
    static Spinner spinner;
    static ToggleButton tbSignInOut;
    static ParseUser puCurrentUser;
    static ParseInstallation piCurrentInstallation;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            login(ParseUser.getCurrentUser(), this);
        } catch (ParseException e) {
            Log.e("ilyag1", e.getMessage());
        }
    }

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
        spinner = (Spinner) findViewById(R.id.spinner);
        piCurrentInstallation = ParseInstallation.getCurrentInstallation();

        bSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();
                if (user == null) {
                    tvMessage.setText("no signed user");
                } else {
                    tvMessage.setText(user.getObjectId() + " / " + user.getUsername());
                }
            }
        });

        tbSignInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tbSignInOut.isChecked()) {
                    logout();
                } else {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment fragment = getFragmentManager().findFragmentByTag("ah102dialog");
                    if (fragment != null) {
                        ft.remove(fragment);
                    }
                    ft.addToBackStack(null);

                    DialogFragment df = MyDialogFragment.newInstance();
                    df.show(ft, "ah102dialog");

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (puCurrentUser != null) {
            ParseInstallation.getCurrentInstallation();
            try {
                puCurrentUser.save();
            } catch (ParseException e) {
                Log.e("ilyag", e.getMessage());
            }
        }

    }

    public static class MyDialogFragment extends DialogFragment {
        EditText etUser, etPassword;
        Button bCancel, bOK;

        public static DialogFragment newInstance() {
            return new MyDialogFragment();
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.dialog_fragment, container, false);
            etUser = (EditText) v.findViewById(R.id.etUser);
            etPassword = (EditText) v.findViewById(R.id.etPassword);
            bCancel = (Button) v.findViewById(R.id.bCancel);
            bOK = (Button) v.findViewById(R.id.bLogIn);

            bCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            bOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String sUser = etUser.getText().toString();
                        String sPassword = etPassword.getText().toString();
                        if ("".equals(sUser)) {
                            Toast.makeText(getActivity(), "Don't forget to type username", Toast.LENGTH_LONG).show();
                        } else {
                            login(sUser, sPassword, getActivity());
                            dismiss();
                        }
                    } catch (ParseException e) {
                        Log.e("ilyag1", "no such etUser");
                        Toast.makeText(getActivity(), "no such user", Toast.LENGTH_LONG).show();
                    }

                }
            });
            return v;
        }

    }
    public static boolean login(String user, String password, Context context) throws ParseException {
        ParseUser loggedUser = ParseUser.logIn(user, password);
        if (loggedUser == null){
            return false;
        }else {
            return login(loggedUser, context);
        }
    }
    public static boolean login(ParseUser puUser, Context context) throws ParseException {
        if (puUser != null) {
            tvUser.setText(puUser.getUsername());
            puCurrentUser = ParseUser.getCurrentUser();
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", puUser.getUsername());
            List<ParseUser> puList = query.find();
            List<String> userList = new ArrayList<String>();
            for (ParseUser pu : puList) {
                userList.add(pu.getUsername());
            }
            ArrayAdapter listAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, userList);
            spinner.setAdapter(listAdapter);

            tbSignInOut.setChecked(true);
            return true;
        } else {
            return false;
        }
    }

    public static void logout() {
        ParseUser.logOut();
        spinner.setAdapter(null);
        List<String> channels = piCurrentInstallation.getList("channels");
        if ()
        puCurrentUser = null;
        tbSignInOut.setChecked(false);
        tvUser.setText("no user");
    }
}
