package com.example.s331153s333975mappe2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Aktivitet_Mote extends AppCompatActivity {
    ListView lv;
    DBHandler db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getApplicationContext().getSharedPreferences("Aktivitet_Mote", Context.MODE_PRIVATE);

        setContentView(R.layout.mote);
        lv = findViewById(R.id.listView_mote);
        db = new DBHandler(this);

        final List<String> visMoter = visMoterListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, visMoter);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                List<Mote> moter = db.finnAlleMoter();
                Mote mote = moter.get(i);
                long MId = mote.get_MID();

                SharedPreferences.Editor editor = sp.edit();
                editor.putLong("MId", MId);
                editor.putString("moteNavn", mote.getNavn());
                editor.putString("moteSted", mote.getSted());
                editor.putString("moteDato", mote.getDato());
                editor.putString("moteTid", mote.getTid());
                editor.apply();
                Intent intent = new Intent(view.getContext(), Aktivitet_MoteDeltagelse.class);
                startActivity(intent);
            }
        });

        /**---- KNAPP FOR REGISTRERING ----**/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Aktivitet_Mote.this, Aktivitet_MoteReg.class));
            }
        });

        /**---- TOOLBAR OPPRETTES ----**/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Planlagte møter");
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
    }

    /**----- METODE FOR Å POPULERE LISTVIEW ------**/
    public List<String> visMoterListView(){
        List <String> stringMoter = new ArrayList<>();
        List<Mote> moter = db.finnAlleMoter();
        for(int i = 0; i < moter.size(); i++){
            stringMoter.add("Møtenavn: "+moter.get(i).navn+", sted: "+moter.get(i).sted+", dato:"+ moter.get(i).dato +", tid: "+moter.get(i).tid);
        }
        return stringMoter;
    }

    /**------------- METODER FOR NEDTREKKSMENY --------------**/
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.kontakter:
                //Her er det egentlig meningen at det skal sendes verdier til neste aktivitet ut ifra det man trykker på i listview
                Intent i = new Intent(this, Aktivitet_Kontakt.class);
                startActivity(i);
                break;
            case R.id.innstillinger:
                Intent i2 = new Intent(this, Aktivitet_Innstillinger.class);
                startActivity(i2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}