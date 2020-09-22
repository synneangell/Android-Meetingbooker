package com.example.s331153s333975mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class OversiktKontakter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oversikt_kontakter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.kontakterBar);
        myToolbar.inflateMenu(R.menu.menu_kontakter);
        setActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_kontakter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.main:
                finish();
                break;
            case R.id.moteBar:
                Intent i3 = new Intent(this, OversiktMote.class);
                startActivity(i3);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
