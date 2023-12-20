package Panels;

import Tetris.TetrisBlock;

import javax.swing.*;
import java.awt.*;

public class PreviewBar extends JPanel {

    private final int gridColumns;
    private TetrisBlock nextBlock1;
    private TetrisBlock nextBlock2;
    private TetrisBlock nextBlock3;

    public PreviewBar(int gridColumns) {
        this.gridColumns = gridColumns;
        setBounds(270, 200, 400, 540);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(true);
    }

    public void setBlocks(TetrisBlock nextBlock1, TetrisBlock nextBlock2, TetrisBlock nextBlock3) {
        this.nextBlock1 = nextBlock1;
        this.nextBlock2 = nextBlock2;
        this.nextBlock3 = nextBlock3;
    }

    private void drawBlockPreview(Graphics g, int[][] shape, int x, int y, int width, int height) {
        int blockWidth = shape[0].length;
        int blockHeight = shape.length;
        int cellSize = Math.min(width / blockWidth, height / blockHeight);
        int startX = x + (width - cellSize * blockWidth) / 2;
        int startY = y + (height - cellSize * blockHeight) / 2;

        Color blockColor = new Color(120,102,120);

        for (int row = 0; row < blockHeight; row++) {
            for (int col = 0; col < blockWidth; col++) {
                if (shape[row][col] == 1) {

                    int cellX = startX + col * cellSize;
                    int cellY = startY + row * cellSize;

                    g.setColor(blockColor);
                    g.fillRect(cellX, cellY, cellSize, cellSize);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int barWidth = getWidth() / 8;
        int barHeight = getHeight() / 8;
        int barX = getWidth() - barWidth - 80;
        int barY = 0;

        drawBlockPreview(g, nextBlock1.getShape(), barX, barY, barWidth, barHeight);
        drawBlockPreview(g, nextBlock2.getShape(), barX, barY + barHeight, barWidth, barHeight);
        drawBlockPreview(g, nextBlock3.getShape(), barX, barY + barHeight * 2, barWidth, barHeight);
    }
}
