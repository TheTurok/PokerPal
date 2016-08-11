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
    private List<Player> players;
    private int playerTurn;

    public Game() {
    m_numPlayers = 1;
    players = new ArrayList<Player>(1);
    players.add(new Player(600));
    playerTurn = 0;
    System.out.println("successfully created Game object");

}

    public Player getCurrentPlayer(){ return players.get(playerTurn);}
}
