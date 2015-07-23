package utilities;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Argenis on 7/10/15.
 */
public class MorseParser {

    //morse duration vars
    private int dot     = 1;
    private int dash    = 3;
    private int space   = 3;
    private int silence = 1;

    TextView currentLetter;
    private String message;
    public char[] letters;
    public char actualLetter;

    private Map<String, Integer[]> morseLetters = new HashMap<String, Integer[]>();

    final Handler myHandler = new Handler();

    final Runnable myRunnable = new Runnable() {
        public void run() {
            currentLetter.setText(String.valueOf(actualLetter));
        }
    };

    public MorseParser(TextView currentLetter,String message)
    {
        this.currentLetter = currentLetter;
        this.message = message;
        this.letters = message.toCharArray();

        //filling the morse letters
        this.fillLetters();
    }


    public void displayMesage()
    {
        currentLetter.setText("Z");
        final char[] l = this.letters;
        final Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
        {
            int i = 0;
            public void run()
            {
                UpdateLetterView(l[i]);
                Log.d("Parser debug",String.valueOf(l[i]));
                i++;
                if (i > l.length-1)
                {
                    timer.cancel();
                }
            }
        }, 0, 1000);

    }

    private void UpdateLetterView(char letter) {
        actualLetter = letter;
        myHandler.post(myRunnable);
    }

    private void fillLetters()
    {
        Integer[] A = {this.dot,this.dash};
        this.morseLetters.put("A",A);

        Integer[] B = {this.dash,this.dot,this.dot,this.dot};
        this.morseLetters.put("B",B);

        Integer[] C = {this.dash,this.dot,this.dash,this.dot};
        this.morseLetters.put("C",C);

        Integer[] D = {this.dash,this.dot,this.dot};
        this.morseLetters.put("D",D);

        Integer[] E = {this.dot};
        this.morseLetters.put("E",E);

        Integer[] F = {this.dot,this.dot,this.dash,this.dot};
        this.morseLetters.put("F",F);

        Integer[] G = {this.dash,this.dash,this.dot};
        this.morseLetters.put("G",G);

        Integer[] H = {this.dot,this.dot,this.dot,this.dot};
        this.morseLetters.put("H",H);

        Integer[] I = {this.dot,this.dot};
        this.morseLetters.put("I",I);

        Integer[] J = {this.dot,this.dash,this.dash,this.dash};
        this.morseLetters.put("J",J);

        Integer[] K = {this.dash,this.dot,this.dash};
        this.morseLetters.put("K",K);

        Integer[] L = {this.dot,this.dash,this.dot,this.dot};
        this.morseLetters.put("L",L);

        Integer[] M = {this.dash,this.dash};
        this.morseLetters.put("M",M);

        Integer[] N = {this.dash,this.dot};
        this.morseLetters.put("N",N);

        Integer[] O = {this.dash,this.dash,this.dash};
        this.morseLetters.put("O",O);

        Integer[] P = {this.dot,this.dash,this.dash,this.dot};
        this.morseLetters.put("P",P);

        Integer[] Q = {this.dash,this.dash,this.dot,this.dash};
        this.morseLetters.put("O",O);

        Integer[] R = {this.dot,this.dash,this.dot};
        this.morseLetters.put("R",R);

        Integer[] S = {this.dot,this.dot,this.dot};
        this.morseLetters.put("S",S);

        Integer[] T = {this.dash};
        this.morseLetters.put("T",T);

        Integer[] U = {this.dot,this.dot,this.dash};
        this.morseLetters.put("U",U);

        Integer[] V = {this.dot,this.dot,this.dot,this.dash};
        this.morseLetters.put("V",V);

        Integer[] W = {this.dot,this.dash,this.dash};
        this.morseLetters.put("W",W);

        Integer[] X = {this.dash,this.dot,this.dot,this.dash};
        this.morseLetters.put("X",X);

        Integer[] Y = {this.dash,this.dot,this.dash,this.dash};
        this.morseLetters.put("Y",Y);

        Integer[] Z = {this.dash,this.dash,this.dot,this.dot};
        this.morseLetters.put("Z",Z);

    }
}
