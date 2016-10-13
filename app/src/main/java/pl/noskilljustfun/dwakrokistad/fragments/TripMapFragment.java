package pl.noskilljustfun.dwakrokistad.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.noskilljustfun.dwakrokistad.R;
import pl.noskilljustfun.dwakrokistad.model.Place;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripMapFragment extends Fragment implements OnMapReadyCallback{
    private List<Place> mPlace;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip_map, container,false);
    }
    public static TripMapFragment newInstance() {

        Bundle args = new Bundle();

        TripMapFragment fragment = new TripMapFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.tripmap);
        fragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mPlace = new ArrayList<Place>();
        String cluesJsonString= loadJSONFromAsset();

        TypeToken<List<Place>> token=new TypeToken<List<Place>>(){};

        Gson gson = new Gson();

        mPlace = gson.fromJson(cluesJsonString,token.getType());

        for (Place place:mPlace){

            LatLng point = new LatLng((double)place.getPositionX(),(double)place.getPositionY());
            googleMap.addMarker(new MarkerOptions().position(point).title(place.getName()));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, (float) 13.5));
        }

    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("points.json");
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
}

