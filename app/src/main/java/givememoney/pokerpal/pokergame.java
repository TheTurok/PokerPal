package givememoney.pokerpal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/** google doc for PokerPal
 *      http://tinyurl.com/thepokerpal
 */



public class pokergame extends Activity {

    private Button betButton;
    private TextView potString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokergame);

        //Gets correct things from activity_pokergame.xml
        betButton = (Button) findViewById(R.id.bet);
        potString = (TextView) findViewById(R.id.potnumber);

        betButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                showBetInputDialog();

            }
        });

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
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
