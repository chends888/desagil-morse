package sms;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.net.Uri;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class SendContact extends AppCompatActivity,Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{
    ListView ContactsList;
    long ContactId;
    String ContactKey;
    Uri ContactUri;
    private SimpleCursorAdapter CursorAdapter;

    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    public SendContact() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        ContactsList.setOnItemClickListener(this);
    }
}