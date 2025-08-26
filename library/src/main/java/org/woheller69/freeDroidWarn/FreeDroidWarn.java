package org.woheller69.freeDroidWarn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class FreeDroidWarn {

    static boolean shouldShowWarningDialog(Context context, int buildVersion) {
        SharedPreferences prefManager = PreferenceManager.getDefaultSharedPreferences(context);
        int versionCode = prefManager.getInt("versionCode",0);

        if (prefManager.contains("versionCode") && buildVersion>versionCode){
            SharedPreferences.Editor editor = prefManager.edit();
            editor.putInt("versionCode", buildVersion);
            editor.apply();
            return true;
        } else {
            SharedPreferences.Editor editor = prefManager.edit();
            editor.putInt("versionCode", buildVersion);
            editor.apply();
            return false;
        }
    }

    public static void showWarningOnUpgrade(Context context, int buildVersion){
        if (shouldShowWarningDialog(context,buildVersion)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(R.string.dialog_Warning).setIcon(R.drawable.ic_warning);
            alertDialogBuilder.setPositiveButton(context.getString(R.string.dialog_more_info), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/developer-verification")));
                }
            });
            alertDialogBuilder.setNegativeButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

}
