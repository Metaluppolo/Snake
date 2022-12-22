import java.util.ArrayList;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Playground extends Pane {
    private Circle apple;
    private ArrayList<Rectangle> snake;
    private ArrayList<Rectangle> obstacles;
    
    public void setPlayground() {
        int w = ConfigurationParameters.BLOCK_SIZE * ConfigurationParameters.PLAYGROUND_WIDTH;
        int h = ConfigurationParameters.BLOCK_SIZE * ConfigurationParameters.PLAYGROUND_HEIGTH;
        setMinSize(w, h);
        setClip(new Rectangle(w,h));
        setBackground(new Background(new BackgroundFill(ConfigurationParameters.PLAYGROUND_COLOR, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
    }

    public void addApple(int posX, int posY) {
        if (apple != null)
            getChildren().remove(apple);
        apple = new Circle();
        getChildren().add(apple);
        renderApple(posX, posY);
    }
    
    public void addSnake(int n) {
        if (snake != null)
            for(Rectangle r:snake)
                getChildren().remove(r);        
        snake = new ArrayList<>();
        for(int i = 0; i < n; i++)
            addSnakeBlock();
    }
    
    public void addSnakeBlock() {
        Rectangle r = new Rectangle();
        snake.add(r);
        getChildren().add(r);        
    }
 
    public void addObstacles(int n) {
        if (obstacles != null)
            for(Rectangle r:obstacles)
                getChildren().remove(r);        
        obstacles = new ArrayList<>();
        for(int i = 0; i < n; i++)
            addObstacle();
    }
    
    public void addObstacle() {
        Rectangle o = new Rectangle();
        obstacles.add(0,o);
        getChildren().add(0,o);
    }    
    
    private void renderApple(int posX, int posY) {
        apple.setRadius(ConfigurationParameters.BLOCK_SIZE * 0.5);
        apple.setFill(Color.LIGHTCORAL);
        apple.setStroke(Color.DARKRED);
        apple.setTranslateX((posX + 0.5) * ConfigurationParameters.BLOCK_SIZE);
        apple.setTranslateY((posY + 0.5) * ConfigurationParameters.BLOCK_SIZE);
    }
    
    public void renderSnakeBlock(int i, int posX, int posY) {
        snake.get(i).setWidth(ConfigurationParameters.BLOCK_SIZE);
        snake.get(i).setHeight(ConfigurationParameters.BLOCK_SIZE);
        snake.get(i).setTranslateX(posX * ConfigurationParameters.BLOCK_SIZE);
        snake.get(i).setTranslateY(posY * ConfigurationParameters.BLOCK_SIZE);
        snake.get(i).setStroke(ConfigurationParameters.SNAKE_BORDER_COLOR);
        if (i == 0)
            snake.get(0).setFill(ConfigurationParameters.SNAKE_HEAD_COLOR);
        else
            snake.get(i).setFill(ConfigurationParameters.SNAKE_BODY_COLOR);
    }
    
    public void renderObstacle(int i, int posX, int posY) {
        obstacles.get(i).setWidth(ConfigurationParameters.BLOCK_SIZE);
        obstacles.get(i).setHeight(ConfigurationParameters.BLOCK_SIZE);
        obstacles.get(i).setTranslateX(posX * ConfigurationParameters.BLOCK_SIZE);
        obstacles.get(i).setTranslateY(posY * ConfigurationParameters.BLOCK_SIZE);
        obstacles.get(i).setFill(ConfigurationParameters.OBSTACLE_COLOR);        
    }
}