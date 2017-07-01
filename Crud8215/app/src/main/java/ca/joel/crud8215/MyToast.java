package ca.joel.crud8215;

import android.content.Context;
import android.widget.Toast;

//Java class to simplify calling a toast message
public class MyToast {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
