package ilyag.ah92;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.List;


public class ShowPhotosActivity extends ActionBarActivity {
    Handler handler = new Handler();
    static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos);
        Bundle bundle = getIntent().getExtras();
        final Calendar calFrom = (Calendar) bundle.getSerializable("from");
        final Calendar calTo = (Calendar) bundle.getSerializable("to");
        final TextView aspTV = (TextView) findViewById(R.id.aspTVResult);
        aspTV.setText("from " + MainActivity.sdf.format(calFrom.getTime()) + " to " + MainActivity.sdf.format(calTo.getTime()));

        final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Picture");
        try {
            query.whereGreaterThan("zDate", calFrom.getTime());
            query.whereLessThan("zDate", calTo.getTime());
            query.orderByDescending("zDate");
            List<ParseObject> results = query.find();
            for(ParseObject po : results) {
                aspTV.setText(aspTV.getText().toString() + "\n" + MainActivity.sdf.format(po.get("zDate")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
//            @Override
//            public ParseQuery<ParseObject> create() {
//                final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Picture");
//                query.whereGreaterThanOrEqualTo("zDate", calFrom.getTime());
//                query.whereLessThanOrEqualTo("zDate", calTo.getTime());
//                query.orderByDescending("zDate");
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            aspTV.setText(query.count() + "");
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                return query;
//            }
//        };
//        final ParseQueryAdapter<ParseObject> pqAdapter = new ParseQueryAdapter<ParseObject>(this, factory);
//        pqAdapter.setImageKey("zFile");
//
//        ListView lv = (ListView) findViewById(R.id.listView);
//        lv.setAdapter(pqAdapter);


    }


}
