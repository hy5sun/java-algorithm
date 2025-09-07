import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * BJ4502. 연구소
 * 
 * [문제]
 * - N * M 크기의 연구소
 * - 세울 수 있는 벽 개수 3개. 반드시 3개를 세워야 함
 * - 0 : 빈칸, 1: 벽, 2: 바이러스
 * - 바이러스가 퍼질 수 없는 영역 크기의 최댓값 구하기
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 세로 크기 N, 가로 크기 M
 *  - 지도 정보
 *      - 바이러스 좌표 저장
 *      - 빈칸 좌표 저장
 *
 * 2. 3중 for문 사용해서 빈칸 좌표 세 군데에 벽 두기
 *
 * 3. BFS
 *  - 바이러스 좌표 모두 queue에 넣기
 *  - 큐가 빌 때까지 다음을 반복
 *      - 현재 큐 뽑기
 *      - 상하좌우 움직이기
 *      - 범위 벗어나면 pass
 *      - 이미 방문한 곳이면 pass
 *      - 0 이면 방문 처리하고 + 큐에 넣기 + 바이러스 퍼진 개수++
 *
 * 4. 벽 뒀던 거 원상복구 시키기
 *
 * 5. 기존 빈칸 수 - 퍼진 바이러스 수 - 3 의 최댓값 갱신하기
 *
 * 6. 안전지대 최댓값 출력하기
 */
public class Main {
    static final int EMPTY = 0;
    static final int WALL = 1;
    static final int VIRUS = 2;
    static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int rowSize, columnSize, spreadVirusCnt, maxSafeArea;
    static int[][] map;
    static List<Virus> viruses = new ArrayList<>();
    static List<int[]> empties = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 세로 크기 N, 가로 크기 M
        st = new StringTokenizer(br.readLine().trim());

        rowSize = Integer.parseInt(st.nextToken());
        columnSize = Integer.parseInt(st.nextToken());

        // *  - 지도 정보
        map = new int[rowSize][columnSize];

        for (int rowIdx = 0; rowIdx < rowSize; rowIdx++) {
            st = new StringTokenizer(br.readLine().trim());

            for (int columnIdx = 0; columnIdx < columnSize; columnIdx++) {
                map[rowIdx][columnIdx] = Integer.parseInt(st.nextToken());

                // *      - 바이러스 좌표 저장
                if (map[rowIdx][columnIdx] == VIRUS) {
                    viruses.add(new Virus(rowIdx, columnIdx));
                }

                // *      - 빈칸 좌표 저장
                if (map[rowIdx][columnIdx] == EMPTY) {
                    empties.add(new int[]{rowIdx, columnIdx});
                }
            }
        }

        // 2. 3중 for문 사용해서 빈칸 좌표 세 군데에 벽 두기
        for (int idx = 0; idx < empties.size(); idx++) {
            for (int jdx = idx+1; jdx < empties.size(); jdx++) {
                for (int kdx = jdx+1; kdx < empties.size(); kdx++) {
                    map[empties.get(idx)[0]][empties.get(idx)[1]] = WALL;
                    map[empties.get(jdx)[0]][empties.get(jdx)[1]] = WALL;
                    map[empties.get(kdx)[0]][empties.get(kdx)[1]] = WALL;

                    spreadVirusCnt = 0; // 퍼진 바이러스 개수
                    bfs();

                    // 4. 벽 뒀던 거 원상복구 시키기
                    map[empties.get(idx)[0]][empties.get(idx)[1]] = EMPTY;
                    map[empties.get(jdx)[0]][empties.get(jdx)[1]] = EMPTY;
                    map[empties.get(kdx)[0]][empties.get(kdx)[1]] = EMPTY;

                    // 5. 기존 빈칸 수 - 퍼진 바이러스 수 - 3 의 최댓값 갱신하기
                    maxSafeArea = Math.max(maxSafeArea, empties.size() - spreadVirusCnt - 3);
                }
            }
        }

        // 6. 안전지대 최댓값 출력하기
        System.out.println(maxSafeArea);
    }

    // 3. BFS
    // *  - 바이러스 좌표 모두 queue에 넣기
    // *  - 큐가 빌 때까지 다음을 반복
    // *      - 현재 큐 뽑기
    // *      - 상하좌우 움직이기
    // *      - 범위 벗어나면 pass
    // *      - 이미 방문한 곳이면 pass
    // *      - 0 이면 방문 처리하고 + 큐에 넣기 + 바이러스 퍼진 개수++
    private static void bfs() {
        Queue<Virus> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rowSize][columnSize];

        for (Virus virusPosition : viruses) {
            queue.offer(new Virus(virusPosition.row, virusPosition.column));
            visited[virusPosition.row][virusPosition.column] = true;
        }

        while (!queue.isEmpty()) {
            Virus current = queue.poll();

            for (int[] direction : DIRECTIONS) {
                int nextRow = current.row + direction[0];
                int nextColumn = current.column + direction[1];

                if (nextRow < 0 || nextRow >= rowSize || nextColumn < 0 || nextColumn >= columnSize) continue;

                if (visited[nextRow][nextColumn]) continue;

                if (map[nextRow][nextColumn] == EMPTY) {
                    visited[nextRow][nextColumn] = true;
                    spreadVirusCnt++;
                    queue.offer(new Virus(nextRow, nextColumn));
                }
            }
        }
    }

    static class Virus {
        int row, column;

        public Virus(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
