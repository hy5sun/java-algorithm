import java.util.*;
import java.io.*;

/**
 * SW1263. 사람 네트워크2
 * 
 * - 네트워크 상에서 한 사용자가 다른 모든 사람에게 얼마나 가까운지
 * - CC(i) = j * dist(i, j) 의 합
 */
public class Solution {
    static final int INF = Integer.MAX_VALUE;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int testCaseCnt, peopleCnt;
    static int[][] distance;

    public static void main(String[] args) throws IOException {
        testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            st = new StringTokenizer(br.readLine().trim());

            peopleCnt = Integer.parseInt(st.nextToken());

            distance = new int[peopleCnt+1][peopleCnt+1];

            for (int person1 = 1; person1 <= peopleCnt; person1++) {
                for (int person2 = 1; person2 <= peopleCnt; person2++) {
                    int isConnected = Integer.parseInt(st.nextToken());
                    if (isConnected == 1) {
                        distance[person1][person2] = 1;
                        distance[person2][person1] = 1;
                    }
                    if (isConnected == 0) {
                        distance[person1][person2] = INF;
                        distance[person2][person1] = INF;
                    }

                    if (person1 == person2) {
                        distance[person1][person2] = 0;
                    }
                }
            }

            for (int stopOver = 1; stopOver <= peopleCnt; stopOver++) {
                for (int person1 = 1; person1 <= peopleCnt; person1++) {
                    for (int person2 = 1; person2 <= peopleCnt; person2++) {
                        if (distance[person1][stopOver] != INF && distance[stopOver][person2] != INF)
                            distance[person1][person2] = Math.min(distance[person1][person2], distance[person1][stopOver] + distance[stopOver][person2]);
                    }
                }
            }

            int minSum = Integer.MAX_VALUE;

            for (int person = 1; person <= peopleCnt; person++) {
                int sum = 0;
                for (int other = 1; other <= peopleCnt; other++) {
                    if (distance[person][other] != INF)
                        sum += distance[person][other];
                }

                minSum = Math.min(minSum, sum);
            }

            sb.append("#").append(testCase).append(" ").append(minSum).append("\n");
        }

        System.out.println(sb.toString());
    }
}
