package dankmemes.wewrite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ricky on 27/08/2016.
 */
public class RoomSelection extends AppCompatActivity
{

    FirebaseDatabase database;

    ListView lv;
    ArrayAdapter<String> adapter;
    String Rooms[] = {
            "Room1", "Room2", "Room3", "Room4", "Room5"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_layout);


        lv = (ListView) findViewById(R.id.roomListView);


        adapter = new ArrayAdapter<>(dankmemes.wewrite.RoomSelection. this, android.R.layout.simple_list_item_1, Rooms);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data=(String)parent.getItemAtPosition(position);
                Log.v("Ricky", data);
               // Intent GoToLobby = new Intent(dankmemes.wewrite.RoomSelection. this, Room.class);
                //GoToLobby.putExtra("RoomName",data);
                //startActivity (GoToLobby);
            }
        });

    }
}
