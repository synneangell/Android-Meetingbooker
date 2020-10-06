package com.example.s331153s333975mappe2;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

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
        lv = (ListView) findViewById(R.id.listDeltagelse);

        final List<String> deltakere = visMoteDeltakelseListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, deltakere);
        lv.setAdapter(adapter);

        //adapter.remove(i);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                adapter.remove(Integer.toString(i));
                //List<MoteDeltagelse> md = db.finnMoteDeltakelse();
                //MoteDeltagelse moteDeltagelse = md.get(i);
                //få opp en popup-boks med slett og avbryt og når man trykker på slett så tar adapter.remove(position) et eller annet
                AlertDialog.Builder builder = new AlertDialog.Builder(Aktivitet_MoteDeltagelse.this);
                builder.setMessage(getResources().getString(R.string.slettKontakt))
                        .setPositiveButton(getResources().getString(R.string.ja), (dialogInterface, i2) -> slettMoteDeltagelse())
                        .setNegativeButton(getResources().getString(R.string.nei), null)
                        .show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.motedeltagelse);
        toolbar.setSubtitle("Inne på møteDeltagelse");
        toolbar.inflateMenu(R.menu.menu_motedeltagelse);
        setActionBar(toolbar);
    }

    public void slettMoteDeltagelse() {
        //long innMDID = MoteDeltagelse.get_KID();
        //db.slettMoteDeltakelse(innMDID);
    }

    /**------------- METODE FOR Å POPULERE LISTVIEW --------------**/
    public List<String> visMoteDeltakelseListView(){
        Intent intent = getIntent();
        long moteid = intent.getLongExtra("moteID", 0);

        List <String> stringKontakter = new ArrayList<>();
        List<Kontakt> kontakter = db.finnMoteDeltakelse(moteid);
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
                Intent i = new Intent(this, Aktivitet_MoteEndre.class);
                startActivity(i);
                break;
            case R.id.slett:
                //her skal det være kode som sletter valgte møte
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}