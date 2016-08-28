package dankmemes.wewrite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Categories extends Activity {


    String Categories[] = {
            "Science Fiction", "Romance", "Satire", "Comedy", "Horror", "Fantasy", "Child Story"
    };
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        lv = (ListView) findViewById(R.id.lvItems);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(dankmemes.wewrite.Categories. this, android.R.layout.simple_list_item_1, Categories);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data=(String)parent.getItemAtPosition(position);

                Intent GoToLobby = new Intent(Categories.this, RoomSelection.class);
                Log.v("Ricky", data);
                GoToLobby.putExtra("Category", data);
                startActivity (GoToLobby);
            }
        });
    }
}
