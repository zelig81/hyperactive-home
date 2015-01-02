package ilyag.ah81;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    HashMap<String, Integer> map;
    TextView tv;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button bAdd = (Button) findViewById(R.id.bAdd);
        Button bShow = (Button) findViewById(R.id.bShow);
        Button bReset = (Button) findViewById(R.id.bReset);
        tv = (TextView) findViewById(R.id.tvOutput);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        bShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ShowActivity.class);
                intent.putExtra("list", map);
                startActivity(intent);
            }
        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map = new HashMap<String, Integer>();
                tv.setText("0");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) {
            tv.setText("" + map.size());
        } else {

            FileInputStream fis;
            ObjectInputStream ois = null;
            try {
                fis = openFileInput("ah81.ser");
                ois = new ObjectInputStream(fis);
                Object o = ois.readObject();
                if (o == null) {
                    Toast.makeText(getApplication(), "got null object", Toast.LENGTH_LONG).show();
                    map = new HashMap<>();
                    tv.setText("0");
                } else {
                    map = (HashMap<String, Integer>) o;
                    int size = map.size();
                    tv.setText("" + size);
                }
                ois.close();
            } catch (FileNotFoundException e) {
                Log.e("ilyag1", e.getMessage());
            } catch (IOException e) {
                Log.e("ilyag1", e.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("ilyag1", e.getMessage());
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileOutputStream fos;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput("ah81.ser", MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
        } catch (FileNotFoundException e) {
            Log.e("ilyag1", e.getMessage());
        } catch (IOException e) {
            Log.e("ilyag1", e.getMessage());
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    Log.e("ilyag1", e.getMessage());
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode >= 0) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "nothing has been returned", Toast.LENGTH_LONG).show();
            } else {
                String itemName = data.getStringExtra("product name");
                int count = data.getIntExtra("count", -1);
                if (itemName == null || count == -1) {
                    Toast.makeText(getApplicationContext(), "incorrect product or count", Toast.LENGTH_LONG).show();
                } else {
                    map.put(itemName, count);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
