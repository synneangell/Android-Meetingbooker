package com.example.s331153s333975mappe2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class Aktivitet_Kontakt extends AppCompatActivity {
    ListView lv;
    DBHandler db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getApplicationContext().getSharedPreferences("Aktivitet_Kontakt", Context.MODE_PRIVATE);

        setContentView(R.layout.kontakter);
        lv = (ListView) findViewById(R.id.listView_kontakter);
        db = new DBHandler(this);

        final List<String> visKontakter = visKontakterListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, visKontakter){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.BLACK);
                return view;
            }
        };

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                List<Kontakt> kontakter = db.finnAlleKontakter();
                Kontakt kontakt = kontakter.get(i);
                long innKId = kontakt.get_KID();
                String innNavn = kontakt.getNavn();
                String innTelefonnr = kontakt.getTelefon();

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("kontaktNavn", innNavn);
                editor.putString("kontaktTelefonnr", innTelefonnr);
                editor.putLong("KId", innKId);
                editor.apply();
                Intent intent = new Intent(view.getContext(), Aktivitet_KontaktInfo.class);
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
        toolbar.setTitle("\tAlle kontakter");
        toolbar.inflateMenu(R.menu.menu_kontakter);
        setActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher_small);

    }

    /**----- POPULERER LISTVIEW ------**/
    public List<String> visKontakterListView(){
        List <String> stringKontakter = new ArrayList<>();
        List<Kontakt> kontakter = db.finnAlleKontakter();
        for(int i = 0; i < kontakter.size(); i++){
            stringKontakter.add("\nID: "+kontakter.get(i)._KID+"\nNavn: "+kontakter.get(i).navn+"\nTelefon: "+kontakter.get(i).telefon);
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
