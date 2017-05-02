package sms;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.provider.ContactsContract;
import android.net.Uri;


public abstract class SendContact extends AppCompatActivity{
    String numero;
    final int PICK_CONTACT = 2015;
    private EditText numberEdit;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            numero = cursor.getString(column);
            numberEdit.setText(numero);


        }
    }

}