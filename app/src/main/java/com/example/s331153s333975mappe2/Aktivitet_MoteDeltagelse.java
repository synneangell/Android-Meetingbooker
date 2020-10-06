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

public class Aktivitet_MoteDeltagelse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_deltagelse);

        //Alt dette er også feil. Her skal man få informasjon om det valgte møtet og listview med deltagerne under. Bare satt inn koden for nå
        ListView lv = (ListView) findViewById(R.id.listDeltagelse);
        String[] verdier = new String[]{"Nikola", "Synne", "Martine", "Camilla"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, verdier);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                if(i == 0){
                    Intent intent = new Intent(view.getContext(), Aktivitet_Kontakt.class);
                    startActivity(intent);
                } else if (i == 1){ //dette er bare en test, denne koden er feil.
                    Intent intent = new Intent(view.getContext(), Aktivitet_Kontakt.class);
                    startActivity(intent);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.motedeltagelse);
        toolbar.setSubtitle("Inne på møteDeltagelse");
        toolbar.inflateMenu(R.menu.menu_motedeltagelse);
        setActionBar(toolbar);
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