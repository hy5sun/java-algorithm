import java.util.*;
import java.io.*;

/**
 * BJ1194. 달이 차오른다, 가자.
 * 
 * - . : 빈칸 (이동 가능) / # : 벽 (이동 불가) / a, b, c, d, e, f : 열쇠 (이동 가능. 처음 들어가면 열쇠를 집는다.)
 * - A, B, C, D, E, F : 대응하는 열쇠가 있을 때만 이동할 수 있다.
 * - 현재 위치 : '0'
 * - 출구 : '1'
 * - 민식이가 미로를 탈출하는 데 걸리는 이동 횟수의 최솟값
 * - 탈출 못하면 -1 출력
 * - 같은 타입의 열쇠가 여러 개 있을 수도. 문에 대응하는 열쇠가 없을수도.
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 세로, 가로 길이
 *  - 미로
 *      - 0인 위치 저장하기
 * 
 * 2. BFS ㄱㄱ
 *  - 시작 위치 방문 처리 + queue에 추가
 *  - queue 빌 때까지 다음을 반복
 *      - 지금 Exit면 출력하고 종료
 *      - 현재 currentRow, currentColumn을 기준으로 상하좌우 살피기
 *          - 범위에 벗어났으면 pass
 *          - 방문했었으면 pass
 *          - 벽이야? -> pass
 *          - 열쇠야?
 *              - map[nextRow][nextColumn] - 'a' 해서 인덱스 알아보고
 *              - 1을 해당 인덱스만큼 왼쪽으로 시프트
 *          - 방이야?
 *              - map[nextRow][nextColumn] - 'A' 해서 인덱스 알아보고
 *              - 1을 해당 인덱스만큼 왼쪽으로 시프트한 거랑 이전까지 모은 열쇠 상태를 AND 연산 
 *                  -> 열쇠가 있으면 1을 반환하고, 없으면 0을 반환할 것임
 * 
 *          - 빈 공간이거나 / 열쇠를 줍줍했거나 / 방을 통과했다면 -> 방문 처리 + queue에 추가
 * 
 * [주의]
 * 이미 다녀온 곳을 또 갈 수 있는 거라, 일반 visited는 사용할 수 없음.
 * 그렇다고 안 사용하기엔 시간 복잡도 감당할 수 없음 ㅋㅋ
 * visited를 3차원으로 사용해서 [row][column][keyState]로 관리해야 함.
 * 가지고 있는 키가 같을 때는 동일한 곳 방문해도 동일한데, 다른 키 갖고 있는 거면 말이 달라지니까
 * keyState는 비트마스킹 사용해야 됨..
 */
public class Main {
    static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static final char WALL = '#';
    static final char START = '0';
    static final char EXIT = '1';

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static String input;

    static int rowSize, columnSize, startRow, startColumn; // 세로, 가로 길이, 시작 row, column
    static char[][] map; // 지도

    public static void main(String[] args) throws IOException {
        /**
         * 1. 입력 받기
         *  - 세로, 가로 길이
         *  - 미로
         */
        st = new StringTokenizer(br.readLine().trim());

        rowSize = Integer.parseInt(st.nextToken());
        columnSize = Integer.parseInt(st.nextToken());

        map = new char[rowSize][columnSize];
        
        for (int rowIdx = 0; rowIdx < rowSize; rowIdx++) {
            input = br.readLine();

            for (int columnIdx = 0; columnIdx < columnSize; columnIdx++) {
                map[rowIdx][columnIdx] = input.charAt(columnIdx);

                // - 0인 위치 저장하기
                if (map[rowIdx][columnIdx] == START) {
                    startRow = rowIdx;
                    startColumn = columnIdx;
                }
            }
        }

        bfs();

        System.out.println(-1);

    }
    /**
     * 2. BFS ㄱㄱ
     *  - 시작 위치 방문 처리 + queue에 추가
     *  - queue 빌 때까지 다음을 반복
     *      - 지금 Exit면 출력하고 종료
     *      - 현재 currentRow, currentColumn을 기준으로 상하좌우 살피기
     *          - 범위에 벗어났으면 pass
     *          - 방문했었으면 pass
     *          - 벽이야? -> pass
     *          - 열쇠야?
     *              - map[nextRow][nextColumn] - 'a' 해서 인덱스 알아보고
     *              - 1을 해당 인덱스만큼 왼쪽으로 시프트
     *          - 방이야?
     *              - map[nextRow][nextColumn] - 'A' 해서 인덱스 알아보고
     *              - 1을 해당 인덱스만큼 왼쪽으로 시프트한 거랑 이전까지 모은 열쇠 상태를 AND 연산 
     *                  -> 열쇠가 있으면 1을 반환하고, 없으면 0을 반환할 것임
     * 
     *          - 빈 공간이거나 / 열쇠를 줍줍했거나 / 방을 통과했다면 -> 방문 처리 + queue에 추가
     */
    private static void bfs() {
        Queue<Block> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[rowSize][columnSize][64];

        visited[startRow][startColumn][0] = true;
        queue.offer(new Block(startRow, startColumn, 0, 0));

        while (!queue.isEmpty()) {
            Block current = queue.poll();

            int row = current.row;
            int column = current.column;
            int keyState = current.keyState;
            int distance= current.distance;

            if (map[row][column] == EXIT) {
                System.out.println(distance);
                System.exit(0);
            }

            for (int[] direction : DIRECTIONS) {
                int nextRow = row + direction[0];
                int nextColumn = column + direction[1];

                if (nextRow < 0 || nextRow >= rowSize || nextColumn < 0 || nextColumn >= columnSize) continue;

                if (visited[nextRow][nextColumn][keyState]) continue;

                if (map[nextRow][nextColumn] == WALL) continue;

                int nexKeyState = keyState;

                if (isGetTheKey(nextRow, nextColumn)) {
                    int roomIdx = map[nextRow][nextColumn] - 'a';
                    nexKeyState |= (1 << roomIdx); // 열쇠 줍줍
                }

                if (isInTheRoom(nextRow, nextColumn)) {
                    int roomIdx = map[nextRow][nextColumn] - 'A';
                    if ((nexKeyState & (1 << roomIdx)) == 0) continue; // 열쇠 없으면 pass
                }

                visited[nextRow][nextColumn][nexKeyState] = true;
                queue.offer(new Block(nextRow, nextColumn, nexKeyState, distance+1));
            }
        }
        
    }

    private static boolean isInTheRoom(int row, int column) {
        return map[row][column] >= 'A' && map[row][column] <= 'F';
    }

    private static boolean isGetTheKey(int row, int column) {
        return map[row][column] >= 'a' && map[row][column] <= 'f';
    }

    static class Block {
        int row, column, keyState, distance;

        public Block(int row, int column, int keyState, int distance) {
            this.row = row;
            this.column = column;
            this.keyState = keyState;
            this.distance = distance;
        }
    }
}
