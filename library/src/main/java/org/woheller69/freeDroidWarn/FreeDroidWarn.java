package org.woheller69.freeDroidWarn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class FreeDroidWarn {

    public static void showWarningOnUpgrade(Context context, int buildVersion){
        SharedPreferences prefManager = PreferenceManager.getDefaultSharedPreferences(context);
        int versionCode = prefManager.getInt("versionCodeWarn",0);
        if (buildVersion > versionCode){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(R.string.dialog_Warning);
            alertDialogBuilder.setPositiveButton(context.getString(R.string.dialog_more_info), (dialog, which) -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/developer-verification"))));
            alertDialogBuilder.setNegativeButton(context.getString(android.R.string.ok), (dialog, which) -> {
                SharedPreferences.Editor editor = prefManager.edit();
                editor.putInt("versionCodeWarn", buildVersion);
                editor.apply();
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

}
