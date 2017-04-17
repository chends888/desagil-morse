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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final int REQUEST_EXAMPLE = 0;

    private Button morse_pad;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        morse_pad = (Button) findViewById(R.id.morse_pad);
        message = (EditText) findViewById(R.id.message);
        morse_pad.setOnTouchListener(this);

        // Se há uma mensagem padrão selecionada
        if (getIntent().getStringExtra("message") != null) {
            addMessageToForm(getIntent().getStringExtra("message"));
        }
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        final Timer timer = new Timer();
        switch ( arg1.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                //start timer
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showMessages();
                    }
                }, 2000); //time out 5s
                return true;
            case MotionEvent.ACTION_UP:
                //stop timer
                timer.cancel();
                return true;
        }
        return false;

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


    public void tryToGoToSendActivity(View view) {
        int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);

        if(permission == PackageManager.PERMISSION_GRANTED) {
            goToSendActivity();
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
                goToSendActivity();
            }
        }
    }


}
