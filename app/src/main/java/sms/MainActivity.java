package sms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.renderscript.Byte2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.telephony.SmsManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private static final int REQUEST_EXAMPLE = 0;

    private EditText message;
    private TextView phone_number;

    private boolean can_vibrate = false;

    // Morse related
    private long timeSpan;
    private MorseCoder morseCoder;

    private String sentence = "";
    private String currentCharacter = "";
    private boolean isEditingPhoneNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa o conversor
        String encoding = LoadData("encodings.txt");
        morseCoder = new MorseCoder(encoding);
        morseCoder.inOrderPrint();

        Button morse_pad = (Button) findViewById(R.id.morse_pad);
        morse_pad.setOnTouchListener(this);

        message = (EditText) findViewById(R.id.message);
        phone_number = (TextView) findViewById(R.id.phone_number);

        // Se há uma mensagem padrão selecionada
        if (getIntent().getStringExtra("message") != null) {
            addMessageToForm(getIntent().getStringExtra("message"));
        }

        // Veja se pode vibrar
        checkVibratePermission();
    }

    /**
     * Carrega um arquivo de texto da pasta de assets
     * @param path - localização do arquvio
     * @return string do arquivo
     */
    public String LoadData(String path) {
        String tContents = "";

        try {
            InputStream stream = getResources().getAssets().open((path));

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
            Log.e("Error loading file", e.getLocalizedMessage());
        }

        return tContents;

    }

    private void checkVibratePermission() {
        int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.VIBRATE);

        if(permission == PackageManager.PERMISSION_GRANTED) {
            can_vibrate = true;
        }
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        double duration;

        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
            timeSpan = System.currentTimeMillis();
        } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
           duration = (System.currentTimeMillis() - timeSpan);
            handleTimeSpan(duration);
        }

        return false;
    }

    /**
     * Decide se usuário digitou um ponto, um traço ou um caracter
     * @param span - duração em milisegundos do clique
     */
    private void handleTimeSpan(double span) {
        if (span > MorseTimeSpan.WORD.getTime()){
            // Caso do usuario dar espaço sem ter traduzido o caracter
            // Queremos fazer isso para ele
            if (currentCharacter != "") {
                String character = morseCoder.decode(currentCharacter);
                sentence = sentence.concat(character);
                currentCharacter = "";
            }
            sentence = sentence.concat(" ");
        } else if (span > MorseTimeSpan.CHARACTER.getTime()) {
            String character = morseCoder.decode(currentCharacter);
            sentence = sentence.concat(character);
            currentCharacter = "";
        } else if (span > MorseTimeSpan.TRACO.getTime()) {
            currentCharacter = currentCharacter.concat("-");
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(300);
        } else { // dot
            currentCharacter = currentCharacter.concat(".");
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(100);

        }

        if (isEditingPhoneNumber){
            phone_number.setText(sentence, TextView.BufferType.NORMAL);
        } else {
            message.setText(sentence, TextView.BufferType.NORMAL);
        }

        // TODO: set current character to special view
        Log.d("char", currentCharacter);
    }

    public void deleteChar(View v) {
        TextView desiredText;
        if (isEditingPhoneNumber){
            desiredText = phone_number;
        } else {
            desiredText = message;
        }

        String text = desiredText.getText().toString();
        currentCharacter = "";

        if (text.length() > 0) {
            desiredText.setText(text.substring(0, text.length() - 1));
            sentence = sentence.substring(0, text.length() - 1);
        }
    }

    /**
     * Adiciona a mensagem padrão no Text View
     * @param message - mensagem a ser adicionada
     */
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

        if (to.length() == 0 || message.length() == 0){
            return;
        }

        SmsManager manager = SmsManager.getDefault();

        try {
            manager.sendTextMessage(to, null, message, null, null);
            Toast toast = Toast.makeText(this, "Messagem enviada!", Toast.LENGTH_SHORT);
            toast.show();
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(500);
        }
        catch(IllegalArgumentException exception) {
            Log.e("SendActivity", "number or message empty");
        }
    }


    public void tryToSendSMS(View view) {
        // Check if no view has focus:
        View currentFocus = this.getCurrentFocus();
        if (currentFocus!= null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

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
