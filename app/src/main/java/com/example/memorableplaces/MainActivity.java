package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView placeList;
    static ArrayList<String> places=new ArrayList<String>();
    static ArrayList<LatLng> locations=new ArrayList<LatLng>();
    static ArrayAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);
        ArrayList<String> lat=new ArrayList<String>();
        ArrayList<String> lon=new ArrayList<String>();
        intent=new Intent(MainActivity.this,MapsActivity.class);
        placeList=findViewById(R.id.placeList);
        try {
            places= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new String())));
            lat= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes",ObjectSerializer.serialize(new String())));
            lon= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes",ObjectSerializer.serialize(new String())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        locations.clear();
        for(int i=0;i<lat.size();i++){
            LatLng latLng=new LatLng(Double.parseDouble(lat.get(i)),Double.parseDouble(lon.get(i)));
            locations.add(latLng);
        }
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);
        placeList.setAdapter(adapter);
        placeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("Place",position);
                startActivity(intent);
            }
        });
    }

    public void addNewPlace(View view){
        intent.putExtra("Place",-1);
        startActivity(intent);
    }
}
