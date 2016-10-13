package pl.noskilljustfun.dwakrokistad.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import pl.noskilljustfun.dwakrokistad.R;
import pl.noskilljustfun.dwakrokistad.interfaces.OnActivityClickListener;
import pl.noskilljustfun.dwakrokistad.model.Clue;
import pl.noskilljustfun.dwakrokistad.model.Quest;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClueDetailFragment extends Fragment implements View.OnClickListener {

    private static Clue clue;
    private int isSolved=0;
    OnActivityClickListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public ClueDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View DetailLayout=inflater.inflate(R.layout.fragment_clue__detail_,container,false);
        TextView title=(TextView) DetailLayout.findViewById(R.id.tvTitle);
        TextView target=(TextView) DetailLayout.findViewById(R.id.tvtarget);
        TextView place=(TextView) DetailLayout.findViewById(R.id.tvplace);
        TextView etarget=(TextView)DetailLayout.findViewById(R.id.ettarget);
        TextView eplace=(TextView)DetailLayout.findViewById(R.id.etplace);
        ScrollView fable=(ScrollView)DetailLayout.findViewById(R.id.svfable);
        Button showOnMap=(Button)DetailLayout.findViewById(R.id.btMap);
        Button btnSolveClue = (Button) DetailLayout.findViewById(R.id.btnSolveClue);
        title.setText(clue.getName());
        etarget.setText(clue.getTarget());
        eplace.setText(clue.getStory());
        isSolved=clue.getStatus();
        if(isSolved==1)
            btnSolveClue.setVisibility(View.INVISIBLE);




        btnSolveClue.setOnClickListener(this);
        showOnMap.setOnClickListener(this);
        return DetailLayout;
    }
    public static ClueDetailFragment newInstance(Clue cluee) {


        Bundle args = new Bundle();

        clue=cluee;


        ClueDetailFragment fragment = new ClueDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnSolveClue:

                mListener.navigateToSolveClue(clue.getId());
                break;
            case R.id.btMap:
                mListener.navigateToMap(clue.getId());
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivityClickListener) {
            mListener = (OnActivityClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnActivityClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
