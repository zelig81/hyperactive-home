package ilyag.ah91;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class ShowActivity extends ActionBarActivity {
    Button bReturn;
    ListView lv;
    static ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        aa = new ArrayAdapter<>(ShowActivity.this, android.R.layout.simple_dropdown_item_1line, (ArrayList<String>) getIntent().getSerializableExtra("zusers"));
        lv = (ListView) findViewById(R.id.lvUserListSA);
        lv.setAdapter(aa);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("mydialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment df = MyDialogFragment.newInstance(item);
                df.show(ft, "mydialog");
                return true;
            }
        });
        bReturn = (Button) findViewById(R.id.bReturnSA);
        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });
    }


    public static class MyDialogFragment extends DialogFragment {
        public static MyDialogFragment newInstance(String s) {
            MyDialogFragment mdf = new MyDialogFragment();
            Bundle b = new Bundle();
            b.putString("user", s);
            mdf.setArguments(b);
            return mdf;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog);
        }

        /**
         * Called to have the fragment instantiate its user interface view.
         * This is optional, and non-graphical fragments can return null (which
         * is the default implementation).  This will be called between
         * {@link #onCreate(android.os.Bundle)} and {@link #onActivityCreated(android.os.Bundle)}.
         * <p/>
         * <p>If you return a View from here, you will later be called in
         * {@link #onDestroyView} when the view is being released.
         *
         * @param inflater           The LayoutInflater object that can be used to inflate
         *                           any views in the fragment,
         * @param container          If non-null, this is the parent view that the fragment's
         *                           UI should be attached to.  The fragment should not add the view itself,
         *                           but this can be used to generate the LayoutParams of the view.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         *                           from a previous saved state as given here.
         * @return Return the View for the fragment's UI, or null.
         */
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_dialog, container, false);
            TextView tv = (TextView) v.findViewById(R.id.dialogTextView);
            Button ok = (Button) v.findViewById(R.id.dialogOK);
            Button cancel = (Button) v.findViewById(R.id.dialogCancel);
            final String username = getArguments().getString("user");
            tv.setText("Do you want to delete " + username + "?");
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("zusers");
                    query.whereEqualTo("zuser", username);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            if (e == null) {
                                parseObject.deleteInBackground();
                            } else {
                                Log.e("ilyag1", e.getMessage());
                            }
                        }
                    });
                    aa.remove(username);
                    aa.notifyDataSetChanged();
                    dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return v;

        }
    }
}
