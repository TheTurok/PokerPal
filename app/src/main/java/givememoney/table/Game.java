package givememoney.table;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Contains all of the properties of a single game state, i.e.:
 *  -Number of players and individual player properties
 *  -Current player turn
 *  -Type of pokergame/turn system
 *  -Time until next action and/or move blinds
 *  etc.
 *
 * This class is intended to be used to save the state of the game so that we can have a
 * compact 'undo' system. PokerTable should have a Game object as currentGame and a stack of
 * Game objects for undo.
 *
 * Alternatively, some other class besides PokerTable (maybe pokergame) could
 * serve as the "master class".
 *
 * Created by Joey on 08/10/2016.
 */

public class Game {
    private int m_numPlayers;
    private int m_numSeats;
    private List<Player> players;
    private int m_playerTurn;
    private double m_totalPot;
    private List<Double> m_sidePots;

    public Game() {

        m_numSeats = 6;
        m_numPlayers = 1;
        players = new ArrayList<Player>(m_numSeats);
        players.add(new Player(600));
        m_playerTurn = 0;
        System.out.println("successfully created Game object");
    }

    public Game(int numSeats, int numPlayers) throws IndexOutOfBoundsException {

        if (numSeats <= 0 || numPlayers <=0 )
            throw new IndexOutOfBoundsException();

        m_numSeats = numSeats;
        m_numPlayers = numPlayers;
        players = new ArrayList<Player>(m_numSeats);

        for (int i = 0; i < m_numPlayers; i++) {
            players.add(new Player(350, "PLAYER_" + Integer.toString(i)));
        }

        m_playerTurn = 0;
        m_totalPot = 0;
        m_sidePots = new ArrayList<Double>(m_numPlayers);

        System.out.println("******Successful call to complicated Game constructor!*****");
    }

    //TODO: get rid of this error
    public Player getCurrentPlayer(){ return players.get(m_playerTurn);}

    //do while?
    public void cycleActivePlayer() {
        //Go back to beginning if there are no more players
        if (m_playerTurn + 1 >= m_numPlayers || m_numPlayers == 1) {
            m_playerTurn = 0;
            return;
        }
        //otherwise go to next player
        m_playerTurn++;
    }

    public String toString() {
        String returnString =
        "\n-----Current Game Info------\n" +
        " Number of Players: " + m_numPlayers + "\n" +
        " Number of Seats: " + m_numSeats + "\n" +
        " Total Pot: " + m_totalPot + "\n" +
        " Current player turn ID: " + players.get(m_playerTurn).toString() + "\n" +
        " \n\nList of Players: " + players.toString() + "\n" + "------------\n";

        return returnString;
    }

    public void consoleLog() {
        System.out.println(this.toString());
    }


    //adapter
}
