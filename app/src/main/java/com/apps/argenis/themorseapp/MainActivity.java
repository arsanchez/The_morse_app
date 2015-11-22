package com.apps.argenis.themorseapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static Toolbar toolbar;                              // Declaring the Toolbar Object
    Button btnEnviarMsg;
    EditText msgText;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //getting the message textview
        msgText = (EditText)findViewById(R.id.editText);

        //getting the button to trigger the message activity
        btnEnviarMsg = (Button)findViewById(R.id.button_enviar);

        //adding the click listener
        btnEnviarMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendMessage()
    {
        msg = msgText.getText().toString();

        if(msg.length() > 0)
        {
            Intent intent = new Intent(MainActivity.this,MessageActivity.class);
            intent.putExtra("MESSAGE",msg);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getBaseContext(),getString(R.string.empty_message_error),Toast.LENGTH_SHORT).show();
        }
    }
}
