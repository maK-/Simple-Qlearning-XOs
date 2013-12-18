import java.util.*;
import org.json.simple.*;
import java.io.*;
public class Q{    
    /*
     * This Stores state as a key, in String format
     * then double Q values for each action in state.
     */
    HashMap<String, double[]> qMap = new HashMap<String, double[]>();
    String mindImport = "";
    
    public void importMind(String mind){
        mindImport = mind;
        boolean isData = true;
        String mindData = "";
        try{
            mindData = strFromFile(mindImport);
        }
        catch(NoSuchElementException e){
            System.out.println("Nothing in file... no data imported!");
            isData = false;
        }
        if(isData){
            reuseMind(mindData);
        }

    }

    /*
     * Lookup a state and find the best action to take
     * if more than single action is the same
     * pick random from bunch
     */
    public int getBestAction(String state){
        double[] actions = new double[9];
        if(qMap.containsKey(state)){
            actions = qMap.get(state); //return all action values
        }
        double highestQ = -5000;
        ArrayList<Integer> next = new ArrayList<Integer>(); //possible choices
        int choice = 0;
        for(int i=0; i < actions.length; i++){
            if(actions[i] >= highestQ){
                highestQ = actions[i];
                choice = i;
            }
        }
        for(int i=0; i < actions.length; i++){
            if(actions[i] == actions[choice]){
                next.add(i);
            }
        }
        
        //return a random choice if more than one 
        //or single choice if only one
        if(next.size()==1){
            return next.get(0);
        }
        else if(next.size() > 1){
            Random r = new Random();
            int random = r.nextInt(next.size());
            return next.get(random);
        }
        else{
            //System.out.println(next.size());
            Random r = new Random();
            int random = r.nextInt(9);
            return random;
        }
    }

    /*
     * Get Q value for particular state/action
     */
    public double getQ(String state, int action){
        double[] actions = new double[9];
        if(qMap.containsKey(state)){
            actions = qMap.get(state);
        }
        return actions[action];
    }


    /*
     * Update Q value for particular state/action with reward
     */
    public void update_Q(String state, int action, double reward){
        double[] actions = new double[9];
        if(qMap.containsKey(state)){
            actions = qMap.get(state);
        }
        actions[action] = reward;
        qMap.put(state, actions);
    }

    public void update_Q_reuse(String state, double[] actionsData){
        qMap.put(state, actionsData);
    }

    /*
        This populates our Hashmap with the Q values from a file
    */
    public void reuseMind(String data){
        Object obj2 = JSONValue.parse(data);
        JSONObject arr = (JSONObject) obj2;
        parseJson(arr);
        
    }
    
    /*
     * Parse our Json object to pull array and object info
     */
    public void parseJson(JSONObject arr){
        Set<Object> set = arr.keySet();
        Iterator<Object> iterator = set.iterator();
        double[] thisk = new double[9];
        String state = "";
        while (iterator.hasNext()){
            Object obj = iterator.next();
            if (arr.get(obj) instanceof JSONArray){
                //State and actions into Q
                state = obj.toString();
                thisk = getArray(arr.get(obj));
                update_Q_reuse(state, thisk);
            } 
            else{
                if (arr.get(obj) instanceof JSONObject) {
                    parseJson((JSONObject) arr.get(obj));
                }   
                else{
                    System.out.println(obj.toString());
                }
            }
        }
    }

   /*
    * Parse a JSONArray into a double array
    */
   public double[] getArray(Object obj){
    JSONArray jsonArr = (JSONArray) obj;
    double[] vals = new double[9];
    for (int i=0; i < jsonArr.size(); i++) {
        vals[i] = (double)jsonArr.get(i);
    }
    return vals;
} 

    
    /* 
    public void dump(){
        System.out.println(qMap.toString());
    }
    
    public static void main(String[] args){
        double x = 100.0;
        String one = "000000001";
        String two = "000200001";
        String thr = "100200001";
        Q q = new Q();
        q.update_Q(two, 1, x);
        q.update_Q(two, 2, 10.0);
        q.update_Q(two, 4, 16.33);
        q.update_Q(one, 2, 200.0);
        q.update_Q(two, 8, 150.0);
        q.update_Q(thr, 3, 0.0);
        q.update_Q(thr, 5, 0.0);
        q.update_Q(thr, 4, -2.0);
        System.out.println(q.getBestAction(two));
        System.out.println(q.getQ(two, 8));
        System.out.println(q.getBestAction(one));
        System.out.println(q.getBestAction(thr));
        q.dump();
    }
    */
    //Print out board - Debugging purposes...
    public void printState(String state){
        char[] sp = state.toCharArray();
        System.out.println(sp[0]+" "+sp[1]+" "+sp[2]);
        System.out.println(sp[3]+" "+sp[4]+" "+sp[5]);
        System.out.println(sp[6]+" "+sp[7]+" "+sp[8]+"\n");
    }

    public void dump(){
        Set<String> enumk = qMap.keySet();
        Iterator<String> iter = enumk.iterator();
        while(iter.hasNext()) {
            String key = iter.next();
            double[] val = qMap.get(key);
            System.out.print(key+":  ");            
            for(int i=0; i<val.length; i++){
                System.out.print("a"+i+":"+val[i]+",");
            }
            System.out.println();
        }
    }
    
    public void dumpJSON(){
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        double[] val = new double[9];
        Set<Map.Entry<String, double[]>> set = qMap.entrySet();
        String key = "";
        for (Map.Entry<String, double[]> me : set) {
            key = me.getKey();
            val = me.getValue();
            for(int i=0; i<val.length; i++){
                arr.add(val[i]);
            }
            obj.put(key, arr);
            arr = new JSONArray();
        }
        
        String jsonString = JSONValue.toJSONString(obj);
        strToFile(mindImport, jsonString);
    }
    
   //write string data to a file
    public void strToFile(String filename, String data){
        File file = new File(filename);
        try{
            FileWriter dataf = new FileWriter(file);
            dataf.write(data);
            dataf.close();
        }
        catch(FileNotFoundException e){
            System.out.println("\nFile not found!");
        }
        catch(IOException e){
            System.out.println("\nIO Error!");
        }
    }

    //Reads string from a file
    public String strFromFile(String filename){
        String mydata = "";
        File file = new File(filename);
        try{
            Scanner data = new Scanner(file);
            mydata = data.nextLine();
            data.close();
        }
        catch(FileNotFoundException e){
            System.out.println("\nFile not found!");
        }
        return mydata;
    }  



}
