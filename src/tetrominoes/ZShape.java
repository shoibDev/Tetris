package tetrominoes;
import Tetris.TetrisBlock;

import java.awt.*;

public class ZShape extends TetrisBlock{

    public ZShape(){
        super(new int[][]{{0,1},{1,1},{1,0}}, Color.red);
    }
}
