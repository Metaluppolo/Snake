import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SnakeGame extends Application {
    HBox root;
    TableView leaderboard;
    Label score;
    TextField playerID;
    Button startButton;
    Scene scene;
    TableColumn idCol, scoreCol;
    Label header;
        
    @Override
    public void start(Stage stage) {
        Playground playground = new Playground();        
        
        VBox vbox = new VBox();        
        score = new Label("SCORE: ");
        playerID = new TextField();
        startButton = new Button("START");
        vbox.getChildren().addAll(createTable(), score, playerID, startButton);
        vbox.setAlignment(Pos.CENTER);
        
        root = new HBox();
        root.getChildren().addAll(playground, vbox);
        scene = new Scene(root);
        
        SnakeController controller = new SnakeController(this, playground, stage);
        
        setStyle();
        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();
    }
    
    public void updateScore(int s) {
        score.setText("SCORE: " + s);
    }
    
    public void disable(boolean b) {
        playerID.setDisable(b);
        startButton.setDisable(b);
        startButton.requestFocus();
    }
    
    private void setStyle() {
        String font = "-fx-font-size:" + ConfigurationParameters.FONT_SIZE 
           + "; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-family:" + ConfigurationParameters.FONT;
        header.setStyle(font); score.setStyle(font);
        root.setStyle("-fx-background-color: gainsboro");
        idCol.prefWidthProperty().bind(leaderboard.widthProperty().multiply(0.5));
        scoreCol.prefWidthProperty().bind(leaderboard.widthProperty().multiply(0.49));
        idCol.setStyle("-fx-alignment: CENTER;"); scoreCol.setStyle("-fx-alignment: CENTER;");
        leaderboard.setFixedCellSize(25);
        leaderboard.prefHeightProperty().bind(leaderboard.fixedCellSizeProperty().multiply(ConfigurationParameters.LEADERBOARD_SIZE + 1).add(1));
        leaderboard.maxHeightProperty().bind(leaderboard.prefHeightProperty());    
        leaderboard.setFocusTraversable(false);
        startButton.requestFocus();
    }
    
    private VBox createTable() {
        VBox table = new VBox();
        header = new Label("LEADERBOARD");
        leaderboard = new TableView();
        idCol = new TableColumn("PLAYER");
        scoreCol = new TableColumn("SCORE");
        leaderboard.getColumns().addAll(idCol, scoreCol);
        table.getChildren().addAll(header, leaderboard);
        table.setAlignment(Pos.CENTER);   
        return table;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}