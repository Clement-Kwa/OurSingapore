package sg.edu.rp.c346.id20002694.oursingapore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SongList extends AppCompatActivity {

    Button btnShow, btnAdd;
    ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song> al;
    ListView lv;

    ArrayAdapter<sg.edu.rp.c346.id20002694.oursingapore.Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        btnShow = findViewById(R.id.btnShow);
        btnAdd = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.lv);
        al = new ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song>();

        sg.edu.rp.c346.id20002694.oursingapore.DBHelper dbh = new sg.edu.rp.c346.id20002694.oursingapore.DBHelper(sg.edu.rp.c346.id20002694.oursingapore.SongList.this);
        al.addAll(dbh.getAllSongs());


        aa = new CustomAdapter(this, R.layout.row, al);

        lv.setAdapter(aa);
        dbh.close();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {
                sg.edu.rp.c346.id20002694.oursingapore.Song data = al.get(position);
                Intent i = new Intent(sg.edu.rp.c346.id20002694.oursingapore.SongList.this, sg.edu.rp.c346.id20002694.oursingapore.EditSong.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sg.edu.rp.c346.id20002694.oursingapore.DBHelper dbh = new sg.edu.rp.c346.id20002694.oursingapore.DBHelper(sg.edu.rp.c346.id20002694.oursingapore.SongList.this);
                al.clear();
                String rating  = "5";
                al.addAll(dbh.getAllSongs(rating));
                aa.notifyDataSetChanged();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the input.xml layout file
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.activity_main, null);

                // Obtain the UI component in the input.xml layout
                // It needs to be defined as "final", so that it can used in the onClick() method later
                final EditText etTitle = viewDialog.findViewById(R.id.etTitle);
                final EditText etSingers = viewDialog.findViewById(R.id.etSingers);
                final EditText etYear = viewDialog.findViewById(R.id.etYear);
                final RatingBar ratingBar = viewDialog.findViewById(R.id.ratingBar);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SongList.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("Insert new island");
                myBuilder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etTitle.getText().toString();
                        String singer = etSingers.getText().toString();
                        int year = 0;

                        if (!etYear.getText().toString().isEmpty()) {
                            year = Integer.parseInt(etYear.getText().toString());
                        }


                        int stars = (int)ratingBar.getRating();

                        sg.edu.rp.c346.id20002694.oursingapore.DBHelper dbh = new sg.edu.rp.c346.id20002694.oursingapore.DBHelper(sg.edu.rp.c346.id20002694.oursingapore.SongList.this);
                        dbh.insertSong(title, singer, year, stars);
                    }
                });
                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sg.edu.rp.c346.id20002694.oursingapore.DBHelper dbh = new sg.edu.rp.c346.id20002694.oursingapore.DBHelper(sg.edu.rp.c346.id20002694.oursingapore.SongList.this);
        al.clear();

        al.addAll(dbh.getAllSongs());

        aa.notifyDataSetChanged();
        dbh.close();
    }
}