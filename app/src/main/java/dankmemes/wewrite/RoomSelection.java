package dankmemes.wewrite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;



/**
 * Created by ricky on 27/08/2016.
 */
public class RoomSelection extends Activity
{


    ListView lv;
    ArrayAdapter<String> adapter;
    String Rooms[] = {
            "Room1", "Room2", "Room3", "Room4", "Room5"
    };
String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_layout);

        Bundle extras = getIntent().getExtras();

        category = extras.getString("Category");


        lv = (ListView) findViewById(R.id.roomListView);


        adapter = new ArrayAdapter<>(dankmemes.wewrite.RoomSelection. this, android.R.layout.simple_list_item_1, Rooms);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data=(String)parent.getItemAtPosition(position);
                String databasePath = category+"/"+data + "/";
                Log.v("Ricky", data);
               Intent GoToRoom = new Intent(dankmemes.wewrite.RoomSelection. this, Room.class);
                GoToRoom.putExtra("dataBasePath",databasePath);
                startActivity (GoToRoom);
            }
        });

    }
}
