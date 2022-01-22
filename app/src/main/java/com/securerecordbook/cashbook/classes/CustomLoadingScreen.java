package com.securerecordbook.cashbook.classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.securerecordbook.cashbook.R;

public class CustomLoadingScreen {
    public static CustomLoadingScreen customLoadingScreen = null;
    AlertDialog mAlertDialog;

    public static CustomLoadingScreen getInstance() {
        if (customLoadingScreen == null) {
            customLoadingScreen = new CustomLoadingScreen();
        }
        return customLoadingScreen;
    }

    public void showCustomLoadingScreen(Activity mActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.DialogTheme);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_loading_screen, null));
        builder.setCancelable(false);

        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    public void hideCustomLoadingScreen() {
        mAlertDialog.dismiss();
    }
}
