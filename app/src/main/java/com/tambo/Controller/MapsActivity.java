package com.tambo.Controller;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tambo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    private CameraPosition mCameraPosition;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private final int LOCATION_PERMISSION_CODE = 1;
    private boolean mLocationPermissionGranted;
    private static final int DEFAULT_ZOOM = 15;
    protected Location lastLocation = new Location("watever");
    public AddressResultReceiver resultReceiver;
    public Context context = this;
    private EditText mSearch;
    private String uInput;
    private RequestQueue requestQueue;
    public double lat=0;
    public double lng=0;
    public String dir="";





    private final int REQUEST_PLACE = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mSearch = findViewById(R.id.search_edit_text);
        mSearch.setMaxLines(1);
        mSearch.setInputType(InputType.TYPE_CLASS_TEXT);


        getLocationPermission();
        search();
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Obtencion del mapa
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void search() {

        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            List<Address> address = null;

            @Override

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH
                        || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    uInput = mSearch.getText().toString();
                    /*
                    Geocoder geocoder = new Geocoder(MapsActivity.this);


                        address = geocoder.getFromLocationName(uInput, 1);
                        double lat = address.get(0).getLatitude();
                        double lng = address.get(0).getLongitude();
                        */

                    requestQueue= Volley.newRequestQueue(getApplicationContext());
                    uInput=uInput.replace(' ','+');

                    JsonObjectRequest request = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?address="+uInput+"&key=AIzaSyCrqAm_G6d05K-KTNIyTR", new JSONObject(),

                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        lat = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location")
                                                .getDouble("lat");
                                     lng = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location")
                                                .getDouble("lng");
                                        dir = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(

                                                new LatLng(lat,
                                                        lng), 17));
                                        Marker markeraux= mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(lat, lng)).title(dir));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });



                    requestQueue.add(request);






                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(final Marker marker) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Selección de ubicación")
                                        .setMessage("¿Estas seguro de querer seleccionar esta ubicación? \n" + dir)


                                        .setPositiveButton("SI!", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(MapsActivity.this, "Ubicación guardada exitosamente", Toast.LENGTH_SHORT).show();
                                                Intent intent = getIntent();
                                                intent.putExtra("address", String.valueOf(dir));
                                                intent.putExtra("latitude", String.valueOf(lat));
                                                intent.putExtra("longitude",String.valueOf(lng));
                                                setResult(RESULT_OK, intent);
                                                Log.d("Info response map",uInput+" - - "+lastLocation.getLatitude()+" - - "+lastLocation.getLongitude());
                                                finish();

                                            }
                                        })
                                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create().show();

                                return false;

                            }
                        });


                }

                return false;
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        //Obtener permisios de ubicacion o verificarlos
        getLocationPermission();
        //Listeners cuando toco un v
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        updateLocationUI();
        getDeviceLocation();
        search();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                double x = latLng.longitude;
                double y = latLng.latitude;
                requestQueue= Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+y+","+x+"&key=AIzaSyCrqAm_G6d05K-KTNIyTR",new JSONObject(),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                     dir = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.error.VolleyError error) {

                    }


                });
                requestQueue.add(request);


                /*
                lastLocation.setLatitude(y);
                lastLocation.setLongitude(x);
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(y, x, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("FUI", "YO");
                }
                Address address = addresses.get(0);

                String dir = "";
                // Fetch the address lines using getAddressLine,
                // join them, and send them to the thread.

                dir += address.getAddressLine(0) + ", " + address.getSubLocality();
                final String direccion = dir;

*/
                final String direccion = dir;
                new AlertDialog.Builder(context)
                        .setTitle("Selección de ubicación")
                        .setMessage("¿Estas seguro de querer seleccionar esta ubicación? \n" + dir)

                        .setPositiveButton("SI!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MapsActivity.this, "Ubicación guardada exitosamente", Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                intent.putExtra("address", direccion);
                                intent.putExtra("latitude", String.valueOf(lastLocation.getLatitude()));
                                intent.putExtra("longitude",String.valueOf(lastLocation.getLongitude()));
                                setResult(RESULT_OK, intent);
                                Log.d("Info response map",direccion+" - - "+lastLocation.getLatitude()+" - - "+lastLocation.getLongitude());
                                finish();

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

                //Toast.makeText(MapsActivity.this, "Encontre "+address.getAddressLine(0)+", "+address.getSubLocality(), Toast.LENGTH_SHORT).show();
                // startIntentService();


            }

        });

    }


    public void getLocationPermission() {
        //Si ya contamos con permisos simplemente seteamos la variable como true
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLocationPermissionGranted = true;
        } else {
            requestLocationPermission();
        }

    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permiso Necesario")
                    .setMessage("Tambo necesita este permiso para poder  recomendarte lugares cerca")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
                            mLocationPermissionGranted = true;
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();


    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();


                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {

                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            double latitude = mLastKnownLocation.getLatitude();
                            double longitude = mLastKnownLocation.getLongitude();
                            LatLng local = new LatLng(latitude, longitude);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(latitude,
                                            longitude), DEFAULT_ZOOM));

                        } else {


                        }

                    }

                });

            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }


    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {


    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.i(TAG, "Pai me enviaron esto");
            // Display the address string
            // or an error message sent from the intent service.
            Toast.makeText(MapsActivity.this, "FUNCIONE PRRO", Toast.LENGTH_SHORT).show();


        }
    }

    protected void startIntentService() {
        resultReceiver   = new AddressResultReceiver(new Handler());
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation);
        startService(intent);
    }
}
