package givememoney.pokerpal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.NumberPicker;

import givememoney.table.Game;

public class playerInfoInput extends Activity {

    int playernum = getIntent().getIntExtra("playerNum",3);
    int stacksizes = getIntent().getIntExtra("stackSizes",3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info_input);
    }

    class playerInfoAdapter extends BaseAdapter{

        Context context;

        playerInfoAdapter(Context c, Game game){
            context = c;

        }


        @Override
        public int getCount() {
            return playernum;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

}
