package ilyag.ah92;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    Button bMake, bShow, bChooseFrom, bChooseTo;
    EditText etDataFrom, etDataTo;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bMake = (Button)findViewById(R.id.bMakePhoto);
        bShow = (Button)findViewById(R.id.bShowAlbum);
        bChooseFrom = (Button)findViewById(R.id.bChooseDateFrom);
        bChooseTo = (Button)findViewById(R.id.bChooseDateTo);

        etDataFrom = (EditText)findViewById(R.id.etDateFrom);
        etDataTo = (EditText)findViewById(R.id.etDateTo);
        tvResult = (TextView)findViewById(R.id.tvResult);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "rtGoLjEKfTGODrWQmjwOmUkOcPGGVo2JaOwivPqb", "LHQtKOC9d4jsQf8ypaXE92GdOPxqvLT685RjBWam");

        bMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MakePhotoActivity.class);
                startActivityForResult(i, 1);
            }
        });

        bShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDateFrom = etDataFrom.getText().toString();
                String sDateTo = etDataTo.getText().toString();
                Calendar calFrom = Calendar.getInstance();
                Calendar calTo = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy, HHmm", Locale.ENGLISH);
                try {
                    calFrom.setTime(sdf.parse(sDateFrom));
                    calTo.setTime(sdf.parse(sDateTo));
                } catch (ParseException e) {
                    Toast.makeText(MainActivity.this, "wrong format of time interval from = [" + sDateFrom + "] to = [" + sDateTo + "]", Toast.LENGTH_LONG).show();
                    Log.e("ilyag1", e.getMessage());
                    return;
                }

                boolean check = calFrom.before(calTo);
                if (check == true) {
                    Toast.makeText(MainActivity.this, "right format of time interval from = [" + sDateFrom + " to = [" + sDateTo + "]", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(MainActivity.this, ShowPhotosActivity.class);
                    startActivityForResult(i, 2);

                }else{
                    Toast.makeText(MainActivity.this, "wrong interval from = [" + sDateFrom + "] to = [" + sDateTo + "]", Toast.LENGTH_LONG).show();

                }
            }
        });

        View.OnClickListener dateChooser = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment newFragment = MyDialog.newInstance();
                ft.add(R.layout.dialog_date_picker, newFragment);
                ft.commit();
            }
        } ;

        bChooseFrom.setOnClickListener(dateChooser);
        bChooseTo.setOnClickListener(dateChooser);

    }

    public static class MyDialog extends DialogFragment {
        String title, type;
        static MyDialog newInstance(){
            MyDialog md = new MyDialog();
            return md;
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
            View v =  inflater.inflate(R.layout.dialog_date_picker, container, false);
            DatePicker dp = (DatePicker)v.findViewById(R.id.dpDatePicker);
            TimePicker tp = (TimePicker)v.findViewById(R.id.dpTimePicker);
            Button bOK, bReturn;
            bOK = (Button)v.findViewById(R.id.bDPOK);
            bReturn = (Button)v.findViewById(R.id.bDPReturn);
            bReturn.setOnClickListener(new View.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return v;
        }
    }


}
