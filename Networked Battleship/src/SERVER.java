import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.security.KeyPair;
import javax.swing.*;

public class SERVER implements ActionListener{

    // GUI items
    JButton ssButton;
    JLabel machineInfo;
    JLabel portInfo;
    private boolean running;

    // Network Items
    boolean serverContinue;
    ServerSocket serverSocket;

    //variables for containers
    private JPanel gameGUI;
    private JPanel serverGUI;

    // set up GUI
    public SERVER(JPanel container, JPanel gameContainer, JPanel serverContainer)
    {
        gameGUI = gameContainer;
        serverGUI = serverContainer;

        // get content pane and set its layout
        container.setLayout( new FlowLayout() );

        // create buttons
        running = false;
        ssButton = new JButton( "Start Listening" );
        ssButton.addActionListener( this );
        container.add( ssButton );

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
        machineInfo = new JLabel (machineAddress);
        container.add( machineInfo );
        portInfo = new JLabel (" Not Listening ");
        container.add( portInfo );

    } // end CountDown constructor


    // handle button event
    public void actionPerformed( ActionEvent event )
    {
        if (running == false)
        {
            new ConnectionThread (this, gameGUI, serverGUI);
        }
        else
        {
            serverContinue = false;
            ssButton.setText ("Start Listening");
            portInfo.setText (" Not Listening ");
        }
    }

} // end class EchoServer3



// class
class ConnectionThread extends Thread
{
    SERVER gui;
    private JPanel gameGUI;
    private JPanel serverGUI;



    public ConnectionThread (SERVER es3, JPanel gameGUI, JPanel serverGUI)
    {
        //set up the main GUI vairables
        this.gameGUI = gameGUI;
        this.serverGUI = serverGUI;


        gui = es3;
        start();
    }

    public void run()
    {
        gui.serverContinue = true;

        try
        {
            gui.serverSocket = new ServerSocket(0);
            gui.portInfo.setText("Listening on Port: " + gui.serverSocket.getLocalPort());
            System.out.println ("Connection Socket Created");
            try {
                while (gui.serverContinue)
                {
                    System.out.println ("Waiting for Connection");
                    gui.ssButton.setText("Stop Listening");
                    new CommunicationThread(gui.serverSocket.accept(), gui, gameGUI, serverGUI);
                }
            }
            catch (IOException e)
            {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        }
        finally
        {
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


// class
class CommunicationThread extends Thread
{
    //private boolean serverContinue = true;
    private Socket clientSocket;
    private SERVER gui;

    private JPanel gameGUI;
    private JPanel serverGUI;


    public CommunicationThread (Socket clientSoc, SERVER ec3, JPanel gameGUI, JPanel serverGUI)
    {
        //variables for controlling main frame gui
        this.gameGUI = gameGUI;
        this.serverGUI = serverGUI;

        clientSocket = clientSoc;
        gui = ec3;
        System.out.println("Comminucating with Port" + clientSocket.getLocalPort()+"\n");
        start();
    }


    public void run()
    {
        System.out.println ("New Communication Thread Started");

        //we have a sucessfull connection go to game
        serverGUI.setVisible(false);
        gameGUI.setVisible(true);

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println ("Server: " + inputLine);
                System.out.println(inputLine+"\n");
                out.println(inputLine);

                if (inputLine.equals("Bye."))
                    break;

                if (inputLine.equals("End Server."))
                    gui.serverContinue = false;
            }

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




}






