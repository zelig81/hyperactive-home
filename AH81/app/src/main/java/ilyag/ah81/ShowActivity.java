package ilyag.ah81;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class ShowActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Button bReturn = (Button) findViewById(R.id.bReturnShowActivity);
        HashMap<String, Integer> map = (HashMap<String,Integer>)getIntent().getSerializableExtra("list");
        String[] strings = {"productName", "count"};
        int[] ids = {R.id.tvProductNameAdapter, R.id.tvCountAdapter};
        ArrayList<String> products = new ArrayList<>(map.keySet());
        ArrayList<HashMap<String, ?>> data = new ArrayList<>();
        Collections.sort(products);
        for (String product : products){
            HashMap<String, Object> miniMap = new HashMap<>();
            miniMap.put("productName", product);
            miniMap.put("count", map.get(product));
            data.add(miniMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.view_adapter, strings, ids);
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(simpleAdapter);

        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
