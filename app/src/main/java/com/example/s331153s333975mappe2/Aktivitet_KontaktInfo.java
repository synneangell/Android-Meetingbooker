package com.example.s331153s333975mappe2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_KontaktInfo extends AppCompatActivity {
    TextView txtNavn, txtTelefonnr;
    DBHandler db;
    SharedPreferences sp;
    SharedPreferences sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.kontakt_info);
        txtNavn = (TextView) findViewById(R.id.txtNavn);
        txtTelefonnr = (TextView) findViewById(R.id.txtTelefonnr);
        db = new DBHandler(this);

        sp = getApplicationContext().getSharedPreferences("Aktivitet_Kontakt", Context.MODE_PRIVATE);
        sp2 = getApplicationContext().getSharedPreferences("Aktivitet_KontaktInfo", Context.MODE_PRIVATE);


        txtNavn.setText(sp.getString("kontaktNavn", "feil"));
        txtTelefonnr.setText(sp.getString("kontaktTelefonnr", "feil"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_kontaktinfo);
        setActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher_small);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_KontaktInfo.this, Aktivitet_Kontakt.class);
                startActivity(intent);
            }
        });
    }

    /**------------- METODER FOR NEDTREKKSMENY --------------**/
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_kontaktinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.endre:
                long kontaktId = sp.getLong("KId", 0);
                String navn = sp.getString("kontaktNavn", "feil");
                String telefonnr = sp.getString("kontaktTelefonnr", "feil");

                SharedPreferences.Editor editor = sp2.edit();
                editor.putLong("KId", kontaktId);
                editor.putString("kontaktNavn", navn);
                editor.putString("kontaktTelefonnr", telefonnr);
                editor.apply();

                Intent intent = new Intent(this, Aktivitet_KontaktEndre.class);
                startActivity(intent);
                break;
            case R.id.slett:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
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
        long kontaktId = sp.getLong("KId", 0);
        db.slettKontakt(kontaktId);
        Intent intent = new Intent(this, Aktivitet_Kontakt.class);
        startActivity(intent);
    }
}
