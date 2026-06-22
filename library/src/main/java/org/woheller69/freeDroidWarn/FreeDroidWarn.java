package org.woheller69.freeDroidWarn;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class FreeDroidWarn {

    // Starts an Activity defensively: no browser app (ActivityNotFoundException)
    // or an MDM/work-profile restriction (SecurityException) must not crash the host app.
    private static void safeStartActivity(Context context, Intent intent) {
        // A non-Activity context (Application/Service) needs FLAG_ACTIVITY_NEW_TASK,
        // otherwise startActivity() throws AndroidRuntimeException before try/catch helps.
        if (!(context instanceof android.app.Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException | SecurityException ignored) {
        }
    }

    public static void showWarningDialogOnUpgrade(Context context, int buildVersion){
        // Load the preferences
        SharedPreferences prefManager = PreferenceManager.getDefaultSharedPreferences(context);
        int versionCode = prefManager.getInt("versionCodeWarn",0);

        // If the current version of the app is newer then the stored value show the dialog
        if (buildVersion > versionCode){
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
            materialAlertDialogBuilder.setMessage(R.string.dialog_Warning);
            // Show a button for more details => If pressed update the stored version
            materialAlertDialogBuilder.setNegativeButton(context.getString(R.string.dialog_more_info), (dialog, which) -> {
                safeStartActivity(context, new Intent(Intent.ACTION_VIEW, Uri.parse("https://keepandroidopen.org")));
                SharedPreferences.Editor editor = prefManager.edit();
                editor.putInt("versionCodeWarn", buildVersion);
                editor.apply();
            });
            // Show a button closing the dialog => If pressed update the stored version
            materialAlertDialogBuilder.setPositiveButton(context.getString(android.R.string.ok), (dialog, which) -> {
                SharedPreferences.Editor editor = prefManager.edit();
                editor.putInt("versionCodeWarn", buildVersion);
                editor.apply();
            });

            // Show a button for possible solutions => If pressed update the stored version
            materialAlertDialogBuilder.setNeutralButton(context.getString(R.string.solution), (dialog, which) -> {
                safeStartActivity(context, new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/woheller69/FreeDroidWarn?tab=readme-ov-file#solutions")));
                SharedPreferences.Editor editor = prefManager.edit();
                editor.putInt("versionCodeWarn", buildVersion);
                editor.apply();
            });

            // Display the dialog
            AlertDialog alertDialog = materialAlertDialogBuilder.create();
            try {
                alertDialog.show();
            } catch (android.view.WindowManager.BadTokenException ignored) {
                // No valid window to attach to - e.g. a non-Activity context, or the
                // Activity already finished/was destroyed before the dialog could show.
                return;
            }

            // Highlight the solution button
            Button neutralButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
            if (neutralButton != null) {
                TypedValue tv = new TypedValue();
                context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorErrorContainer, tv, true);
                int color = tv.resourceId != 0
                        ? ContextCompat.getColor(context, tv.resourceId)
                        : tv.data;

                neutralButton.setTextColor(color);
            }
        }

    }

    public static void showWarningSnackBarOnUpgrade(Context context, View view, int buildVersion){
        // Load the preferences
        SharedPreferences prefManager = PreferenceManager.getDefaultSharedPreferences(context);
        int versionCode = prefManager.getInt("versionCodeWarn",0);

        // If the current version of the app is newer then the stored value show the SnackBar
        if (buildVersion > versionCode && view.isAttachedToWindow()){
            // Create the SnackBar
            Snackbar snackbar = Snackbar.make(view, R.string.dialog_Warning, Snackbar.LENGTH_INDEFINITE);
            View snackbarView = snackbar.getView();

            // Configure the TextView
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(Integer.MAX_VALUE);
            textView.setSingleLine(false);

            // Show a button for more details => If pressed update the stored version
            snackbar.setAction(R.string.dialog_more_info, v -> {
                safeStartActivity(context, new Intent(Intent.ACTION_VIEW, Uri.parse("https://keepandroidopen.org")));
                SharedPreferences.Editor editor = prefManager.edit();
                editor.putInt("versionCodeWarn", buildVersion);
                editor.apply();
            });

            // Display the SnackBar for 5 seconds
            snackbar.setDuration(5000);
            snackbar.show();
        }

    }

}
