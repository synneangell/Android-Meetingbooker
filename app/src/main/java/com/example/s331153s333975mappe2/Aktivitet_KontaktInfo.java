package com.example.s331153s333975mappe2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_KontaktInfo extends AppCompatActivity {
    TextView navn, telefonnr;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakt_info);
        navn = (TextView) findViewById(R.id.txtNavn);
        telefonnr = (TextView) findViewById(R.id.txtTelefonnr);
        db = new DBHandler(this);

        navn.setText(getIntent().getStringExtra("navn"));
        telefonnr.setText(getIntent().getStringExtra("telefonnr"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.kontaktInfo);
        toolbar.inflateMenu(R.menu.menu_kontaktinfo);
        setActionBar(toolbar);
    }

    /**------------- METODER FOR NEDTREKKSMENY --------------**/
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_kontaktinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.endre:
                Intent intent = new Intent(this, Aktivitet_KontaktEndre.class);
                intent.putExtra("navn",  navn.toString());
                intent.putExtra("telefonnr", telefonnr.toString());
                startActivity(intent);
                break;
            case R.id.slett:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.slettKontakt))
                        .setPositiveButton(getResources().getString(R.string.ja), (dialogInterface, i) -> slettKontakt())
                        .setNegativeButton(getResources().getString(R.string.nei), null)
                        .show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void slettKontakt(){
        Intent intent = getIntent();
        long kontaktId = intent.getLongExtra("KId", 0);
        db.slettKontakt(kontaktId);
        Intent i = new Intent(this, Aktivitet_Kontakt.class);
        startActivity(i);
    }
}
