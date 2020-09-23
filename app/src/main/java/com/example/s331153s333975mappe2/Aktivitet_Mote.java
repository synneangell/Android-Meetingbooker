package com.example.s331153s333975mappe2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toolbar;

public class Aktivitet_Mote extends AppCompatActivity implements Fragment_Mote.UrlEndret {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote);

        /**---- TOOLBAR OPPRETTES ----**/
        Toolbar toolbar = (Toolbar) findViewById(R.id.moteToolbar);
        toolbar.setSubtitle("Inne på møter");
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
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

    /**------------- METODE SOM BESTEMMER OM DET ER STÅENDE ELLER LIGGENDE MODUS --------------**/
    //viser skjermbildet ut ifra om det er stående eller liggende modus
    @Override
    public void linkEndret(String link) {
        if(findViewById(R.id.innholdMote) != null){
            Fragment_MoteInnhold visMoter = (Fragment_MoteInnhold) getSupportFragmentManager().findFragmentById(R.id.innholdMote);
            if(visMoter == null){
                visMoter = new Fragment_MoteInnhold();
                visMoter.init(link);
            }
        }
    }
}