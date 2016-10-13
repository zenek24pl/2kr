package pl.noskilljustfun.dwakrokistad.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.noskilljustfun.dwakrokistad.R;
import pl.noskilljustfun.dwakrokistad.interfaces.OnActivityClickListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {


    public MainActivityFragment() {
    }

    View view;
    OnActivityClickListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_main, container, false);
        Button btnStart = (Button) view.findViewById(R.id.btnStart);
        Button btnRules = (Button) view.findViewById(R.id.btnRules);
        Button btnTrip = (Button) view.findViewById(R.id.btnTrip);
        btnStart.setOnClickListener(this);
        btnRules.setOnClickListener(this);
        btnTrip.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnActivityClickListener)
        {
            mListener = (OnActivityClickListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() +"must implement OnActivityClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnStart:
                mListener.navigateToGame(false,0);
            break;
            case R.id.btnTrip:
                mListener.navigateToTrip();
                break;
            case R.id.btnRules:
                mListener.navigateToRules();
                break;
        }
    }


    public static MainActivityFragment newInstance() {

        Bundle args = new Bundle();
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
