import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CLIENT implements ActionListener
{
    // GUI items
    JButton connectButton;
    JTextField machineInfo;
    JTextField portInfo;

    // Network Items
    boolean connected;
    Socket echoSocket;
    PrintWriter out;
    BufferedReader in;

    private JPanel mainGame;
    private JPanel container;
    private JPanel serverPage;

    // set up GUI
    public CLIENT(JPanel c, JPanel gameHolder, JPanel gameServerPage)
    {
        this.container = c;
        this.mainGame = gameHolder;
        this.serverPage = gameServerPage;

        // get content pane and set its layout
        container.setLayout (new FlowLayout());

        // set up the North panel
        JPanel upperPanel = new JPanel ();
        upperPanel.setLayout (new GridLayout (4,2));
        container.add(upperPanel);

        // create buttons
        connected = false;


        upperPanel.add ( new JLabel ("Server Address: ", JLabel.LEFT) );
        machineInfo = new JTextField ("127.0.0.1");
        upperPanel.add( machineInfo );


        upperPanel.add ( new JLabel ("Server Port: ", JLabel.LEFT) );
        portInfo = new JTextField ("");
        upperPanel.add( portInfo );

        connectButton = new JButton( "Connect to Server" );
        connectButton.addActionListener( this );
        upperPanel.add( connectButton );

    } // end CountDown constructor

    // handle button event
    public void actionPerformed( ActionEvent event )
    {
        if (connected)
        {
            //doSendMessage();
        }
        else if (event.getSource() == connectButton)
        {
            doManageConnection();
        }
    }

    public void doManageConnection()
    {
        if (connected == false)
        {
            String machineName = null;
            int portNum = -1;
            try {
                machineName = machineInfo.getText();
                portNum = Integer.parseInt(portInfo.getText());
                echoSocket = new Socket(machineName, portNum );
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(
                        echoSocket.getInputStream()));

                connected = true;
                connectButton.setText("Disconnect from Server");

                //we are connected to the server.  now start the game
                serverPage.setVisible(false);
                mainGame.setVisible(true);

            } catch (NumberFormatException e) {
                System.out.println("Server Port must be an integer\n");
            } catch (UnknownHostException e) {
                System.out.println("Don't know about host: " + machineName);
            } catch (IOException e) {
                System.out.println("Couldn't get I/O for " + "the connection to: " + machineName);
            }

        }
        else
        {
            try
            {
                out.close();
                in.close();
                echoSocket.close();

                connected = false;
                connectButton.setText("Connect to Server");
            }
            catch (IOException e)
            {
                System.out.println("Error in closing down Socket ");
            }
        }


    }

} // end class EchoServer3





