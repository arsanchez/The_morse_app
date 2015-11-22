package utilities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by Argenis on 11/17/15.
 */
public class HelperFunctions {

    Camera cam;
    private boolean isFlashUsed = false;

    public HelperFunctions()
    {

    }

    public static boolean hasLight(Context context)
    {
        PackageManager packageManager = context.getPackageManager();

        boolean flash = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        return  flash;
    }

    public void soundSignal()
    {

    }

    public  void startLight()
    {
        if(!this.isFlashUsed)
        {
            cam = Camera.open();
            Camera.Parameters p = cam.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            cam.startPreview();
            this.isFlashUsed = true;
        }

    }

    public  void endLight()
    {
        if(this.isFlashUsed)
        {
            this.isFlashUsed = false;
            cam.stopPreview();
            cam.release();
        }

    }



}
