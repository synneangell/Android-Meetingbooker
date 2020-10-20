package com.example.s331153s333975mappe2;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Aktivitet_MoteReg extends AppCompatActivity implements View.OnClickListener {
    EditText navn, sted, tid, dato;
    private int year, month, day, mHour, mMinute;
    DBHandler db;
    Button btnReg, btnDatePicker, btnTimePicker;
    ListView lv;

    public static final Pattern NAVN = Pattern.compile("[a-zæøåA-ZÆØÅ0-9 ]{2,20}");
    public static final Pattern STED = Pattern.compile("[a-zæøåA-ZÆØÅ0-9 ]{2,20}");
    public static final Pattern TID = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");

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
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher_small);
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

    public boolean validerDato() throws ParseException {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = sdformat.parse(currentDate);
        String datoInput = dato.getText().toString().trim();

        if(datoInput.isEmpty()) {
            dato.setError("Dato må være valgt eller skrevet inn");
            return false;
        }
        else {
            try {
                Date d2 = sdformat.parse(datoInput);
                if (d1.compareTo(d2) > 0) {
                    dato.setError("Dato kan ikke være tilbake i tid");
                    return false;
                } else {
                    dato.setError(null);
                    return true;
                }
            }
            catch(ParseException e){
                dato.setError("Dato må være i format DD-MM-YYYY");
                return false;
            }
        }
    }

    public boolean validerTid() throws ParseException {
        String currentDateAndTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date d1 = sdformat.parse(currentDateAndTime);
        String datoInput = dato.getText().toString().trim();
        String tidInput = tid.getText().toString().trim();
        String datoOgTidInput = datoInput + " "+tidInput;
        if(tidInput.isEmpty()){
            tid.setError("Tid må være valgt eller skrevet inn");
            return false;
        } else {
            Date d2 = sdformat.parse(datoOgTidInput);
            if (!TID.matcher(tidInput).matches()) {
                tid.setError("Tid må være i format TT:MM");
                return false;
            } else if (d1.compareTo(d2) > 0) {
                tid.setError("Velg et klokkeslett som ikke har vært i dag");
                return false;

            } else {
                tid.setError(null);
                return true;
            }
        }
    }

    public void regMote(View v) throws ParseException {
        if(!validerNavn() | !validerSted() | !validerTid() |!validerDato()){
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
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if(dayOfMonth <= 9 && monthOfYear <= 9){
                                String day = String.format("%02d" , dayOfMonth);
                                monthOfYear += 1;
                                String month = String.format("%02d" , monthOfYear);

                                dato.setText(day + "-" + month + "-" + year);

                            }
                            else if(dayOfMonth <= 9){
                                String day = String.format("%02d" , dayOfMonth);
                                dato.setText(day + "-" + (monthOfYear+1) + "-" + year);

                            }
                            else if(monthOfYear<= 9){
                                monthOfYear += 1;
                                String month = String.format("%02d" , monthOfYear);
                                dato.setText(dayOfMonth + "-" + month + "-" + year);

                            }
                            else{
                                dato.setText(dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
                            }
                        }
                    }, year, month, day);

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
                            if(hourOfDay <= 9 && minute <= 9){
                                String hour = String.format("%02d" , hourOfDay);
                                String min = String.format("%02d" , minute);
                                tid.setText(hour + ":" + min);

                            }
                            else if(hourOfDay <= 9){
                                String hour = String.format("%02d" , hourOfDay);
                                tid.setText(hour + ":" + minute);

                            }
                            else if(minute<= 9){
                                String min = String.format("%02d" , minute);
                                tid.setText(hourOfDay + ":" + min);

                            }
                            else{
                                tid.setText(hourOfDay + ":" + minute);
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}

