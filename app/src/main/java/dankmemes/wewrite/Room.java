package dankmemes.wewrite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Room extends AppCompatActivity {
    TextView story;
    String fullStory = "";
    String word = "";
    EditText userInput;
    Button sendButton;
    int counter = 0;

    String dataBasePath;

    FirebaseDatabase roomDatabase;
    DatabaseReference roomRef;

    TextView numberWords;

    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        dataBasePath = extras.getString("dataBasePath");
        roomDatabase = FirebaseDatabase.getInstance();
        roomRef = roomDatabase.getReference(dataBasePath + "Message");

    setContentView(R.layout.room_1);

        userInput = (EditText) findViewById(R.id.editTextAnswer);


        story  =  (TextView) findViewById(R.id.textViewStory);

        sendButton = (Button) findViewById(R.id.button);


        numberWords =  (TextView) findViewById(R.id.numberWords) ;


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 word = userInput.getText().toString() + " ";

                roomRef.setValue(fullStory + word);

                counter++;

                numberWords.setText("Number of words: " + counter);

            }
        });


        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fullStory = dataSnapshot.getValue(String.class);
                story.setText(fullStory);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }








}
