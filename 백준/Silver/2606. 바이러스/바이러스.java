import java.util.*;
import java.io.*;

/**
 * BJ2606. 바이러스
 * 
 * - 웜 바이러스 걸리면 연결된 모든 컴퓨터도 다 걸림
 * 
 * - 플로이드 워샬 사용 -> 1이랑 연결되어 있는 개수 구하기
 * 
 * 1. 입력 받기
 *  - 컴퓨터 수
 *  - 네트워크 상에 연결되어 있는 컴퓨터 쌍의 수
 * 
 * 2. 플로이드 워샬
 * 
 * 3. 1이랑 연결되어 있는 개수 구하기
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    
    static int computerCnt, edgeCnt;
    static boolean[][] isConnected;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        computerCnt = Integer.parseInt(br.readLine());
        edgeCnt = Integer.parseInt(br.readLine());

        isConnected = new boolean[computerCnt+1][computerCnt+1];

        for (int idx = 0; idx < edgeCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int computerA = Integer.parseInt(st.nextToken());
            int computerB = Integer.parseInt(st.nextToken());

            isConnected[computerA][computerB] = true;
            isConnected[computerB][computerA] = true;
        }

        // 2. 플로이드 워샬
        for (int stopOver = 1; stopOver <= computerCnt; stopOver++) {
            for (int start = 1; start <= computerCnt; start++) {
                for (int end = 1; end <= computerCnt; end++) {
                    if (isConnected[start][end]) continue;

                    if (isConnected[start][stopOver] && isConnected[stopOver][end]) {
                        isConnected[start][end] = true;
                    }
                }
            }
        }

        // 3. 1이랑 연결되어 있는 개수 구하기
        int count = 0;
        for (int idx = 2; idx <= computerCnt; idx++) {
            if (isConnected[1][idx]) count++;
        }

        System.out.println(count);
    }
}
