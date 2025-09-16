import java.util.*;
import java.io.*;

/**
 * BJ17472. 다리 만들기2
 * 
 * [문제]
 * - 모든 섬을 다리로 연결한다.
 * - N * M 크기로, 땅 혹은 바다
 * - 섬은 연결된 땅이 상하좌우로 붙어있는 덩어리임
 * - 다리는 바다에만 건설 가능
 * - 다리 길이 = 다리가 격자에서 차지하는 칸의 수
 * - 다리를 연결해서 모든 섬을 연결하려고 한다.
 * - 다리 길이는 2 이상이어야 함
 * 
 * 
 * [아이디어]
 * 처음에는 두 섬 사이에 다리를 설치해야 되는지 확인하고, 설치하는 줄..
 * 하지만? 아님 그냥 다 설치해 그리고 MST로 최소 가중치 길 찾아내기 였음..
 * 
 * 1. 가로로 다리를 세우기 위한 조건 : 
 *  - 같은 row에 두 개의 섬이 존재해야 함
 *  - 같은 row에 놓인 두 섬의 차가 2 이상이어야 함
 *  - ㄹㅇ 두 섬 사이에 필요한지 확인해야 된다. >> 어떻게..?
 * 
 * 2. 세로로 다리를 세우기 위한 조건 :
 *  - 같은 column에 두 개의 섬이 존재해야 함
 *  - 같은 column에 놓인 두 섬의 차가 2 이상이어야 함
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 세로 크기, 가로 크기
 *  - 지도 정보
 * 
 * 2. 섬 표시하기
 *  - 섬 인덱스는 2부터 표시 (1은 기본값이니까)
 *  - BFS를 통해 연결되어 있는 섬의 숫자를 섬의 인덱스로 수정함 (map 배열 직접 수정)
 *  - 섬의 개수 = (섬 인덱스 - 2)
 * 
 * 3. 먼저 row 별로 조회.
 *  - exIdx : 이전에 발견한 섬의 column 인덱스를 저장해두는 용도
 *  - 만약 섬을 발견하면
 *      - 이전에 발견한 섬이 있고 && 예비 다리의 길이가 2 이상인 경우
 *          - 이전에 발견한 섬과 지금 발견한 섬의 인덱스, 다리의 길이를 edges에 저장한다.
 *      - exIdx에 방금 발견한 섬의 column 인덱스를 저장한다.
 *      
 * 4. column 별로 조회 : 3번과 동일하게 진행한다.
 * 
 * 5. MST로 최소 가중치 선택한다.
 * 
 * 6. 최종 다리 길이 출력
 */
public class Main {
    static final int ISLAND = 1;
    static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int rowSize, columnSize, islandCnt, minBridgeLength;
    static int[][] map;
    static boolean[][] visited;

    static int[] parents;
    static int[] rank;
    static Set<Node> edges = new HashSet<>();

    static class Node implements Comparable<Node> {
        int island1, island2, bridgeLength;

        Node(int island1, int island2, int bridgeSize) {
            this.island1 = island1;
            this.island2 = island2;
            this.bridgeLength = bridgeSize;
        }

        @Override
        public int compareTo(Node other) {
            return this.bridgeLength - other.bridgeLength;
        }
    }

    public static void main(String[] args) throws IOException {
        /**
         * 1. 입력 받기
         *  - 세로 크기, 가로 크기
         *  - 지도 정보
         */
        st = new StringTokenizer(br.readLine().trim());

        rowSize = Integer.parseInt(st.nextToken());
        columnSize = Integer.parseInt(st.nextToken());

        map = new int[rowSize][columnSize];
        visited = new boolean[rowSize][columnSize];

        for (int rowIdx = 0; rowIdx < rowSize; rowIdx++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int columnIdx = 0; columnIdx < columnSize; columnIdx++) {
                map[rowIdx][columnIdx] = Integer.parseInt(st.nextToken());
            }
        }
        
        /**
         * 2. 섬 표시하기
         *  - 섬 인덱스는 2부터 표시 (1은 기본값이니까)
         *  - BFS를 통해 연결되어 있는 섬의 숫자를 섬의 인덱스로 수정함 (map 배열 직접 수정)
         *  - 섬의 개수 = (섬 인덱스 - 2)
         */
        int islandIdx = 2;

        for (int rowIdx = 0; rowIdx < rowSize; rowIdx++) {
            for (int columnIdx = 0; columnIdx < columnSize; columnIdx++) {
                if (map[rowIdx][columnIdx] == ISLAND)
                    countIsland(rowIdx, columnIdx, islandIdx++);
            }
        }

        islandCnt = islandIdx - 2; // 섬 개수

        /**
         * 3. 먼저 row 별로 조회.
         *  - exIdx : 이전에 발견한 섬의 column 인덱스를 저장해두는 용도
         *  - 만약 섬을 발견하면
         *      - 이전에 발견한 섬이 있고 && 예비 다리의 길이가 2 이상인 경우
         *          - 이전에 발견한 섬과 지금 발견한 섬의 인덱스, 다리의 길이를 edges에 저장한다.
         *      - exIdx에 방금 발견한 섬의 column 인덱스를 저장한다.
         */
        int exIdx, preBridgeLength;

        for (int rowIdx = 0; rowIdx < rowSize; rowIdx++) {
            exIdx = -1;
            for (int columnIdx = 0; columnIdx < columnSize; columnIdx++) {
                if (map[rowIdx][columnIdx] > ISLAND) {

                    preBridgeLength = columnIdx - exIdx - 1;

                    if (exIdx != -1 && preBridgeLength >= 2) {
                        int island1 = map[rowIdx][exIdx];
                        int island2 = map[rowIdx][columnIdx];

                        edges.add(new Node(island1, island2, preBridgeLength));
                    }

                    exIdx = columnIdx;
                }
            }
        }

        /**
         * 4. column 별로 조회 : 3번과 동일하게 진행한다.
         */
        for (int columnIdx = 0; columnIdx < columnSize; columnIdx++) {
            exIdx = -1;
            
            for (int rowIdx = 0; rowIdx < rowSize; rowIdx++) {
                if (map[rowIdx][columnIdx] > ISLAND) {

                    preBridgeLength = rowIdx - exIdx - 1; // 예비 다리 길이

                    if (exIdx != -1 && preBridgeLength >= 2) { // 이전에 발견한 섬이 있고, 다리 길이가 2 이상이면
                        // 다리 설치
                        int island1 = map[exIdx][columnIdx];
                        int island2 = map[rowIdx][columnIdx];

                        edges.add(new Node(island1, island2, preBridgeLength));
                    }

                    exIdx = rowIdx;
                }
            }
        }

        // MST로 최소 가중치로 다리 계산하기
        make();

        // edges에 중복된 값이 존재하기 때문에 시간 초과가 될 수 있다. -> set으로 관리
        // 다리 길이를 중심으로 오름차순을 한다.
        List<Node> lst = new ArrayList<>(edges);
        lst.sort(Comparator.comparingInt(edge -> edge.bridgeLength));

        minBridgeLength = 0; // 최종 다리 길이
        int count = 0; // 간선 개수

        for (Node edge : lst) {
            int parent1 = find(edge.island1);
            int parent2 = find(edge.island2);

            if (parent1 == parent2) continue;

            union(edge.island1, edge.island2);
            count++;

            minBridgeLength += edge.bridgeLength;
        }

        // 간선 개수가 섬 개수 - 1 이면 다 연결된거임
        if (count == islandCnt - 1) System.out.println(minBridgeLength);
        else System.out.println(-1);
    }

    private static void countIsland(int row, int column, int islandIdx) {
        Queue<int[]> queue = new LinkedList<>();
        
        queue.offer(new int[]{row, column});
        visited[row][column] = true;
        map[row][column] = islandIdx;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            
            for (int[] direction : DIRECTIONS) {
                int nextRow = current[0] + direction[0];
                int nextColumn = current[1] + direction[1];

                if (nextRow < 0 || nextRow >= rowSize || nextColumn < 0 || nextColumn >= columnSize) continue;

                if (map[nextRow][nextColumn] == ISLAND && !visited[nextRow][nextColumn]) {
                    visited[nextRow][nextColumn] = true;
                    map[nextRow][nextColumn] = islandIdx;
                    queue.offer(new int[]{nextRow, nextColumn});
                }
            }
        }
    }

    private static void make() {
        parents = new int[islandCnt+2];
        rank = new int[islandCnt+2];

        for (int idx = 2; idx <= islandCnt; idx++) {
            parents[idx] = idx;
        }
    }

    private static int find(int element) {
        if (parents[element] == element) return element;
        return parents[element] = find(parents[element]);
    }

    private static void union(int element1, int element2) {
        int parent1 = find(element1);
        int parent2 = find(element2);

        if (parent1 == parent2) return;

        if (rank[parent1] > rank[parent2]) {
            parents[parent2] = parent1;
            return;
        }

        parents[parent1] = parent2;

        if (rank[parent1] == rank[parent2]) {
            rank[parent2]++;
        }
    }
}
