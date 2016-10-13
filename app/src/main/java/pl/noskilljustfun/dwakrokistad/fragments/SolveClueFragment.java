package pl.noskilljustfun.dwakrokistad.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.noskilljustfun.dwakrokistad.R;
import pl.noskilljustfun.dwakrokistad.database.ClueDatabaseHelper;
import pl.noskilljustfun.dwakrokistad.interfaces.OnActivityClickListener;
import pl.noskilljustfun.dwakrokistad.model.Clue;
import pl.noskilljustfun.dwakrokistad.model.Relationship;
import pl.noskilljustfun.dwakrokistad.model.Rest;

/**
 * A simple {@link Fragment} subclass.
 */
public class SolveClueFragment extends Fragment {

    private  ClueDatabaseHelper clueDatabaseHelper;
    public EditText etsolution;
    public Button bsolution;
    public TextView tvcluetitle;

    private static Clue clue;
    OnActivityClickListener mListener;


    public SolveClueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
              // Inflate the layout for this fragment
        View SolveLayout= inflater.inflate(R.layout.fragment_solve_clue, container, false);

        etsolution=(EditText)SolveLayout.findViewById(R.id.etSolution);
        bsolution=(Button)SolveLayout.findViewById(R.id.btnSolution);
        tvcluetitle=(TextView)SolveLayout.findViewById(R.id.tvClueTitle) ;
        tvcluetitle.setText(clue.getName());

        bsolution.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String str = etsolution.getText().toString();
                if(str.equals(clue.getSurveyClue())) {
                    Toast.makeText(getActivity(), "Super odpowiedź", Toast.LENGTH_LONG).show();
                    clue.setStatus(1);
                    clueDatabaseHelper.updateSolvedClue(clue.getId());

                    v.getId();

                    mListener.navigateToGame(true,clue.getId());
                }
                else {
                    Toast.makeText(getActivity(), "Zła odpowiedź", Toast.LENGTH_LONG).show();

                }
            }
        });
        return SolveLayout;
    }

    public static Fragment newInstance(Clue c) {
        Bundle args = new Bundle();
        clue=c;
        SolveClueFragment fragment = new SolveClueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivityClickListener) {
            mListener = (OnActivityClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnActivityClickListener");
        }
        clueDatabaseHelper= ClueDatabaseHelper.getInstance(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener= null;
    }
}
