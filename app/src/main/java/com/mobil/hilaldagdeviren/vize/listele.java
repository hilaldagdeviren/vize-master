package com.mobil.hilaldagdeviren.vize;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by hilaldağdeviren on 21.04.2017.
 */

public class listele extends AppCompatActivity {

    ProgressDialog pd;
    ListView lvPlaces;
    VenueAdapter venueAdapter;
    String client_id = "HNDMUAIVL1KQZGNJNNM4FONNM2WQOAMRPM1XRM35TCJZGKS0";
    String client_secret = "MQ255OAQF1TAUN4B1F50IJRUZ2KSTL3CJXD2YH3BUHAETOEE";
    String api_version ="20161016";
    String ll;
    FoursquareResponse foursquareResponse;
    List<Venue> venue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lvPlaces = (ListView) findViewById(R.id.lvPlaces);
        double lat = getIntent().getExtras().getDouble("lat");
        double lng = getIntent().getExtras().getDouble("lng");
        ll = String.valueOf(lat)+","+lng;

        Log.i("INFO", "HAZIR = "+ll);

        pd = new ProgressDialog(listele.this);
        pd.setMessage("Lütfen bekleyin.");
        pd.show();

        RetroInterface retroInterface = RetroClient.getClient().create(RetroInterface.class);
        Call<FoursquareResponse> call = retroInterface.getVenueJson(client_id,client_secret,api_version,ll);
        call.enqueue(new Callback<FoursquareResponse>() {
            @Override
            public void onResponse(Call<FoursquareResponse> call, Response<FoursquareResponse> response) {
                foursquareResponse = response.body();
                int code = foursquareResponse.getMeta().getCode();

                    venue = foursquareResponse.getResponse().getVenues();
                    venueAdapter = new VenueAdapter(getApplicationContext(),venue);
                    lvPlaces.setAdapter(venueAdapter);



                pd.dismiss();
            }

            @Override
            public void onFailure(Call<FoursquareResponse> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Bağlantı yok!", Toast.LENGTH_SHORT).show();
            }
        });

        lvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = venue.get(position).getName();
                double lat = venue.get(position).getLocation().getLat();
                double lng = venue.get(position).getLocation().getLng();

                Intent maps = new Intent(listele.this, com.mobil.hilaldagdeviren.vize.maps.class);
                maps.putExtra("lat",lat);
                maps.putExtra("lng", lng);
                maps.putExtra("name", name);
                startActivity(maps);
            }
        });



    }
}
