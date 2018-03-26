import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.SortedSet;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI {

    private JFrame frame;
    private coord [][] userBoard;
    private coord [][] opponentBoard;
    private Vector<JButton> shipButtons;
    private Vector<ship> ships;


    private JLabel statusBar;
    private JLabel orientationLabel;
    private JButton rotateButton;
    private char orientation;
    private String shipSelected;

    private gameChecks gameChecker;

    //reference to all panels
    private JPanel holder;
    private JPanel preGameHolder;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUI() {



        //exampleBackEnd e = new exampleBackEnd();
        //intialize arrays for the boards and boats
        userBoard = new coord[10][10];
        opponentBoard = new coord[10][10];
        shipButtons = new Vector<JButton>();
        orientation = 'h';
        shipSelected = "";
        ships = new Vector<ship>();

        //this checks all rules for the game during play time
        gameChecker = new gameChecks(userBoard);

        //create the board and frame
        initialize();

        //create the menu
        Menu menu = new Menu(frame);

        //hide all of the pannels and stuff and show server and client button
        waitForServer();

        frame.setVisible(true);

    }


    //-----------------------------------------------------------
    //function hides all of the pannels and waits for click
    private void waitForServer(){
        holder.setVisible(false);

        //create the server button
        JButton ServerBtn = new JButton("Server");
        ServerBtn.setBounds(190, 136, 195, 104);
        preGameHolder.add(ServerBtn);

        //create the client button
        JButton clientBtn = new JButton("Client");
        clientBtn.setBounds(190, 276, 195, 104);
        preGameHolder.add(clientBtn);

        JLabel introLabel = new JLabel("Please Choose Server or Client");
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setBounds(158, 72, 270, 16);
        preGameHolder.add(introLabel);

        JPanel serverPanel = new JPanel();
        serverPanel.setBounds(56, 623, 469, 202);
        preGameHolder.add(serverPanel);
        serverPanel.setVisible(false);

        JPanel clientPanel = new JPanel();
        clientPanel.setBounds(56, 410, 469, 202);
        preGameHolder.add(clientPanel);
        clientPanel.setVisible(false);


        //action isteneer for the server button
        ServerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                serverPanel.setVisible(true);
                SERVER S = new SERVER(serverPanel, holder, preGameHolder);

            }
        });

        //action listener for the client button
        clientBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientPanel.setVisible(true);
                CLIENT C = new CLIENT(clientPanel, holder, preGameHolder);
            }
        });

    }


    //------------------------------------------------------------
    //function returns the ships tail
    private coord getShipTail(coord head){

        //create a new ship to place on the board
        if (orientation == 'h'){
           return (new coord(head.getxPos(), head.getyPos()+getShipSize(shipSelected)-1));
        }
        else{

            return (new coord(head.getxPos()+getShipSize(shipSelected)-1,head.getyPos() ));
        }
    }

    //------------------------------------------------------------
    //function resets the button color for a ship
    private void resetButtonColor(boolean hidden){

        //loop through and find correct button
        for (JButton b: shipButtons){
            if (b.getText() == shipSelected){
                if (hidden == true) {
                    b.setVisible(false);
                    return;
                }
                b.setBackground(new JButton("").getBackground());
                return;
            }
        }
    }

    //------------------------------------------------------------
    //function deals with user click on click actions
    private void userBoardClickAction(coord button){
        System.out.println("user board clicked at " + button.getxPos() +", " + button.getyPos());
        ship s;
        coord head;
        coord tail;

        //else check if there is a ship selected
        if (shipSelected == ""){
            System.out.println("no ship selected");
            return;
        }

        //first get the x and y coord of the ship

        head = button;
        tail = getShipTail(button);

        System.out.println("head = " + head.getxPos() + ", " + head.getyPos());
        System.out.println("tail = " + tail.getxPos() + ", " + tail.getyPos());

        //check if the ship is leagal
        if (!gameChecker.isLegal(head, tail, orientation, getShipSize(shipSelected))){
            //print error and return
            System.out.println("ship placement is not legal");
            //reset the ship
            resetButtonColor(false);
            shipSelected = "";
            return;
        }

        s = new ship(userBoard, shipSelected,head,tail);

        if (orientation == 'h') {
            s.setOrientation('h');
        }
        else{
            s.setOrientation('v');
        }

        gameChecker.addShipToBoard(s);

        ships.add(s);

        //set the ship selected to -1 again and undo the color
        resetButtonColor(true);
        shipSelected = "";

        System.out.println("ship added");

        //check if we should remove the rotate button and text
        removeRotateButton();

    }

    //----------------------------------------------------
    //function checks if we should remove the rotate button
    private void removeRotateButton(){
        if (ships.size() == 5){
            rotateButton.setVisible(false);
            orientationLabel.setText("");
        }
    }

    private int getShipSize(String shipName)
    {
        switch (shipName.toUpperCase())
        {
            case "AIRCRAFT":
                return 5;

            case "BATTLESHIP":
                return 4;

            case "DESTROYER":
                return 3;

            case "SUBMARINE":
                return 3;

            case "PATROL":
                return 2;
        }
        return 0;
    }

    //------------------------------------------------------------
    //function deals with opponent on click action
    private void opponentBoardClickAction(coord button){
        System.out.println("opponent board clicked at " + button.getxPos() +", " + button.getyPos());

        //check if the spot has already been clicked
        if (button.isSpotOccupied()){
            System.out.println("spot is occupied Please try again");
            return;
        }

        //mark the spot as a hit or miss
        markOpponentBoard(false, button);
        button.setToOccupied();

    }

    //------------------------------------------------------------
    //function deals with ship buttons on the right side
    private void shipClickAction(JButton shipbtn){
        System.out.println(shipbtn.getText() +" was pressed");
        //set the ship that is selected
        shipSelected = shipbtn.getText();
        shipbtn.setBackground(Color.cyan);

    }

    //-----------------------------------------------------------
    //function updates the ships orientation
    private void updateShipOrientation(){

        //traverse the ships and update their orientation
        for (ship s : ships){
            if (!s.shipIsPlaced()){
                s.setOrientation(orientation);
            }
        }
    }

    //-----------------------------------------------------------
    //function marks the user board with a hit or miss
    private void markUserBoard(boolean hit, coord index){

        //chcek if its water or boat
        if (index.getVal() == 0){
            //they hit water put a water marker
            setImage(index, "miss.gif");
            index.setToOccupied();
            return;
        }

        //else there is a ship there put the hit pictures
        if (index.getCurrentShip().isHorizontal()){
            //check if its the back of the ship or the front
            if (index.getBackOfShip()){
               setImage(index, "Hhitback.gif");
               index.setToOccupied();
            }
            else if (index.getFrontOfShip()){
                setImage(index, "hHitFront.gif");
                index.setToOccupied();
            }
            //else its the middle image of the ship
            else {
                setImage(index, "hHitMiddle.gif");
                index.setToOccupied();
            }
        }
        //the ship is not horizontal
        else{
            //check if its the back of the ship or the front
            if (index.getBackOfShip()){
                setImage(index, "vhitback.gif");
                index.setToOccupied();
            }
            else if (index.getFrontOfShip()){
                setImage(index, "vhitfront.gif");
                index.setToOccupied();
            }
            //else its the middle image of the ship
            else {
                setImage(index, "vhitmiddle.gif");
                index.setToOccupied();
            }
        }
    }

    //--------------------------------------------------------------
    //function sets the picture for a given index and a given image
    private void setImage(coord index, String imageName){
        try {
            Image waterImage = ImageIO.read(getClass().getResource(imageName));
            index.setIcon(new ImageIcon(waterImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------
    //function marks the opponent board as a miss or hit
    private void markOpponentBoard(boolean hit, coord index){
        if (hit){
            setImage(index, "hit.gif");
            index.setToOccupied();
        }
        else {
            setImage(index, "miss.gif");
            index.setToOccupied();
        }
    }

    //-----------------------------------------------------------------------------------------------------ALL GUI CODE
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        //create the frame
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 585, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        //this panel holds all others
        holder = new JPanel();
        holder.setBackground(SystemColor.activeCaption);
        holder.setBounds(0, 0, 578, 945);
        frame.getContentPane().add(holder);
        holder.setLayout(null);


        //this panel holds the intro page
        preGameHolder = new JPanel();
        preGameHolder.setBackground(SystemColor.activeCaption);
        preGameHolder.setBounds(0, 0, 578, 945);
        preGameHolder.setLayout(null);
        frame.getContentPane().add(preGameHolder);


        //this is the opponent grid
        JPanel opponentPanel = new JPanel();
        opponentPanel.setBackground(SystemColor.activeCaption);
        opponentPanel.setBounds(34, 60, 390, 390);
        opponentPanel.setLayout(new GridLayout(10,10));
        holder.add(opponentPanel);

        //this is A-J for the opponent row
        JPanel opponentRow= new JPanel();
        opponentRow.setBackground(SystemColor.activeCaption);
        opponentRow.setBounds(34, 30, 390, 31);
        opponentRow.setLayout(new GridLayout(1, 0, 0, 0));
        holder.add(opponentRow);

        //1-10 opponent Col
        JPanel opponentCol = new JPanel();
        opponentCol.setBackground(SystemColor.activeCaption);
        opponentCol.setBounds(0, 60, 33, 390);
        opponentCol.setLayout(new GridLayout(0, 1));
        holder.add(opponentCol);

        //this is the user grid
        JPanel userPanel = new JPanel();
        userPanel.setBackground(SystemColor.activeCaption);
        userPanel.setLayout(new GridLayout(10,10));
        userPanel.setBounds(34, 506, 390, 390);
        holder.add(userPanel);

        //a-j for user row
        JPanel userRow = new JPanel();
        userRow.setBackground(SystemColor.activeCaption);
        userRow.setBounds(34, 475, 390, 31);
        userRow.setLayout(new GridLayout(1, 0));
        holder.add(userRow);

        //1-10 fdor user col
        JPanel userCol = new JPanel();
        userCol.setBackground(SystemColor.activeCaption);
        userCol.setBounds(0, 506, 33, 390);
        userCol.setLayout(new GridLayout(0,1));
        holder.add(userCol);

        //status bar label
        statusBar = new JLabel("Welcome to Battleship!  Not Connected..  Please connect to server");
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);
        statusBar.setBounds(0, 0, 578, 31);
        holder.add(statusBar);

        //code for rotate button and label
        orientationLabel = new JLabel("Ship Mode Horizontal");
        orientationLabel.setBounds(430, 505, 120, 14);
        holder.add(orientationLabel);

        rotateButton = new JButton("Rotate");
        rotateButton.setBounds(455, 530, 72, 23);
        rotateButton.addActionListener(new FindCorrectClick());
        holder.add(rotateButton);

        //call functions to create the rest of the board
        createShipButtons(holder);
        populateUserBoard(userCol,userRow, userPanel );
        populateOpponentBoard(opponentCol, opponentRow, opponentPanel);

    }

    //-----------------------------------------------------------
    //function creates the user Side of the board
    private void populateUserBoard(JPanel userCol, JPanel userRow, JPanel userPanel ){
        char x = 'A';
        //set the user col numbers
        for (int i = 0; i< 10; i++) {
            JLabel label = new JLabel("");
            label.setText(String.valueOf(i+1));
            label.setBackground(SystemColor.activeCaption);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            userCol.add(label);

        }

        //set the user row numbers
        for (int i = 0; i< 10; i++) {
            JLabel label = new JLabel("");
            label.setText(String.valueOf(x++));
            label.setBackground(SystemColor.activeCaption);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            userRow.add(label);

        }

        //add buttons for userPanel
        for (int i = 0; i< 10; i++) {
            for (int j = 0; j < 10; j++) {
                coord button = new coord(i, j);
                userPanel.add(button);
                userBoard[i][j] = button;
                button.setValue(0);
                button.addActionListener(new FindCorrectClick());
            }
        }

        addImagesToUserBoard();

    }

    //------------------------------------------------------------
    //function adds all of the images to the opponent board
    private void addImagesToUserBoard(){

        //add all water images to opponent board
        for (int i = 0; i< 10; i++){
            for (int j = 0; j< 10; j++){
                try {
                    Image waterImage = ImageIO.read(getClass().getResource("water.gif"));
                    userBoard[i][j].setIcon(new ImageIcon(waterImage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //-----------------------------------------------------------
    //function creates the opponent Side of the board
    private void populateOpponentBoard(JPanel opponentCol, JPanel opponentRow, JPanel opponentPanel){

        char x = 'A';
        //set the opponent col numbers
        for (int i = 0; i< 10; i++) {
            JLabel label = new JLabel("");
            label.setText(String.valueOf(i+1));
            label.setBackground(SystemColor.activeCaption);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            opponentCol.add(label);

        }

        //set the opponent row numbers
        for (int i = 0; i< 10; i++) {
            JLabel label = new JLabel("");
            label.setText(String.valueOf(x++));
            label.setBackground(SystemColor.activeCaption);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            opponentRow.add(label);

        }


        //add buttons for opponentPanel
        for (int i = 0; i< 10; i++) {
            for (int j = 0; j < 10; j++) {
                coord button = new coord(i, j);
                opponentPanel.add(button);
                opponentBoard[i][j] = button;
                button.addActionListener(new FindCorrectClick());
            }
        }

        addImagesToOpponentBoard();
    }

    //------------------------------------------------------------
    //function adds all of the images to the opponent board
    private void addImagesToOpponentBoard(){

        //add all water images to opponent board
        for (int i = 0; i< 10; i++){
            for (int j = 0; j< 10; j++){
                try {
                    Image waterImage = ImageIO.read(getClass().getResource("water.gif"));
                    opponentBoard[i][j].setIcon(new ImageIcon(waterImage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //------------------------------------------------------------
    //function creates the ship buttons
    private void createShipButtons(JPanel holder){
        //Buttons for ships

        JButton banAircraftCarrier = new JButton("Aircraft");
        banAircraftCarrier.setBounds(440, 577, 100, 23);
        banAircraftCarrier.addActionListener(new FindCorrectClick());
        holder.add(banAircraftCarrier);
        shipButtons.add(banAircraftCarrier);

        JButton btnPatrolBoat = new JButton("Patrol");
        btnPatrolBoat.setBounds(440, 642, 100, 23);
        btnPatrolBoat.addActionListener(new FindCorrectClick());
        holder.add(btnPatrolBoat);
        shipButtons.add(btnPatrolBoat);

        JButton btnSubmarine = new JButton("Submarine");
        btnSubmarine.setBounds(440, 707, 100, 23);
        btnSubmarine.addActionListener(new FindCorrectClick());
        holder.add(btnSubmarine);
        shipButtons.add(btnSubmarine);

        JButton btnDestroyer = new JButton("Destroyer");
        btnDestroyer.setBounds(440, 772, 100, 23);
        btnDestroyer.addActionListener(new FindCorrectClick());
        holder.add(btnDestroyer);
        shipButtons.add(btnDestroyer);

        JButton btnBattleship = new JButton("battleShip");
        btnBattleship.setBounds(440, 837, 100, 23);
        btnBattleship.addActionListener(new FindCorrectClick());
        holder.add(btnBattleship);
        shipButtons.add(btnBattleship);
    }

    //------------------------------------------------------------
    //class handles on click listens
    private class FindCorrectClick implements ActionListener {

        //Method will figure out what button was clicked
        public void actionPerformed(ActionEvent e) {


            //go through userBoard and see which button it is
            for (int i = 0; i< 10; i++) {
                for (int j = 0; j< 10; j++) {
                    if (e.getSource() == userBoard[i][j]) {
                        userBoardClickAction(userBoard[i][j]);
                        return;
                    }
                }
            }

            //go through opponent board and see which button it is
            for (int i = 0; i< 10; i++) {
                for (int j = 0; j< 10; j++) {
                    if (e.getSource() == opponentBoard[i][j]) {
                        opponentBoardClickAction(opponentBoard[i][j]);
                        return;
                    }
                }
            }

            //go through the boats and check if one of them was clicked
            for (JButton s : shipButtons){
                if (e.getSource() == s){
                    shipClickAction(s);
                }
            }

            //check rotate button
            if (e.getSource() == rotateButton){
                if (orientation == 'h'){
                    orientation = 'v';
                    orientationLabel.setText("Ship Mode Vertical");
                    updateShipOrientation();
                }
                else{
                    orientation = 'h';
                    orientationLabel.setText("Ship Mode Horizontal");
                    updateShipOrientation();
                }
            }
        }
    }
}
