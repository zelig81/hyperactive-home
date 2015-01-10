package ilyag.ah91;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.ListMenuItemView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ShowActivity extends ActionBarActivity {
    Button bReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(ShowActivity.this, android.R.layout.simple_dropdown_item_1line, (ArrayList<String>) getIntent().getSerializableExtra("zusers"));
        Toast.makeText(this, "" + (ArrayList<String>) getIntent().getSerializableExtra("zusers"), Toast.LENGTH_LONG).show();
        ListView lv = (ListView) findViewById(R.id.lvUserListSA);
        lv.setAdapter(aa);
        bReturn = (Button) findViewById(R.id.bReturnSA);
        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show, menu);
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
