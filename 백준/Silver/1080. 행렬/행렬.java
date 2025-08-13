import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;
    public static String input;

    public static int N, M, count = 0;
    public static int[][] arrayA, arrayB;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 2개의 배열을 입력 받는다.
        initArrays();

        // 배열을 돌아가며 3*3 크기씩 돌린다.
        for (int rowIdx = 0; rowIdx < N-2; rowIdx++) {
            for (int columnIdx = 0; columnIdx < M-2; columnIdx++) {
                if (arrayA[rowIdx][columnIdx] != arrayB[rowIdx][columnIdx] ) {
                    reverse(rowIdx, columnIdx);
                    count++;
                }
            }
        }

        // 돌렸는데 동일하지 않다면 -1, 동일하면 돌린 횟수를 반환한다.
        if (!Arrays.deepEquals(arrayA, arrayB)) {
            System.out.println(-1);
        } else {
            System.out.println(count);
        }
    }

    /**
     * 배열 A, 배열 B 값을 입력 받아 저장합니다.
     *
     * @throws IOException
     */
    private static void initArrays() throws IOException {
        arrayA = new int[N][M];
        arrayB = new int[N][M];

        for (int rowIdx = 0; rowIdx < N; rowIdx++) {
            input = br.readLine().trim();
            for (int columnIdx = 0; columnIdx < M; columnIdx++) {
                arrayA[rowIdx][columnIdx] = input.charAt(columnIdx) - '0';
            }
        }

        for (int rowIdx = 0; rowIdx < N; rowIdx++) {
            input = br.readLine().trim();
            for (int columnIdx = 0; columnIdx < M; columnIdx++) {
                arrayB[rowIdx][columnIdx] = input.charAt(columnIdx) - '0';
            }
        }
    }

    /**
     * 시작 좌표(상단 왼쪽 좌표)로부터 3 * 3 크기만큼 뒤집음
     *
     * @param startRow : 시작 X좌표
     * @param startColumn : 시작 Y좌표
     */
    private static void reverse(int startRow, int startColumn) {
        for (int rowIdx = startRow; rowIdx < startRow+3; rowIdx++) {
            for (int columnIdx = startColumn; columnIdx < startColumn+3; columnIdx++) {
                arrayA[rowIdx][columnIdx] = (arrayA[rowIdx][columnIdx] == 0) ? 1 : 0;
            }
        }
    }
}
