package sms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class SendContact extends AppCompatActivity {
    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    ListView lvOpcoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        lvOpcoes = (ListView) findViewById(R.id.default_messages);

        opcoes = new ArrayList<String>();

        opcoes.add("Banheiro por favor");
        opcoes.add("Comida por favor");
        opcoes.add("Agua por favor");
        opcoes.add("Estou com dor");
        opcoes.add("Emergencia");
        opcoes.add("Preciso de ajuda");

        adaptador = new ArrayAdapter<String>(SendContact.this, android.R.layout.simple_list_item_1, opcoes);
        lvOpcoes.setAdapter(adaptador);
        lvOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleListItemClick((String) lvOpcoes.getItemAtPosition(position));
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