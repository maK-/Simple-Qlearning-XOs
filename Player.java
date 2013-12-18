import java.util.Random;

public class Player{
    int currentMove;
    String currentState;
    int lastMove;
    String lastState;
    Q q = new Q();

    Player(String mind){
        q.importMind(mind);    
    }
    public void nextAction(String state){
        currentMove = q.getBestAction(state);
        currentState = state;
    }

    public void storeInLast(){
        lastMove = currentMove;
        lastState = currentState;
    }

    //Q(y,b)
    public double retQNextAction(String state){
        return q.getQ(state, q.getBestAction(state));
    }

    public double retQ(String state, int action){
        return q.getQ(state, action);
    }   
    
    public void rewards(String state, int action, double reward){
        double oldQ = retQ(state, action);
        double newQ = oldQ + reward;
        //System.out.println("State: "+state+", Action: "+action+", Value: "+newQ);
        q.update_Q(state, action, newQ);
    }

    public void directReward(String state, int action, double reward){
        double oldQ = retQ(state, action);
        q.update_Q(state, action, oldQ+reward);
    }

    public void dump(){
       q.dump(); 
    }

    public void dumpJSON(){
        q.dumpJSON();
    }

}
