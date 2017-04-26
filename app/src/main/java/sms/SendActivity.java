package sms;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hugo on 17/04/2017.
 */

public class SendActivity extends AppCompatActivity {
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

        adaptador = new ArrayAdapter<String>(SendActivity.this, android.R.layout.simple_list_item_1, opcoes);
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

    private void exibirSobre() {

    }

    private void fazerLigacao() {
        Uri uri = Uri.parse("tel:0123456789012");
        Intent itNavegar = new Intent(Intent.ACTION_DIAL,uri);
        startActivity(itNavegar);
    }

    private void navegarInternet() {
        Uri uri = Uri.parse("http://www.devmedia.com.br");
        Intent itNavegar = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(itNavegar);
    }
}