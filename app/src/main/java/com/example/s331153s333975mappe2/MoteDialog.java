package com.example.s331153s333975mappe2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

/** DETTE ER BARE NOE JEG VURDERTE Å BRUKE FOR Å FÅ UT INFO OM MØTE INNE PÅ HOVED, MEN DEN ER IKKE I BRUK NOE STED NÅ**/
public class MoteDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Møteinformasjon")
                .setMessage("Informasjon om møtet")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
