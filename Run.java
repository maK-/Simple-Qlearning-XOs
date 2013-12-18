//Running our game
public class Run{
    public static int countRuns = 0;
    public static Player p1 = new Player("QData.json");
    public static RPlayer p2 = new RPlayer();
    public static void main(String[] args){
        Game game = new Game();
        int[] results = new int[4];

        for(int i=0; i<Integer.parseInt(args[0]); i++){
            results[run(game)]++;
            countRuns += 1;
            if(countRuns % 100000==0){
                System.out.println("1:"+results[1]+", 2:"+results[2]+", draw:"+results[3]);
            }
        }
        for(int i=1; i<results.length; i++){
            if(i==3){
                System.out.println("Draws: "+results[i]);
            }
            else{
                System.out.println("Player "+i+": "+results[i]);
            }
        }
        p1.dumpJSON();
        //System.out.println("-------");
        //p1.dump();
    }
    
    public static int run(Game game){
        double reward = 0.0;
        double yb = 0.0;
        double gamma = 0.5;
        String nextState = "";
        String x = "";
        int a = -1;
        String y = "";
        int b = -1;
        game.newBoard();
        while(game.whoWins() <= 0){
            if(game.turn == 1){
                //Fill in our current move & state
                x = game.getState();
                p1.nextAction(game.getState());
                while(!game.isValidAction(1,p1.currentMove)){
                    p1.nextAction(game.getState());
                }
                a = p1.currentMove;
                y = game.getNextState(1, p1.currentMove);
                yb = p1.retQNextAction(y);
                
                game.makeMove(1, p1.currentMove);
            }

            //2nd player takes move
            else{
                x = game.getState();
                p2.nextAction(game.getState());
                while(!game.isValidAction(2, p2.currentMove)){
                    p2.nextAction(game.getState());
                }
                a = p2.currentMove;
                y = game.getNextState(2, p2.currentMove);
                yb = p1.retQNextAction(y);
                game.makeMove(2, p2.currentMove);
            }

            reward = gamma * yb;
            p1.rewards(x, a, reward);
        }
        if(game.whoWins() == 1){
            reward = 100.0;
            p1.directReward(game.getState(),a,reward);
            reward = 0.0;
        }
        else if(game.whoWins() == 2){
            reward = -100.0;
            p1.directReward(game.getState(),a,reward);
            reward = 0.0;
        }
        return game.whoWins();
    }

}
