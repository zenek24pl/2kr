package pl.noskilljustfun.dwakrokistad.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.noskilljustfun.dwakrokistad.R;
import pl.noskilljustfun.dwakrokistad.interfaces.OnActivityClickListener;


public class RulesFragment extends Fragment {


    public static RulesFragment newInstance() {

        Bundle args = new Bundle();

        RulesFragment fragment = new RulesFragment();
        fragment.setArguments(args);
        return fragment;
    }
    OnActivityClickListener mListener;

    public RulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rules, container, false);
        
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnActivityClickListener) {
            mListener = (OnActivityClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
