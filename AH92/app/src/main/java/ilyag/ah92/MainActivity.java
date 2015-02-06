package ilyag.ah92;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
    static EditText ETToChange;
    static Handler handler = new Handler();
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            }
        });

        bShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDateFrom = etDataFrom.getText().toString();
                String sDateTo = etDataTo.getText().toString();
                Calendar calFrom = Calendar.getInstance();
                Calendar calTo = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
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
                    Intent i = new Intent(MainActivity.this, ShowPhotosActivity.class);
                    startActivityForResult(i, 2);

                }else{
                    Toast.makeText(MainActivity.this, "wrong interval from = [" + sDateFrom + "] to = [" + sDateTo + "]", Toast.LENGTH_LONG).show();

                }
            }
        });

        bChooseFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("before");
                if (prev != null){
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                ETToChange = etDataFrom;
                DialogFragment df = MyDialog.newInstance();
                df.show(ft, "before");
            }
        });
        bChooseTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("after");
                if (prev != null){
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                ETToChange = etDataTo;
                DialogFragment df = MyDialog.newInstance();
                df.show(ft, "after");
            }
        });

    }

    public static class MyDialog extends DialogFragment {
        String title, type;
        static MyDialog newInstance(){
            return new MyDialog();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v =  inflater.inflate(R.layout.dialog_date_picker, container, false);
            final DatePicker dp = (DatePicker)v.findViewById(R.id.dpDatePicker);
            final TimePicker tp = (TimePicker)v.findViewById(R.id.dpTimePicker);
            Button bOK, bReturn;
            bOK = (Button)v.findViewById(R.id.bDPOK);
            bOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ETToChange.setText(dp.getMonth() + "/" + dp.getDayOfMonth() + "/" + dp.getYear() + " " + tp.getCurrentHour() + ":" + tp.getCurrentMinute());
                        }
                    });
                    dismiss();
                }
            });
            bReturn = (Button)v.findViewById(R.id.bDPReturn);
            bReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return v;
        }
    }


}
