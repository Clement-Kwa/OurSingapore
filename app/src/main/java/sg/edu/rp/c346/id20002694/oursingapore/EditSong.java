package sg.edu.rp.c346.id20002694.oursingapore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditSong extends AppCompatActivity {

    EditText etTitle, etSingers, etYear, etID;
    RatingBar ratingBar;
    Button btnUpdate, btnDelete, btnCancel;
    sg.edu.rp.c346.id20002694.oursingapore.Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);

        Intent i = getIntent();
        data = (sg.edu.rp.c346.id20002694.oursingapore.Song) i.getSerializableExtra("data");

        etTitle  = findViewById(R.id.etTitle);
        etSingers= findViewById(R.id.etSingers);
        etYear= findViewById(R.id.etYear);
        etID= findViewById(R.id.etID);
        ratingBar = findViewById(R.id.ratingBar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete= findViewById(R.id.btnDelete);
        btnCancel= findViewById(R.id.btnCancel);

        etID.setText(data.getId()+"");
        etTitle.setText(data.getTitle());
        etSingers.setText(data.getSingers());
        etYear.setText(data.getYear()+"");

        ratingBar.setRating((data.getStars()));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sg.edu.rp.c346.id20002694.oursingapore.DBHelper dbh = new sg.edu.rp.c346.id20002694.oursingapore.DBHelper(sg.edu.rp.c346.id20002694.oursingapore.EditSong.this);
                dbh.deleteSong(data.getId());
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditSong.this);

                myBuilder.setTitle("Warning");
                myBuilder.setMessage("Are you sure you want to cancel changes? ");
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                myBuilder.setNegativeButton("Continue Editing", null);

                AlertDialog myDialogue = myBuilder.create();
                myDialogue.show();

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sg.edu.rp.c346.id20002694.oursingapore.DBHelper dbh = new sg.edu.rp.c346.id20002694.oursingapore.DBHelper(sg.edu.rp.c346.id20002694.oursingapore.EditSong.this);


                String title = etTitle.getText().toString();
                String singers = etSingers.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());

                int stars = (int)ratingBar.getRating();

                data.setSongContent(title, singers, year, stars);
                dbh.updateSong(data);
                dbh.close();
                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditSong.this);

                myBuilder.setTitle("Warning");
                myBuilder.setMessage("Are you sure you want to delete "+etTitle.getText().toString()+"?");
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sg.edu.rp.c346.id20002694.oursingapore.DBHelper dbh = new sg.edu.rp.c346.id20002694.oursingapore.DBHelper(sg.edu.rp.c346.id20002694.oursingapore.EditSong.this);
                        dbh.deleteSong(data.getId());
                        finish();
                    }
                });
                myBuilder.setNegativeButton("Cancel", null);

                AlertDialog myDialogue = myBuilder.create();
                myDialogue.show();
            }
        });

    }
}