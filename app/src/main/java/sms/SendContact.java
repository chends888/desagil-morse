package sms;

import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.View;
import static sms.R.id.phone_number;


public class SendContact extends AppCompatActivity {
    final int PICK_CONTACT = 2015;
    TextView contact_list;
    private static String numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);



        contact_list = (TextView) findViewById(R.id.contact_list);
        (findViewById(R.id.contact_list)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            numero = cursor.getString(column);
            contact_list.setText(numero);
        }
    }
}