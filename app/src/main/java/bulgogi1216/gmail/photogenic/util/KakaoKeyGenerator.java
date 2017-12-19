package bulgogi1216.gmail.photogenic.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bulgo on 2017-10-30.
 */

public class KakaoKeyGenerator {
    public final static String TAG = "KakaoKeyGenerator";

    public static String getKeyHash(Context _context) {
        PackageInfo packageInfo = null;
        String hashKey = null;

        try {
            packageInfo = _context.getPackageManager().getPackageInfo("bulgogi1216.gmail.photogenic", PackageManager.GET_SIGNATURES);

            for(Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.i(TAG, "HASH = " + hashKey);
            }
        } catch (PackageManager.NameNotFoundException _e) {
            _e.printStackTrace();
        } catch (NoSuchAlgorithmException _e) {
            _e.printStackTrace();
        }

        if (packageInfo == null)
            return null;

        return hashKey;
    }


}
