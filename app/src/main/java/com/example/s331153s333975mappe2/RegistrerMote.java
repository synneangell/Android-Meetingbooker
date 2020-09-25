package com.example.s331153s333975mappe2;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RegistrerMote extends AppCompatActivity {
    EditText navn, sted, tidspunkt;
    DBHandler db;
    Button regMote, visMote;
    TextView txtVisMote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrer_mote);
        navn = (EditText) findViewById(R.id.txtNavnMote);
        sted = (EditText) findViewById(R.id.txtSted);
        tidspunkt = (EditText) findViewById(R.id.txtTidspunkt);
        regMote = (Button) findViewById(R.id.btnRegMote);
        visMote = (Button) findViewById(R.id.btnVisMote);
        txtVisMote = (TextView) findViewById(R.id.txtVisMote);
        db = new DBHandler(this); //for å kunne bruke databasen.
    }

    //---------- onClick fra layout på knapp for å registrere møte -------//
    public void regMote(View v){
        Mote mote = new Mote(navn.getText().toString(), sted.getText().toString(), tidspunkt.getText().toString());
        db.leggTilMote(mote);
        Log.d("Legger inn møte", mote.navn + " "+mote.sted + " "+mote.tidspunkt);
    }

    //---- onClick fra layout på knapp for å vise mote i TextView----//
    public void visMote(View v){
        String tekst = "";
        List<Mote> moter = db.finnAlleMoter();
        for (Mote mote : moter) {
            tekst = tekst + ",Navn: " +
                    mote.getNavn() + " ,Sted: " +
                    mote.getSted() + " , Tidspunkt " + mote.getTidspunkt();
            Log.d("Navn: ", tekst);
        }
        visMote.setText(tekst);
    }
}
