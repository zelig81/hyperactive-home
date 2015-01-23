package ilyag.ah92;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    Button bMake, bShow;
    EditText etDataFrom, etDataTo, etTimeFrom, etTimeTo;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bMake = (Button)findViewById(R.id.bMakePhoto);
        bShow = (Button)findViewById(R.id.bShowAlbum);

        etDataFrom = (EditText)findViewById(R.id.etDateFrom);
        etDataTo = (EditText)findViewById(R.id.etDateTo);
        etTimeFrom = (EditText)findViewById(R.id.etTimeFrom);
        etTimeTo = (EditText)findViewById(R.id.etTimeTo);
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
                String sTimeFrom = etTimeFrom.getText().toString();
                String sTimeTo = etTimeTo.getText().toString();
                Calendar calFrom = Calendar.getInstance();
                Calendar calTo = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy, HHmm", Locale.ENGLISH);
                try {
                    calFrom.setTime(sdf.parse(sDateFrom + ", " + sTimeFrom));
                    calTo.setTime(sdf.parse(sDateTo + ", " + sTimeTo));
                } catch (ParseException e) {
                    Toast.makeText(MainActivity.this, "wrong format of time interval from = [" + sDateFrom + "] [" + sTimeFrom + "] to = [" + sDateTo + "] [" + sTimeTo + "]", Toast.LENGTH_LONG).show();


                    Log.e("ilyag1", e.getMessage());
                }

                boolean check = dateCheck(sDateFrom, sDateTo);
                check = check && timeCheck(sTimeFrom, sTimeTo);
                if (check == true) {
                    Toast.makeText(MainActivity.this, "right format of time interval from = [" + sDateFrom + "] [" + sTimeFrom + "] to = [" + sDateTo + "] [" + sTimeTo + "]", Toast.LENGTH_LONG).show();

                    //Intent i = new Intent(MainActivity.this, ShowPhotosActivity.class);
                    //i.putExtra();
                    //startActivityForResult(i, 2);

                }
            }
        });

        View.OnLongClickListener dateChooser = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvResult.setText("date " + ((EditText)v).getText().toString());
                return false;
            }
        } ;
        View.OnLongClickListener timeChooser = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvResult.setText("time " + ((EditText)v).getText().toString());
                return false;
            }
        } ;

        etDataFrom.setOnLongClickListener(dateChooser);
        etDataTo.setOnLongClickListener(dateChooser);
        etTimeTo.setOnLongClickListener(timeChooser);
        etTimeFrom.setOnLongClickListener(timeChooser);
    }

    private boolean timeCheck(String sTimeFrom, String sTimeTo) {
        return true;
    }

    private boolean dateCheck(String sDateFrom, String sDateTo) {
        return true;
    }


}
