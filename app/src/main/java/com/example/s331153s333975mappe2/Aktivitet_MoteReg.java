package com.example.s331153s333975mappe2;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;


import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Aktivitet_MoteReg extends AppCompatActivity implements View.OnClickListener {
    EditText navn, sted, tid, dato;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DBHandler db;
    Button btnReg, btnDatePicker, btnTimePicker;
    ListView lv;

    public static final Pattern NAVN = Pattern.compile("[a-zæøåA-ZÆØÅ]{2,20}");
    public static final Pattern STED = Pattern.compile("[a-zæøåA-ZÆØÅ]{2,20}");
    public static final Pattern DATO = Pattern.compile("(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}");
    public static final Pattern TID = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    //Tid formatet må kunne være på formatet 5:5 også

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_registrer);
        navn = (EditText) findViewById(R.id.txtNavnMote);
        sted = (EditText) findViewById(R.id.txtSted);
        dato = (EditText) findViewById(R.id.dato);
        tid = (EditText) findViewById(R.id.tid);
        btnReg = (Button) findViewById(R.id.btnRegMote);
        btnDatePicker=(Button)findViewById(R.id.btn_dato);
        btnTimePicker=(Button)findViewById(R.id.btn_tid);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        db = new DBHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("\t\t Registrer møte ");
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_MoteReg.this, Aktivitet_Mote.class);
                startActivity(intent);
            }
        });
    }

    public boolean validerNavn(){
        String navnInput = navn.getText().toString().trim();
        if(navnInput.isEmpty()){
            navn.setError("Navn kan ikke være tomt");
            return false;
        } else if (!NAVN.matcher(navnInput).matches()){
            navn.setError("Navnet må bestå av mellom 2 og 20 tegn");
            return false;
        } else {
            navn.setError(null);
            return true;
        }
    }

    public boolean validerSted(){
        String stedInput = sted.getText().toString().trim();
        if(stedInput.isEmpty()){
            sted.setError("Sted kan ikke være tomt");
            return false;
        } else if(!STED.matcher(stedInput).matches()){
            sted.setError("Sted må bestå av mellom 2 og 20 tegn");
            return false;
        } else {
            sted.setError(null);
            return true;
        }
    }

    public boolean validerDato(){
        String datoInput = dato.getText().toString().trim();
        if(datoInput.isEmpty()){
            dato.setError("Dato må være valgt eller skrevet inn");
            return false;
/*        } else if(!DATO.matcher(datoInput).matches()){
            dato.setError("Dato må være i format DD-MM-YYYY");
            return false;*/
        } else {
            dato.setError(null);
            return true;
        }
    }

    public boolean validerTid(){
        String tidInput = tid.getText().toString().trim();
        if(tidInput.isEmpty()){
            tid.setError("Tid må være valgt eller skrevet inn");
            return false;
        } else if(!TID.matcher(tidInput).matches()){
            tid.setError("Tid må være i format TT:MM");
            return false;
        } else {
            tid.setError(null);
            return true;
        }
    }

    public void regMote(View v){
        if(!validerNavn() | !validerSted() | !validerDato() | !validerTid()){
            Toast.makeText(Aktivitet_MoteReg.this, "Alle felt må være riktig fylt inn", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Mote mote = new Mote(navn.getText().toString(), sted.getText().toString(), dato.getText().toString(), tid.getText().toString());
            db.leggTilMote(mote);
            Intent intent = new Intent(this, Aktivitet_Mote.class);
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            dato.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            tid.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}

