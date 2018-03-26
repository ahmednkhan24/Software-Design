import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Menu {
    private JMenuBar menuBar;

    //menu items
    private JFrame Frame;
    private JMenu FileMenu;
    private JMenu helpMenu;
    private JMenu connectionMenu;

    Vector<JMenuItem> allMenuButtons;

    public Menu(JFrame frame){
        Frame = frame;


        allMenuButtons = new Vector<JMenuItem>();

        //create the menu bar
        menuBar = new JMenuBar();
        Frame.setJMenuBar(menuBar);

        //create menus bar items
        FileMenu = new JMenu("File");
        menuBar.add(FileMenu);

        //create menus bar items
        connectionMenu = new JMenu("Connections");
        menuBar.add(connectionMenu);

        //create menus bar items
        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        //call functions to create sub menus
        createFileSubMenu();
        createHelpSubMenu();
        createConnectionsSubMenu();

    }

    //---------------------------------------
    //function creates sub menus for help menu
    private void createConnectionsSubMenu(){
        //how to play
        JMenuItem connectToServer = new JMenuItem("Connect To Server");
        connectToServer.addActionListener(new FindCorrectClick());
        allMenuButtons.addElement(connectToServer);
        connectionMenu.add(connectToServer);

    }

    //---------------------------------------
    //function creates file submenus
    private void createFileSubMenu(){
        //for the quit option
        JMenuItem statistics = new JMenuItem("Statistics");
        statistics.addActionListener(new FindCorrectClick());
        allMenuButtons.addElement(statistics);
        FileMenu.add(statistics);

        //for the about box
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new FindCorrectClick());
        allMenuButtons.addElement(about);
        FileMenu.add(about);

        //For the help Menu item
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new FindCorrectClick());
        allMenuButtons.addElement(exit);
        FileMenu.add(exit);
    }

    //---------------------------------------
    //function creates sub menus for help menu
    private void createHelpSubMenu(){
        //how to play
        JMenuItem howToPlay = new JMenuItem("How To Play");
        howToPlay.addActionListener(new FindCorrectClick());
        allMenuButtons.addElement(howToPlay);
        helpMenu.add(howToPlay);

        //how to use interface
        JMenuItem serverConnectionHelp = new JMenuItem("Server Connection Help");
        serverConnectionHelp.addActionListener(new FindCorrectClick());
        allMenuButtons.addElement(serverConnectionHelp);
        helpMenu.add(serverConnectionHelp);

    }

    //-------------------------------------------------                                          TODO:  add legit statistics
    //function create a display box showing game statistics
    private void displayStatistics(){
        String message = "YOUR STATISTICS:\n" +
                         "number of Hits: " + 0 + "\nnumber of misses: " + 0 + "\nNumber of moves: " + 0 +
                         "\n\nOPPONENT STATISTICS:" +
                "\nnumber of Hits: " + 0 + "\nnnumber of misses: " + 0 + "\nNumber of moves: " + 0;


        JOptionPane.showConfirmDialog(null, message,
                "Statistics",
                JOptionPane.CLOSED_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    //-------------------------------------------------
    //function create a display box telling the user the game is finished
    private void showAbout(){
        String message = "About The Programmers:" +
                "\nJames Warda- jwarda4" +
                "\nAhmed Khan  - akhan227";


        JOptionPane.showConfirmDialog(null, message,
                "About",
                JOptionPane.CLOSED_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    //-------------------------------------------------------------
    //function deals with listeners for menu items
    private boolean fileListeners(JMenuItem menuItem){
        //check if statistics was clicked
        if (menuItem.getText() == "Statistics"){
            displayStatistics();
            return true;
        }

        //if user wants to save puzzle
        else if (menuItem.getText() == "About"){
            showAbout();
            return true;
        }

        //if user wants to Exit
        else if (menuItem.getText() == "Exit"){
            System.exit(0);
        }

        return false;
    }

    //-------------------------------------------------------------
    //function displays to user how the game is played
    private void displayHowToPlay(){
        String message = "The game is played on four square grids, two for each player. The grids are to be 10x10 square\n" +
                "and the individual squares in the grid are identified by letter and number. On one grid the player\n" +
                "arranges ships and records the shots by the opponent. On the other grid the player records their\n" +
                "own shots. Before play begins, each player arranges a number of ships secretly on the grid for that player.\n" +
                "Each ship occupies a number of consecutive squares on the grid, arranged either horizontally or\n" +
                "vertically. The number of squares for each ship is determined by the type of the ship. The ships\n" +
                "cannot overlap";


        JOptionPane.showConfirmDialog(null, message,
                "how To Play",
                JOptionPane.CLOSED_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    //-------------------------------------------------------------                                 TODO:  add help on how to connect
    //function displays to user how to user the server
    private void showServerConnectionHelp(){
        String message = "No help yet for this feature";


        JOptionPane.showConfirmDialog(null, message,
                "Server Connection Help",
                JOptionPane.CLOSED_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    //-------------------------------------------------------------
    //function deals with listeners for help menu items
    private boolean helpMenuListeners(JMenuItem menuItem){

        //check if statistics was clicked
        if (menuItem.getText() == "How To Play"){
            displayHowToPlay();
            return true;
        }

        //if user wants to save puzzle
        else if (menuItem.getText() == "Server Connection Help"){
            showServerConnectionHelp();
            return true;
        }

        return false;
    }

    //-------------------------------------------------------------                                 TODO:  add code to actually connect to server
    //function displays to user how to user the server
    private void connectToServer(){
        String message = "This feature is not available yet";


        JOptionPane.showConfirmDialog(null, message,
                "Connecting to server...",
                JOptionPane.CLOSED_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    //-------------------------------------------------------------
    //function deals with listeners for connection menu items
    private boolean connectionMenuListener(JMenuItem menuItem){
        if (menuItem.getText() == "Connect To Server"){
            connectToServer();
            return true;
        }

        return false;
    }

    //-------------------------------------------------------------
    //on click listener for all menu buttons
    private void onClickAction(JMenuItem menuItem){
        //ceck if file listener was the item called
        if (fileListeners(menuItem)){
            return;
        }
        //check if help menu was the listener called
        else if (helpMenuListeners(menuItem)){
            return;
        }
        //function checks if hits menu was called
        else{
            connectionMenuListener(menuItem);
        }
    }

    //-------------------------------------------------------------
    //class handles on click listens
    private class FindCorrectClick implements ActionListener {

        //Method will figure out what button was clicked
        public void actionPerformed(ActionEvent e) {

            //go through and see which button it is
            for (JMenuItem m: allMenuButtons) {
                if (e.getSource() == m) {
                    onClickAction(m);
                }
            }
        }
    }


}
