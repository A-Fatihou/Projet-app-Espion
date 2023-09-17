package com.example.trakeur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Les variables
    Button btLocation;
    TextView textView1,textView2,textView3,textView4,textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigner des valeur au variable


        btLocation = findViewById(R.id.bt_location);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        
        // initialiser le fusedLocationProviderClient

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Verifier la permission de l'utilisateur

                if (ActivityCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    
                    // quand la permission est accordé
                    
                    getLocation();
                    
                    
                }else {

                    //Quand la permisson et refusé

                    ActivityCompat.requestPermissions(MainActivity.this
                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
        
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                //initialiser la localisation

                Location location = task.getResult();
                if (location != null){
                    try {
                        // initialiser les coordonnée

                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());

                        //initialiser l'adresse

                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                        //Parametrer la latitude

                        textView1.setText(Html.fromHtml(
                                "<font color='#f00020'><b>Latitude :</b><br></font>"
                                + addresses.get(0).getLatitude()
                        ));

                        //Parametrer la logitude

                        textView2.setText(Html.fromHtml(
                                "<font color='#f00020'><b>Longitude :</b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));
                        //Parametrer le nom du pays

                        textView3.setText(Html.fromHtml(
                                "<font color='#f00020'><b>Nom du pays :</b><br></font>"
                                        + addresses.get(0).getCountryName()
                        ));

                        //Parametrer la localisation

                        textView4.setText(Html.fromHtml(
                                "<font color='#f00020'><b>Localisation :</b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));
                        //Parametrer l'adresse

                        textView5.setText(Html.fromHtml(
                                "<font color='#f00020'><b>Adresses :</b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));




                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}