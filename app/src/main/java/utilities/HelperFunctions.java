package utilities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;

import java.io.IOException;

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

    public void playSound(Context context) throws IllegalArgumentException,
            SecurityException,
            IllegalStateException,
            IOException
    {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(context, soundUri);
        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }
    }

    public void stopSound()
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
