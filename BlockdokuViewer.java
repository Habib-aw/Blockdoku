package blockPuzzle;

import java.awt.*;
import java.util.ArrayList;
public class BlockdokuViewer {
 public void drawGameOver(Graphics g,int score,int width, int height){
  g.setColor(Color.BLACK);
  drawCenteredText(g,"Game Over",width,height);
  drawCenteredText(g,"Score: "+score,width,height+35);
 }
 public void drawScore(Graphics g,int score){
  g.setColor(Color.BLACK);
  g.drawString("Score: "+score, 10, 500);
 }
 private void drawCenteredText(Graphics g, String text, int width, int height) {
  FontMetrics fontMetrics = g.getFontMetrics();
  int x = (width - fontMetrics.stringWidth(text)) / 2;
  int y = (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

  g.drawString(text, x, y);
 }
 public void drawPalletePieces(Graphics g,ArrayList<Piece> palletePieces,int blockSize){
  for(Piece piece: palletePieces){
   for (int i =0;i<piece.blocks.size();i++) {
    for(int j =0;j<piece.blocks.get(i).size();j++ ){
     g.setColor(Color.BLACK);
     g.fillRect(piece.blocks.get(i).get(j).getX(), piece.blocks.get(i).get(j).getY(), blockSize, blockSize);
    }
   }
  }
 }
 public void drawGrid(Graphics g, Grid grid) {
  // Draw outer border around the entire grid
  g.setColor(Color.BLACK);
  g.fillRect(0, 0, grid.SIZE * grid.blockSize + grid.THICKNESS_OUTER * 2, grid.SIZE * grid.blockSize + grid.THICKNESS_OUTER * 2);

  for (int i = 0; i < grid.SIZE; i++) {
   for (int j = 0; j < grid.SIZE; j++) {
    int value = grid.gridArray[i][j];

    Color color = value == 0 ? Color.WHITE :value == 2? Color.GRAY:value ==3 || value==4? Color.RED:Color.BLACK; // Use different colors for zero and non-zero values
    g.setColor(color);
    g.fillRect(j * grid.blockSize + grid.THICKNESS_OUTER, i * grid.blockSize + grid.THICKNESS_OUTER, grid.blockSize, grid.blockSize);
    g.setColor(Color.BLACK);
    g.drawRect(j * grid.blockSize + grid.THICKNESS_OUTER, i * grid.blockSize + grid.THICKNESS_OUTER, grid.blockSize, grid.blockSize);

    // Draw thicker lines for subgrids
    if (j % grid.SUBGRID_SIZE == 0 && j != 0) {
     g.fillRect(j * grid.blockSize, i * grid.blockSize, grid.THICKNESS_OUTER, grid.blockSize + grid.THICKNESS_OUTER * 2);
    }
    if (i % grid.SUBGRID_SIZE == 0 && i != 0) {
     g.fillRect(j * grid.blockSize, i * grid.blockSize, grid.blockSize + grid.THICKNESS_OUTER * 2, grid.THICKNESS_OUTER);
    }

    if ((j + 1) % grid.SUBGRID_SIZE == 0 && j + 1 != grid.SIZE) {
     g.fillRect((j + 1) * grid.blockSize - grid.THICKNESS_INNER, i * grid.blockSize + grid.THICKNESS_OUTER, grid.THICKNESS_INNER, grid.blockSize);
    }
    if ((i + 1) % grid.SUBGRID_SIZE == 0 && i + 1 != grid.SIZE) {
     g.fillRect(j * grid.blockSize + grid.THICKNESS_OUTER, (i + 1) * grid.blockSize - grid.THICKNESS_INNER, grid.blockSize, grid.THICKNESS_INNER);
    }
   }
  }
 }
}
