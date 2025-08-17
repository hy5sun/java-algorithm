import java.util.*;
import java.io.*;

/**
 * SW2805. 농작물 수확하기
 *
 * 1. 입력값을 입력 받는다.
 *  1-1. 테스트 케이스 수를 입력 받는다.
 *  1-2. 농장의 크기를 입력 받는다.
 *  1-3. 농장의 가치를 2차원 배열로 입력 받는다.
 *
 * 2. 현재 중앙 좌표를 방문 처리한다.
 *  - 중앙 좌표 : (농장 크기) / 2
 * 3. 총 가치값을 현재 중앙 좌표의 가치값으로 초기화한다.
 *
 * 4. 농장의 중앙을 기준으로 마름모 모양 영역의 총 가치를 계산한다.
 * 5. 가치값을 출력한다.
 */
public class Solution {

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static String input;

    public static int size;
    public static int center;
    public static int[][] worth;
    public static int totalWorth;

    public static void main(String[] args) throws IOException {
        // 1-1. 테스트 케이스 수를 입력 받는다.
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // 1-2. 농장 크기를 입력 받는다.
            size = Integer.parseInt(br.readLine());

            // 1-3. 농장의 가치를 저장한다.
            initWorth();

            // 총 가치값과 중앙값을 초기화한다.
            totalWorth = 0;
            center = size / 2;

            // 4. 현재 중앙 좌표를 기준으로 마름모 모양 영역의 총 가치를 계산한다.
            calculateWorth();

            // 5. 가치값을 출력한다.
            System.out.println("#" + testCase + " " + totalWorth);
        }
    }

    /**
     * 땅별 가치를 입력 받아 2차원 배열에 저장한다.
     *
     * @throws IOException
     */
    private static void initWorth() throws IOException{
        worth = new int[size][size];

        for (int rowIdx = 0; rowIdx < size; rowIdx++) {
            input = br.readLine();
            for (int columnIdx = 0; columnIdx < size; columnIdx++) {
                worth[rowIdx][columnIdx] = input.charAt(columnIdx) - '0';
            }
        }
    }

    /**
     * 농장의 중앙을 기준으로 마름모 모양 영역의 총 가치를 계산한다.
     *
     * 아래 규칙을 사용하여 구현함.
     *
     * ex. size = 5, center = 2
     * rowIdx = 0 -> columnIdx = 2
     * rowIdx = 1 -> 1 <= columnIdx <= 3
     * rowIdx = 2 -> 0 <= columnIdx <= 5
     * rowIdx = 3 -> 1 <= columnIdx <= 3
     * rowIdx = 4 -> columnIdx = 2
     */
    private static void calculateWorth() {
        for (int rowIdx = 0; rowIdx < center; rowIdx++) {
            for (int columnIdx = center - rowIdx; columnIdx <= center + rowIdx; columnIdx++) {
                totalWorth += worth[rowIdx][columnIdx] + worth[size-1-rowIdx][columnIdx];
            }
        }

        for (int columnIdx = 0; columnIdx < size; columnIdx++) {
            totalWorth += worth[center][columnIdx];
        }
    }
}
