package ilyag.ah82;

import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by gorban on 1/3/2015.
 */
public class MyDialog extends DialogFragment {
    String url;
    TextView tv;
    static MyDialog newInstance(String url){
        MyDialog df = new MyDialog();
        Bundle args = new Bundle();
        args.putString("url", url);
        df.setArguments(args);
        return df;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
        setCancelable(false);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        tv = (TextView)v.findViewById(R.id.tvOutput);
        tv.setText(url);
        View bCancel = v.findViewById(R.id.bCancel);
        ((Button)bCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.this.dismiss();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
