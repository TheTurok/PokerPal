package givememoney.pokerpal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class IngameOption extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame_option);
    }

    public void goToMainMenu(View    v) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void goResume(View v) {
        finish();
    }

}
