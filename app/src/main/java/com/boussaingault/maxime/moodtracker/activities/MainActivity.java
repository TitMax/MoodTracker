package com.boussaingault.maxime.moodtracker.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.VerticalViewPager;
import com.boussaingault.maxime.moodtracker.models.ViewPagerAdapter;

/**
 * Created by Maxime Boussaingault on 14/11/2017.
 */

public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;
    private ImageButton mImageButtonAddNote;
    private ImageButton mImageButtonHistory;
    private EditText mEditTextNote;
    private String note = "";
    private String[] moods = {"Sad", "Disappointed", "Normal", "Happy", "Super Happy"};
    private int currentMoodId = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVerticalViewPager = findViewById(R.id.activity_main_view_pager);
        mImageButtonAddNote = (ImageButton) findViewById(R.id.activity_main_note_add_btn);
        mImageButtonHistory = (ImageButton) findViewById(R.id.activity_main_history_btn);

        mVerticalViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mVerticalViewPager.setCurrentItem(currentMoodId);

        mImageButtonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteDialog();
            }
        });

        mImageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AddNoteDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_add_note_dialog, null, false);
        dialogBuilder.setView(dialogView);

        mEditTextNote = (EditText) dialogView.findViewById(R.id.layout_add_note_edit_text);
        mEditTextNote.setText(note);
        mEditTextNote.setSelection(mEditTextNote.length());

        dialogBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mEditTextNote.getText().toString().equals("")) {
                    note = mEditTextNote.getText().toString();
                    Toast.makeText(MainActivity.this, "Aucune note trouvée", Toast.LENGTH_SHORT).show();
                }
                else {
                    note = mEditTextNote.getText().toString();
                    Toast.makeText(MainActivity.this, "Note ajoutée", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Annulation", Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy()");
    }
}
