package com.mindnotix.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mindnotix.youthhub.FullScreenViewActivity;
import com.mindnotix.youthhub.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    public AVLoadingIndicatorView indicatorView;
    ProgressDialog context;


    public Utils(FullScreenViewActivity fullScreenViewActivity) {
    }

    public static String getOnlyDate(String dates) {

        String start_dt = dates;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
        Date date = null;
        try {
            date = (Date) formatter.parse(start_dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        String finalString = newFormat.format(date);
        return finalString;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ProgressDialog createProgressDialog1(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        AVLoadingIndicatorView avi;
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Utils.debug(e.getMessage());
        }
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.setContentView(R.layout.custom_loader);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        avi = (AVLoadingIndicatorView) dialog.findViewById(R.id.avi);
        avi.setIndicator("BallClipRotatePulseIndicator");
        // dialog.setMessage(Message);
        return dialog;
    }


    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Utils.debug(e.getMessage());
        }
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // dialog.setContentView(R.layout.custom_dialog);

        // dialog.setMessage(Message);
        return dialog;
    }


    public static final Log Debug(String s) {
        Log.e("YouthHUB", s);
        return null;
    }


    public static boolean checkNullValues(String s) {
        label0:
        {
            if (s != null) {
                s = s.trim();
                if (!s.equals("") && !s.equals("0")) {
                    break label0;
                }
            }
            return false;
        }
        return true;
    }


    public static final void debug(String s) {
        Log.e("HomeStar", (new StringBuilder()).append("Message : ").append(s).toString());
    }


    public static final int getHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static final int getWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static final boolean hasText(EditText edittext) {
        return edittext.getText().toString().trim().length() > 0;
    }

    public static final boolean hasText(TextView textview) {
        return textview.getText().toString().length() > 0;
    }


    public static final boolean isEmailValid(String edittext) {

        if (edittext == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(edittext).matches();
        }
    }


    public static final boolean isPhoneNumberValid(EditText edittext) {
        return edittext.getText().toString().length() >= 10;
    }

    public static final boolean isValidPassword(EditText edittext) {
        int i = edittext.getText().toString().length();
        return i > 4 && i < 20;
    }

    public static final boolean isValidZipCode(EditText edittext) {
        return edittext.getText().toString().length() == 5;
    }

    public static final String toUpperCaseFirstChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        StringBuilder stringbuilder = (new StringBuilder()).append(Character.toUpperCase(s.charAt(0)));
        if (s.length() > 1) {
            s = s.substring(1);
        } else {
            s = "";
        }
        return stringbuilder.append(s).toString();
    }

    public static boolean validateFirstName(String s) {
        return s.matches("[A-Z][a-zA-Z]*");
    }

    public static boolean validateLastName(String s) {
        return s.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }

    public static boolean validatePhoneNumber(String s) {
        while (s.matches("\\d{10}") || s.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}") || s.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}") || s.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }
        return false;
    }

    public static boolean validateMobileNZ(String s) {


        String r = "^(0){1}(20|21|22|23|24|25|26|27|28|29){1}\\d{6,8}[\\s]*$";
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        boolean b = m.matches();

        Log.d("aaaa", "validateMobileNZ:s  " + s);
        if (b) {
            Log.d("aaaa", "validateMobileNZ: true " + true);
            return true;
        } else {
            Log.d("aaaa", "validateMobileNZ:false " + false);
            return false;
        }

    }

    public static boolean validText(String s) {

        String r = " [a-zA-Z]";
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        boolean b = m.matches();

        Log.d("aaaa", "validateMobileNZ:s  " + s);
        if (b) {
            Log.d("aaaa", "validateMobileNZ: true " + true);
            return true;
        } else {
            Log.d("aaaa", "validateMobileNZ:false " + false);
            return false;
        }


    }


    public static boolean validPassword(String password) {
       /* Pattern pattern;
        Matcher matcher;

        String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();*/


        Pattern pattern;
        Matcher matcher;

/*        (/^(?=.*\d)(?=.*[A-Z])([@$%&#])[0-9a-zA-Z]{4,}$/)*/
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        //  final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[A-Z])([@$%&#])[0-9a-zA-Z]{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}
