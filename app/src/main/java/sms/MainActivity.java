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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.telephony.SmsManager;
import android.view.View.OnLongClickListener;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private static final int REQUEST_EXAMPLE = 0;

    private Button morse_pad;
    private Button send_button;
    private Button space_button;
    private EditText message;
    private TextView phone_number;
    private TextView morse_hint;

    private boolean can_vibrate = false;
    private boolean isEditingPhoneNumber = false;


    // Timer related
    private long timeSpan;
    private MorseCoder morseCoder;
    private String sentence;
    private String currentCharacter;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        queue = new ArrayList<MorseNode>();
//        dictionary = new ArrayList<MorseNode>();

        // Inicializa o conversor
        String encoding = LoadData("encodings.txt");
        morseCoder = new MorseCoder(encoding);
        morseCoder.inOrderPrint();
        sentence = "";
        currentCharacter = "";

        morse_pad = (Button) findViewById(R.id.morse_pad);
        message = (EditText) findViewById(R.id.message);

        phone_number = (TextView) findViewById(R.id.phone_number);
        morse_hint = (TextView) findViewById(R.id.morsehint);
        morse_pad.setOnTouchListener(this);

        space_button = (Button) findViewById(R.id.space);
        space_button.setOnClickListener(handleSpace);

        send_button = (Button) findViewById(R.id.button);
        send_button.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                goToSendActivity();
                return true;
            }
        });


        // Se há uma mensagem padrão selecionada
        if (getIntent().getStringExtra("message") != null) {
            addMessageToForm(getIntent().getStringExtra("message"));
            isEditingPhoneNumber = true;
        }
        mDrawerList = (ListView)findViewById(R.id.navList);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Veja se pode vibrar
        checkVibratePermission();
    }

    public String LoadData(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getResources().getAssets().open((inFile));

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

    // Lida com o botão de espaço
    View.OnClickListener handleSpace = new View.OnClickListener() {
        public void onClick(View v) {
            String character = morseCoder.decode(currentCharacter);
            sentence = sentence.concat(character);
            currentCharacter = "";

            sentence = sentence.concat(" ");

            message.setText(sentence, TextView.BufferType.NORMAL);
            morse_hint.setText(currentCharacter);
        }
    };

    private void handleTimeSpan(double span) {
        if (span > MorseTimeSpan.CHARACTER.getTime()) {
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

        TextView desiredText;
        if (isEditingPhoneNumber){
            desiredText = phone_number;
        } else {
            desiredText = message;
        }
        desiredText.setText(sentence, TextView.BufferType.NORMAL);
        morse_hint.setText(currentCharacter);
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
        morse_hint.setText("");

        if (text.length() > 0) {
            desiredText.setText(text.substring(0, text.length() - 1));
            sentence = sentence.substring(0, text.length() - 1);
        }
    }

    // Adiciona a mensagem padrão ao nosso formulário
    private void addMessageToForm (String message) {
        this.message.setText(message);
    }



    public void goToSendActivity() {
        Intent intent = new Intent(this, SendActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendSMS() {
        String to = phone_number.getText().toString();
        String message = this.message.getText().toString();

        // Focus on the appropriate field
        if (message.length() == 0){
            isEditingPhoneNumber = false;
            sentence = "";
            return;
        } else if (to.length() == 0) {
            isEditingPhoneNumber = true;
            sentence = "";
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
    private void addDrawerItems() {
        List<String> morselist = new ArrayList<String>();
        String words = new String("abcdefghijklmnopqrstuvwxyz0123456789");

        ArrayList<Character> alphabetlist = new ArrayList<Character>();

        for(int j = 0; j<words.length(); j++) {
            alphabetlist.add(words.charAt(j));
        }
        for (char i : alphabetlist) {
            morselist.add(morseCoder.encode(i));
        }
        List<String> romanomorsedic = new ArrayList<String>();
        List<String> dictionary = new ArrayList<String>();
        //dictionary = morseCoder.create_dictionary();
        for(int i =0;i<alphabetlist.size();i++){
            romanomorsedic.add(alphabetlist.get(i).toString() + " -> " + morselist.get(i) + "          || " /*+ dictionary.get(i)*/);
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, romanomorsedic);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
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
