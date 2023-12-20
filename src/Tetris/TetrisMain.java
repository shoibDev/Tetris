package Tetris;

import Panels.*;

import javax.swing.*;

public class TetrisMain {
    private final GameBoard gameObject;
    private final LevelPanel levelPanel;
    private final TetrisFrame tetrisFrame;

    private static HeldBlockPanel heldBlockPanel;

    public TetrisMain() {
        tetrisFrame = new TetrisFrame(this);

        gameObject = new GameBoard(9);
        tetrisFrame.add(gameObject);

        levelPanel = new LevelPanel();
        levelPanel.setBounds(200, 390, 100, 30);
        tetrisFrame.add(levelPanel);

        heldBlockPanel = new HeldBlockPanel();
        tetrisFrame.add(heldBlockPanel);

        PreviewBar previewBar = GameBoard.getPreviewBar();
        tetrisFrame.add(previewBar);

        startGame();
    }

    public static void updateHeldBlock(TetrisBlock block) {
        heldBlockPanel.setHeldBlock(block);
    }

    public void startGame() {
        new GameThread(gameObject, this).start();
    }

    public void updateLevel(int level) {
        levelPanel.updateLevel(level);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TetrisMain tetrisMain = new TetrisMain();
                tetrisMain.getTetrisFrame().setVisible(true);
            }
        });
    }

    public TetrisFrame getTetrisFrame() {
        return tetrisFrame;
    }
}
