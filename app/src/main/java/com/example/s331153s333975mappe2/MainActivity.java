package com.example.s331153s333975mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    /**
     * Denne aktiviteten skal inneholde møteFragment og deltagelseFragment?
     * Her skal det også plasseres toolbar hvor man kan velge å gå til møteAktivitet eller kontaktAktivitet,
     * og der får man opp ListView med alle møter, kan endre/slette og legge til nye møter. TIlsvarende for kontakter.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mintoolbar);
        myToolbar.inflateMenu(R.menu.minmeny);
        setActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.minmeny, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.moteBar:
                Intent i = new Intent(this, OversiktMote.class); //lager intent
                startActivity(i);
                break;
            case R.id.kontakterBar:
                Intent i2 = new Intent(this, OversiktKontakter.class);
                startActivity(i2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}