package com.example.s331153s333975mappe2;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Aktivitet_Kontakt extends AppCompatActivity {
    ListView lv;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakter);
        lv = (ListView) findViewById(R.id.listView_kontakter);
        db = new DBHandler(this);

        final List<String> visKontakter = visKontakterListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, visKontakter);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                List<Kontakt> kontakter = db.finnAlleKontakter();
                Kontakt kontakt = kontakter.get(i);
                Intent intent = new Intent(view.getContext(), Aktivitet_KontaktInfo.class);
                long innKId = kontakt.get_KID();
                String innNavn = kontakt.getNavn();
                String innTelefonnr = kontakt.getTelefon();
                intent.putExtra("KId", innKId);
                intent.putExtra("navn", innNavn);
                intent.putExtra("telefonnr", innTelefonnr);
                startActivity(intent);
            }
        });

        /**---- KNAPP FOR REGISTRERING ----**/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Aktivitet_Kontakt.this, Aktivitet_KontaktReg.class));
            }
        });

        /**---- TOOLBAR OPPRETTES ----**/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("\t\t Alle kontakter ");
        toolbar.inflateMenu(R.menu.menu_kontakter);
        setActionBar(toolbar);
    }

    /**----- Populere listview ------**/
    public List<String> visKontakterListView(){
        List <String> stringKontakter = new ArrayList<>();
        List<Kontakt> kontakter = db.finnAlleKontakter();
        for(int i = 0; i < kontakter.size(); i++){
            stringKontakter.add("ID: "+kontakter.get(i)._KID+", navn: "+kontakter.get(i).navn+", telefon: "+kontakter.get(i).telefon);
        }
        return stringKontakter;
    }


    /**------------- METODER FOR NEDTREKKSMENY --------------**/
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_kontakter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mote:
                Intent i = new Intent(this, Aktivitet_Mote.class);
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
