import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int WHITE = 0;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int length, whiteCnt, blueCnt, exColor;
    static int[][] paper;

    public static void main(String[] args) throws IOException {
        length = Integer.parseInt(br.readLine());

        paper = new int[length][length];
        whiteCnt = 0;
        blueCnt = 0;

        for (int rowIdx = 0; rowIdx < length; rowIdx++) {

            st = new StringTokenizer(br.readLine().trim());

            for (int columnIdx = 0; columnIdx < length; columnIdx++) {
                paper[rowIdx][columnIdx] = Integer.parseInt(st.nextToken());
            }
        }

        divide(0, 0, length);

        System.out.println(sb.append(whiteCnt).append("\n").append(blueCnt).toString());
    }

    private static void divide(int startRow, int startColumn, int size) {
        if (isAllSamePaper(startRow, startColumn, size)) {
            if (exColor == WHITE) whiteCnt++;
            else blueCnt++;
            return;
        }

        if (size == 1) return;

        int half = size / 2;
        divide(startRow, startColumn, half); // 1사분면
        divide(startRow, startColumn + half, half); // 2사분면
        divide(startRow + half, startColumn, half); // 3사분면
        divide(startRow + half, startColumn + half, half); // 4사분면
    }

    private static boolean isAllSamePaper(int startRow, int startColumn, int size) {
        exColor = paper[startRow][startColumn];

        for (int rowIdx = startRow; rowIdx < startRow + size; rowIdx++) {
            for (int columnIdx = startColumn; columnIdx < startColumn + size; columnIdx++) {
                if (exColor != paper[rowIdx][columnIdx]) {
                    return false;
                }
            }
        }

        return true;
    }
}
