package sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.renderscript.Byte2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.telephony.SmsManager;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private static final int REQUEST_EXAMPLE = 0;

    private Button morse_pad;
    private EditText message;
    private TextView phone_number;


    // Timer related
    private long timeSpan;

    private String sentence;
    private String currentCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sentence = "";
        currentCharacter = "";

        morse_pad = (Button) findViewById(R.id.morse_pad);
        message = (EditText) findViewById(R.id.message);
        phone_number = (TextView) findViewById(R.id.phone_number);
        morse_pad.setOnTouchListener(this);

        // Se há uma mensagem padrão selecionada
        if (getIntent().getStringExtra("message") != null) {
            addMessageToForm(getIntent().getStringExtra("message"));
        }
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {

        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
            timeSpan = System.nanoTime();
        } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
            // TODO: nao consigo extrair um valor que faça sentido desse timeSpan. O conversor
            // não devolve um valor significativo no simulator
            timeSpan = (System.nanoTime() - timeSpan);
            long secondsElapsed = TimeUnit.SECONDS.convert(timeSpan, TimeUnit.SECONDS);
            //handleTimeSpan(secondsElapsed);
            handleTimeSpan(300);
        }

        return false;
    }

    private void handleTimeSpan(long span) {
        if (span > MorseTimeSpan.WORD.getTime()){
            sentence = sentence.concat(" ");
        } else if (span > MorseTimeSpan.CHARACTER.getTime()) {
            // Chamada da funcao do ruhman
            Log.d("Converter para romano", currentCharacter);
            String character = "a";
            sentence = sentence.concat(character);
        } else if (span > MorseTimeSpan.TRACO.getTime()) {
            currentCharacter = currentCharacter.concat("-");
        } else { // dot
            currentCharacter = currentCharacter.concat(".");
        }

        Log.d("char", currentCharacter);
    }

    // Adiciona a mensagem padrão ao nosso formulário
    private void addMessageToForm (String message) {
        this.message.setText(message);
    }

    public void showMessages() {
        Intent intent = new Intent(this, SendActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSendActivity() {
        Intent intent = new Intent(this, SendActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendSMS() {
        String to = phone_number.getText().toString();
        String message = this.message.getText().toString();

        SmsManager manager = SmsManager.getDefault();
        try {
            manager.sendTextMessage(to, null, message, null, null);

            Toast toast = Toast.makeText(this, "Messagem enviada!", Toast.LENGTH_SHORT);
            toast.show();
        }
        catch(IllegalArgumentException exception) {
            Log.e("SendActivity", "number or message empty");
        }
    }


    public void tryToSendSMS(View view) {

        int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);

        if(permission == PackageManager.PERMISSION_GRANTED) {
            sendSMS();
        }
        else {
            String[] permissions = new String[1];
            permissions[0] = Manifest.permission.SEND_SMS;
            ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_EXAMPLE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_EXAMPLE) {
            if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast toast = Toast.makeText(this, "Cannot use this application without permission to send SMS!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                sendSMS();
            }
        }
    }


}
