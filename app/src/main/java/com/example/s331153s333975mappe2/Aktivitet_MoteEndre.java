package com.example.s331153s333975mappe2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Aktivitet_MoteEndre extends AppCompatActivity implements View.OnClickListener {
    EditText navn, sted, tid, dato;
    Button endre, btnDatePicker, btnTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DBHandler db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_endre);
        navn = findViewById(R.id.txtNavnMote);
        sted = findViewById(R.id.txtSted);
        dato = findViewById(R.id.dato);
        tid = findViewById(R.id.tid);
        endre = (Button) findViewById(R.id.endre);
        btnDatePicker=(Button)findViewById(R.id.btn_dato);
        btnTimePicker=(Button)findViewById(R.id.btn_tid);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        db = new DBHandler(this);

        sp = getApplicationContext().getSharedPreferences("Aktivitet_MoteDeltagelse", Context.MODE_PRIVATE);

        navn.setText(sp.getString("moteNavn", "feil"));
        sted.setText(sp.getString("moteSted", "feil"));
        tid.setText(sp.getString("moteTid", "feil"));
        dato.setText(sp.getString("moteDato","feil"));
        //tidspunkt.setText(sp.getString("moteTidspunkt", "feil"));


        /**------------- METODER FOR LAGRE-KNAPPEN --------------**/
        endre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long MId = sp.getLong("MId", 0);
                Log.d("MoteId i endre:", Long.toString(MId));

                Mote mote = new Mote();
                mote.set_MID(MId);
                mote.setNavn(navn.getText().toString());
                mote.setSted(sted.getText().toString());
                mote.setDato(dato.getText().toString());
                mote.setTid(tid.getText().toString());
                db.oppdaterMote(mote);

                Intent intent = new Intent(Aktivitet_MoteEndre.this, Aktivitet_Mote.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("\t\t Endre møteinformasjon ");
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_MoteEndre.this, Aktivitet_Mote.class);
                startActivity(intent);
            }
        });
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