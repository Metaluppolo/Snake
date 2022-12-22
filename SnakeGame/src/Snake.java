import java.io.Serializable;
import java.util.ArrayList;

public class Snake implements Serializable {
    GameObjectPosition head;
    ArrayList<GameObjectPosition> body = new ArrayList<>();
    private boolean isTurning;
    private int direction;
    
    
    public Snake (int x, int y) {
        direction = GameObjectPosition.RIGHT;
        head = new GameObjectPosition(x, y);
        for (int i = 1; i < 4; i++) 
            body.add(new GameObjectPosition(head.posX - i, head.posY));
    }
    
    public void move() {
        moveBody();
        moveHead();
    }
    
    private void moveBody() {
        GameObjectPosition tail = body.get(body.size() - 1);
        body.remove(tail);
        body.add(0, tail);
        tail.copy(head);
    }
    
    private void moveHead() {
        head.translate(direction);
        isTurning = false;
    }
    
    public void changeDirection(int newDirection) {
        int oppositeDirection = (newDirection + 2) % 4;
        if (!isTurning && direction != newDirection && direction != oppositeDirection) {
            direction = newDirection;
            isTurning = true;
        }
    }

    public void grow() {
        GameObjectPosition tail = body.get(body.size() - 1);
        body.add(new GameObjectPosition(tail.posX, tail.posY));
    }
    
}
