package tetrominoes;
import Tetris.TetrisBlock;

import java.awt.*;

public class SShape extends TetrisBlock{

    public SShape(){
        super(new int[][]{{0,1,1},{1,1,0}}, Color.green);
    }
}
