package com.capitipalismadmin.ui.helpers;

import android.app.Activity;

import com.capitipalismadmin.R;
import com.tapadoo.alerter.Alerter;

public class AlertCreator {
    public static void showPromptAlert(Activity activity, String title, String message) {
        Alerter.create(activity)
                .setTitle(title)

                .setIcon(R.drawable.hyphen_success)
                .setText(message)
                .enableProgress(true)
                .setDuration(3000)
                .setProgressColorRes(R.color.capitipalism_success)
                .setBackgroundColorRes(R.color.capitipalism_error)
                .enableSwipeToDismiss()
                .enableIconPulse(false)
                .setOnShowListener(() -> {

                })
                .setOnHideListener(() -> {

                })
                .show();
    }

    public static void showSuccessAlert(Activity activity, String title, String message) {
        Alerter.create(activity)
                .setTitle(title)
                .setIcon(R.drawable.hyphen_success)
                .setText(message)
                .enableProgress(true)
                .setDuration(3000)
                .setProgressColorRes(R.color.capitipalism_success)
                .setBackgroundColorRes(R.color.capitipalism_success) // or setBackgroundColorInt(Color.CYAN)
                .enableSwipeToDismiss()
                .enableIconPulse(false)
                .setOnShowListener(() -> {

                })
                .setOnHideListener(() -> {

                })
                .show();
    }

    public static void showErrorAlert(Activity activity, String title, String message) {
        Alerter.create(activity)
                .setTitle(title)
                .setIcon(R.drawable.hyphen_error)
                .setText(message)
                .enableProgress(true)
                .setDuration(3000)
                .setProgressColorRes(R.color.capitipalism_error)
                .setBackgroundColorRes(R.color.capitipalism_error) // or setBackgroundColorInt(Color.CYAN)
                .enableSwipeToDismiss()
                .enableIconPulse(false)
                .setOnShowListener(() -> {

                })
                .setOnHideListener(() -> {

                })
                .show();
    }
}
