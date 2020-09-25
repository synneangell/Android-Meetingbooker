package com.example.s331153s333975mappe2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AktivitetMoteDeltagelse extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motedeltagelse);

        ListView lv = (ListView) findViewById(R.id.listDeltagelse);
        String[] verdier = new String[]{"Nikola", "Synne", "Martine", "Camilla"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, verdier);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                if(i == 0){
                    Intent intent = new Intent(view.getContext(), AktivitetMote.class);
                    startActivity(intent);
                } else if (i == 1){
                    Intent intent = new Intent(view.getContext(), AktivitetKontakt.class);
                    startActivity(intent);
                }
            }
        });
    }
}