import java.io.*;
import java.nio.file.*;
import javafx.scene.control.*;

public class LastGameCache {
    private final GameState game;
    private final TextField playerID;
    

    public LastGameCache(GameState g, TextField id){
        game = g;
        playerID = id;
        new File("./myfiles/cache/").mkdir();
    }
    
    public void saveGame(boolean isGameRunning) {
        savePlayerIdTxt();
        if (isGameRunning)
            saveGameStateBin();
    }
    
    public boolean loadGame() {
        loadPlayerIdTxt();
        return game.copy(loadGameStateBin());
    }
    
    public void clear() {
        File f = new File("./myfiles/cache/GameState.bin");
        f.delete();
    }
    
    private void savePlayerIdTxt() {
        try {
            Files.write(Paths.get("./myfiles/cache/playerID.txt"), playerID.getText().getBytes());
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }
    
    private void loadPlayerIdTxt() {
        try {
            playerID.setText(new String( Files.readAllBytes(Paths.get("./myfiles/cache/playerID.txt")) ));
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }
    
    private void saveGameStateBin() {
        try ( ObjectOutputStream ooutf = 
                new ObjectOutputStream( new FileOutputStream("./myfiles/cache/GameState.bin") ); 
            ) { ooutf.writeObject(game); } 
        catch (Exception e) { System.err.println(e.getMessage()); }        
    }
    
    private GameState loadGameStateBin() {
        try ( ObjectInputStream oinf = 
                new ObjectInputStream( new FileInputStream("./myfiles/cache/GameState.bin") );
            ) { return (GameState)oinf.readObject(); } 
        catch (Exception e) { System.err.println(e.getMessage()); return null; }
    }
}
