package ilyag.ah91;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddActivity extends ActionBarActivity {
    Button bAdd, bReturn;
    EditText etUser, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etUser = (EditText) findViewById(R.id.etUsernameAA);
        etPassword = (EditText) findViewById(R.id.etPasswordAA);
        bAdd = (Button) findViewById(R.id.bAddAA);
        bReturn = (Button) findViewById(R.id.bReturnAA);
        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(-1);
                finish();
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String sUser = etUser.getText().toString();
                String sPassword = etPassword.getText().toString();
                if (sUser != null && sPassword != null && !"".equals(sUser) && !"".equals(sPassword)) {
                    intent.putExtra("zuser", sUser);
                    intent.putExtra("zpassword", sPassword);
                    setResult(0, intent);
                } else {
                    Toast.makeText(getApplication(), "don't forget to type something in both fields", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
