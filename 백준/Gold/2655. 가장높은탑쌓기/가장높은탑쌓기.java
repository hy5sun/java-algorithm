import java.io.*;
import java.util.*;

/**
 * BJ2655. 가장 높은 탑 쌓기
 * 
 * - 정사각형 직육면체 벽돌을 사용해 탑을 쌓는다.
 * - 아래에서 위로 쌓으면서 만든다.
 * - 아래 조건을 만족하면서 가장 높은 탑 쌓기
 *  1) 벽돌을 회전시킬 수 없다.
 *  2) 밑면의 넣ㅂ이가 같은 벽돌은 없으며, 무게가 같은 벽돌도 없다.
 *  3) 벽돌 높이는 같을 수도 있다.
 *  4) 탑을 쌓을 때 밑면이 좁은 벽돌 위에 밑면이 넓은 벽돌을 놓을 수 없다.
 *  5) 무게가 무거운 벽돌을 가벼운 놈 위에 놓을 수 없다.
 * 
 * - 출력 : 사용된 벽돌 수 -> 위 벽돌부터 아래 순으로 벽돌 번호 출력 
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 블록 수 (최대 100개)
 *  - 밑면 넓이, 벽돌 높이, 무게 (1 ~ 블록 수)
 * 
 * 2. 은진 언니 코드를 뺏기다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int blockCnt;
    static Block[] blocks;

    static class Block implements Comparable<Block> {
        int idx, area, height, weight;

        public Block(int idx, int area, int height, int weight) {
            this.idx = idx;
            this.area = area;
            this.height = height;
            this.weight = weight;
        }

        @Override
        public int compareTo(Block other) {
            return other.area - this.area;
        }
    }

    public static void main(String[] args) throws IOException {
        blockCnt = Integer.parseInt(br.readLine());

        blocks = new Block[blockCnt];

        for (int idx = 0; idx < blockCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int area = Integer.parseInt(st.nextToken());
            int height = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            blocks[idx] = new Block(idx + 1, area, height, weight);
        }

        // 밑면 넓이 내림차순 정렬
        Arrays.sort(blocks);

        int[] dp = new int[blockCnt]; // dp[i] : i번째 블록을 꼭대기로 할 때 최대 높이
        int[] prev = new int[blockCnt]; // prev[i] : i번째 블록 아래에 어떤 블록이 있는지

        int maxHeight = 0;
        int maxIdx = 0;

        for (int topIdx = 0; topIdx < blockCnt; topIdx++) {
            dp[topIdx] = blocks[topIdx].height;
            prev[topIdx] = -1;

            for (int prevIdx = 0; prevIdx < topIdx; prevIdx++) {
                // 밑에 두려는 애가 더 밑넓이가 넓고, 무게가 더 무거우면
                if (blocks[topIdx].weight < blocks[prevIdx].weight && blocks[topIdx].area < blocks[prevIdx].area) {
                    if (dp[topIdx] < dp[prevIdx] + blocks[topIdx].height) { // 지금 topIdx를 꼭대기에 뒀을 때 높이 보다 밑에 쟤를 깔고서 뒀을 때가 더 높이가 높다면
                        dp[topIdx] = dp[prevIdx] + blocks[topIdx].height; // 더 높은 걸로 갱신
                        prev[topIdx] = prevIdx; //topIdx 밑에는 prevIdx가 오도록 함
                    }
                }
            }

            if (dp[topIdx] > maxHeight) {
                maxHeight = dp[topIdx];
                maxIdx = topIdx;
            }
        }

        // 경로 역추적
        List<Integer> result = new ArrayList<>();

        while (maxIdx != -1) {
            result.add(blocks[maxIdx].idx);
            maxIdx = prev[maxIdx];
        }

        sb.append(result.size()).append("\n");

        for (int idx = 0; idx < result.size(); idx++) {
            sb.append(result.get(idx)).append("\n");
        }

        System.out.println(sb.toString());
    }
}
