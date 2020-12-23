package com.capitipalismadmin.ui.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Validator {
    public static boolean isValidURL(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.WEB_URL.matcher(target).matches());
    }
}
