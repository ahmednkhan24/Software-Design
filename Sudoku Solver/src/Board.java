// Zakee Jabbar (zjabba2)
// Azam Jamal (ajamal6)
// Ahmed Khan (akhan227)
// CS 342
// Project 3



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

// Internal board for the game
// Correlates to the graphics board
public class Board {
    private int[][] board;
    private ArrayList<Integer>[][] candidateList;
    private ArrayList<Integer>[] rowValues;
    private ArrayList<Integer>[] columnValues;
    private ArrayList<Integer>[] blockValues;
    private static final int SIZE = 9;

    // Constructor
    @SuppressWarnings("unchecked")
    public Board() {
        candidateList = new ArrayList[9][9];
        rowValues = new ArrayList[9];
        columnValues = new ArrayList[9];
        blockValues = new ArrayList[9];
        for (int i = 0; i < 9; i++) {
            rowValues[i] = new ArrayList<>();
            columnValues[i] = new ArrayList<>();
            blockValues[i] = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                candidateList[i][j] = initializeCand();
            }
        }
        board = new int[9][9];
    }

    // Sets a value at a specific position
    public void setPosValue(int xPos, int yPos, int value) {
        board[xPos][yPos] = value;

    }

    // Returns the value of a position
    public int getPosValue(int xPos, int yPos) {
        return board[xPos][yPos];

    }

    // Return the position of a specific value
    public int[] getPos(int value) {
        int coord[] = new int[2];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (board[i][j] == value) {
                    coord[0] = i;
                    coord[1] = j;
                    return coord;
                }
        }
        return coord;
    }

    // Returns the whole board
    public int[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println(" ");

        }
        System.out.println(" ");

    }

    // Checks if game winner
    public boolean isWinner() {
        boolean winner = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    winner = false;
                }

            }
        }
        return winner;
    }

    public void updateBoard(int arr[][]) {
        board = arr;
    }

    public void resetBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }
    }

    // Initialize candidate list
    public ArrayList<Integer> initializeCand() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            temp.add(i);
        }
        return temp;
    }

    public String candToString(int xPos, int yPos) {
        String temp = " ";
        for (int i = 0; i < candidateList[xPos][yPos].size(); i++) {
            if (i == 0) {
                temp += candidateList[xPos][yPos].get(i);
            } else {
                temp += ", " + candidateList[xPos][yPos].get(i);
            }
        }
        return temp;
    }


    // Candidate List functions
    public ArrayList<Integer> removeValuesForRow(int row) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int col;
        for (col = 0; col < 9; col++)
            list.add(board[row][col]);
        return list;
    }


    public ArrayList<Integer> removeValuesForColumn(int col) {
        ArrayList<Integer> list = new ArrayList<>();
        int row;
        for (row = 0; row < 9; row++)
            list.add(board[row][col]);
        return list;
    }

    public void removeValues() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int blockIndex = getIndex(i, j);
                if(board[i][j] == 0)
                {
                    candidateList[i][j].removeAll(rowValues[i]);
                    candidateList[i][j].removeAll(columnValues[j]);
                    candidateList[i][j].removeAll(blockValues[blockIndex]);
                }
                else
                {
                    candidateList[i][j].clear();
                }

            }
        }
    }

    public void fillRowAndCols() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    rowValues[i].add(board[i][j]);
                    columnValues[j].add(board[i][j]);
                }
            }
        }

    }

    public int getIndex(int xPos, int yPos) {
        if (xPos >= 0 && xPos <= 2) // First row of subgrids
        {
            if (yPos >= 0 && yPos <= 2) // First column of subgrids
            {
                return 0;
            }
            else if (yPos >= 3 && yPos <= 5) // Second column of subgrids
            {
                return 1;
            }
            else // Third column of subgrids
            {
                return 2;
            }
        }
        else if (xPos >= 3 && xPos <= 5) // Second row of subgrids
        {
            if (yPos >= 0 && yPos <= 2) // First column of subgrids
            {
                return 3;
            }
            else if (yPos >= 3 && yPos <= 5) // Second column of subgrids
            {
                return 4;
            }
            else // Third column of subgrids
            {
                return 5;
            }
        }
        else // Third row of subgrids
        {
            if (yPos >= 0 && yPos <= 2) // First column of subgrids
            {
                return 6;
            }
            else if (yPos >= 3 && yPos <= 5) // Second column of subgrids
            {
                return 7;
            }
            else // Third column of subgrids
            {
                return 8;
            }

        }
    }

    public Coord getBoxX(int xPos)
    {
        if (xPos >= 0 && xPos <= 2) // First row of subgrids
        {
            Coord x = new Coord(0, 3);
            return x;
        }
        else if (xPos >= 3 && xPos <= 5) // Second row of subgrids
        {
            Coord x = new Coord(3, 6);
            return x;
        }
        else // Third row of subgrids
        {
            Coord x = new Coord(6, 9);
            return x;
        }
    }

    public Coord getBoxY(int yPos)
    {
        if (yPos >= 0 && yPos <= 2) // First column of subgrids
        {
            Coord y = new Coord(0, 3);
            return y;
        }
        else if (yPos >= 3 && yPos <= 5) // Second column of subgrids
        {
            Coord y = new Coord(3, 6);
            return y;
        }
        else // Third column of subgrids
        {
            Coord y = new Coord(6, 9);
            return y;
        }
    }


    public ArrayList<Integer> removeValuesForBlocks(int xStart, int xStop, int yStart, int yStop) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = xStart; i < xStop; i++) {
            for (int j = yStart; j < yStop; j++) {
                if (board[i][j] != 0) {
                    list.add(board[i][j]);
                }

            }
        }
        return list;
    }

    public void fillBlocks() {
        blockValues[0] = removeValuesForBlocks(0, 3, 0, 3);
        blockValues[1] = removeValuesForBlocks(0, 3, 3, 6);
        blockValues[2] = removeValuesForBlocks(0, 3, 6, 9);
        blockValues[3] = removeValuesForBlocks(3, 6, 0, 3);
        blockValues[4] = removeValuesForBlocks(3, 6, 3, 6);
        blockValues[5] = removeValuesForBlocks(3, 6, 6, 9);
        blockValues[6] = removeValuesForBlocks(6, 9, 0, 3);
        blockValues[7] = removeValuesForBlocks(6, 9, 3, 6);
        blockValues[8] = removeValuesForBlocks(6, 9, 6, 9);
    }

    public void initCandidateLists() {
        for (int i = 0; i < 9; i++) {
            rowValues[i].clear();
            columnValues[i].clear();
            blockValues[i].clear();
            for (int j = 0; j < 9; j++) {
                candidateList[i][j] = initializeCand();
            }
        }
        fillBlocks();
        fillRowAndCols();
        removeValues();
    }

    public void updateCandidateListsRemove(int xPos, int yPos, int value)
    {
        for(int i = 0; i < 9; i++)
        {
            if(board[xPos][i] == 0)
            {
                candidateList[xPos][i].removeAll(Arrays.asList(value));
            }
            else
            {
                candidateList[xPos][i].clear();
            }
            if(board[i][yPos] == 0)
            {
                candidateList[i][yPos].removeAll(Arrays.asList(value));
            }
            else
            {
                candidateList[i][yPos].clear();
            }
        }
        Coord x = getBoxX(xPos);
        Coord y = getBoxY(yPos);

        int xStart = x.getxPos();
        int xStop = x.getyPos();
        int yStart = y.getxPos();
        int yStop = y.getyPos();

        for(int i = xStart; i < xStop; i++)
        {
            for(int j = yStart; j < yStop; j++)
            {
                if(board[i][j] == 0)
                {
                    candidateList[i][j].removeAll(Arrays.asList(value));
                }
                else
                {
                    candidateList[i][j].clear();
                }
            }
        }
    }

    public ArrayList<Integer> getCandidateList(int xPos, int yPos)
    {
        return candidateList[xPos][yPos];
    }

    public boolean inCandidateList(int xPos, int yPos, int value)
    {
        if(xPos > 8 || yPos > 8)
        {
            return false;
        }
        return candidateList[xPos][yPos].contains(value);

    }

    public boolean isEmpty(int xPos, int yPos)
    {
        if(xPos > 8 || yPos > 8)
        {
            return false;
        }
        if(board[xPos][yPos] == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    // Start Naked Pairs
    private ArrayList<Integer> howManyCandListsSize2ROW(int row)
    {
        ArrayList<Integer> sizeTwo = new ArrayList<Integer>();

        // find each column index whose candidate list is of size 2
        for (int i = 0; i < 9; i++)
        {
            if (candidateList[row][i].size() == 2)
            {
                sizeTwo.add(i);
            }
        }
        return sizeTwo;
    }

    private ArrayList<Coord> howManyCandListsSize2Box(int xStart, int xStop, int yStart, int yStop)
    {
        ArrayList<Coord> sizeTwo = new ArrayList<>();

        // find each column index whose candidate list is of size 2
        for (int i = xStart; i < xStop; i++)
        {
            for(int j = yStart; j < yStop; j++)
            {
                if (candidateList[i][j].size() == 2)
                {
                    Coord c = new Coord(i, j);
                    sizeTwo.add(c);
                }
            }

        }
        return sizeTwo;
    }

    private ArrayList<Integer> howManyCandListsSize2Col(int col)
    {
        ArrayList<Integer> sizeTwo = new ArrayList<Integer>();

        // find each column index whose candidate list is of size 2
        for (int i = 0; i < 9; i++)
        {
            if (candidateList[i][col].size() == 2)
            {
                sizeTwo.add(i);
            }
        }
        return sizeTwo;
    }



    private ArrayList<Integer> findNakedPairROW(ArrayList<Integer> x, int row)
    {
        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < x.size(); i++)
        {
            for (int j = i+1; j < x.size(); j++)
            {
                if (candidateList[row][x.get(i)].equals(candidateList[row][x.get(j)]))
                {
                    index1 = x.get(i);
                    index2 = x.get(j);
                    break;
                }
            }
        }

        ArrayList<Integer> L = new ArrayList<>();
        L.add(index1);
        L.add(index2);
        return L;
    }

    private ArrayList<Coord> findNakedPairBox(ArrayList<Coord> x)
    {
        Coord c1 = new Coord(-1, -1);
        Coord c2 = new Coord(-1 , -1);

        for (int i = 0; i < x.size(); i++)
        {
            for (int j = i+1; j < x.size(); j++)
            {
                int x1 =  x.get(i).getxPos();
                int y1 = x.get(i).getyPos();
                int x2 = x.get(j).getxPos();
                int y2 = x.get(j).getyPos();

                if (candidateList[x1][y1].equals(candidateList[x2][y2]))
                {
                    c1 = x.get(i);
                    c2 = x.get(j);
                    break;
                }
            }
        }

        ArrayList<Coord> L = new ArrayList<>();
        L.add(c1);
        L.add(c2);
        return L;
    }

    private ArrayList<Integer> findNakedPairCol(ArrayList<Integer> x, int col)
    {
        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < x.size(); i++)
        {
            for (int j = i+1; j < x.size(); j++)
            {
                if (candidateList[x.get(i)][col].equals(candidateList[x.get(j)][col]))
                {
                    index1 = x.get(i);
                    index2 = x.get(j);
                    break;
                }
            }
        }

        ArrayList<Integer> L = new ArrayList<>();
        L.add(index1);
        L.add(index2);
        return L;
    }

    public boolean nakedPairRow(int row)
    {

        int col1 = -1;
        int col2 = -1;

        // any column index in this row whose candidate list is of size 2 is stored here
        ArrayList<Integer> columnIndex = howManyCandListsSize2ROW(row);

        if (columnIndex.size() >= 2)
        {	// open if
            columnIndex = findNakedPairROW(columnIndex, row);

            col1 = columnIndex.get(0);
            col2 = columnIndex.get(1);

            if(col1 == -1)
            {
                return false;
            }

            for (int i = 0; i < 9; i++)
            {
                if (board[row][i] == 0 && i != col1 && i != col2)
                {
                    candidateList[row][i].removeAll(candidateList[row][col1]);
                }
            }
            return true;

        }	// close if
        else
        {
            return false;
        }
    }

    public boolean nakedPairBox(int xStart, int xStop, int yStart, int yStop)
    {

        Coord c1= new Coord(-1 , - 1);
        Coord c2 = new Coord(-1 , -1);

        // any column index in this row whose candidate list is of size 2 is stored here
        ArrayList<Coord> indices = howManyCandListsSize2Box(xStart, xStop, yStart, yStop);

        if (indices.size() >= 2)
        {	// open if
            indices = findNakedPairBox(indices);

            c1 = indices.get(0);
            c2 = indices.get(1);

            if(c1.getyPos() == -1)
            {
                return false;
            }

            for (int i = xStart; i < xStop; i++)
            {
                for(int j = yStart; j < yStop; j++)
                {
                    Coord temp = new Coord(i, j);
                    if (board[i][j] == 0 && !temp.equals(c1) && !temp.equals(c2))
                    {
                        candidateList[i][j].removeAll(candidateList[c1.getxPos()][c1.getyPos()]);
                    }
                }

            }
            return true;

        }	// close if
        else
        {
            return false;
        }
    }

    public boolean nakedPairCol(int col)
    {

        int row1 = -1;
        int row2 = -1;

        // any column index in this row whose candidate list is of size 2 is stored here
        ArrayList<Integer> rowIndex = howManyCandListsSize2Col(col);

        if (rowIndex.size() >= 2)
        {	// open if
            rowIndex = findNakedPairCol(rowIndex, col);

            row1 = rowIndex.get(0);
            row2 = rowIndex.get(1);

            if(row1 == -1)
            {
                return false;
            }

            for (int i = 0; i < 9; i++)
            {
                if (board[i][col] == 0 && i != row1 && i != row2)
                {
                    candidateList[i][col].removeAll(candidateList[row1][col]);
                }
            }
            return true;

        }// close if
        else
        {
            return false;
        }
    }

    public boolean nakedPair()
    {
        boolean ret1 = false;
        boolean ret2 = false;
        for(int i = 0; i < 9; i++)
        {
            ret1 = nakedPairRow(i);
            if(ret1)
            {
                break;
            }
            ret2 = nakedPairCol(i);
            if(ret2)
            {
                break;
            }
        }

        for(int i = 0; i < 9; i = i + 3)
        {
            for(int j = 0; j < 9; j = j + 3)
            {
                boolean ret3 = nakedPairBox(i, i + 3, j, j + 3);
                if(ret3)
                {
                    return true;
                }
            }
        }
        if(ret1 || ret2)
        {
            return true;
        }
        return false;
    }
    // End naked pair


    // Single algorithm
    public int[] single()
    {
        int coord[] = new int[3];
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(candidateList[i][j].size() == 1)
                {
                    coord[0] = i;
                    coord[1] = j;
                    coord[2] = candidateList[i][j].get(0);
                    return  coord;
                }
            }
        }

        coord[0] = -1;
        return coord;
    }

    // returns an array list of tuple's of each index in a single box
    // that is empty i.e. contains a 0
    // a tuple is an object that has the x and y coordinate of the 0
    private ArrayList<Tuple> findZeros(int[][] board, int row, int col)
    {
        ArrayList<Tuple> zero = new ArrayList<Tuple>();

        for (int i = row; i < row + 3; i++)
        {
            for (int j = col; j < col + 3; j++)
            {
                if (board[i][j] == 0)
                {
                    zero.add(new Tuple(i, j));
                }
            }
        }
        return zero;
    }


    public void lockedCandidate(int[][] board, ArrayList<Integer>[][] list)
    {
        System.out.println("\n");


        @SuppressWarnings("unchecked")
        ArrayList<Tuple>[] boxes = new ArrayList[SIZE];

        int index = 0;

        // creates an array of array lists of each box
        for (int j = 0; j < SIZE; j+=3)
        {
            for (int k = 0; k < SIZE; k+=3)
            {
                boxes[index] = findZeros(board, j, k);
                index++;
            }
        }


        for (int i = 0; i < SIZE; i++)
        {
            // get the list of empty locations in this box
            ArrayList<Tuple> tmp = boxes[i];

            // if there is an empty cell in this box
            if (!tmp.isEmpty())
            {
                //System.out.println("Box " + i);

                // for each empty cell in this box
                for (int j = 0; j < tmp.size(); j++)
                {
                    int ROW = tmp.get(j).row;
                    int COL = tmp.get(j).col;


                    // get the location of each empty cell in this row
                    ArrayList<Tuple> emptyCellLocations = getEmptysRow(board, ROW);

                    if (emptyCellLocations.size() >= 2)
                    {
                        ArrayList<Integer> lsit = getCandListROW(board, ROW);

                        for (int x = 0; x < emptyCellLocations.size(); x++)
                        {
                            if (!(emptyCellLocations.get(x).row == ROW && emptyCellLocations.get(x).col == COL))
                            {

                            }
                        }
                    }


                }
            }

        }



    }



    private ArrayList<Tuple> getEmptysRow(int board[][], int row)
    {
        ArrayList<Tuple> x = new ArrayList<Tuple>();

        for (int i = 0; i < SIZE; i++)
        {
            if (board[row][i] == 0)
            {
                x.add(new Tuple(row, i));
            }
        }
        return x;
    }


    private ArrayList<Integer> getCandListROW(int[][] board, int row)
    {
        ArrayList<Integer> cL = new ArrayList<Integer>();

        for (int i = 0; i < SIZE; i++)
        {
            cL.add(i+1);
        }

        for (int i = 0; i < SIZE; i++)
        {
            if (board[row][i] != 0 && cL.contains(board[row][i]))
            {
                cL.remove(cL.indexOf(board[row][i]));
            }
        }
        return cL;
    }

    // Hidden single code
    public int[] hiddenSingleRow(int row)
    {
        int arr[] = new int[10];

        for(int i = 0; i < 9; i++)
        {
            ArrayList<Integer> temp = candidateList[row][i];

            for(int x : temp)
            {
                arr[x]++;
            }
        }

        for(int i = 1; i < 10; i++)
        {
            if(arr[i] == 1)
            {
                Coord c = hiddenSingleRowHelper(row, i);
                int ret[] = new int[3];
                ret[0] = c.getxPos();
                ret[1] = c.getyPos();
                ret[2] = i;
                return ret;
            }
        }

        return null;
    }

    public int[] hiddenSingleBox(int xStart, int xStop, int yStart, int yStop)
    {
        int arr[] = new int[10];

        for(int i = xStart; i < xStop; i++)
        {
            for(int j = yStart; j < yStop; j++)
            {
                ArrayList<Integer> temp = candidateList[i][j];
                for(int x : temp)
                {
                    arr[x]++;
                }
            }
        }

        for(int i = 1; i < 10; i++)
        {
            if(arr[i] == 1)
            {
                Coord c = hiddenSingleBoxHelper(xStart,xStop,yStart, yStop, i);
                int ret[] = new int[3];
                ret[0] = c.getxPos();
                ret[1] = c.getyPos();
                ret[2] = i;
                return ret;
            }
        }

        return null;
    }

    public int[] hiddenSingleCol(int col)
    {
        int arr[] = new int[10];

        for(int i = 0; i < 9; i++)
        {
            ArrayList<Integer> temp = candidateList[i][col];

            for(int x : temp)
            {
                arr[x]++;
            }
        }

        for(int i = 1; i < 10; i++)
        {
            if(arr[i] == 1)
            {
                Coord c = hiddenSingleColHelper(col, i);
                int ret[] = new int[3];
                ret[0] = c.getxPos();
                ret[1] = c.getyPos();
                ret[2] = i;
                return ret;
            }
        }

        return null;
    }

    public Coord hiddenSingleRowHelper(int row, int value)
    {
        for(int i = 0; i < 9; i++)
        {
            if(candidateList[row][i].contains(value))
            {
                Coord temp = new Coord(row, i);
                return temp;
            }
        }
        return null;
    }

    public Coord hiddenSingleColHelper(int col, int value)
    {
        for(int i = 0; i < 9; i++)
        {
            if(candidateList[i][col].contains(value))
            {
                Coord temp = new Coord(i, col);
                return temp;
            }
        }
        return null;
    }

    public Coord hiddenSingleBoxHelper(int xStart, int xStop, int yStart, int yStop, int value)
    {
        for(int i = xStart; i < xStop; i++)
        {
            for(int j = yStart; j < yStop; j++)
            {
                if(candidateList[i][j].contains(value))
                {
                    Coord temp = new Coord(i, j);
                    return temp;
                }
            }
        }
        return null;
    }

    public int[] hiddenSingle()
    {
        for(int i = 0; i < 9; i++)
        {
            int rowAns[] = hiddenSingleRow(i);
            if(rowAns != null)
            {
                return rowAns;
            }
            int colAns[] = hiddenSingleCol(i);
            if(colAns != null)
            {
                return colAns;
            }
        }

        for(int i = 0; i < 9; i = i + 3)
        {
            for (int j = 0; j < 9; j = j + 3)
            {
                int boxAns[] = hiddenSingleBox(i, i + 3, j, j + 3);
                if (boxAns != null) {
                    return boxAns;
                }
            }
        }
        return null;
    }








}
