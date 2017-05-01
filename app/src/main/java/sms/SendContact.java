package sms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class SendContact extends AppCompatActivity {
    List<String> contacts;
    ArrayAdapter<String> adaptador;
    ListView lvContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        lvContacts = (ListView) findViewById(R.id.default_messages);

        contacts = new ArrayList<String>();
        for(int number=0,number < READ_CONTACT, number++){
            contacts.add(number);
        }
        adaptador = new ArrayAdapter<String>(SendContact.this, android.R.layout.simple_list_item_1, contacts);
        lvContacts.setAdapter(adaptador);
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleListItemClick((String) lvContacts.getItemAtPosition(position));
            };
        });
    }

    private void handleListItemClick(String messageParam) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("message",messageParam);
        startActivity(intent);
        finish();
    }

}