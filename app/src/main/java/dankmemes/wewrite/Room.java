package dankmemes.wewrite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Room extends AppCompatActivity {
    TextView story;
    String fullStory = "";
    EditText userInput;
    Button sendButton;
    int counter = 0;

    TextView numberWords;

    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.room_1);

        userInput = (EditText) findViewById(R.id.editTextAnswer);


        story  =  (TextView) findViewById(R.id.textViewStory);

        sendButton = (Button) findViewById(R.id.button);


        numberWords =  (TextView) findViewById(R.id.numberWords) ;


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String word = userInput.getText().toString() + " ";

                fullStory = fullStory + word;

                story.setText(fullStory);

                counter++;

                numberWords.setText("Number of words: " + counter);

            }
        });









    }








}
