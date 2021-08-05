package sg.edu.rp.c346.id20002694.oursingapore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SongList extends AppCompatActivity {

    Button btnShow;
    ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song> al;
    ListView lv;

    ArrayAdapter<sg.edu.rp.c346.id20002694.oursingapore.Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        btnShow = findViewById(R.id.btnShow);
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