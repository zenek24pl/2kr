package pl.noskilljustfun.dwakrokistad.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.noskilljustfun.dwakrokistad.R;
import pl.noskilljustfun.dwakrokistad.adapters.ClueAdapter;
import pl.noskilljustfun.dwakrokistad.interfaces.OnActivityClickListener;
import pl.noskilljustfun.dwakrokistad.model.Clue;
import pl.noskilljustfun.dwakrokistad.model.ClueItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends ListFragment   {
    private static List<Clue> cList;
    private List<ClueItem> mItems; //deklaracja listy ktora zawierajacej ClueItemy
    // private List<Clue> cList;


    OnActivityClickListener mListener; //odwołanie sie do komunikatora

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<ClueItem>(); //deklaracja dynamicznej listy

        // cList=new ArrayList<Clue>();
        //  String cluesJsonString= loadJSONFromAsset();

        //    TypeToken<List<Clue>>token=new TypeToken<List<Clue>>(){};

        //Gson gson = new Gson();

        //List<Clue>cList = gson.fromJson(cluesJsonString,token.getType());

        Resources resources = getResources();

        for (Clue clue:cList) {
            if(clue.getStatus()==1)
                mItems.add(new ClueItem(resources.getDrawable(R.drawable.yes),clue.getName(),clue.getDescription()));
            else
                mItems.add(new ClueItem(resources.getDrawable(R.drawable.no),clue.getName(),clue.getDescription()));
        }
        setListAdapter(new ClueAdapter(getActivity(), mItems));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_game, container, false);
    }
    public static GameFragment newInstance(List<Clue>clueList) {
        Bundle args = new Bundle();
        cList=clueList;
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }
    //pusty konstruktor fragmentu GameFragment
    public GameFragment() {

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.navigateToClueDetail((int) id);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
/*
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("clues.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
*/

}





