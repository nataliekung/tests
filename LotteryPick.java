import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LotteryPick {
    private static final int LOTTERY_NUM = 7;
    private static final int MAX_NUM = 59;
    private static final int MAX_DIGIT = 9;
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output.txt";
    /**
     * Get the valid lottery numbers if the string provided is valid, otherwise empty list
     * @param s
     * @return the result list of the valid lottery numbers if the string is valid, empty list if not.
     */
    private List<List<Integer>> pickNumber(String s){
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> path = new ArrayList<Integer>();
        if(s == null || s.length() < LOTTERY_NUM || s.length() > LOTTERY_NUM * 2)
            return ret;
        char[] cs = s.toCharArray();

        traverse(cs, 0, path, ret);

        return ret;
    }

    /**
     * Recursively traverse the numbers to get valid lottery numbers, will return immediately if there is invalid number
     * @param s
     * @param pos
     * @param path
     * @param ret
     */
    private void traverse(char[] s, int pos, List<Integer> path, List<List<Integer>> ret){
        if(pos == s.length || path.size() == LOTTERY_NUM){
            if(pos == s.length && path.size() == LOTTERY_NUM){
                ret.add(new ArrayList<Integer>(path));
            }
            return;
        }

        //take one number, range between 1 - 9 inclusively
        int one = s[pos] - '0';
        if(one <= 0 || one > MAX_DIGIT || path.contains(one)){
            return;
        }else{
            path.add(one);
            traverse(s, pos + 1, path, ret);
            path.remove(path.size() - 1);
        }

        //take two numbers, range between 10 - 59 inclusively
        if(pos + 1 < s.length){
            int two = (s[pos] - '0') * 10 + (s[pos + 1] - '0');
            if(two <= MAX_DIGIT || two > MAX_NUM || path.contains(two)){
                return;
            }else{
                path.add(two);
                traverse(s, pos + 2, path, ret);
                path.remove(path.size() - 1);
            }
        }
    }

    public List<String> readFile(){
        List<String> stringList = new ArrayList<String>();
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(INPUT_FILE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                if(line.indexOf("[") == 0){
                    line = line.replaceAll("\\[", "").replaceAll("\\]", "");
                    if(line.length() == 0){
                        stringList.add(null);
                    }else{
                        String[] strs = line.split(",");
                        for(String s : strs){
                            s = s.replaceAll("\"", "");;
                            stringList.add(s.trim());
                        }
                    }
                }
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
        }
        catch(IOException ex) {
            System.out.println("Error reading file");
        }

        return stringList;
    }

    public void writeFile(List<String> result){
        BufferedWriter bw = null;
        FileWriter fw = null;
        String content = "Results: \n";

        for(String r : result){
            content += r +" \n";
        }
        try {
            fw = new FileWriter(OUTPUT_FILE);
            bw = new BufferedWriter(fw);
            bw.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @param argv
     */
    public static void main(String argv[]) {
        LotteryPick lp = new LotteryPick();
        List<String> output = new ArrayList<String>();
        List<String> picks = lp.readFile();

        for(String s : picks){
            List<List<Integer>> ret  = lp.pickNumber(s);

            if(ret.size() > 0){
                String retStr = "";
                retStr += s + " -> ";
                for(int rr: ret.get(0)){
                    retStr += rr + " ";
                }
                output.add(retStr);
            }
        }

        lp.writeFile(output);
    }
}
