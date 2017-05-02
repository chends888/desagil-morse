package sms;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.provider.ContactsContract;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


public class SendContact extends AppCompatActivity {
    String numero;
    ArrayAdapter<String> adaptador;
    final int PICK_CONTACT = 2015;
    private TextView contact_list;
    String caregiver_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        caregiver_number = "11940388041";

        contact_list = (TextView) findViewById(R.id.contact_list);
        (findViewById(R.id.contact_list)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
            }
        });
    }
}
