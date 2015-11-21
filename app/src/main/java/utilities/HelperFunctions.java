package utilities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by Argenis on 11/17/15.
 */
public class HelperFunctions {

    public static boolean hasLight(Context context)
    {
        PackageManager packageManager = context.getPackageManager();

        boolean flash = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        return  flash;
    }

    public void flashSignal(int duration)
    {
        new CountDownTimer(duration * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

            }
        }.start();
    }

    public void soundSignal()
    {

    }

}
