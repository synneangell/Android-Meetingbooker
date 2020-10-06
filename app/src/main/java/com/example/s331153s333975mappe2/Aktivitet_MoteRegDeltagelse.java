package com.example.s331153s333975mappe2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Aktivitet_MoteRegDeltagelse extends Activity {
    ListView lv;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_registrer_deltakere);
        lv = (ListView) findViewById(R.id.listView_kontakter);
        db = new DBHandler(this);

        final List<String> visKontaker = visKontakerListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, visKontaker);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                List<Kontakt> kontakter = db.finnAlleKontakter();
                Kontakt kontakt = kontakter.get(i);
                long KId = kontakt.get_KID();

                Intent intent = getIntent();
                long MId = intent.getLongExtra("MId", 0);
                if(db.sjekkMoteDeltagelse(KId, MId)){
                    Toast.makeText(Aktivitet_MoteRegDeltagelse.this, "Deltaker er allerede lagt til", Toast.LENGTH_SHORT).show();
                }
                else {
                    MoteDeltagelse moteDeltagelse = new MoteDeltagelse(MId, KId);
                    db.leggTilMoteDeltakelse(moteDeltagelse);
                    Toast.makeText(Aktivitet_MoteRegDeltagelse.this, "Deltaker er nå lagt til", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**---- TOOLBAR OPPRETTES ----**/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle("Inne på registrer deltakere");
        toolbar.inflateMenu(R.menu.menu_mote_reg_deltagelse);
        setActionBar(toolbar);
    }

    /**----- Populere listview ------**/
    public List<String> visKontakerListView(){
        List <String> stringKontakter = new ArrayList<>();
        List<Kontakt> kontakter = db.finnAlleKontakter();
        for(int i = 0; i < kontakter.size(); i++){
            stringKontakter.add("ID: "+kontakter.get(i)._KID+", navn: "+kontakter.get(i).navn+", telefon: "+kontakter.get(i).telefon);
        }
        return stringKontakter;
    }

}

