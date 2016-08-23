package givememoney.pokerpal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import givememoney.table.Game;

public class ChooseGameOptions extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_options);
    }

    public void populateInfoConfirm(View v) {
        Intent intent = new Intent(this, playerInfoInput.class);

        EditText playernumET = (EditText) findViewById(R.id.editText9);
        EditText stacksizesET = (EditText) findViewById(R.id.editText3);
        int playernum = Integer.valueOf(playernumET.getText().toString());
        int stacksizes = Integer.valueOf(stacksizesET.getText().toString());

        intent.putExtra("playerNum", 5);
        intent.putExtra("stackSizes", 100);

        startActivity(intent);
    }
}
