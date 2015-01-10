package ilyag.ah91;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    Button bAdd, bShow;
    TextView tv;
    List<ParseObject> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bAdd = (Button)findViewById(R.id.bAddUserMA);
        bShow = (Button)findViewById(R.id.bShowUsersMA);
        Parse.initialize(this, "55xzOXMlrupi2Ak3G5GHudXITvRwjcB93M2mBdTp", "jJUVHrwq64dWrxW7TRMPfjryzG3896sYKeUP1WJd");
        ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().saveInBackground();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicWriteAccess(true);
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("zusers");
        query.selectKeys(Arrays.asList("zuser", "zpassword"));
        try {
            list = query.find();
        } catch (ParseException e) {
            Log.e("ilyag1", e.getMessage());
        }
        tv = (TextView)findViewById(R.id.textView);



        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        bShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                ArrayList<String> users = new ArrayList<>();
                for(ParseObject po : list){
                    users.add(po.getString("zuser"));
                }
                intent.putExtra("zusers", users);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        tv.setText("" + list.size());

    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        for (ParseObject po : list){
            po.saveInBackground();
        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode >= 0){
            ParseObject po = new ParseObject("zusers");
            po.put("zuser", data.getStringExtra("zuser"));
            po.put("zpassword", data.getStringExtra("zpassword"));
            po.saveInBackground();
            list.add(po);
            tv.setText("" + list.size());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
