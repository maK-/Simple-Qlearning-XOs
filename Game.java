import java.util.Random;
import java.util.Arrays;

//This class represents the Noughts and Crosses Game Environment.
public class Game{
    //Stores game board
    private int[] state = new int[9];
    public int turn = 1;
   
    public void newBoard(){
        //reset state of Game Board
        for(int i=0;i<state.length;i++){
            state[i] = 0;
        }
        //randomize who takes first turn
        Random r = new Random();
        boolean whoFirst = r.nextBoolean();
        if(whoFirst)    turn = 1;
        else    turn = 2;
    } 

    public String getState(){
        String s = Arrays.toString(state);
        String theState = s.replaceAll("[^\\d]","");
        return theState;        
    }

    //Print out board - Debugging purposes...
    public void printState(String state){
        char[] sp = state.toCharArray();
        System.out.println(sp[0]+" "+sp[1]+" "+sp[2]);
        System.out.println(sp[3]+" "+sp[4]+" "+sp[5]);
        System.out.println(sp[6]+" "+sp[7]+" "+sp[8]+"\n");
    }

    //return next state string
    public String getNextState(int who, int action){
        int[] nextState = state;
        nextState[action] = who;
        String s = Arrays.toString(nextState);
        String theState = s.replaceAll("[^\\d]","");
        return theState;
    }

    public void makeMove(int who, int move){
        state[move] = who;
        if(who == 1)    turn=2;
        else    turn=1;
    }

    public int whoWins(){
        if((state[0]==state[1] & state[1]==state[2]) & (state[0]!=0))
            return state[0]; //top row
        else if((state[3]==state[4] & state[4]==state[5]) & (state[3]!=0))
            return state[3]; //middle row
        else if((state[6]==state[7] & state[7]==state[8]) & (state[6]!=0))
            return state[6]; //bottom row
        else if((state[0]==state[3] & state[3]==state[6]) & (state[0]!=0))
            return state[0]; //left side down
        else if((state[1]==state[4] & state[4]==state[7]) & (state[1]!=0))
            return state[1]; //middle down
        else if((state[2]==state[5] & state[5]==state[8]) & (state[2]!=0))
            return state[2]; //right side down
        else if((state[0]==state[4] & state[4]==state[8]) & (state[0]!=0))
            return state[0]; //left to right diag down 
        else if((state[2]==state[4] & state[4]==state[6]) & (state[2]!=0))
            return state[2]; //right to left diag down
        else if(isDraw())
            return 3;
        else return 0;
    }

    public boolean isDraw(){
        int count = 0;
        for(int i=0; i < state.length; i++){
            if(state[i]==0)
                count++;
        }
        if(count > 0)   return false;
        else    return true;
    }

    public boolean isValidAction(int who, int move){
        if((state[move]==0) & (who == turn))
            return true;
        else return false;
    }
}
