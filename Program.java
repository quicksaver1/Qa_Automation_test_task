import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Program {
    private static Map<String,String> valuesTable = new HashMap<>();
    private static int lineCounter = 1;
    public static void main(String[] args) throws FileNotFoundException
    {
        if (args.length != 2){
            throw new IllegalArgumentException("Wrong amount of arguments: "+args.length+" instead of 2");
        }
        /* Files initialization */
        FileReader configFileReader =new FileReader(args[0]);
        FileReader textFileReader = new FileReader(args[1]);
        Scanner configFile=new Scanner(configFileReader);
        Scanner textFile = new Scanner(textFileReader);
        /*Values initialization*/
        while(configFile.hasNextLine()) {
            parseConfigLine(configFile.nextLine());
            lineCounter++;
        }
        configFile.close();
        ArrayList<String> lines= new ArrayList<>();
        while(textFile.hasNextLine()) {
            lines.add(textFile.nextLine());
        }
        textFile.close();
        for (int i=lines.size()-1;i>=0;i--){
            String printedLine=lines.get(i);
            for(Map.Entry<String,String> entry : valuesTable.entrySet() ){
                printedLine=printedLine.replace(entry.getKey(),entry.getValue());
            }
            System.out.println(printedLine);
        }
    }

    private static void parseConfigLine(String nextLine)
    {
        int equalityIndex=nextLine.indexOf("=");
        String leftValue;
        String rightValue;
        if (equalityIndex<0){
            throw new IllegalArgumentException("Config File Error: equality sign is missing at line "+ lineCounter);
        }
        if (nextLine.indexOf("=",equalityIndex+1)>=0){
            throw new IllegalArgumentException("Config File Error: there are more than one equality signs at line "+ lineCounter);
        }
        leftValue = nextLine.substring(0,equalityIndex).trim();
        rightValue=nextLine.substring(equalityIndex+1).trim();
        if(leftValue.length()==0||rightValue.length()==0){
            throw new IllegalArgumentException("Config File Error: empty value at line "+ lineCounter);
        }
        valuesTable.put(leftValue,rightValue);
    }
}
