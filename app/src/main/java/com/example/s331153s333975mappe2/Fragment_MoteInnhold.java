package com.example.s331153s333975mappe2;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;

public class Fragment_MoteInnhold extends Fragment {
    private String scriptnavn;

    public void init(String navn){
        scriptnavn = navn;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceBundle){
        super.onActivityCreated(saveInstanceBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceBundle){
        View moteInnholdVindu = inflater.inflate(R.layout.innhold_mote_layout, container, false);
        if(moteInnholdVindu != null){
            WebView mote = (WebView) moteInnholdVindu.findViewById(R.id.innholdMote);
            //File imgFile = new File("file:///android_asset/"+scriptNavn);
            mote.loadUrl("file:///android_asset/"+scriptnavn);
        }
        return moteInnholdVindu;
    }

    public void updateUrl(String navn){
        scriptnavn = navn;
        WebView mittMote = (WebView) getView().findViewById(R.id.innholdMote);
        mittMote.loadUrl("file:///android_asset/"+scriptnavn);
    }
}
