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
    }

    public Status getStatus(){ return m_status;}
    public double getCash() { return m_totalCash;}
    public String getName() { return m_name;}

}
