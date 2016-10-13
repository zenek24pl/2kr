package pl.noskilljustfun.dwakrokistad.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.noskilljustfun.dwakrokistad.R;
import pl.noskilljustfun.dwakrokistad.model.Clue;
import pl.noskilljustfun.dwakrokistad.model.Place;
import pl.noskilljustfun.dwakrokistad.model.Quest;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameMapFragment extends Fragment implements OnMapReadyCallback {


    private static Clue clue;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_map, container, false);
    }

    public static GameMapFragment newInstance(Clue clue1) {

        Bundle args = new Bundle();

        clue = clue1;

        GameMapFragment fragment = new GameMapFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.gamemap);
        fragment.getMapAsync(this);

        googleMap=fragment.getMap();
        googleMap.setMyLocationEnabled(true);


        LocationManager loctionManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = loctionManager.getBestProvider(criteria,true);
        Location location = loctionManager.getLastKnownLocation(provider);


        LocationListener lisenerLok = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //  drawMarker(location);



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        loctionManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,100, lisenerLok);
        loctionManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,100, lisenerLok);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng point = new LatLng((double)clue.getPositionX(),(double)clue.getPositionY());
        googleMap.addMarker(new MarkerOptions().position(point).title(clue.getName()));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, (float) 13.5));



    }

    private void drawMarker(Location location){
        googleMap.clear();
        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 17));
    }


}
