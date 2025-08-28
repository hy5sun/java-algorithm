import java.util.*;
import java.io.*;

/**
 * BJ9205. 맥주 마시면서 걸어가기
 * 
 * 
 * [문제]
 * - 출발 : 상근이네 집
 * - 맥주 한 박스 : 맥주 20개
 * - 50미터에 한 병씩 마심 (50m를 가려면 그 직전에 맥주 한 병을 마셔야 함)
 * - 편의점 : 빈 병은 버리고, 새 맥주 병을 살 수 있음
 * - 박스에 들어있는 맥주는 최대 20병
 * - 편의점 나선 직후에도 50미터 가기 전에 맥주 한 병 마셔야 함
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 테스트 케이스 수
 *  - 편의점 개수 (n)
 *  - 상근이 집 좌표
 *  - 편의점 좌표 * n개 만큼
 *  - 페스티벌 좌표
 * 
 * 2. 각각의 위치를 정점이라고 생각하면서 양방향 그래프로 이어준다.
 *  - 단, 위치간의 사이가 1000m 이하일 경우에만 이어준다. (1000m 초과된 거리는 갈 수 없음)
 * 
 * 3. BFS를 통해 갈 수 있는지 확인한다.
 *  - 처음 시작 위치 인덱스 (0) 를 queue에 넣어주고, 방문처리 한다.
 *  - 큐가 빌 때까지 다음을 반복한다.
 *      - 맨 앞 요소를 꺼낸다. (현재 위치한 위치의 인덱스)
 *      - 현재 위치한 인덱스가 n+1 이면 축제 위치 인덱스이므로, happy 문자열을 반환한다.
 *      - 아직 축제에 도착하지 않았다면, 현재 위치랑 연결된 장소를 조회하고, 방문여부를 확인하여 큐에 넣는다.
 */

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    
    static int storeCnt;
    static int currentRow, currentColumn;
    static int festivalRow, festivalColumn;
    static int[][] storePosition;

    static List<Point> positions;
    static List<List<Integer>> graph;

    static class Point {
        int row, column;

        public Point(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        //*  - 테스트 케이스 수
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            //*  - 편의점 개수 (n)
            storeCnt = Integer.parseInt(br.readLine());

            positions = new ArrayList<>();
            graph = new ArrayList<>();

            //*  - 상근이 집 좌표
            //*  - 편의점 좌표 * n개 만큼
            //*  - 페스티벌 좌표
            for (int idx = 0; idx < storeCnt + 2; idx++) {
                st = new StringTokenizer(br.readLine().trim());

                int row = Integer.parseInt(st.nextToken());
                int column = Integer.parseInt(st.nextToken());

                positions.add(new Point(row, column));
            }
            
            // 인접 리스트 초기화 및 저장
            for (int idx = 0; idx < storeCnt + 2; idx++) {
                graph.add(new ArrayList<>());
            }

            // 2. 각각의 위치를 정점이라고 생각하면서 양방향 그래프로 이어준다.
            // *  - 단, 위치간의 사이가 1000m 이하일 경우에만 이어준다. (1000m 초과된 거리는 갈 수 없음)
            for (int positionAIdx = 0; positionAIdx < storeCnt + 2; positionAIdx++) {
                for (int positionBIdx = positionAIdx + 1; positionBIdx < storeCnt + 2; positionBIdx++) {
                    if (getDistance(positions.get(positionAIdx), positions.get(positionBIdx)) <= 1000) {
                        graph.get(positionAIdx).add(positionBIdx);
                        graph.get(positionBIdx).add(positionAIdx);
                    }
                }
            }

            sb.append(bfs(0)).append("\n");

        }

        System.out.println(sb.toString());
    }

    private static double getDistance(Point p1, Point p2) {
        return Math.abs(p1.row - p2.row) + Math.abs(p1.column - p2.column);
    }

    /*
     * 3. BFS를 통해 갈 수 있는지 확인한다.
     *  - 처음 시작 위치 인덱스 (0) 를 queue에 넣어주고, 방문처리 한다.
     *  - 큐가 빌 때까지 다음을 반복한다.
     *      - 맨 앞 요소를 꺼낸다. (현재 위치한 위치의 인덱스)
     *      - 현재 위치한 인덱스가 n+1 이면 축제 위치 인덱스이므로, happy 문자열을 반환한다.
     *      - 아직 축제에 도착하지 않았다면, 현재 위치랑 연결된 장소를 조회하고, 방문여부를 확인하여 큐에 넣는다.
     */
    private static String bfs(int startPositionIdx) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[storeCnt + 2];

        //- 처음 시작 위치 인덱스 (0) 를 queue에 넣어주고, 방문처리 한다.
        queue.add(startPositionIdx);
        visited[startPositionIdx] = true;

        while (!queue.isEmpty()) {
            // - 맨 앞 요소를 꺼낸다. (현재 위치한 위치의 인덱스)
            int currentPositionIdx = queue.poll();

            // - 현재 위치한 인덱스가 n+1 이면 축제 위치 인덱스이므로, happy 문자열을 반환한다.
            if (currentPositionIdx == storeCnt + 1) return "happy";

            // - 아직 축제에 도착하지 않았다면, 현재 위치랑 연결된 장소를 조회하고, 방문여부를 확인하여 큐에 넣는다.
            for (int nextPositionIdx : graph.get(currentPositionIdx)) {
                if (!visited[nextPositionIdx]) {
                    visited[nextPositionIdx] = true;
                    queue.offer(nextPositionIdx);
                }
            }
        }

        return "sad";
    }
}