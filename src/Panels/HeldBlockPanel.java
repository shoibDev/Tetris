package Panels;

import Tetris.TetrisBlock;

import javax.swing.*;
import java.awt.*;

public class HeldBlockPanel extends JPanel {
    private TetrisBlock heldBlock;
    private int gridCellSize = 10;

    public HeldBlockPanel() {
        this.setBounds(200, 195, 50, 50);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);
    }

    public void setHeldBlock(TetrisBlock heldBlock) {
        this.heldBlock = heldBlock;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (heldBlock == null) {
            return;
        }

        int[][] shape = heldBlock.getShape();
        int height = heldBlock.getHeight();
        int width = heldBlock.getWidth();
        Color blockColor = heldBlock.getColor();

        int offsetX = (getWidth() - width * gridCellSize) / 2;
        int offsetY = (getHeight() - height * gridCellSize) / 2;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (shape[row][col] == 1) {
                    int x = col * gridCellSize + offsetX;
                    int y = row * gridCellSize + offsetY;

                    g.setColor(blockColor);
                    g.fillRect(x, y, gridCellSize, gridCellSize);
                }
            }
        }
    }
}
