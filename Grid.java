package blockPuzzle;

import java.util.ArrayList;

public class Grid {
    final int SIZE;
    final int SUBGRID_SIZE;
    final int blockSize;
    final int THICKNESS_OUTER;
    final int THICKNESS_INNER;
    final int[][] gridArray;


    public Grid(int SIZE, int SUBGRID_SIZE, int blockSize, int THICKNESS_OUTER, int THICKNESS_INNER) {
        this.SIZE = SIZE;
        this.SUBGRID_SIZE = SUBGRID_SIZE;
        this.blockSize = blockSize;
        this.THICKNESS_OUTER = THICKNESS_OUTER;
        this.THICKNESS_INNER = THICKNESS_INNER;
        this.gridArray = new int[SIZE][SIZE];
    }

    public boolean canFitAny(ArrayList<ArrayList<Integer>> piece) {
        int gridRows = gridArray.length;
        int gridCols = gridArray[0].length;

        // Iterate through the whole grid
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                if (canFit(piece, row, col)) {
                    return true;  // Piece can fit at this position
                }
            }
        }

        return false; // Piece cannot fit at any position
    }
    public boolean canFit(ArrayList<ArrayList<Integer>> piece,int row,int col) {
        if(!(row<9 && row >=0 && col <9 && col>=0) )

            return false;
        int gridRows = gridArray.length;
        int gridCols = gridArray[0].length;

        int pieceRows = piece.size();
        int pieceCols = 0;
        for (ArrayList<Integer> rowList : piece) {
            pieceCols = Math.max(pieceCols, rowList.size());
        }


        // Check if the piece can fit within the grid boundaries
        if (row + pieceRows > gridRows || col + pieceCols > gridCols) {
            return false;
        }

        // Check if each element of the piece can fit into the corresponding position in the grid
        for (int i = 0; i < pieceRows; i++) {
            for (int j = 0; j < piece.get(i).size(); j++) {
                if (piece.get(i).get(j) == 1 && gridArray[row + i][col + j] == 1 || gridArray[row + i][col + j] == 4) {
                    return false; // Collision, the piece can't fit
                }
            }
        }

        return true;
    }

    public void clearTrailingPieces(){
        for (int i = 0; i < gridArray.length; i++) {
            for (int j = 0; j < gridArray[i].length; j++) {
                if(gridArray[i][j] == 2)
                    gridArray[i][j] = 0;
                else if(gridArray[i][j] == 4)
                    gridArray[i][j] = 1;
                else if(gridArray[i][j] == 3)
                    gridArray[i][j] = 2;
            }
        }
    }
    public int clearIfFilled() {
        int score = 0;
        ArrayList<Integer> clearingColumns = new ArrayList<>();
        ArrayList<Integer> clearingRows = new ArrayList<>();
        ArrayList<int[]> clearingSubgrids = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            // Check and handle rows
            if (isFilledWithOnes(gridArray[i])) {
                clearingRows.add(i);
            }

            // Check and handle columns
            if (isFilledWithOnes(getColumn(i))) {
                clearingColumns.add(i);
            }
        }

        // Check and handle subgrids
        for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
            for (int j = 0; j < SIZE; j += SUBGRID_SIZE) {
                if (isFilledWithOnes(getSubgrid(i, j))) {
                    clearSubgrid(i, j);
                    clearingSubgrids.add(new int[]{i,j});
                }
            }
        }
        for(int i: clearingColumns ){
            clearColumn(i);
            score+=10;
        }
        for(int i: clearingRows){
            clearColumn(i);
            score+=10;
        }
        for (int[] nums: clearingSubgrids) {
            clearSubgrid(nums[0],nums[1]);
            score+=10;
        }
        clearTrailingPieces();
        return score;
    }

    public void highlightIfPoppable() {

        for (int i = 0; i < SIZE; i++) {
            // Check and handle rows
            if (isFilledWithMixed(gridArray[i])) {
                highlightRow(i);
            }

            if (isFilledWithMixed(getColumn(i))) {
                highlightColumn(i);
            }
        }

        // Check and handle subgrids
        for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
            for (int j = 0; j < SIZE; j += SUBGRID_SIZE) {
               if (isFilledWithMixed(getSubgrid(i, j))) {
                    highlightSubgrid(i, j);
               }
            }
        }

    }

    private boolean isFilledWithMixed(int[] array) {
        for (int value : array) {
            if (value != 1 && value != 2) {
                return false;
            }
        }
        return true;
    }
    private void changeRowValue(int rowIndex,int[] checkInts,int[] changeInts){
        if(checkInts.length!=changeInts.length){
            System.out.println("Error checkInts and changeInts not equal");
        }
        for (int i = 0; i < SIZE; i++) {
            for(int j =0;j<checkInts.length;j++){
                if(gridArray[rowIndex][i]== checkInts[j]){
                    gridArray[rowIndex][i] = changeInts[j];
                }
            }

        }
    }
    private void highlightRow(int rowIndex) {
        int [] checkInts = {1,2};
        int [] changeInts = {4,3};
        changeRowValue(rowIndex,checkInts,changeInts);
    }


    private void highlightColumn(int columnIndex) {
        for (int i = 0; i < SIZE; i++) {
            if(gridArray[i][columnIndex]== 1){
                gridArray[i][columnIndex]=4;
            }else {
                gridArray[i][columnIndex] = 3; // Highlighted
            }
        }
    }


    private void highlightSubgrid(int startRow, int startColumn) {
        for (int i = startRow; i < startRow + SUBGRID_SIZE; i++) {
            for (int j = startColumn; j < startColumn + SUBGRID_SIZE; j++) {
                if(gridArray[i][j] ==1){
                    gridArray[i][j] = 4;
                }else {
                    gridArray[i][j] = 3; // Highlighted
                }
            }
        }
    }



    private boolean isFilledWithOnes(int[] array) {
        for (int value : array) {
            if (value != 1) {
                return false;
            }
        }
        return true;
    }

    private int[] getColumn(int columnIndex) {
        int[] column = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            column[i] = gridArray[i][columnIndex];
        }
        return column;
    }

    private int[] getSubgrid(int startRow, int startColumn) {
        int[] subgrid = new int[SIZE];
        int index = 0;
        for (int i = startRow; i < startRow + SUBGRID_SIZE; i++) {
            for (int j = startColumn; j < startColumn + SUBGRID_SIZE; j++) {
                subgrid[index++] = gridArray[i][j];
            }
        }
        return subgrid;
    }

    private void clearRow(int rowIndex) {
        for (int i = 0; i < SIZE; i++) {
            gridArray[rowIndex][i] = 0;
        }
    }

    private void clearColumn(int columnIndex) {
        for (int i = 0; i < SIZE; i++) {
            gridArray[i][columnIndex] = 0;
        }
    }

    private void clearSubgrid(int startRow, int startColumn) {
        for (int i = startRow; i < startRow + SUBGRID_SIZE; i++) {
            for (int j = startColumn; j < startColumn + SUBGRID_SIZE; j++) {
                gridArray[i][j] = 0;
            }
        }
    }
}

