package net.simplifiedcoding.androidimageupload;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class temp extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    String JSON_String;
    String  json_string0,json_string1,json_string2;
    JSONObject jsonObject,JO;
    JSONArray jsonArray;
    ImageButton visit, eat, hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //       try{
        setContentView(R.layout.activity_maps);
        visit = (ImageButton) findViewById(R.id.imageButton1);
        eat = (ImageButton) findViewById(R.id.imageButton2);
        hotel = (ImageButton) findViewById(R.id.imageButton3);


        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                System.out.println(json_string0);
                try {
                    JO = new JSONObject(json_string0);
                    jsonArray = JO.optJSONArray("attract");
                    int count = 0;
                    String name, lat, lng;

                    while (count < jsonArray.length()) {
                        //  JSONObject JO = null;
                        try {
                            JO = jsonArray.getJSONObject(count);
                            name = JO.getString("NAME");
                            lat = JO.getString("LATITUDE");
                            lng = JO.getString("LONGITUDE");
                            LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                            mMap.addMarker(new MarkerOptions().position(sydney).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            count++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    LatLngBounds GWALIOR = new LatLngBounds(new LatLng(26.104292, 78.111943), new LatLng(26.320862, 78.270518));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GWALIOR.getCenter(), 12));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                System.out.println(json_string1);
                try {
                    JO = new JSONObject(json_string1);
                    jsonArray = JO.optJSONArray("res");
                    int count = 0;
                    String name, lat, lng;

                    while (count < jsonArray.length()) {
                        //  JSONObject JO = null;
                        try {
                            JO = jsonArray.getJSONObject(count);
                            name = JO.getString("NAME");
                            lat = JO.getString("LATITUDE");
                            lng = JO.getString("LONGITUDE");
                            LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                            mMap.addMarker(new MarkerOptions().position(sydney).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            count++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    LatLngBounds GWALIOR = new LatLngBounds(new LatLng(26.104292, 78.111943), new LatLng(26.320862, 78.270518));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GWALIOR.getCenter(), 18));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                System.out.println(json_string2);
                try {
                    JO = new JSONObject(json_string2);
                    jsonArray = JO.optJSONArray("hotels");
                    int count = 0;
                    String name, lat, lng;

                    while (count < jsonArray.length()) {
                        //  JSONObject JO = null;
                        try {
                            JO = jsonArray.getJSONObject(count);
                            name = JO.getString("NAME");
                            lat = JO.getString("LATITUDE");
                            lng = JO.getString("LONGITUDE");
                            LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                            mMap.addMarker(new MarkerOptions().position(sydney).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            count++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    LatLngBounds GWALIOR = new LatLngBounds(new LatLng(26.104292, 78.111943), new LatLng(26.320862, 78.270518));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GWALIOR.getCenter(), 12));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        //       // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
 /*       }
       catch (Exception e){
            startActivity(new Intent(MapsActivity.this,TravelActivity.class));
        }*/

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setBuildingsEnabled(false);
        new BackGroundTask().execute();

        // Add a marker in Sydney and move the camera


    }

    class BackGroundTask extends AsyncTask<Void, Void, String> {// params,progress,result
        String json_url[]=new String[3];

        @Override
        protected void onPreExecute() {// handled by UI threads
            json_url[0] = "http://176.32.230.250/anshuli.com/tourism/attractions.php";
            json_url[1] = "http://176.32.230.250/anshuli.com/tourism/restaurants.php";
            json_url[2] = "http://176.32.230.250/anshuli.com/tourism/hotels.php";

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }// can be used for displaying progress bars

        @Override
        protected String doInBackground(Void... voids) {// this carries out the background task
            // StringBuilder stringBuilder = new StringBuilder();
            try {

                int i = 0;
                while (i < 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    URL url = new URL(json_url[i]);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                    while ((JSON_String = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_String + "\n");
                    }
                    if(i==0)
                    {
                        json_string0 = stringBuilder.toString().trim();
                    }
                    if(i==1)
                    {
                        json_string1 = stringBuilder.toString().trim();
                    }

                    if(i==2)
                    {
                        json_string2 = stringBuilder.toString().trim();
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    i++;
                }


                return json_string0;    // trim deletes white space
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            json_string0 = result;
            try {
                JO = new JSONObject(json_string0);
                jsonArray = JO.optJSONArray("attract");
                int count = 0;
                String name, lat, lng;
                System.out.println(json_string0);

                while (count < jsonArray.length()) {
                    //  JSONObject JO = null;
                    try {
                        JO = jsonArray.getJSONObject(count);
                        name = JO.getString("NAME");
                        lat = JO.getString("LATITUDE");
                        lng = JO.getString("LONGITUDE");

                        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                        mMap.addMarker(new MarkerOptions().position(sydney).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        count++;

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
                LatLngBounds GWALIOR = new LatLngBounds(new LatLng(26.104292, 78.111943), new LatLng(26.320862, 78.270518));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GWALIOR.getCenter(), 12));

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}