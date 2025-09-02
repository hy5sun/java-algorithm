import java.io.*;
import java.util.*;

/**
 * 보급로
 * 
 * [문제]
 * - S -> G 까지 가기 위한 도로 복구 작업을 빠르게 수행하려 함.
 * - 깊이가 1 ->> 복구 시간 = 1
 * - 상하좌우 1칸씩
 * - 각 칸마다 파여진 도로의 깊이가 주어짐.
 * - 현재 위치한 도로를 복구해야만 다른 곳 이동 가능
 * - 출발 ~ 도착지점까지의 거리는 상관없음
 * - 출발지와 도착지는 좌상단, 우상단, 입력 데이터는 0으로 표시
 * - 결국엔 가장 적은 숫자가 있는 곳으로 이동하는 게 최선. 
 *      - 각각의 칸에 있는 숫자 = 가중치
 * 
 *
 * [풀이]
 * 1. 입력 받기
 *  - 테스트 케이스 수
 *  - 지도 크기 N
 *      - 지도 배열 초기화
 *  - 지도에 구멍 높이 크기
 * 
 * 2. distance 초기화 : 모두 다 Integer.MAX_VALUE로 저장
 * 
 * 3. 다익스트라 구현
 *  - distance (int[][]) : 출발 지점에서 현재 지점까지의 최소 가중치
 *  - PQ : 가중치가 더 낮은 놈을 우선으로 하는 큐
 *  - visited (boolean[][]) : 방문 여부를 저장할 배열
 * 
 *  - 큐에 new Node(출발 row, 출발 column, weight) 넣기
 *  - vistied[출발row][출발column] 방문처리 하기
 *  - 큐가 빌 때까지 다음을 반복
 *      - 큐에서 poll() 하기
 *      - 현재 출발 위치에서 상하좌우를 살펴서 nextRow, nextColumn에 저장
 *         - 범위에서 안 벗어나는지? continue
 *         - 이미 방문했는지? continue
 *         - 방문 처리하고, new Node(다음 row, 다음 column, weight) 넣기
 *         - 현재 distance의 값 vs 방금 poll한 놈에서 나온 weight + 지금 wieght 중에 더 작은 값을 distance에 저장
 * 
 * 4. distance[length-1][length-1] 값을 출력한다.
 */
public class Solution {
    static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String input;
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int testCaseCnt, length;
    static int[][] map;
    static int[][] distance;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 테스트 케이스 수
        testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // - 지도 크기 N
            length = Integer.parseInt(br.readLine());

            //*      - 지도 배열 초기화
            map = new int[length][length];
            distance = new int[length][length];

            //*  - 지도에 구멍 높이 크기
            for (int rowIdx = 0; rowIdx < length; rowIdx++) {

                input = br.readLine();

                for (int columnIdx = 0; columnIdx < length; columnIdx++) {
                    map[rowIdx][columnIdx] = input.charAt(columnIdx) - '0';
                }
            }

            // 2. distance 초기화 : 모두 다 Integer.MAX_VALUE로 저장
            for (int rowIdx = 0; rowIdx < length; rowIdx++)
                Arrays.fill(distance[rowIdx], Integer.MAX_VALUE);

            // 3. 다익스트라
            findPerfectRoad();

            // 4. distance[length-1][length-1] 값을 출력한다.
            sb.append("#").append(testCase).append(" ").append(distance[length-1][length-1]).append("\n");
        }

        System.out.println(sb.toString());
    }

    /**
     * 3. 다익스트라 구현
     *  - distance (int[][]) : 출발 지점에서 현재 지점까지의 최소 가중치
     *  - PQ : 가중치가 더 낮은 놈을 우선으로 하는 큐
     *  - visited (boolean[][]) : 방문 여부를 저장할 배열
     * 
     *  - 큐에 new Node(출발 row, 출발 column, weight) 넣기
     *  - vistied[출발row][출발column] 방문처리 하기
     *  - 큐가 빌 때까지 다음을 반복
     *      - 큐에서 poll() 하기
     *      - 현재 출발 위치에서 상하좌우를 살펴서 nextRow, nextColumn에 저장
     *         - 범위에서 안 벗어나는지? continue
     *         - 이미 방문했는지? continue
     *         - 방문 처리하고, new Node(다음 row, 다음 column, weight) 넣기
     *         - 현재 distance의 값 vs 방금 poll한 놈에서 나온 weight + 지금 wieght 중에 더 작은 값을 distance에 저장
     */
    private static void findPerfectRoad() {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        boolean[][] visited = new boolean[length][length];

        // - 큐에 new Node(출발 row, 출발 column, weight) 넣기
        queue.offer(new Node(0, 0, 0));

        //  - 출발 지점의 distance는 0으로 설정
        distance[0][0] = 0;

        // - 큐가 빌 때까지 다음을 반복
        while (!queue.isEmpty()) {
            //*      - 큐에서 poll() 하기
            Node current = queue.poll();

            int currentRow = current.row;
            int currentColumn = current.column;

            // 방문 여부 확인 *******************
            if (visited[currentRow][currentColumn]) continue;

            // - vistied[다음row][다음column] 방문처리 하기 *******************
            visited[0][0] = true;
            
            for (int[] direction : DIRECTIONS) {
                //*      - 현재 출발 위치에서 상하좌우를 살펴서 nextRow, nextColumn에 저장
                int nextRow = currentRow + direction[0];
                int nextColumn = currentColumn + direction[1];

                //*         - 범위에서 안 벗어나는지? continue
                if (nextRow < 0 || nextRow >= length || nextColumn < 0 || nextColumn >= length) continue;

                //*         - 이미 방문했는지? continue
                if (visited[nextRow][nextColumn]) continue;

                //*         - 현재 distance의 값 vs 방금 poll한 놈에서 나온 weight + 지금 weight 중에 더 작은 값을 distance에 저장
                int nextDistance = distance[currentRow][currentColumn] + map[nextRow][nextColumn];

                if (nextDistance < distance[nextRow][nextColumn]) {
                    distance[nextRow][nextColumn] = nextDistance;
                    queue.offer(new Node(nextRow, nextColumn, distance[nextRow][nextColumn]));
                }
            }
        }
    }

    static class Node implements Comparable<Node>{
        int row, column, weight;

        Node(int row, int column, int weight) {
            this.row = row;
            this.column = column;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return this.weight - other.weight;
        }
    }
}
