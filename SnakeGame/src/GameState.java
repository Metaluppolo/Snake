import java.io.Serializable;
import java.util.*;

public class GameState implements Serializable {
    int score;
    Snake snake;
    GameObjectPosition apple = new GameObjectPosition(0,0);
    ArrayList<GameObjectPosition> obstacles = new ArrayList<>();
    
    
    public boolean copy(GameState gs) {
        if (gs == null)  
            return false; 
        score = gs.score;
        snake = gs.snake;
        apple = gs.apple;
        obstacles = gs.obstacles;
        return true;
    }
 
    public boolean checkEatenApple() {
        if (snake.head.samePosition(apple.posX, apple.posY)) {
            score += 5;
            snake.grow();
            createApple();
            if (ConfigurationParameters.OBSTACLES)
                createObstacle();
            return true;
        }
        return false;
    }
    
    public boolean isSnakeDead() {
        if (snake.head.posX >= ConfigurationParameters.PLAYGROUND_WIDTH  || snake.head.posX < 0 
         || snake.head.posY >= ConfigurationParameters.PLAYGROUND_HEIGTH || snake.head.posY < 0)
            return true;
        for (GameObjectPosition sb:snake.body)
            if (sb.samePosition(snake.head.posX, snake.head.posY))
                return true;
        for (GameObjectPosition o:obstacles)
            if (o.samePosition(snake.head.posX, snake.head.posY))
                return true;
        return false;
    }    
    
    public void createNewGame() {
        score = 0;
        obstacles = new ArrayList<>();
        snake = new Snake(ConfigurationParameters.PLAYGROUND_WIDTH  / 2, 
                          ConfigurationParameters.PLAYGROUND_HEIGTH / 2);
        createApple();
    }
    
    private void createApple() {
        int rnd = getRandomFreePosition();
        apple = new GameObjectPosition(rnd % ConfigurationParameters.PLAYGROUND_WIDTH,
                                       rnd / ConfigurationParameters.PLAYGROUND_WIDTH);
    }
    
    private void createObstacle() {
        int rnd = getRandomFreePosition();
        obstacles.add(0, new GameObjectPosition(rnd % ConfigurationParameters.PLAYGROUND_WIDTH,
                                     rnd / ConfigurationParameters.PLAYGROUND_WIDTH));
    }
    
    private int getRandomFreePosition() {
        ArrayList<Integer> exclude = new ArrayList<>();
        exclude.add(apple.posY * ConfigurationParameters.PLAYGROUND_WIDTH + apple.posX);
        exclude.add(snake.head.posY * ConfigurationParameters.PLAYGROUND_WIDTH + snake.head.posX);
        for (GameObjectPosition sb:snake.body)
            exclude.add(sb.posY * ConfigurationParameters.PLAYGROUND_WIDTH + sb.posX);
        for (GameObjectPosition o:obstacles)
            exclude.add(o.posY * ConfigurationParameters.PLAYGROUND_WIDTH + o.posX);
        
        ArrayList<Integer> pool = new ArrayList<>();
        for (int i=0; i < ConfigurationParameters.PLAYGROUND_WIDTH * ConfigurationParameters.PLAYGROUND_HEIGTH; i++)
            if(!exclude.contains(i))
                pool.add(i);
                    
        Random rnd = new Random();
        return pool.get(rnd.nextInt(pool.size()));
    }
        
}
