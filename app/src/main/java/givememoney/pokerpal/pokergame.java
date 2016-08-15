package givememoney.pokerpal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import givememoney.table.Game;
import givememoney.table.Player;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.ListIterator;

/** google doc for PokerPal
 *      http://tinyurl.com/thepokerpal
 */

//TODO: Add ability to pass params (type of pokergame, numplayers, etc) to onCreate

public class pokergame extends Activity {

    /*ListView Adapter Code start **/
    /*ListView Adapter Code start **/
    ListView plv;

    class mockRow
    {
        String mockname;
        String mockmoney;
        String mockbet;
        mockRow(String mn,String mm, String mb){
            this.mockname = mn;
            this.mockmoney = mm;
            this.mockbet = mb;
        }
    }

    class myAdapter extends BaseAdapter {

        ArrayList<mockRow> mockList;
        Context context;
        myAdapter(Context c, Game game)
        {
            context = c;
            mockList = new ArrayList<mockRow>();
            String[] mockplayername = game.getNames();
            String[] mockplayermoney = game.getStacks();
            String[] mockplayerbet = {"1500","1500","1500","1500","1500","1500","1500","1500","1500","1500" };

            for(int i = 0; i< game.getNumPlayers(); i++)
            {
                mockList.add(new mockRow(mockplayername[i], mockplayermoney[i], mockplayerbet[i]));
            }
        }

        @Override
        public int getCount() {
            return mockList.size();
        }

        @Override
        public Object getItem(int i) {
            return mockList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.playerpopulate,viewGroup,false);

            TextView pname = (TextView) row.findViewById(R.id.populateName);
            TextView pmoney = (TextView) row.findViewById(R.id.populateMoney);
            TextView pbet = (TextView) row.findViewById(R.id.populateBet);

            mockRow temp = mockList.get(i);

            pname.setText(temp.mockname);
            pmoney.setText(temp.mockmoney);
            pbet.setText(temp.mockbet);

            return row;
        }

        public void refreshAdapter(Player player, int playerID){
            mockRow updatedRow = new mockRow(player.getName(), Double.toString(player.getCash()),
                    "69");
            mockList.set(playerID, updatedRow);
            notifyDataSetChanged();}
    }
    //final double minBet = previousBet;
    /**     end     **/
    /**     end     **/

    private Button betButton;
    private TextView potString;
    Game currentGame;
    Player currentPlayer;

    myAdapter gameAdapter;

    double maxBet = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);

        currentGame = EventBus.getDefault().removeStickyEvent(Game.class);
        if (currentGame == null)
            throw new NullPointerException("No game on EventBus!");

        currentPlayer = currentGame.getCurrentPlayer();
        maxBet = currentPlayer.getCash();
        setContentView(R.layout.activity_pokergame);

        //adapter Class code
        gameAdapter = new myAdapter(this, currentGame);
        plv = (ListView) findViewById(R.id.PlayerListView);
        plv.setAdapter(gameAdapter);


        //Gets correct things from activity_pokergame.xml
        betButton = (Button) findViewById(R.id.bet);
        potString = (TextView) findViewById(R.id.potnumber);

        betButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                currentGame.consoleLog();
                showBetInputDialog(currentGame);
            }
        });


    }

    //Creates an input dialog for user BET on click of BET button
    //TODO: Add greater functionality (bet X amount of big blinds)
    //TODO: Raise, reraise, pot size bet, x2, etc.
    protected void showBetInputDialog(final Game game) {

        final Player player = currentGame.getCurrentPlayer();
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(pokergame.this);
        View promptView = layoutInflater.inflate(R.layout.prompts, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(pokergame.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.betInput);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Get user input and current pot as string
                        String inputToString = editText.getText().toString();
                        String currentPot = potString.getText().toString();

                        //Turn them into ints, then calculate pot
                        Double betAmount = Double.parseDouble(inputToString);
                        Double potAmount = Double.parseDouble(currentPot);
                        Double finalAmount = betAmount+potAmount;
                        String finalPot = Double.toString(finalAmount);

                        System.out.println(
                                "\n\n==== New Bet ====\n" +
                                        "Player making bet: " + player.toString() + "\n" +
                        "bet size: " + inputToString + "\n" +
                        "current player id: " + game.getCurrentPlayerID() + "\n======\n");
                        player.removeCash(betAmount);
                        gameAdapter.refreshAdapter(player, game.getCurrentPlayerID());
                        potString.setText(finalPot);
                        game.cycleActivePlayer();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();

        //Disable bet for invalid inputs
        //TODO: Set max bet as current user chips amount
        editText.addTextChangedListener(new TextWatcher() {
            private void handleText() {
                // Grab the button
                final Button okButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);

                boolean validBet = false;
                double betSize = 0;
                double minBet = 1;
                String betString = editText.getText().toString();
                if (betString.length() > 0)
                {
                    try {
                        betSize = Double.valueOf(betString.toString());
                    }
                    catch(Exception e) {
                        betSize = 0;
                    }

                    if (betSize <= maxBet && betSize >= minBet)
                        validBet = true;
                }
                if (!validBet){
                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                handleText();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing to do
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nothing to do
            }
        });
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }

    /*
    //Updates the current game of the pokerGame with passed gameEvent
    @Subscribe
    public void onGameEvent(Game gameEvent){
        currentGame = gameEvent;
//        maxBet = currentGame.getCurrentPlayer().getCash();
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pokergame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, IngameOption.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
