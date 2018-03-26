// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3


import javax.swing.*;
import java.awt.*;

// Makes the side panel for the program
public class SidePanel extends JPanel
{
    private JButton buttons[];

    // Constructor
    public SidePanel()
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
        setBackground(Color.BLACK);
        buttons = new JButton[11];

        for(int i = 0; i < 9; i++)
        {
            buttons[i] = new JButton("" +  (i + 1));
            buttons[i].setPreferredSize(new Dimension(42,60));
            buttons[i].setBackground(Color.RED);
            buttons[i].setForeground(Color.BLACK);
            add(buttons[i]);
        }

        buttons[9] = new JButton("X");
        buttons[9].setPreferredSize(new Dimension(42,60));
        buttons[9].setBackground(Color.RED);
        buttons[9].setForeground(Color.BLACK);
        add(buttons[9]);

        buttons[10] = new JButton("?");
        buttons[10].setPreferredSize(new Dimension(42,60));
        buttons[10].setBackground(Color.RED);
        buttons[10].setForeground(Color.BLACK);
        add(buttons[10]);
    }

    // Add action listeners to all the buttons
    public void addListener(SidePanelListener panelListener)
    {
        for(int i = 0; i < 11; i++)
        {
            buttons[i].addActionListener(panelListener);
        }
    }

    // Reset the previous selected state
    public void resetPrevious(int value)
    {
        buttons[value - 1].setBackground(Color.RED);
    }


}
