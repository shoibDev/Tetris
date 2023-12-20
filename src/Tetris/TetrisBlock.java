package Tetris;

import java.awt.*;

public class TetrisBlock {

    private int[][] shape;
    private final Color color;
    private int x,y;
    private int[][][] shapes;
    private int currentRotation;


    public TetrisBlock(int[][] shape, Color color){
        this.shape = shape;
        this.color = color;

        currentShape();
    }

    private void currentShape(){
        shapes = new int[4][][];

        for (int i = 0; i < 4; i++){
            int r = shape[0].length;
            int c = shape.length;

            shapes[i] = new int[r][c];

            for (int y = 0; y < r; y++) {
                for (int x = 0; x < c; x++) {
                    shapes[i][y][x] = shape[c - x - 1][y];
                    
                }
            }
            shape = shapes[i];
        }
    }

    public int[][] getRotatedShape() {
        int[][] rotatedShape = new int[getWidth()][getHeight()];
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                rotatedShape[col][getHeight() - row - 1] = shape[row][col];
            }
        }
        return rotatedShape;
    }

    public void spawn(int gridWidth){
        currentRotation = 3;
        shape = shapes[currentRotation];

        y = -getHeight();
        x = (gridWidth - getWidth()) / 2;
    }

    public TetrisBlock clone() {
        return new TetrisBlock(this.shape, this.color);
    }

    public int[][] getShape(){
        return shape;
    }

    public Color getColor(){
        return color;
    }

    public int getHeight(){
        return shape.length;
    }

    public int getWidth(){
        return shape[0].length;
    }

    public int getX(){
        return x;
    }

    public void setX(int newX){
        x = newX;
    }

    public  int getY(){
        return y;
    }

    public void setY(int newY){
        y = newY;
    }

    public void moveDown(){
        y++;
    }

    public void moveLeft(){
        x--;
    }

    public void moveRight(){
        x++;
    }

    public void rotate(){
        currentRotation++;
        if(currentRotation > 3){
            currentRotation = 0;
        }
        shape = shapes[currentRotation];
    }

    public int getBottomEdge(){
        return y + getHeight();
    }

    public int getLeftEdge(){
        return x;
    }

    public int getRightEdge(){
        return x + getWidth();
    }

}
