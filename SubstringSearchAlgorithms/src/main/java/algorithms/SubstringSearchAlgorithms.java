package algorithms;

import java.util.ArrayList;
import java.util.List;

public class SubstringSearchAlgorithms {
    /**
     * @param string input string for search
     * @param pattern pattern search
     * @return All start occurrences of matches
     * */
    public static List<Integer> directSearch(String string, String pattern){
        ArrayList<Integer> matchesPositions = new ArrayList<>();
        int patternLength = pattern.length();
        int matchCounter = 0;
        for (int i=0;i<string.length();i++){
            Character ch = string.charAt(i);
            if (ch.equals(pattern.charAt(matchCounter))) matchCounter++;
            else matchCounter = 0;
            if (matchCounter == patternLength)
            {
                matchesPositions.add(i-patternLength+1);
                matchCounter=0;
            }
        }
        return matchesPositions;
    }
    /**
     * @param string input string for search
     * @param pattern pattern search
     * @return All start occurrences of matches
     * */
    public static List<Integer> kmpSearch(String string, String pattern){
        ArrayList<Integer> matches = new ArrayList<>();
        int[] prefix = getPrefixArray(pattern);
        int i=0;
        int j=0;
        while(i < string.length()){
            if (string.charAt(i) == pattern.charAt(j)){
                i++;
                j++;
                if (j==pattern.length()) {
                    matches.add(i - pattern.length());
                    j=0;
                }
            }
            else if(j==0){
                i++;
            } else {
                j = prefix[j-1];
            }
        }
        return matches;
    }

    private static int[] getPrefixArray (String pattern){
        int[] prefix = new int[pattern.length()];
        prefix[0] = 0;
        int j =0;
        int i =1;
        if (pattern.charAt(i) == pattern.charAt(j)) {
            prefix[i]=j+1;
            i++;
            j++;
        }
        else if (j==0){
            prefix[i] = 0;
            i++;
        }
        else j= prefix[j-1];
        return prefix;
    }

    /**
     * @param string input string for search
     * @param pattern pattern search
     * @return All start occurrences of matches
     * */
    public static List<Integer> bmSearch(String string, String pattern){
        ArrayList<Integer> matches = new ArrayList<>();
        int m = pattern.length();
        int n = string.length();
        int[] badChar = new int[256];
        badChar = badCharHeuristic(pattern,m,badChar);
        int s=0;
        while(s <= (n-m)){
            int j= m-1;
            while (j>=0 && pattern.charAt(j) == string.charAt(s+j))
                j--;
            if (j<0) {
                matches.add(s);
                s += (s + m < n) ? m - badChar[string.charAt(s + m)] : 1;
            }
            else
                s+=Math.max(1,j-badChar[string.charAt(s+j)]);
        }
        return matches;
    }

    private static int[] badCharHeuristic( String str, int size,int badchar[])
    {
        for (int i=0;i<size;i++)
            badchar[i]= -1;
        for (int i=0;i<size;i++)
            badchar[str.charAt(i)] = i;
        return badchar;
    }

    /**
     * @param string input string for search
     * @param pattern pattern search
     * @return All start occurrences of matches
     * */
    public static List<Integer> rkSearch(String string, String pattern) {
        ArrayList<Integer> matches = new ArrayList<>();
        final int d = 256;
        final int q = 173;
        int M = pattern.length();
        int N = string.length();
        int i, j;
        int p = 0;
        int t = 0;
        int h = 1;
        for (i = 0; i < M - 1; i++)
            h = (h * d) % q;
        for (i = 0; i < M; i++) {
            p = (d * p + pattern.charAt(i)) % q;
            t = (d * t + string.charAt(i)) % q;
        }
        for (i = 0; i <= N - M; i++) {
            if (p == t) {
                for (j = 0; j < M; j++) {
                    if (string.charAt(i + j) != pattern.charAt(j))
                        break;
                }
                if (j == M)
                    matches.add(i);
            }
            if (i < N - M) {
                t = (d * (t - string.charAt(i) * h) + string.charAt(i + M)) % q;
                if (t < 0)
                    t = (t + q);
            }
        }
        return matches;
    }
}
