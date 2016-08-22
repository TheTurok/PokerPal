package givememoney.table;

/**
 * Basic implementation of a Player object
 * Created by Joey on 08/09/2016.
 */



public class Player {
    public static enum Status {
        ACTIVE, WAITING, IDLE
    }

    private double m_totalCash;
    private Status m_status;
    private boolean m_checkFold;
    private boolean m_sitOut;
    private double m_bet;
    private String m_name;

    public Player() {
        m_totalCash = 100;
        init();
        m_name = "Khris Go";
    }

    public Player(double startingCash) {
        if (startingCash < 0)
            startingCash = 0;
        m_totalCash = startingCash;
        m_name = "Khris Go";
        init();
    }

    public Player(double startingCash, String name) {
        if (startingCash < 0)
            startingCash = 0;
        m_totalCash = startingCash;
        m_name = name;
        init();
    }

    private void init() {
        m_status = Status.WAITING;
        m_checkFold = false;
        m_sitOut = false;
        m_bet=-1;
    }

    public Status getStatus(){ return m_status;}
    public double getCash() { return m_totalCash;}
    public boolean isCheckFold() {return m_checkFold;}
    public boolean sittingOut() {return m_sitOut;}

    public void removeCash(double spend) {
        if (spend > m_totalCash)
            throw new IndexOutOfBoundsException("Player " + m_name + " tried to overspend!");

        m_totalCash -= spend;

    }
    public void setStatus(Player.Status status) { m_status = status;}
    public String getName() { return m_name;}
    public String toString() {
        String returnString = "\n**Name: " + m_name + "\n" +
                "Total Money: " + m_totalCash + "\n" +
                "Status: " + m_status + "\n\n**";

        return returnString;
    }

    public void setSitout(boolean wantSit) { m_sitOut = wantSit;}
    public void setCheckFold(boolean wantCheckFold) {m_checkFold = wantCheckFold;}
    public void setBet(double bet) {m_bet = bet;}

    public double getBet() {return m_bet;}

}
