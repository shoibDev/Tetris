package Panels;

import Tetris.TetrisMain;

import javax.swing.*;
import java.awt.*;

public class TetrisFrame extends JFrame {

    public TetrisFrame(TetrisMain tetrisMain) {
        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setSize(800, 800);  // set size explicitly
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        ImageIcon backgroundImage = new ImageIcon("src/Panels/template.png");
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, this.getWidth() - 300, this.getHeight());
        background.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(background.getWidth(), background.getHeight(), Image.SCALE_DEFAULT)));
        this.setContentPane(background);
    }
}
