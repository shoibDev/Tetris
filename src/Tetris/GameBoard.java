package Tetris;

import Panels.PreviewBar;
import tetrominoes.IShape;
import tetrominoes.JShape;
import tetrominoes.LShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


import tetrominoes.*;

public class GameBoard extends JPanel implements KeyListener {

    private final int gridRows;
    private final int gridColumns;
    private final int gridCellSize;
    private final Color[][] background;

    private TetrisBlock block;
    private final TetrisBlock[] blocks;

    private TetrisBlock heldBlock;
    private boolean hasBlockBeenHeld;

    private TetrisBlock nextBlock1;
    private TetrisBlock nextBlock2;
    private TetrisBlock nextBlock3;

    private Timer dropTimer;

    private static PreviewBar previewBar;

    public GameBoard(int gridColumns){
        this.setBounds(270,200,400,540);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);

        this.gridColumns = 9;
        this.gridRows = 20;
        this.gridCellSize = 250 / this.gridColumns;

        background = new Color[gridRows][gridColumns];

        blocks = new TetrisBlock[]{new IShape(), new JShape(), new LShape(), new OShape(), new SShape(), new TShape(), new ZShape()};

        this.addKeyListener(this);
        this.setFocusable(true);

        previewBar = new PreviewBar(gridColumns);
        this.add(previewBar);

        spawnBlock();
    }

    public void spawnBlock() {
        Random r = new Random();
        block = nextBlock1 != null ? nextBlock1.clone() : blocks[r.nextInt(blocks.length)].clone();
        nextBlock1 = nextBlock2 != null ? nextBlock2.clone() : blocks[r.nextInt(blocks.length)].clone();
        nextBlock2 = nextBlock3 != null ? nextBlock3.clone() : blocks[r.nextInt(blocks.length)].clone();
        nextBlock3 = blocks[r.nextInt(blocks.length)].clone();

        block.spawn(gridColumns);
        hasBlockBeenHeld = false;

        previewBar.setBlocks(nextBlock1, nextBlock2, nextBlock3);
    }

    public static PreviewBar getPreviewBar() {
        return previewBar;
    }


    private boolean checkBottom(){
        if(block.getBottomEdge() == gridRows){
            return false;
        }

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int col = 0; col < w; col++) {
            for (int row = h - 1; row >= 0 ; row--) {
                if(shape[row][col] != 0){
                    int x = col + block.getX();
                    int y = row + block.getY() + 1;
                    if(y < 0){
                        break;
                    }
                    if(background[y][x] != null){
                        return false;
                    }
                    break;
                }
            }
        }

        return true;
    }

    private boolean checkLeft(){
        if(block.getLeftEdge() == 0){
            return false;
        }

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w ; col++) {
                if(shape[row][col] != 0){
                    int x = col + block.getX() - 1;
                    int y = row + block.getY();
                    if(y < 0){
                        break;
                    }
                    if(background[y][x] != null){
                        return false;
                    }
                    break;
                }
            }
        }

        return true;
    }

    private boolean checkRight(){
        if(block.getRightEdge() == gridColumns){
            return false;
        }

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int row = 0; row < h; row++) {
            for (int col = w - 1; col >= w ; col--) {
                if(shape[row][col] != 0){
                    int x = col + block.getX() + 1;
                    int y = row + block.getY();
                    if(y < 0){
                        break;
                    }
                    if(background[y][x] != null){
                        return false;
                    }
                    break;
                }
            }
        }

        return true;
    }


    public boolean moveBlockDown(){
        if(!checkBottom()){
            return false;
        }
        block.moveDown();
        repaint();
        return true;
    }

    public void moveBlockRight(){
        if(block == null){
            return;
        }
        if(!checkRight()){
            return;
        }
        block.moveRight();
        repaint();
    }

    public void moveBlockLeft(){
        if(block == null){
            return;
        }
        if (!checkLeft()){
            return;
        }
        block.moveLeft();
        repaint();
    }

    public void dropBlock(){
        if(block == null){
            return;
        }
        while (checkBottom()) {
            block.moveDown();
        }
        repaint();
    }

    public void slowDrop(boolean start){
        if (start) {
            if (dropTimer == null || !dropTimer.isRunning()) {
                dropTimer = new Timer(50, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!moveBlockDown()) {
                            ((Timer) e.getSource()).stop();
                        }
                    }
                });
                dropTimer.start();
            }
        } else {
            if (dropTimer != null && dropTimer.isRunning()) {
                dropTimer.stop();
            }
        }
    }


    private boolean canRotate() {
        int[][] rotatedShape = block.getRotatedShape();
        int rotatedWidth = rotatedShape[0].length;
        int rotatedHeight = rotatedShape.length;
        int xPos = block.getX();
        int yPos = block.getY();

        for (int row = 0; row < rotatedHeight; row++) {
            for (int col = 0; col < rotatedWidth; col++) {
                if (rotatedShape[row][col] == 1) {
                    int newRow = yPos + row;
                    int newCol = xPos + col;

                    if (newRow < 0 || newRow >= gridRows || newCol < 0 || newCol >= gridColumns) {
                        return false;
                    }

                    if (background[newRow][newCol] != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void rotateBlock() {
        if (block == null) {
            return;
        }

        if (!canRotate()) {
            return;
        }

        block.rotate();

        if (block.getLeftEdge() < 0) {
            block.setX(0);
        }
        if (block.getRightEdge() >= gridColumns) {
            block.setX(gridColumns - block.getWidth());
        }
        if (block.getBottomEdge() >= gridRows) {
            block.setY(gridRows - block.getHeight());
        }

        repaint();
    }

    public boolean isBlockOutOfBounds(){
        if(block.getY() < 0){
            block = null;
            return true;
        }
        return false;
    }


    private void drawBlock(Graphics g){
        int height = block.getHeight();
        int width = block.getWidth();
        Color blockColor = block.getColor();
        int[][] shape = block.getShape();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if(shape[row][col] == 1){

                    int x = (block.getX() + col) * gridCellSize;
                    int y = (block.getY() + row) * gridCellSize;

                    g.setColor(blockColor);
                    g.fillRect(x, y, gridCellSize, gridCellSize);

                }
            }
        }
    }

    public int clearLines(){
        boolean lineFilled;
        int linesCleared = 0;

        for (int i = gridRows - 1; i >= 0 ; i--) {
            lineFilled = true;
            for (int j = 0; j < gridColumns; j++){
                if(background[i][j] == null){
                    lineFilled = false;
                    break;
                }
            }
            if(lineFilled){
                linesCleared++;
                deleteBlock(i);
                shiftDown(i);
                deleteBlock(0);

                i++;

                repaint();
            }
        }
        return linesCleared;
    }

    public void deleteBlock(int r){
        for (int j = 0; j < gridColumns; j++) {
            background[r][j] = null;
        }
    }

    private void shiftDown(int r){
        for(int row = r; row > 0; row--){
            if (gridColumns >= 0) System.arraycopy(background[row - 1], 0, background[row], 0, gridColumns);
        }
    }

    private void drawBackground(Graphics g){
        Color color;

        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                color = background[i][j];

                if(color != null){
                    int x = j * gridCellSize;
                    int y = i * gridCellSize;

                    g.setColor(color);
                    g.fillRect(x, y, gridCellSize, gridCellSize);
                }
            }
        }
    }

    public void moveBlockToBackGround(){
        int[][] shape = block.getShape();
        int height = block.getHeight();
        int width = block.getWidth();

        int xPos = block.getX();
        int yPos = block.getY();

        Color color = block.getColor();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (shape[i][j] == 1){
                    background[i + yPos][j + xPos] = color;
                }
                
            }
            
        }
    }


    public void holdBlock() {
        if (hasBlockBeenHeld) {
            return;
        }

        if (heldBlock == null) {
            heldBlock = block;
            spawnBlock();
        } else {
            TetrisBlock temp = block;
            block = heldBlock;
            heldBlock = temp;
            block.spawn(gridColumns);
        }
        hasBlockBeenHeld = true;
        TetrisMain.updateHeldBlock(heldBlock);
        repaint();
    }

    private void drawPreviewBar() {
        previewBar.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i <gridRows; i++) {

            for (int j = 0; j < gridColumns; j++) {
                g.drawRect(j * gridCellSize, i * gridCellSize, gridCellSize, gridCellSize);
            }
        }
        drawBackground(g);
        drawBlock(g);
        drawPreviewBar();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            slowDrop(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveBlockLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveBlockRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            rotateBlock();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            dropBlock();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            holdBlock();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            slowDrop(false);
        }
    }

}
