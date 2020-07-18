package com.example.notez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashSet;
import java.util.Objects;

public class NotezEditor extends AppCompatActivity
{
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notez_editor);
        final EditText editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        Toolbar my_toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        my_toolbar.setTitle("");

        noteId = intent.getIntExtra("noteId",-1);

        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        } else {
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size()-1 ;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notez", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }
            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_icons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.save:
            {
                if(MainActivity.set!=null)
                {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    overridePendingTransition(0,0);
                    Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_SHORT).show();
                    break;
                }
                else
                {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    MainActivity.notes.remove(noteId);
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    MainActivity.sharedPreferences.edit().putStringSet("notes", set).apply();
                    overridePendingTransition(0,0);
                    break;
                }

            }
            case R.id.back:
            {
                if(MainActivity.set!=null)
                {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    overridePendingTransition(0,0);
                    break;
                }
                else
                {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    MainActivity.notes.remove(noteId);
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    MainActivity.sharedPreferences.edit().putStringSet("notes", set).apply();
                    overridePendingTransition(0,0);
                    break;
                }
            }
            case  R.id.delete:
            {
                new AlertDialog.Builder(NotezEditor.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure to DELETE this?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                MainActivity.notes.remove(noteId);
                                MainActivity.arrayAdapter.notifyDataSetChanged();
                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                MainActivity.sharedPreferences.edit().putStringSet("notes", set).apply();


                            }
                        }).setNegativeButton("No",null)
                        .show();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}


