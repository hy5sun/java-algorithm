import java.util.*;
import java.io.*;

public class Main {
    static final int IMPORTANCE = 0;
    static final int STUDY_TIME = 1;
    
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int maxStudyTime, subjectCnt;
    static int[][] subjects;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        maxStudyTime = Integer.parseInt(st.nextToken());
        subjectCnt = Integer.parseInt(st.nextToken());

        subjects = new int[subjectCnt][2];
        dp = new int[subjectCnt][maxStudyTime+1];

        for (int idx = 0; idx < subjectCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int importance = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            subjects[idx] = new int[]{importance, time};

            Arrays.fill(dp[idx], -1);
        }

        calculate(subjectCnt-1, maxStudyTime);

        System.out.println(dp[subjectCnt-1][maxStudyTime]);
    }

    private static int calculate(int subjectIdx, int remainStudyTime) {
        if (subjectIdx < 0 || remainStudyTime <= 0) return 0;

        if (dp[subjectIdx][remainStudyTime] != -1) return dp[subjectIdx][remainStudyTime];

        if (subjects[subjectIdx][STUDY_TIME] > remainStudyTime) {
            return dp[subjectIdx][remainStudyTime] = calculate(subjectIdx-1, remainStudyTime);
        }

        else {
            return dp[subjectIdx][remainStudyTime] =
                Math.max(calculate(subjectIdx-1, remainStudyTime), subjects[subjectIdx][IMPORTANCE] + calculate(subjectIdx-1, remainStudyTime - subjects[subjectIdx][STUDY_TIME]));
        }
    }
}
