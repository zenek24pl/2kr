package pl.noskilljustfun.dwakrokistad;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pl.noskilljustfun.dwakrokistad.database.ClueDatabaseHelper;
import pl.noskilljustfun.dwakrokistad.fragments.ClueDetailFragment;
import pl.noskilljustfun.dwakrokistad.fragments.GameFragment;
import pl.noskilljustfun.dwakrokistad.fragments.GameMapFragment;
import pl.noskilljustfun.dwakrokistad.fragments.MainActivityFragment;
import pl.noskilljustfun.dwakrokistad.fragments.RulesFragment;
import pl.noskilljustfun.dwakrokistad.fragments.SolveClueFragment;
import pl.noskilljustfun.dwakrokistad.fragments.TripMapFragment;
import pl.noskilljustfun.dwakrokistad.interfaces.OnActivityClickListener;
import pl.noskilljustfun.dwakrokistad.model.Clue;
import pl.noskilljustfun.dwakrokistad.model.Quest;
import pl.noskilljustfun.dwakrokistad.model.Relationship;
import pl.noskilljustfun.dwakrokistad.model.Rest;

public class MainActivity extends AppCompatActivity implements OnActivityClickListener {

    private ClueDatabaseHelper clueDatabaseHelper=null;
    private List<Clue> clueList;
    private String jsonResponse;
    private List<Integer> relationship;
    private List<Integer> startingPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        relationship= new ArrayList<>();
        startingPoints= new ArrayList<>();
        clueDatabaseHelper = ClueDatabaseHelper.getInstance(getApplicationContext());

        boolean firstUse = clueDatabaseHelper.getAllClues().isEmpty();

        /**
         * Downloading informations about starting points. When it's first use of app.
         */
        if(firstUse) {
            Rest.init();
            Rest.getPointsAsync(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.d("Error", "Connection failed");
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    jsonResponse = response.body().string();
                    Log.d("okhttp punkty startowe", jsonResponse);

                    JSONObject obj = null;
                    JSONArray arr = null;
                    JSONObject obj1 = null;
                    try {
                        obj = new JSONObject(jsonResponse);
                        arr = obj.getJSONArray("data");

                        JSONObject tempObj;
                        for (int i = 0; i < arr.length(); i++) {
                            tempObj = arr.getJSONObject(i);
                            startingPoints.add(tempObj.getInt("id"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    /**
                     *  Getting starting point, inserting into database
                     *  getting list of IDs, which are related with starting point
                     */
                    //TODO add Randomize function to get random case to solve
                    Rest.getNextPointAsync( startingPoints.get(0), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("Error", "Connection failed");

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            jsonResponse = response.body().string();
                            Log.d("okhttp3 pobranie punktu", jsonResponse);

                            JSONObject obj = null;
                            JSONArray arr = null;
                            JSONObject obj1 = null;

                            try {
                                obj = new JSONObject(jsonResponse);
                                obj = obj.getJSONObject("data");
                                arr = obj.getJSONArray("relationships");

                                JSONObject tempObj;
                                for (int i = 0; i < arr.length(); i++) {
                                    tempObj = arr.getJSONObject(i);
                                    relationship.add(tempObj.getInt("id"));

                                clueDatabaseHelper.createClue(obj.getString("name")
                                        , obj.getString("target")
                                        , Float.valueOf(obj.getString("lat"))
                                        , Float.valueOf(obj.getString("lng"))
                                        , 0
                                        , Integer.valueOf(obj.getString("game_id"))
                                        , Float.valueOf(obj.getString("radius"))
                                        , obj.getString("target")
                                        , obj.getString("story")
                                        , obj.getString("survey_clue")
                                        , Integer.valueOf(obj.getString("game_id"))
                                        , Integer.valueOf(obj.getString("type"))
                                        ,relationship
                                );


                                }
                                for (int i=0;i< relationship.size();i++) {
                                    Log.d("Relacje: ", String.valueOf(relationship.get(i)));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            });
        }

        clueList = clueDatabaseHelper.getAllClues();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, MainActivityFragment.newInstance());
        fragmentTransaction.commit();

    }

    @Override
    public void navigateToGame(boolean solved,int p) {

        if(solved) {

            List<Relationship> relationships = clueDatabaseHelper.getAllRelationships(p);

            for(Relationship r: relationships) {
                Rest.init();
                Rest.getNextPointAsync(r.getNext_clue(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Error", "Connection failed");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        jsonResponse = response.body().string();
                        Log.d("okhttp3 pobranie punktu", jsonResponse);

                        JSONObject obj = null;
                        JSONArray arr = null;
                        JSONObject obj1 = null;

                        try {
                            obj = new JSONObject(jsonResponse);
                            obj = obj.getJSONObject("data");

                            JSONObject tempObj;


                                clueDatabaseHelper.createClue(obj.getString("name")
                                        , obj.getString("target")
                                        , Float.valueOf(obj.getString("lat"))
                                        , Float.valueOf(obj.getString("lng"))
                                        , 0
                                        , Integer.valueOf(obj.getString("game_id"))
                                        , Float.valueOf(obj.getString("radius"))
                                        , obj.getString("target")
                                        , obj.getString("story")
                                        , obj.getString("survey_clue")
                                        , Integer.valueOf(obj.getString("game_id"))
                                        , Integer.valueOf(obj.getString("type"))
                                        , relationship
                                );

                            } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }
                });
            }
        }
        clueList=clueDatabaseHelper.getAllClues();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment, GameFragment.newInstance(clueList));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void navigateToTrip() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment, TripMapFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void navigateToRules() {


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.replace(R.id.fragment,RulesFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void navigateToClueDetail(int id ){

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();


        fragmentTransaction.replace(R.id.fragment, ClueDetailFragment.newInstance(clueList.get(id)));
        fragmentTransaction.addToBackStack("id"+id);
        fragmentTransaction.commit();


    }

    @Override
    public void navigateToSolveClue(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.replace(R.id.fragment, SolveClueFragment.newInstance(clueList.get(id-1)));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void navigateToMap(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.replace(R.id.fragment, GameMapFragment.newInstance(clueList.get(id-1)));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
