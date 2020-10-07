package com.example.s331153s333975mappe2;
import androidx.appcompat.app.AlertDialog;
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

public class Aktivitet_MoteDeltagelse extends AppCompatActivity {
    ListView lv;
    DBHandler db;
    SharedPreferences sp;
    SharedPreferences sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_deltagelse);
        db = new DBHandler(this);

        sp = getApplicationContext().getSharedPreferences("Aktivitet_Mote", Context.MODE_PRIVATE);
        sp2 = getApplicationContext().getSharedPreferences("Aktivitet_MoteDeltagelse", Context.MODE_PRIVATE);

        lv = (ListView) findViewById(R.id.listView_kontakter);

        List <String> deltakere = visMoteDeltakelseListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, deltakere);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Deltakere til møte");
        setActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_motedeltagelse);
        setActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_MoteDeltagelse.this, Aktivitet_Mote.class);
                startActivity(intent);
            }
        });

        /**---- KNAPP FOR REGISTRERING AV MØTEDELTAGELSE ----**/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long moteid = sp.getLong("MId", 0);

                SharedPreferences.Editor editor = sp2.edit();
                editor.putLong("MId", moteid);
                editor.apply();
                Intent intent = new Intent(Aktivitet_MoteDeltagelse.this, Aktivitet_MoteRegDeltagelse.class);
                startActivity(intent);
            }
        });
    }

    /**------------- METODE FOR Å POPULERE LISTVIEW --------------**/
    public List<String> visMoteDeltakelseListView(){
        long moteid = sp.getLong("MId", 0);
        Log.d("Moteid i motedeltagelse", Long.toString(moteid));

        List <Long> kontaktId = db.finnMoteDeltakelse(moteid);
        List <String> stringKontakter = new ArrayList<>();
        List <Kontakt> kontakter = new ArrayList<>();
        for(int i = 0; i < kontaktId.size(); i++){
            Log.d("Kontaktid", Long.toString(kontaktId.get(i)));
            kontakter.add(db.finnKontakt(kontaktId.get(i)));
        }
        for(int i = 0; i < kontakter.size(); i++){
            stringKontakter.add("ID: "+kontakter.get(i)._KID+", navn: "+kontakter.get(i).navn+", telefon: "+kontakter.get(i).telefon);
        }
        return stringKontakter;
    }

    /**------------- METODER FOR NEDTREKKSMENY --------------**/
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_motedeltagelse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.endre:
                long MId = sp.getLong("MId", 0);
                String innNavn = sp.getString("moteNavn", "feil");
                String innSted = sp.getString("moteSted", "feil");
                String innDato = sp.getString("moteDato", "feil");
                String innTid = sp.getString("moteTid","feil");

                SharedPreferences.Editor editor = sp2.edit();
                editor.putLong("MId", MId);
                editor.putString("moteNavn", innNavn);
                editor.putString("moteSted", innSted);
                editor.putString("moteDato", innDato);
                editor.putString("moteTid", innTid);
                editor.apply();
                Intent intent = new Intent(this, Aktivitet_MoteEndre.class);
                startActivity(intent);
                break;
            case R.id.slett:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.slettMote))
                        .setPositiveButton(getResources().getString(R.string.ja), (dialogInterface, i) -> slettMote())
                        .setNegativeButton(getResources().getString(R.string.nei), null)
                        .show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void slettMote() {
        long MoteId = sp.getLong("MId", 0);
        db.slettMote(MoteId);
        Intent i = new Intent(this, Aktivitet_Mote.class);
        startActivity(i);
    }

}