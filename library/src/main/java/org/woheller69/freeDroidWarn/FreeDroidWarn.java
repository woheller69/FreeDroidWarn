package org.woheller69.freeDroidWarn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FreeDroidWarn {

    private static final String VERSION_CODE_PREF = "versionCodeWarn";
    private static final List<String> FREE_OS_INDICATORS = Arrays.asList(
            "graphene",
            "lineage",
            "/e/",
            "calyx",
            "iodé",
            "iode",
            "divestos",
            "replicant"
    );

    public static void showWarningOnUpgrade(Context context, int buildVersion) {
        if (!shouldShowWarning(context, buildVersion)) {
            return;
        }

        SharedPreferences prefManager = PreferenceManager.getDefaultSharedPreferences(context);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(R.string.dialog_Warning);
        alertDialogBuilder.setNegativeButton(context.getString(R.string.dialog_more_info), (dialog, which) ->
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://keepandroidopen.org"))));
        alertDialogBuilder.setPositiveButton(context.getString(android.R.string.ok), (dialog, which) -> {
            SharedPreferences.Editor editor = prefManager.edit();
            editor.putInt(VERSION_CODE_PREF, buildVersion);
            editor.apply();
        });
        alertDialogBuilder.setNeutralButton(context.getString(R.string.solution), (dialog, which) ->
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/woheller69/FreeDroidWarn?tab=readme-ov-file#solutions"))));

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Button neutralButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        if (neutralButton != null) {
            neutralButton.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        }
    }

    static boolean shouldShowWarning(Context context, int buildVersion) {
        SharedPreferences prefManager = PreferenceManager.getDefaultSharedPreferences(context);
        int shownVersionCode = prefManager.getInt(VERSION_CODE_PREF, 0);
        return shouldShowWarning(buildVersion, shownVersionCode, isLikelyFreeOs());
    }

    static boolean shouldShowWarning(int buildVersion, int shownVersionCode, boolean likelyFreeOs) {
        return buildVersion > shownVersionCode && !likelyFreeOs;
    }

    static boolean isLikelyFreeOs() {
        return isLikelyFreeOs(Build.FINGERPRINT, Build.DISPLAY, Build.PRODUCT, Build.BRAND, Build.MANUFACTURER);
    }

    static boolean isLikelyFreeOs(String fingerprint, String display, String product, String brand, String manufacturer) {
        return containsFreeOsIndicator(fingerprint)
                || containsFreeOsIndicator(display)
                || containsFreeOsIndicator(product)
                || containsFreeOsIndicator(brand)
                || containsFreeOsIndicator(manufacturer);
    }

    static boolean containsFreeOsIndicator(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        String normalizedValue = value.toLowerCase(Locale.ROOT);
        for (String indicator : FREE_OS_INDICATORS) {
            if (normalizedValue.contains(indicator)) {
                return true;
            }
        }
        return false;
    }
}
