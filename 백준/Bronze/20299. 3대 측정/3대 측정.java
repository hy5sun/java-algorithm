import java.util.*;
import java.io.*;

/**
 * BJ20299. 3대 측정
 * 
 * [문제]
 * - 모든 팀원의 레이팅이 L 이상이고, 팀원 세 명의 레이팅 합이 K 이상인 팀만 가입 가능
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int teamCnt, K, L;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        teamCnt = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        int count = 0;

        for (int idx = 0; idx < teamCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int person1 = Integer.parseInt(st.nextToken());
            int person2 = Integer.parseInt(st.nextToken());
            int person3 = Integer.parseInt(st.nextToken());

            if (person1 + person2 + person3 >= K && person1 >= L && person2 >= L && person3 >= L) {
                count++;
                sb.append(person1).append(" ").append(person2).append(" ").append(person3).append(" ");
            }
        }

        System.out.println(count + "\n" + sb.toString());
    }
}
