package blockPuzzle;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Palette {
    final int blockSize;
    private final int numPieces;
    private final int maxBlocks;
    private final int pieceSpacingHorizontal;
    private final int pieceSpacingVertical;
    private final int pieceSpacingHorizontalFactor;
    private final int gap;
    final ArrayList<Piece> pieces = new ArrayList<>();
    Piece selectedPiece;

    public Palette(int gridSize, int blockSize, int numPieces, int maxBlocks, int pieceSpacingHorizontal, int pieceSpacingVertical, int pieceSpacingHorizontalFactor,int gap) {
        this.blockSize = blockSize;
        this.numPieces = numPieces;
        this.maxBlocks = maxBlocks;
        this.pieceSpacingHorizontal = pieceSpacingHorizontal;
        this.pieceSpacingVertical = pieceSpacingVertical;
        this.pieceSpacingHorizontalFactor = pieceSpacingHorizontalFactor;
        this.gap = gap;

        generatepieces(gridSize);
    }

    private void generatepieces(int gridSize){
        for(int i = 0;i<numPieces;i++ ){
            pieces.add(
                    new Piece(pieceSpacingHorizontal+i*(blockSize+pieceSpacingHorizontalFactor)*maxBlocks,gridSize*blockSize+ pieceSpacingVertical,blockSize,maxBlocks,gap));
        }
        selectedPiece = pieces.get(0);
    }
    public boolean checkPiecesCanFit(Grid grid){
        for(Piece piece: pieces){
            if(grid.canFitAny(piece.pieceData))return true;
        }
        return false;
    }

    public void checkPieceReleased(MouseEvent e,Grid grid){
        selectedPiece.dragging=false;
        selectedPiece.gap=2;
        if(selectedPiece.checkPieceOnGrid(e,grid))
            pieces.remove(selectedPiece);
        else selectedPiece.resetBlocks();

        if(pieces.isEmpty()){
            generatepieces(grid.SIZE);

        }
    }
    public void checkAnyPiecePressed(MouseEvent e){
        for(Piece piece: pieces){
            if(piece.checkPiecePressed(e) ){
                piece.gap = piece.gap*4;
                selectedPiece = piece;
                piece.dragging=true;
            }
        }
    }
    public void moveSelectedPiece(MouseEvent e,Grid grid){
        selectedPiece.movePiece(e,grid);

    }

}
