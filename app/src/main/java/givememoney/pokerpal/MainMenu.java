package givememoney.pokerpal;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startPokerGame(View v) {
        Intent intent = new Intent(this, pokergame.class);

        //TODO: Add parameters to new game activity (type of game, num players, etc.)
        startActivity(intent);
    }

}
