/*
    Server class: Handles the server side of the connection. The server allows multiple users to connect to one central server.
                  The server receives an encrypted message and sends to the users for the user to decrypt.
 */

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class server extends JFrame {

    // GUI items
    JButton ssButton;
    JLabel machineInfo;
    JLabel portInfo;
    JTextArea history;
    private boolean running;

    // Network Items
    boolean serverContinue;
    ServerSocket serverSocket;
    Vector <PrintWriter> outStreamList;
    Vector <String> names;  //names of people in the program


    // class constructor
    public server()
    {
        super( "Echo Server" );

        // set up the shared outStreamList and names vector
        outStreamList = new Vector<PrintWriter>();
        names = new Vector<String>();

        // get content pane and set its layout
        Container container = getContentPane();
        container.setLayout( new FlowLayout() );

        // create buttons
        running = false;
        ssButton = new JButton( "Start Listening" );
        ssButton.addActionListener( e -> doButton (e) );
        container.add( ssButton );

        // get the IP address
        String machineAddress = null;
        try
        {
            InetAddress addr = InetAddress.getLocalHost();
            machineAddress = addr.getHostAddress();
        }
        catch (UnknownHostException e)
        {
            machineAddress = "127.0.0.1";
        }
        // display the IP address to the user
        machineInfo = new JLabel (machineAddress);
        container.add( machineInfo );
        portInfo = new JLabel (" Not Listening ");
        container.add( portInfo );

        // text area that will contain all encrypted messages that
        // the server receives
        history = new JTextArea ( 10, 40 );
        history.setEditable(false);
        container.add( new JScrollPane(history) );

        setSize( 500, 250 );
        setVisible( true );
    }

    // handle button event
    public void doButton( ActionEvent event )
    {
        // if the server connection has not been established
        // create the connection thread
        if (running == false)
        {
            new ConnectionThread (this);
        }
        else
        {
            serverContinue = false;
            ssButton.setText ("Start Listening");
            portInfo.setText (" Not Listening ");
        }
    }
}


// class to handle the connection
class ConnectionThread extends Thread
{
    // keep track of the gui
    server gui;

    // class constructor
    public ConnectionThread (server es3)
    {
        gui = es3;
        start();
    }

    // run method to create a server connection
    public void run()
    {
        gui.serverContinue = true;

        try
        {
            // create the socket and add messages
            gui.serverSocket = new ServerSocket(0);
            gui.portInfo.setText("Listening on Port: " + gui.serverSocket.getLocalPort());
            System.out.println ("Connection Socket Created");
            try {
                // while loop that waits until a connection is established with a client
                while (gui.serverContinue)
                {
                    System.out.println ("Waiting for Connection");
                    gui.ssButton.setText("Stop Listening");
                    new CommunicationThread (gui.serverSocket.accept(), gui, gui.outStreamList, gui.names);
                }
            }
            catch (IOException e)
            {
                // if the accpetion has failed
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        catch (IOException e)
        {
            // if the port is unsuccessful
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        }
        finally
        {
            // when the program is exited, close the connection
            try {
                gui.serverSocket.close();
            }
            catch (IOException e)
            {
                System.err.println("Could not close port: 10008.");
                System.exit(1);
            }
        }
    }
}


// class to handle the communication between clients and server
class CommunicationThread extends Thread
{
    // variables
    private Socket clientSocket;
    private server gui;
    private Vector<PrintWriter> outStreamList;
    private Vector<String> names;
    PrintWriter out;

    // class constructor
    public CommunicationThread (Socket clientSoc, server ec3, Vector<PrintWriter> oSL, Vector<String> n)
    {
        clientSocket = clientSoc;
        gui = ec3;
        outStreamList = oSL;
        names = n;

        BufferedReader in;
        String inputLine;

        //read in the clients username
        try {
            // receive the client input stream
            in = new BufferedReader(
                    new InputStreamReader( clientSocket.getInputStream()));
            inputLine = in.readLine();

            // create the way to send data to the client(s)
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);

            outStreamList.add(out);
            names.add(inputLine);

            // display the names of any user that has connected to the server
            gui.history.insert (inputLine + " :Connected\n", 0);

            //send list of people to client side
            for ( PrintWriter out1: outStreamList ) {
                String name = combineNames();
                //out1.println (name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // begin transferring of messages
        start();
    }

    // run method
    public void run()
    {
        System.out.println ("New Communication Thread Started");

        try {
            // create the input stream to receive messages from the client
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( clientSocket.getInputStream()));


            String inputLine;
            String pubKey;
            String encrypt;

            // receive the actual messages from the client
            while ((inputLine = in.readLine()) != null)
            {
                pubKey = in.readLine();
                encrypt = in.readLine();

                System.out.println ("Server: " + encrypt);
                gui.history.insert (encrypt+"\n", 0);

                // send the messages to the desired client(s)
                for ( PrintWriter out1: outStreamList )
                {
                    System.out.println ("Sending Message");
                    out1.println (inputLine);
                    out1.println(pubKey);
                }

                // if the user wants to leave the chat
                if (inputLine.equals("Bye."))
                    break;

                // if the users want to close the connection
                if (inputLine.equals("End Server."))
                    gui.serverContinue = false;
            }

            // close all streams and sockets
            outStreamList.remove(out);
            out.close();
            in.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("Problem with Communication Server");
            //System.exit(1);
        }
    }

    // Function that gets all the names and
    // combines them into a string in order to
    // send to the clients
    private String combineNames(){
        String name = null;
        for (String n : names) {
            if (n != null){
                name += n + " ";
            }
        }
        return name;
    }
}