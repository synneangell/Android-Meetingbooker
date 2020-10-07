package com.example.s331153s333975mappe2;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Aktivitet_MoteDeltagelse extends AppCompatActivity {
    DBHandler db;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_deltagelse);
        db = new DBHandler(this);
        lv = (ListView) findViewById(R.id.listView_kontakter);

        List <String> deltakere = visMoteDeltakelseListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, deltakere);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){

            }
        });

        /**---- TOOLBAR OPPRETTES ----**/
        Toolbar toolbar = (Toolbar) findViewById(R.id.motedeltagelse);
        toolbar.setSubtitle("Inne på møteDeltagelse");
        toolbar.inflateMenu(R.menu.menu_motedeltagelse);
        setActionBar(toolbar);

        /**---- KNAPP FOR REGISTRERING AV MØTEDELTAGELSE ----**/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                long moteid = intent.getLongExtra("MId", 0);

                Intent intent2 = new Intent(Aktivitet_MoteDeltagelse.this, Aktivitet_MoteRegDeltagelse.class);
                intent2.putExtra("MId", moteid);
                startActivity(intent2);
            }
        });
    }

    /**------------- METODE FOR Å POPULERE LISTVIEW --------------**/
    public List<String> visMoteDeltakelseListView(){
        Intent intent = getIntent();
        long moteid = intent.getLongExtra("MId", 0);

        List <Long> kontaktId = db.finnMoteDeltakelse(moteid);
        List <String> stringKontakter = new ArrayList<>();
        List <Kontakt> kontakter = new ArrayList<>();
        for(int i = 0; i < kontaktId.size(); i++){
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
                Intent intent1 = getIntent();
                long MId = intent1.getLongExtra("MId", 0);
                String innNavn = intent1.getStringExtra("navn");
                String innSted = intent1.getStringExtra("sted");
                String innTidspunkt = intent1.getStringExtra("tidspunkt");

                Intent intent2 = new Intent(this, Aktivitet_MoteEndre.class);
                intent2.putExtra("MId", MId);
                intent2.putExtra("navn", innNavn);
                intent2.putExtra("sted", innSted);
                intent2.putExtra("tidspunkt", innTidspunkt);
                startActivity(intent2);
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
        Intent intent = getIntent();
        long MoteId = intent.getLongExtra("MId", 0);
        db.slettMote(MoteId);
        Intent i = new Intent(this, Aktivitet_Mote.class);
        startActivity(i);
    }
}