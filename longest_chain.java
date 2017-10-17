import java.io.IOException;
import java.util.*;


public class Solution {
    int longest_chain(String[] w){

        Arrays.sort(w, new Comparator<String>(){
            public int compare(String a, String b){
                return a.length() - b.length();
            }
        });

        Set<String> dict = new HashSet<String>(Arrays.asList(w));

        Map<String, Integer> map = new HashMap<String, Integer>();

        int ret = 0;

        for(String s : dict){
            int len = helper(s, dict, map) + 1;

            ret = Math.max(ret, len);
        }
        return ret;
    }

    int helper(String word, Set<String> dict, Map<String, Integer> map){
        int res = 0;
        for(int i = 0; i < word.length(); i ++){
            StringBuilder s = new StringBuilder(word);
            s = s.deleteCharAt(i);
            String nWord = s.toString();
            if(dict.contains(nWord)){
                if(map.containsKey(nWord)){
                    res = Math.max(res, map.get(nWord));
                }else{
                    res = Math.max(res, helper(nWord, dict, map) + 1);
                }
            }
        }
        map.put(word, res + 1);

        return res;
    }
    public static void main(String[] args) throws IOException {
        Solution s  = new Solution();
//        String[] src = {"YYNN", "YYYN", "NYYN", "NNNY"};
        String[] src = {"6", "a", "b", "ba", "bca", "bda", "bdca"};
        int ret = s.longest_chain(src);
        System.out.println(ret);
    }
}


/********************************************************From website*********************************************************************************************/

static int longestChain(String[] words) {
        // has set to store all words
        Set<String> set = new HashSet<String>();
        
        // hashmap to store the longest chain starting from visited words
        // so that we dont need to calculate again if we meet it again
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        // add words to set
        for (String word : words) {
            set.add(word);
        }
        
        // global value of longest chain
        int longest = 0;
        
        // go through words and calculate the longest length of chain starting from each word
        for (String word : words) {
            /// longest chain of each word is always less of equal to its length(), so no need to calculate if it's greater
            if (word.length() > longest) {
                //depth first search to calculate local longest chain
                int length = dfs(word, set, map} + 1;
                // update the global longest value
                longest = Math.max(length, longest);
            }
        }
        
        return longest;
    }
    
    static int dfs(String word, Set<String> set, Map<String, Integer> map) {
        int result = 0;
        
        for (int i = 0; i < word.length(); i++) {
            // generate candidates for next word string
            String nextWord = word.substring(0,i) + word.substring(i + 1);
            
            // if candidate word doesn't exist, skip it
            if (!set.contains(nextWord)) {
                continue;
            }
            
            if (map.containsKey(nextWord)) {
                // if candidate word has been calculated before, just use it to update result
                result = Math.max(result, map.get(nextWord));
            } else {
                // if candidate word has not been calculated before
                result = Math.max(result, dfs(nextWord, set, map) + 1);
            }
        }
        
        // save the calculated value to map
        map.put(word, result +1);
        
        return result;
    }