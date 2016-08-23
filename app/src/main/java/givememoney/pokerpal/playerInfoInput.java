package givememoney.pokerpal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import givememoney.table.Game;
import givememoney.table.Player;

public class playerInfoInput extends Activity {
    ListView pii;
    playerInfoAdapter adapt;
    Game currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info_input);

        currentGame = EventBus.getDefault().removeStickyEvent(Game.class);

        adapt = new playerInfoAdapter(this, currentGame);
        pii = (ListView) findViewById(R.id.listViewpii);
        pii.setAdapter(adapt);

    }

    class infoRow{
        String ir_name;
        String ir_stack;

        infoRow(String name, String stack){
            this.ir_name = name;
            this.ir_stack = stack;
        }
    }

    class playerInfoAdapter extends BaseAdapter{
        ArrayList<infoRow> infoList;
        Context context;

        playerInfoAdapter(Context c, Game game){
            context = c;
            infoList = new ArrayList<infoRow>();
            String[] playernames = game.getNames();
            String[] playerstacks = game.getStacks();

            for(int i = 0; i < game.getNumPlayers(); i++){
                infoList.add(new infoRow(playernames[i], playerstacks[i]));
            }
        }


        @Override
        public int getCount() {
            return infoList.size();
        }

        @Override
        public Object getItem(int i) {
            return infoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.playerinfopopulate,viewGroup,false);

            EditText nameinput = (EditText) row.findViewById(R.id.editText);
            EditText stackinput = (EditText) row.findViewById(R.id.editText2);

            infoRow temp = infoList.get(i);

            nameinput.setText(temp.ir_name );
            stackinput.setText(temp.ir_stack, TextView.BufferType.EDITABLE);

            return row;
        }
    }

    public void createGame(View v){
        Intent intent = new Intent(this, pokergame.class);


        for(int i = 0; i < currentGame.getNumPlayers(); i++){
            View row = pii.getChildAt(i);
            EditText nameinput = (EditText) row.findViewById(R.id.editText);
            EditText stackinput = (EditText) row.findViewById(R.id.editText2);
            //String et_name = v.findViewById(R.id.editText).getText().toString();
            currentGame.getPlayerList().get(i).setName(nameinput.getText().toString());
            currentGame.getPlayerList().get(i).setStack(Double.parseDouble(stackinput.getText().toString()));
        }

        EventBus.getDefault().postSticky(currentGame);

        startActivity(intent);


    }

}
