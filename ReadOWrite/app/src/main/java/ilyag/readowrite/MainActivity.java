package ilyag.readowrite;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity implements WriteFragment.OnFragmentInteractionListener, ReadFragment.OnFragmentInteractionListener {
    Fragment readFragment, writeFragment;
    boolean isRead = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readFragment = new ReadFragment();
        writeFragment = new WriteFragment();



        View fragmentContainer = findViewById(R.id.fragment_container);

        if (fragmentContainer != null) {
            if (savedInstanceState != null) {
                return;
            }
            getFragmentManager().beginTransaction().add(R.id.fragment_container, readFragment).commit();
        }

        fragmentContainer.setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector !=null && gestureDetector.onTouchEvent(event);

            }

            @Override
            public void onSwipeRight() {
                if (isRead) {
                    move();
                }
            }

            @Override
            public void onSwipeLeft() {
                if (!isRead) {
                    move();
                }
            }
        });
    }


    private void move() {
        if (isRead) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, writeFragment).commit();
            isRead = false;
        } else {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, readFragment).commit();
            isRead = true;
        }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
