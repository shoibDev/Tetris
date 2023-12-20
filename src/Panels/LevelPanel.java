package Panels;

import javax.swing.*;
import java.awt.*;

public class LevelPanel extends JPanel {
    private final JLabel levelLabel;
    private int level;

    public LevelPanel() {
        levelLabel = new JLabel(""+level);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(true);
        this.add(levelLabel);
    }

    public void updateLevel(int level) {
        this.level = level;
        levelLabel.setText("" + level);
    }
}
