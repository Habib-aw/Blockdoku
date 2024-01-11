package blockPuzzle;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Piece {
    private int offSetX, offSetY;

    private final int blockSize;
    private final int startX;
    private final int startY;
    int gap ;
    boolean dragging;
    final ArrayList<ArrayList<Integer>> pieceData;

    final ArrayList<ArrayList<Block>> blocks = new ArrayList<>();
    public Piece(int startX,int startY,int blockSize,int maxBlocks,int initialGap){
        gap = initialGap;
        this.startX = startX;
        this.startY = startY;
        this.blockSize = blockSize;
        pieceData = generateRandomPiece(maxBlocks);
        for(int i =0;i<pieceData.size();i++){
            blocks.add(new ArrayList<>());
            for(int j =0;j<pieceData.get(i).size();j++){
                blocks.get(i).add(new Block(startX+j*(blockSize + gap),startY+i*(blockSize + gap)));
            }
        }
    }
    public boolean checkPiecePressed(MouseEvent e){
        boolean canBeDragged = false;
        for (int i = 0; i < blocks.size(); i++) {
            for(int j =0;j<blocks.get(i).size();j++){
                if (e.getX() >= blocks.get(i).get(j).getX() && e.getX() <= blocks.get(i).get(j).getX() + blockSize &&
                        e.getY() >= blocks.get(i).get(j).getY() && e.getY() <= blocks.get(i).get(j).getY() + blockSize) {
                    offSetX = e.getX() - blocks.get(0).get(0).getX();
                    offSetY = e.getY() - blocks.get(0).get(0).getY();
                    canBeDragged = true;
                }
            }
        }
        return canBeDragged;
    }
    public void movePiece(MouseEvent e,Grid grid){
        if (dragging) {
            blocks.get(0).get(0).setX(e.getX()-offSetX);
            blocks.get(0).get(0).setY(e.getY()-offSetY);
            int blockX = blocks.get(0).get(0).getX()/blockSize;
            int blockY = blocks.get(0).get(0).getY()/blockSize;
            grid.clearTrailingPieces();
            for(int i =0;i<blocks.size();i++){
                for(int j =0;j<blocks.get(i).size();j++){
                    blocks.get(i).get(j).setX( blocks.get(0).get(0).getX()+j*(blockSize+gap));
                    blocks.get(i).get(j).setY( blocks.get(0).get(0).getY()+i*(blockSize+gap));
                    if(grid.canFit(pieceData,blockY,blockX)){
                        if(grid.gridArray[blockY+i][blockX+j] != 3)
                        grid.gridArray[blockY+i][blockX+j]=2;
                    }
                }
            }
            grid.highlightIfPoppable();


        }
    }
    public void resetBlocks(){
        blocks.get(0).get(0).setX(startX);
        blocks.get(0).get(0).setY(startY);
        for(int i =0;i<blocks.size();i++){
            for(int j =0;j<blocks.get(i).size();j++){
                blocks.get(i).get(j).setX( blocks.get(0).get(0).getX()+j*(blockSize+gap));
                blocks.get(i).get(j).setY( blocks.get(0).get(0).getY()+i*(blockSize+gap));
            }
        }
    }
    public boolean checkPieceOnGrid(MouseEvent e, Grid grid){
        int blockX = blocks.get(0).get(0).getX()/blockSize;
        int blockY = blocks.get(0).get(0).getY()/blockSize;
        if(blockX < 9 && blockY <9){// inside grid
            if(grid.canFit(pieceData,blockY,blockX)){
                for(int i =0;i<blocks.size();i++){
                    for(int j=0;j<blocks.get(i).size();j++){
                        grid.gridArray[blockY+i][blockX+j] = 1;
                    }

                }
                return  true;
            }
        }else{
            resetBlocks();
        }
        return false;
    }
    private static ArrayList<ArrayList<Integer>> generateRandomPiece(int max) {
        ArrayList<ArrayList<Integer>> piece = new ArrayList<>();

        if (max <= 0) {
            // Error: Invalid max value
            System.err.println("Invalid max value. Must be greater than 0.");
            return piece;
        }

        // Create the initial block
        ArrayList<Integer> row = new ArrayList<>();
        row.add(1); // Add the integer value instead of a Block
        piece.add(row);

        if (max == 1) {
            // Single block piece
            return piece;
        }

        Random random = new Random();
        int counter = 1;

        // Add more blocks to the piece
        while (counter < max) {
            boolean addToRight = random.nextBoolean();
            boolean addToBottom = random.nextBoolean();

            if (addToRight) {
                addBlockToRight(piece);
                counter++;
            }

            if (addToBottom && counter < max) {
                addBlockToBottom(piece);
                counter++;
            }
        }

        return piece;
    }

    private static void addBlockToRight(ArrayList<ArrayList<Integer>> piece) {
        ArrayList<Integer> lastRow = piece.get(piece.size() - 1);
        lastRow.add(1); // Add the integer value instead of a Block
    }

    private static void addBlockToBottom(ArrayList<ArrayList<Integer>> piece) {
        ArrayList<Integer> newRow = new ArrayList<>();
        newRow.add(1); // Add the integer value instead of a Block
        piece.add(newRow);
    }
}
