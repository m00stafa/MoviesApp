package aboali.mostafa.moviesapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import aboali.mostafa.moviesapp.R;

/**
 * Created by Mostafa on 08/01/2016.
 */
public class FragmentListDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
    return rootView;
    }
}
