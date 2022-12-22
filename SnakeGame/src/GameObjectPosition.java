import java.io.Serializable;

public class GameObjectPosition implements Serializable {
    static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;   
    int posX, posY;
    
    
    public GameObjectPosition (int x, int y) {
        posX = x;
        posY = y;
    }
    
    public void copy(GameObjectPosition sb) {
        posX = sb.posX;
        posY = sb.posY;
    }
    
    public void translate(int direction) {
        switch(direction) {
            case LEFT: posX--; break;
            case RIGHT: posX++; break;
            case UP: posY--; break;
            case DOWN: posY++; break;
        }
    }
    
    public boolean samePosition(int x, int y) {
        return (posX == x && posY == y);
    }
    
}
