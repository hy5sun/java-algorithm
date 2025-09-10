import java.util.*;
import java.io.*;

/**
 * BJ1613. 역사
 * 
 * - 일부 사건의 전후 관계가 주어질 때, 
 * - 앞에 있는 번호 사건이 먼저 일어나면 -1, 뒤에 있는 사건이 먼저 일어나면 1
 * - 모르면 0 출력
 * 
 * 1. 입력 받기
 *  - 사건 개수, 관계 개수
 *  - 전후 관계
 *  - 전후 관계 알고 싶은 사건 쌍의 수
 *  - 사건 번호 2개씩
 * 
 * 2. 플로이드 워샬
 * 
 * 3. 전후 관계 알아내기
 *  - time[사건A][사건B] = true -> 사건A가 먼저 일어남
 *  - time[사건B][사건A] = true -> 사건B가 먼저 일어남
 *  - time[사건A][사건B] == false && time[사건B][사건A] == false -> 모름
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    
    static int caseCnt, relationshipCnt, questionCnt;
    static boolean[][] time;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        st = new StringTokenizer(br.readLine().trim());

        caseCnt = Integer.parseInt(st.nextToken());
        relationshipCnt = Integer.parseInt(st.nextToken());

        time = new boolean[caseCnt+1][caseCnt+1];

        for (int idx = 0; idx < relationshipCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int case1 = Integer.parseInt(st.nextToken());
            int case2 = Integer.parseInt(st.nextToken());

            time[case1][case2] = true;
        }
        
        // 2. 플로이드 워샬
        for (int stopOver = 1; stopOver <= caseCnt; stopOver++) {
            for (int case1 = 1; case1 <= caseCnt; case1++) {
                for (int case2 = 1; case2 <= caseCnt; case2++) {
                    if (time[case1][case2]) continue;

                    if (time[case1][stopOver] && time[stopOver][case2]) {
                        time[case1][case2] = true;
                    }
                }
            }
        }

        // 3. 전후 관계 알아내기
        questionCnt = Integer.parseInt(br.readLine());
        for (int idx = 0; idx < questionCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int case1 = Integer.parseInt(st.nextToken());
            int case2 = Integer.parseInt(st.nextToken());

            if (time[case1][case2] && !time[case2][case1]) sb.append(-1);
            else if (!time[case1][case2] && time[case2][case1]) sb.append(1);
            else if (!time[case1][case2] && !time[case2][case1]) sb.append(0);

            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}
