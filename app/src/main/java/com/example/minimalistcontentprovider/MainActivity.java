package com.example.minimalistcontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
    }

    public void onClickDisplayEntries(View view) {
        String queryUri = Contract.CONTENT_URI.toString();
        //Only get words
        String[] projection = new String[]{Contract.CONTENT_PATH};
        String selectionClause;
        String selectionArgs[];
        String sortOrder = null;

        switch (view.getId()) {
            case R.id.button_display_all:
                selectionClause = null;
                selectionArgs = null;
                break;
            case R.id.button_display_first:
                selectionClause = Contract.WORD_ID + " = ?";
                selectionArgs = new String[]{"0"};
                break;
            default:
                selectionClause = null;
                selectionArgs = null;
        }

        Cursor cursor = getContentResolver()
                .query(Uri.parse(queryUri),
                        projection,
                        selectionClause,
                        selectionArgs,
                        sortOrder);

        if (cursor!= null){
            if(cursor.getCount()> 0 ){
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(projection[0]);
                do{
                    String word = cursor.getString(columnIndex);
                    textView.append(word+"\n");
                }while (cursor.moveToNext());

            }else {
                Log.d(TAG, "onClickDisplayEntries: No data returned.");
                textView.append("No data returned."+"\n");
            }
            cursor.close();
        }else{
            Log.d(TAG, "onClickDisplayEntries: Cursor is null.");
            textView.append("Cursor is null."+"\n");
        }
    }
}
