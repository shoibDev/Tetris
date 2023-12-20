package Tetris;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class GameThread extends Thread{

    private final GameBoard gameObject;
    private final TetrisMain mainObject;
    private int score = 1;
    private int level = 1;

    public GameThread(GameBoard gameObject, TetrisMain mainObject){
        this.gameObject = gameObject;
        this.mainObject = mainObject;
    }

    @Override
    public void run() {
        while (true) {

            gameObject.spawnBlock();

            while (gameObject.moveBlockDown()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if(gameObject.isBlockOutOfBounds()){
                JOptionPane.showMessageDialog(null, "Game Over");
                break;
            }
            gameObject.moveBlockToBackGround();
            score += gameObject.clearLines();

            int scorePerLevel = 1;
            int lvl = score / scorePerLevel + 1;
            if(lvl > level){
                level = lvl;
                mainObject.updateLevel(level);
            }
        }
    }
}
