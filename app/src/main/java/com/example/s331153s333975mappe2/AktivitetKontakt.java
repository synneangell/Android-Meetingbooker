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

public class AktivitetKontakt extends AppCompatActivity {
    ListView listView_kontakter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakter);

        listView_kontakter = findViewById(R.id.listView_kontakter);
        String[] verdier = new String[]{"Fredrik", "Tor", "Jacob", "Simen"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, verdier);

        listView_kontakter.setAdapter(adapter);
        listView_kontakter.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                if(i == 0){
                    //Her er det egentlig meningen at det skal sendes verdier til neste aktivitet ut ifra det man trykker på i listview
                    Intent intent = new Intent(view.getContext(), AktivitetMoteDeltagelse.class);
                    startActivity(intent);
                }
            }
        });

        /**---- KNAPP FOR REGISTRERING ----**/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AktivitetKontakt.this, RegistrerKontakt.class));
            }
        });

        /**---- TOOLBAR OPPRETTES ----**/
        Toolbar toolbar = (Toolbar) findViewById(R.id.kontaktToolbar);
        toolbar.setSubtitle("Inne på kontakter");
        toolbar.inflateMenu(R.menu.menu_kontakter);
        setActionBar(toolbar);
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
                Intent i = new Intent(this, AktivitetMote.class);
                startActivity(i);
                break;
            case R.id.innstillinger:
                Intent i2 = new Intent(this, AktivitetInnstillinger.class);
                startActivity(i2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
