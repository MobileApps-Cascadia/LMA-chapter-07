package com.example.mycontactlist;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class ContactGPSActivity extends Activity {

    LocationManager locationManager;
    LocationListener gpsListener;
    LocationListener networkListener;
    Location currentBestLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_gps);

        initListButton();
        initMapButton();
        initGPSButton();
        initSettingsButton();
        initGetLocationButton();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_g, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initGetLocationButton() {
        Button locationButton = (Button) findViewById(R.id.buttonGetLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
                    gpsListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            if (isBetterLocation(location)) {
                                currentBestLocation = location;
                                TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                                TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                                TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);
                                txtLatitude.setText(String.valueOf(location.getLatitude()));
                                txtLongitude.setText(String.valueOf(location.getLongitude()));
                                txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                            }
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {}
                        public void onProviderEnabled(String provider) {}
                        public void onProviderDisabled(String provider) {}
                    };
                    networkListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            if (isBetterLocation(location)) {
                                currentBestLocation = location;
                                TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                                TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                                TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);
                                txtLatitude.setText(String.valueOf(location.getLatitude()));
                                txtLongitude.setText(String.valueOf(location.getLongitude()));
                                txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                            }
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {}
                        public void onProviderEnabled(String provider) {}
                        public void onProviderDisabled(String provider) {}
                    };

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, gpsListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, networkListener);
                }
                catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error, Location not available", Toast.LENGTH_LONG).show();                              //4
                }
            }

        });
    }

    @Override
    public void onPause() {
        try {
            locationManager.removeUpdates(gpsListener);
            locationManager.removeUpdates(networkListener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    private boolean isBetterLocation(Location location) {
        boolean isBetter = false;
        if (currentBestLocation == null) {
            isBetter = true;
        }
        else if (location.getAccuracy() <= currentBestLocation.getAccuracy()) {
            isBetter = true;
        }
        else if (location.getTime() - currentBestLocation.getTime() > 5*60*1000) {
            isBetter = true;
        }
        return isBetter;
    }

    private void initListButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonList);
        list.setEnabled(false);
    }

    private void initMapButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonMap);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactGPSActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettingsButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonSettings);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactGPSActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initGPSButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonGPS);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactGPSActivity.this, ContactGPSActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
