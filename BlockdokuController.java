package blockPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockdokuController extends JFrame {
    Palette palette;
    Grid grid;
    int score;

    BlockdokuViewer viewer;
    boolean gameOver;
    JPanel panel;
    MouseAdapter mousePressedReleasedAdapter;
    MouseAdapter mouseDraggedAdapter;


    public BlockdokuController() {
        setTitle("Blockdoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(325, 600);
        setVisible(true);
        int blockSize =30;
        score = 0;
        grid = new Grid(9, 3, blockSize, 3, 1);
        palette = new Palette(grid.SIZE, blockSize, 3, 3, 10, 30, 5,2);
        viewer = new BlockdokuViewer();
        gameOver = checkGameOver();
        panel= new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(gameOver){
                    viewer.drawGameOver(g,score,getWidth(),getHeight());
                }else{
                    viewer.drawGrid(g,grid);
                    int addScore =grid.clearIfFilled();
                    if(addScore!=0){
                        score+=addScore;
                        repaint();
                    }

                    viewer.drawPalletePieces(g, palette.pieces, palette.blockSize);
                    viewer.drawScore(g,score);

                }
            }
        };
        mousePressedReleasedAdapter=new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                palette.checkAnyPiecePressed(e);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                palette.checkPieceReleased(e,grid);
                gameOver = checkGameOver();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                palette.checkPieceReleased(e,grid);
                gameOver = checkGameOver();
                repaint();
            }
        };
        mouseDraggedAdapter =  new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                palette.moveSelectedPiece(e,grid);
                repaint();
            }
        };
        panel.addMouseListener(mousePressedReleasedAdapter);
        panel.addMouseMotionListener(mouseDraggedAdapter);
        add(panel);
    }
    private boolean checkGameOver(){
        if(!palette.checkPiecesCanFit(grid)){
            panel.removeMouseListener(mousePressedReleasedAdapter);
            panel.removeMouseMotionListener(mouseDraggedAdapter);
            return true;
        };
        return false;
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlockdokuController());

    }
}


