package com.example.s331153s333975mappe2;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment_Mote extends Fragment {
    private static ArrayAdapter<String> adapter;
    private static UrlEndret listener;

    public interface UrlEndret {
        public void linkEndret(String link);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity;
        activity = (Activity) context;
        try {
            listener = (UrlEndret) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+ "Må implementere UrlEndret");
        }
    }
    public Fragment_Mote(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceBundle){
        View v = inflater.inflate(R.layout.liste_mote_layout, container, false);
        ListView lv = (ListView) v.findViewById(R.id.listeMote);
        String[] values = new String[]{"mote1", "mote2"};
        final ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < values.length; i++){
            list.add(values[i]);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                String data = adapter.getItem(i);
                listener.linkEndret(data);
            }
        });
        return v;
    }
}
