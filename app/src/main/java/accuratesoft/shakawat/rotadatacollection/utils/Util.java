package accuratesoft.shakawat.rotadatacollection.utils;

import android.content.Context;
import android.widget.Toast;

public class Util {
    public static final String secret_key = "rotasecret";
    public static final String url = "https://surveillance.iedcr.gov.bd/rota/";
    public static void makeToast(Context context, String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
