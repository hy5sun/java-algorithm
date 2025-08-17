import java.io.*;
import java.util.*;

/**
 * BJ3109. 빵집
 *
 * - 가스관과 빵집을 연결하는 파이프를 설치하려고 한다.
 * - 빵집과 가스관 사이에는 건물이 있을 수 있는데, 건물이 있으면 파이프를 놓을 수 없다.
 * - 모든 파이프라인은 첫 번째 열에서 시작해서 마지막 열에서 끝나야 한다.
 * - 오른쪽, 오른쪽 위 대각선, 오른쪽 아래 대각선으로 연결할 수 있고, 각 칸의 중심끼리 연결한다.
 * - 가스를 최대한 많이 훔치려고 한다.
 * - 파이프 라인을 여러 개 설치할 수 있는데, 경로는 겹치거나 서로 접할 수 없다.
 * - 설치할 수 있는 파이프라인의 최대 개수를 구한다.
 *
 * [풀이] - dfs + 백트래킹 사용
 */
public class Main {
    private static final char BUILDING = 'x';
    private static final int[][] directions = {{-1, 1}, {0, 1}, {1, 1}};

    public static BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
    public static StringTokenizer st;
    public static String input;

    public static int row, column;
    public static char[][] map;
    public static boolean[][] visited;

    public static int pipeLineCnt;
    public static boolean isEnded;

    public static void main(String[] args) throws IOException {
        initMap();

        pipeLineCnt = 0;

        for (int rowIdx = 0; rowIdx < row; rowIdx++) {
            isEnded = false;
            dfs(rowIdx, 0);
        }

        System.out.println(pipeLineCnt);
    }

    private static void initMap() throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        row = Integer.parseInt(st.nextToken());
        column = Integer.parseInt(st.nextToken());

        map = new char[row][column];
        visited = new boolean[row][column];

        for (int rowIdx = 0; rowIdx < row; rowIdx++) {
            input = br.readLine().trim();
            for (int columnIdx = 0; columnIdx < column; columnIdx++) {
                map[rowIdx][columnIdx] = input.charAt(columnIdx);
            }
        }
    }

    private static void dfs(int rowIdx, int columnIdx) {
        if (columnIdx == column - 1) {
            pipeLineCnt++;
            isEnded = true;
            return;
        }

        for (int[] direction : directions) {

            int nextRowIdx = rowIdx + direction[0];
            int nextColumnIdx = columnIdx + direction[1];

            if (nextRowIdx < 0 || nextRowIdx >= row || nextColumnIdx >= column) continue;

            // 방문하지 않았고, 빌딩이 아닌 경우
            if (!visited[nextRowIdx][nextColumnIdx] && map[nextRowIdx][nextColumnIdx] != BUILDING) {
                visited[nextRowIdx][nextColumnIdx] = true;
                dfs(nextRowIdx, nextColumnIdx);
                if (isEnded) return;
            }
        }
    }
}
