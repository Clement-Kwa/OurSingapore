package sg.edu.rp.c346.id20002694.oursingapore;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYear;

    Button btnInsert, btnShow;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);

        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);
        ratingBar = findViewById(R.id.ratingBar);


        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(sg.edu.rp.c346.id20002694.oursingapore.MainActivity.this,
                        sg.edu.rp.c346.id20002694.oursingapore.SongList.class);

                startActivity(i);

            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singer = etSingers.getText().toString();
                int year = 0;

                if (!etYear.getText().toString().isEmpty()) {
                    year = Integer.parseInt(etYear.getText().toString());
                }


                int stars = (int)ratingBar.getRating();

                sg.edu.rp.c346.id20002694.oursingapore.DBHelper dbh = new sg.edu.rp.c346.id20002694.oursingapore.DBHelper(sg.edu.rp.c346.id20002694.oursingapore.MainActivity.this);
                dbh.insertSong(title, singer, year, stars);
            }
        });

    }
}