package com.example.s331153s333975mappe2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote);
        db = new DBHandler(this);

        lv = findViewById(R.id.listView_mote);
        List<String> visMoter = visMoterListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, visMoter);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                List<Mote> moter = db.finnAlleMoter();
                Mote mote = moter.get(i);
                //adapterView.getOnItemClickListener();
                Intent intent = new Intent(view.getContext(), Aktivitet_MoteDeltagelse.class);

                long moteID = mote.get_MID();
                intent.putExtra("moteID", moteID);
                /*
                String innNavn = mote.getNavn();
                String innSted = mote.getSted();
                String innTidspunkt = mote.getTidspunkt();
                intent.putExtra("navn", innNavn);
                intent.putExtra("sted", innSted);
                intent.putExtra("tidspunkt", innTidspunkt);*/
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
        toolbar.setSubtitle("Inne på møter");
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
    }

    /**----- METODE FOR Å POPULERE LISTVIEW ------**/
    public List<String> visMoterListView(){
        List <String> stringMoter = new ArrayList<>();
        List<Mote> moter = db.finnAlleMoter();
        for(int i = 0; i < moter.size(); i++){
            stringMoter.add("ID: "+moter.get(i)._MID+", navn: "+moter.get(i).navn+", sted: "+moter.get(i).sted+", tid: "+moter.get(i).tidspunkt);
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