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
import android.widget.Toolbar;


import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Aktivitet_MoteReg extends AppCompatActivity implements View.OnClickListener {
    EditText navn, sted, tid, dato;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DBHandler db;
    Button btnReg, btnDatePicker, btnTimePicker;
    ListView lv;

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

    public void regMote(View v){
        Mote mote = new Mote(navn.getText().toString(), sted.getText().toString(), dato.getText().toString(), tid.getText().toString());
        db.leggTilMote(mote);
        Log.d("Legger inn møte", "Navn: "+mote.navn + ", Sted: "+mote.sted +  ", Dato: "+mote.dato +", Tid: "+mote.tid);
        Intent intent = new Intent(this, Aktivitet_Mote.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
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
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
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

