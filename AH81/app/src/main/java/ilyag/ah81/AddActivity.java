package ilyag.ah81;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddActivity extends ActionBarActivity {
    EditText pn, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button bReturn = (Button) findViewById(R.id.bReturnAA);
        Button bAdd = (Button) findViewById(R.id.bAddButtonAA);
        pn = (EditText) findViewById(R.id.etProductName);
        count = (EditText) findViewById(R.id.etCount);
        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.this.finish();
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPN = pn.getText().toString();
                String sCount = count.getText().toString();
                if ("".equals(sPN) || "".equals(sCount)) {
                    Toast.makeText(getApplicationContext(), "Please enter product name and count of it", Toast.LENGTH_LONG).show();
                    pn.setText("");
                    count.setText("");
                    return;
                } else {
                    int iCount = Integer.parseInt(sCount);
                    if (iCount <= 0){
                        Toast.makeText(getApplicationContext(),"Product count cannot be non positive",Toast.LENGTH_LONG).show();
                        pn.setText("");
                        count.setText("");
                        return;
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra("product name", sPN);
                        intent.putExtra("count", iCount);
                        setResult(0, intent);
                        finish();
                    }
                }
            }
        });

    }


}
