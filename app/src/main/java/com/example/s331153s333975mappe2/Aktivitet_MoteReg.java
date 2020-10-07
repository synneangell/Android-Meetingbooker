package com.example.s331153s333975mappe2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_MoteReg extends AppCompatActivity {
    EditText navn, sted, tidspunkt;
    DBHandler db;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_registrer);
        navn = (EditText) findViewById(R.id.txtNavnMote);
        sted = (EditText) findViewById(R.id.txtSted);
        tidspunkt = (EditText) findViewById(R.id.txtTidspunkt);
        btnReg = (Button) findViewById(R.id.btnRegMote);
        db = new DBHandler(this);
    }

    public void regMote(View v){
        Mote mote = new Mote(navn.getText().toString(), sted.getText().toString(), tidspunkt.getText().toString());
        db.leggTilMote(mote);
        Intent intent = new Intent(this, Aktivitet_Mote.class);
        startActivity(intent);
    }
}
