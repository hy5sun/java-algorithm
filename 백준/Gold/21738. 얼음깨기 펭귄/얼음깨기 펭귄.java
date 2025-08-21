import java.io.*;
import java.util.*;

/**
 * BJ21738. 얼음깨기 펭귄
 * 
 * [문제]
 * 1. 얼음 종류
 *  - 지지대 역할 얼음 (빨간색) -> 일반 얼음을 지탱해줘서 일반 얼음이 깨지지 않도록 도와줌
 *  - 일반 얼음 
 *      -> 1개의 지지대만 연결되어도 깨지지 않음
 *      -> 펭귄이 올라가 있는 얼음은 2개 이상의 지지대 얼음과 연결되어 있어야 함
 * 2. 지지되어 있다 == 서로 다른 얼음을 통해 연결 관계가 이어져 있음
 * 
 * [풀이]
 * 1. 입력값
 *  - 얼음 블록 개수 N, 지지대 얼음 개수 S, 펭귄 위치 얼음 번호 P
 *      -> 1 ~ S번까지는 지지대 역할
 *  - (N-1)줄로 2개의 정수 A, B가 주어지는데, A 얼음과 B 얼음은 연결되어 있음.
 * 
 * 2. 인접 리스트를 만들어 연결된 얼음을 저장한다.
 * 
 * 3. 최대한 많이 부수려면 멀리 위치한 지지대와 연결된 얼음을 부숴야 한다.
 *    따라서 가장 가까운 2개의 지지대를 찾고, 그 지지대와 펭귄과의 거리를 계산하여 최대로 부술 수 있는 개수에서 빼준다.
 *    => BFS 사용
 * 
 * 4. breakCount를 출력한다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static List<List<Integer>> linkedIce = new ArrayList<>();

    static int iceCount, supportCount, penguinBlock;
    static int breakCount;

    public static void main(String[] args) throws IOException {
        // 1. 입력값
        //  - 얼음 블록 개수 N, 지지대 얼음 개수 S, 펭귄 위치 얼음 번호 P
        //      -> 1 ~ S번까지는 지지대 역할
        //  - (N-1)줄로 2개의 정수 A, B가 주어지는데, A 얼음과 B 얼음은 연결되어 있음.
        st = new StringTokenizer(br.readLine().trim());

        iceCount = Integer.parseInt(st.nextToken());
        supportCount = Integer.parseInt(st.nextToken());
        penguinBlock = Integer.parseInt(st.nextToken());

        // 인접 리스트 초기화
        for (int idx = 0; idx <= iceCount; idx++) {
            linkedIce.add(new ArrayList<>());
        }

        // 2. 인접 리스트를 만들어 연결된 얼음을 저장한다.
        for (int idx = 0; idx < iceCount-1; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            linkedIce.get(from).add(to);
            linkedIce.get(to).add(from);
        }

        // 최대로 부실 수 있는 얼음 수 초기화
        breakCount = iceCount - 1;

        // 3. 가까운 지지대 2개를 찾아내고, 나머지를 부순다.
        breakIce(penguinBlock);

        // 4. breakCount를 출력한다.
        sb.append(breakCount);
        System.out.println(sb.toString());
    }

    private static void breakIce(int start) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[iceCount+1];

        queue.add(start);
        visited[start] = true;

        int distance = -1; // 펭귄으로부터의 현재 탐색 중인 노드까지의 거리 (펭귄 위치한 곳도 빼줘야 하니까 -1)
        int nearSupportCount = 0; // 탐색한 지지대 수 (가장 가까운 지지대부터)

        // 가장 가까운 지지대 2개만 찾아내고, 나머지는 깨버린다.
        while (!queue.isEmpty() && nearSupportCount < 2) {
            // 거리 증가
            distance++;

            // 현재 레벨에서 처리해야 되는 노드 수
            int levelSize = queue.size();

            for (int idx = 0; idx < levelSize; idx++) {

                int iceIdx = queue.poll();

                // 얼음이 지지대 얼음이고, 아직 탐색된 지지대 수가 2개 미만이면 해당 지지대 얼음을 탐색 성공한다.
                if (iceIdx <= supportCount && nearSupportCount < 2) {
                    // 가까운 지지대 2개는 깨서 안되니까 제외시키기
                    breakCount -= distance;
                    nearSupportCount++;
                    continue;
                }

                // 현재 얼음을 정점으로 가지고 있는 얼음들 다 돌려서 queue에 더하기
                for (int next : linkedIce.get(iceIdx)) {
                    if (!visited[next]) {
                        queue.add(next);
                        visited[next] = true;
                    }
                }
            }
        }
    }
}
