import javafx.animation.AnimationTimer;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.stage.*;

public class SnakeController {
    private final SnakeGame gui;
    private final Playground playground;
    private final Stage stage;
    private final GameState game;
    private LastGameCache cache;
    private boolean continueGame;
    private TextField oldID;
    private AnimationTimer timer;
    private long before;
    
    public SnakeController(SnakeGame sg, Playground p, Stage st) {
        gui = sg;
        playground = p;
        stage = st;
        game = new GameState();
        
        checkConfigFile();
        playground.setPlayground();
        checkCache();        
        initializeListeners();
        
        gui.idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        gui.scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        DBSync();
        
        new GUINavigationEvent("AVVIO").sendNavEvent();
    }
    
    private void checkConfigFile() {
        if(ConfigurationParameters.validateConfigFileXML()) {
            ConfigurationParameters.deserializeConfigFileXML();
        } else { System.exit(1); }
    }

    private void checkCache() {
        cache = new LastGameCache(game, gui.playerID);
        if (cache.loadGame()) {
            continueGame = true;
            initializePlayground();
            renderObstacles();
        }        
        oldID = new TextField(gui.playerID.getText());
        gui.updateScore(game.score);
    }
    
    private void initializeListeners() {
        gui.startButton.setOnAction(e -> {startGame();});
        gui.scene.setOnKeyPressed(e -> {changeSnakeDirection(e);});
        gui.playerID.textProperty().addListener(e -> {
            if (!gui.playerID.getText().equals(oldID.getText()))
                continueGame = false;
        });
        stage.setOnCloseRequest((WindowEvent we) -> {
            cache.saveGame(timer != null);
            new GUINavigationEvent("TERMINE").sendNavEvent();
        });        
    }
    
    private void initializePlayground() {
            playground.addSnake(game.snake.body.size() + 1);
            playground.addApple(game.apple.posX, game.apple.posY);
            playground.addObstacles(game.obstacles.size());
            renderSnake();        
    }
    
    private void startGame() {
        gui.disable(true);        
        new GUINavigationEvent("START").sendNavEvent();    
        if (!continueGame) {
            game.createNewGame();
            initializePlayground();
        }
        continueGame = false;
        startTimer();       
    }
    
    private void stopGame() {
        timer.stop();
        timer = null;
        LeaderboardArchive.addNewRecord(gui.playerID.getText(), game.score);
        DBSync();
        cache.clear();
        gui.disable(false);
    }
    
    private void updateGame() {
        if(game.checkEatenApple()) {
            playground.addSnakeBlock();
            playground.addApple(game.apple.posX, game.apple.posY);
            if(ConfigurationParameters.OBSTACLES) {
                playground.addObstacle();
                playground.renderObstacle(0, game.obstacles.get(0).posX, game.obstacles.get(0).posY);
            }
        }
        gui.updateScore(game.score);
        renderSnake();
    }
    
    private void renderSnake() {
        playground.renderSnakeBlock(0, game.snake.head.posX, game.snake.head.posY);
        int i = 1;
        for (GameObjectPosition sb:game.snake.body) {
            playground.renderSnakeBlock(i, sb.posX, sb.posY);
            i++;
        }
    }
    
    private void renderObstacles() {
        int i = 0;
        for (GameObjectPosition o:game.obstacles) {
            playground.renderObstacle(i, o.posX, o.posY);
            i++;
        }          
    }
    
    private void changeSnakeDirection(KeyEvent e) {
        if (e.getCode().equals(ConfigurationParameters.KEY_UP))
            game.snake.changeDirection(GameObjectPosition.UP);
        else if (e.getCode().equals(ConfigurationParameters.KEY_DOWN))
            game.snake.changeDirection(GameObjectPosition.DOWN);
        else if (e.getCode().equals(ConfigurationParameters.KEY_LEFT))
            game.snake.changeDirection(GameObjectPosition.LEFT);
        else if (e.getCode().equals(ConfigurationParameters.KEY_RIGHT))
            game.snake.changeDirection(GameObjectPosition.RIGHT);
    }
    
    private void startTimer(){
        long clock = 1000000000 / (5 * ConfigurationParameters.GAME_DIFFICULTY);
        before = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - before > clock) {
                    game.snake.move();
                    if (game.isSnakeDead())
                        stopGame();
                    else
                        updateGame();
                    before = now;
                }
            }
        };
        timer.start();
    }
    
    private void DBSync() {
        ObservableList ol = FXCollections.observableArrayList();
        for (Object o: LeaderboardArchive.getLeaderboard())
            ol.add(o);
        gui.leaderboard.setItems(ol);
    }
    
}
