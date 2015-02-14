package ilyag.ah92;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.GridView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ShowPhotosActivity extends ActionBarActivity {
    Handler handler = new Handler();
    static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos);
        Bundle bundle = getIntent().getExtras();
        final Date dateFrom = (Date) bundle.getSerializable("from");
        final Date dateTo = (Date) bundle.getSerializable("to");
        final TextView aspTV = (TextView) findViewById(R.id.aspTVResult);
        GridView gv = (GridView)findViewById(R.id.gridView);
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Bitmap> files = new ArrayList<>();
        aspTV.setText("from " + MainActivity.sdf.format(dateFrom) + " to " + MainActivity.sdf.format(dateTo));
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Picture");
        try {
            query.whereGreaterThanOrEqualTo("zDate", dateFrom);
            query.whereLessThanOrEqualTo("zDate", dateTo);
            query.orderByDescending("zDate");
            List<ParseObject> results = query.find();
            for(ParseObject po : results) {
                dates.add((Date)po.get("zDate"));
                ParseFile pf = (ParseFile)po.get("zFile");
                byte[] bFile = pf.getData();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bFile,0,bFile.length);
                files.add(bitmap);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dates.size() * files.size() > 0){
            gv.setAdapter(new GridAdapter(this, dates, files));
        }

    }


}
