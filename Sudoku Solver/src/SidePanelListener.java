// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Action listener for the side panel
public class SidePanelListener implements ActionListener {
    private Board board;
    private GraphicsBoard graphicsBoard;

    // Constructor
    public SidePanelListener(Board b, GraphicsBoard gB)
    {
        board = b;
        graphicsBoard = gB;
    }

    // Does the actions for a click
    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton) event.getSource();
        if(clicked.getText().equals("X")) // Set clear mode
        {
            if(graphicsBoard.getCurValue() != 0)
            {
                graphicsBoard.resetCurrentMode(graphicsBoard.getCurValue());
            }
            graphicsBoard.toggeCandidateLabel(false);
            clicked.setBackground(Color.blue);
            graphicsBoard.setCandidateMode(false);
            graphicsBoard.setValMode(false);
            graphicsBoard.setClearMode(true);
            graphicsBoard.setCurValue(10);
        }
        else if(clicked.getText().equals("?")) // Set candidate mode
        {
            if(graphicsBoard.getCurValue() != 0)
            {
                graphicsBoard.resetCurrentMode(graphicsBoard.getCurValue());
            }
            graphicsBoard.toggeCandidateLabel(true);
            clicked.setBackground(Color.blue);
            graphicsBoard.setCandidateMode(true);
            graphicsBoard.setValMode(false);
            graphicsBoard.setClearMode(false);
            graphicsBoard.setCurValue(11);

        }
        else
        {
            if(graphicsBoard.getCurValue() != 0)  // Set value mode
            {
                graphicsBoard.resetCurrentMode(graphicsBoard.getCurValue());
            }
            clicked.setBackground(Color.blue);
            graphicsBoard.toggeCandidateLabel(false);
            int clickedVal = Integer.parseInt(clicked.getText());
            graphicsBoard.setCandidateMode(false);
            graphicsBoard.setValMode(true);
            graphicsBoard.setClearMode(false);
            graphicsBoard.setCurValue(clickedVal);

        }

    }
}