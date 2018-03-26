// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Handles the user clicks on the grid
public class GridListener implements ActionListener
{
    private Board board;
    private GraphicsBoard graphicsBoard;

    // Constructor
    public GridListener(Board b, GraphicsBoard gB)
    {
        board = b;
        graphicsBoard = gB;
    }

    // Does the actions for a click
    public void actionPerformed(ActionEvent event)
    {
        MyJButton clicked = (MyJButton) event.getSource();
        int xPos = clicked.getxPos();
        int yPos = clicked.getyPos();
        if(graphicsBoard.isClearMode()) // Clear mode
        {
            if(clicked.isRemovable())
            {
                int currentVal = board.getPosValue(xPos, yPos);
                clicked.setText("");
                board.setPosValue(xPos, yPos,0);
                if(currentVal != 0)
                {
                    board.initCandidateLists();
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null, "This value cannot be cleared!");
            }
        }
        if(graphicsBoard.isValMode()) // Value Mode
        {
            if(clicked.isRemovable())
            {
                if(graphicsBoard.isCheckFillMode())
                {
                    ArrayList<Integer> temp = board.getCandidateList(xPos, yPos);
                    if(temp.contains(graphicsBoard.getCurValue()))
                    {
                        clicked.setText("" + graphicsBoard.getCurValue());
                        board.setPosValue(xPos, yPos, graphicsBoard.getCurValue());
                        board.updateCandidateListsRemove(xPos, yPos, graphicsBoard.getCurValue());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "This value cannot be placed here.\n" +
                                "Not in candidate list!");
                    }

                }
                else
                {
                    clicked.setText("" + graphicsBoard.getCurValue());
                    board.setPosValue(xPos, yPos, graphicsBoard.getCurValue());
                    board.updateCandidateListsRemove(xPos, yPos, graphicsBoard.getCurValue());
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"This value cannot be changed!");
            }
        }
        if(graphicsBoard.isCandidateMode()) // Candidate Mode
        {
            String text = "Candidate List (" + Integer.toString(xPos + 1) + ", "
                            + Integer.toString(yPos + 1) + ") : ";
            text += board.candToString(xPos, yPos);
            graphicsBoard.setCandidateLabel(text);
        }
        if(board.isWinner())
        {
            JOptionPane.showMessageDialog(null, "Congratulations you finished the puzzle!");
        }
    }

}
