package br.pro.hashi.ensino.desagil.morse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXAMPLE = 0;


    private void goToSendActivity() {
        //Integrar para enviar sms (classe SendActivity ver repo Hash)
        Intent intent = new Intent(this, SendActivity.class);
        startActivity(intent);
        finish();
    }

    //Ao tentar enviar msg, checar se existe permissao e agir de acordo
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

    //Pede permissao de SMS
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_EXAMPLE) {
            if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast toast = Toast.makeText(this, "É necessário permissão de SMS para utilizar esse aplicativo!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                goToSendActivity();
            }
        }
    }
}
