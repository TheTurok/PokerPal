package givememoney.pokerpal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import givememoney.table.Game;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/** google doc for PokerPal
 *      http://tinyurl.com/thepokerpal
 */

//TODO: Add ability to pass params (type of pokergame, numplayers, etc) to onCreate

public class pokergame extends Activity {

    /*ListView Adapter Code start **/
    ListView plv;
    String[] mockplayerlist = {"player1", "player2", "player3", "player4", "player5", "player6",
            "player7", "player8", "player9", "player10" };
    /**end **/

    private Button betButton;
    private TextView potString;

    Game currentGame = new Game();
    double maxBet = 500;

    //final double minBet = previousBet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentGame = EventBus.getDefault().getStickyEvent(Game.class);
       // maxBet = currentGame.getCurrentPlayer().getCash();

        setContentView(R.layout.activity_pokergame);
        //Gets correct things from activity_pokergame.xml
        betButton = (Button) findViewById(R.id.bet);
        potString = (TextView) findViewById(R.id.potnumber);

        betButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                //currentGame.consoleLog();
                currentGame.cycleActivePlayer();
                //currentGame.consoleLog();
                showBetInputDialog();

            }
        });

        //adapter Class code
        plv = (ListView) findViewById(R.id.PlayerListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , mockplayerlist);
        plv.setAdapter(adapter);
    }

    //Creates an input dialog for user BET on click of BET button
    //TODO: Add greater functionality (bet X amount of big blinds)
    //TODO: Raise, reraise, pot size bet, x2, etc.
    protected void showBetInputDialog() {

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

                        potString.setText(finalPot);
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

    //Updates the current game of the pokerGame with passed gameEvent
    @Subscribe
    public void onGameEvent(Game gameEvent){
        currentGame = gameEvent;
      //  maxBet = currentGame.getCurrentPlayer().getCash();
    }

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
}
