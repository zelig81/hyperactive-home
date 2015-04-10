package ilyag.readowrite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ReadPagerFragment extends Fragment {


    public static ReadPagerFragment newInstance() {
        ReadPagerFragment fragment = new ReadPagerFragment();
        return fragment;
    }

    public ReadPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_pager, container, false);
    }

}
