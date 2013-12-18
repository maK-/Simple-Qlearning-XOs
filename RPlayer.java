import java.util.Random;

public class RPlayer{
    int currentMove;
    String currentState;
    public void nextAction(String state){
        Random r = new Random();
        int random = r.nextInt(9);
        currentMove = random;
        currentState = state;
    }
}
