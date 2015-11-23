package utilities;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import utilities.HelperFunctions;

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
    public  char actualLetter;
    public int  actualLetterIndex;
    private Context context;
    public boolean hasFlash;
    public HelperFunctions helperFunctions;
    private IMessageActivity sender;
    public boolean canContinue;

    private Map<String, Integer[]> morseLetters = new HashMap<String, Integer[]>();

    final Handler myHandler = new Handler();

    final Runnable myRunnable = new Runnable() {
        public void run() {
            currentLetter.setText(String.valueOf(actualLetter));
        }
    };

    public MorseParser(TextView currentLetter,String message,Context context,IMessageActivity sender)
    {
        this.currentLetter = currentLetter;
        //this.message = message.replace(" ","-");
        this.letters = message.toCharArray();
        this.context = context;
        //filling the morse letters
        this.fillLetters();
        helperFunctions = new HelperFunctions();
        this.hasFlash = helperFunctions.hasLight(context);
        this.sender = sender;
        this.canContinue = true;

        for (int i = 0;i<this.letters.length;i++ )
        {
            Log.d("Letra",String.valueOf(Character.toChars(this.letters[i])));
        }
    }


    public void displayMessage()
    {
        sender.hideResend();
        this.canContinue = true;
        this.actualLetter = this.letters[0];
        this.actualLetterIndex = 0;
        int duration = getLetterDuration(morseLetters.get(String.valueOf(this.actualLetter)));
        Log.d("Letra", actualLetter + " " + String.valueOf(duration));
        UpdateLetterView();

    }

    private void initializeLetterPart(final Integer[] parts, final int index){

        final int duration = parts[index];
        //Log.d("Test",String.valueOf(duration));

        if(hasFlash)
        {
            helperFunctions.startLight();
        }

        new CountDownTimer(duration * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(!canContinue)
                {
                    try {
                        helperFunctions.endLight();
                        this.cancel();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }

            public void onFinish() {

                helperFunctions.endLight();
                Log.d("Letter part","Part number "+index+" of duration "+duration);
                if(index < parts.length -1 )
                {
                    new CountDownTimer((silence * 1000), 1000) {

                        public void onTick(long millisUntilFinished) {
                            if(!canContinue)
                            {
                                try {
                                    helperFunctions.endLight();
                                    this.cancel();
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        }

                        public void onFinish() {

                            Log.d("Letter part","End of the part now a silence");
                            initializeLetterPart(parts,index+1);

                        }
                    }.start();
                }
                else
                {
                    new CountDownTimer((space * 1000), 1000) {

                        public void onTick(long millisUntilFinished) {
                            if(!canContinue)
                            {
                                try {
                                    helperFunctions.endLight();
                                    this.cancel();
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        }

                        public void onFinish() {
                            Log.d("Letter part","End of the letter now a space");

                            if(actualLetterIndex < letters.length -1)
                            {
                                actualLetterIndex += 1;
                                actualLetter  = letters[actualLetterIndex];
                                UpdateLetterView();
                            }
                            else
                            {
                                Log.d("End","End of the message");
                                sender.setResendButton();
                            }
                        }
                    }.start();
                }

            }
        }.start();
    }

    private void UpdateLetterView() {
        myHandler.post(myRunnable);
        Integer[] parts = morseLetters.get(String.valueOf(this.actualLetter));
        int currentPart = 0;
        if(!String.valueOf(this.actualLetter).equals(" "))
        {
            Log.d("Letras",String.valueOf(this.actualLetter));
            initializeLetterPart(parts, currentPart);
        }
        else
        {
            new CountDownTimer((space * 1000), 1000) {

                public void onTick(long millisUntilFinished) {
                    if(!canContinue)
                    {
                        try {
                            helperFunctions.endLight();
                            this.cancel();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }

                public void onFinish() {
                    Log.d("Letter part","space space space");

                    if(actualLetterIndex < letters.length -1)
                    {
                        actualLetterIndex += 1;
                        actualLetter  = letters[actualLetterIndex];
                        UpdateLetterView();
                    }
                    else
                    {
                        Log.d("End","End of the message");
                        sender.setResendButton();
                    }
                }
            }.start();
        }
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
        this.morseLetters.put("Q",Q);

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

        Integer[] a = {this.dot,this.dash};
        this.morseLetters.put("a",a);

        Integer[] b = {this.dash,this.dot,this.dot,this.dot};
        this.morseLetters.put("b",b);

        Integer[] c = {this.dash,this.dot,this.dash,this.dot};
        this.morseLetters.put("c",c);

        Integer[] d = {this.dash,this.dot,this.dot};
        this.morseLetters.put("d",d);

        Integer[] e = {this.dot};
        this.morseLetters.put("e",e);

        Integer[] f = {this.dot,this.dot,this.dash,this.dot};
        this.morseLetters.put("f",f);

        Integer[] g = {this.dash,this.dash,this.dot};
        this.morseLetters.put("g",g);

        Integer[] h = {this.dot,this.dot,this.dot,this.dot};
        this.morseLetters.put("h",h);

        Integer[] i = {this.dot,this.dot};
        this.morseLetters.put("i",i);

        Integer[] j = {this.dot,this.dash,this.dash,this.dash};
        this.morseLetters.put("j",j);

        Integer[] k = {this.dash,this.dot,this.dash};
        this.morseLetters.put("k",k);

        Integer[] l = {this.dot,this.dash,this.dot,this.dot};
        this.morseLetters.put("l",l);

        Integer[] m = {this.dash,this.dash};
        this.morseLetters.put("m",m);

        Integer[] n = {this.dash,this.dot};
        this.morseLetters.put("n",n);

        Integer[] o = {this.dash,this.dash,this.dash};
        this.morseLetters.put("o",o);

        Integer[] p = {this.dot,this.dash,this.dash,this.dot};
        this.morseLetters.put("p",p);

        Integer[] q = {this.dash,this.dash,this.dot,this.dash};
        this.morseLetters.put("q",q);

        Integer[] r = {this.dot,this.dash,this.dot};
        this.morseLetters.put("r",r);

        Integer[] s = {this.dot,this.dot,this.dot};
        this.morseLetters.put("s",s);

        Integer[] t = {this.dash};
        this.morseLetters.put("t",t);

        Integer[] u = {this.dot,this.dot,this.dash};
        this.morseLetters.put("u",u);

        Integer[] v = {this.dot,this.dot,this.dot,this.dash};
        this.morseLetters.put("v",v);

        Integer[] w = {this.dot,this.dash,this.dash};
        this.morseLetters.put("w",w);

        Integer[] x = {this.dash,this.dot,this.dot,this.dash};
        this.morseLetters.put("x",x);

        Integer[] y = {this.dash,this.dot,this.dash,this.dash};
        this.morseLetters.put("y",y);

        Integer[] z = {this.dash,this.dash,this.dot,this.dot};
        this.morseLetters.put("z",z);

    }

    public int getLetterDuration(Integer[] letter)
    {
        int count = 0;
        for (int i = 0; i < letter.length ; i++)
        {
            count += letter[i];

            if(letter.length -1 != i)
            {
                count += 1;
            }
        }
        return  count;
    }


    public void closeParser()
    {
        canContinue = false;
    }

}
