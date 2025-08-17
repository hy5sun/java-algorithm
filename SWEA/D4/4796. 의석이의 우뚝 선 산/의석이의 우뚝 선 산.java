import java.util.*;

public class Solution {
    public static int mountainCount;
    public static int[] mountains;

    public static int count;
    public static int[] left;
    public static int[] right;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int testCaseCnt = sc.nextInt();

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // 산의 개수를 입력 받는다.
            mountainCount = sc.nextInt();

            // 산의 높이를 입력 받는다.
            initMountain(sc);

            // 오르막 수와 내리막 수를 계산하고, 두 값을 곱하여 우뚝 선 산의 개수를 누적한다.
            compareHeights();

            // 출력한다.
            System.out.println("#" + testCase + " " + count);
        }

        sc.close();
    }

    private static void initMountain(Scanner sc) {
        mountains = new int[mountainCount];

        for (int idx = 0; idx < mountainCount; idx++) {
            mountains[idx] = sc.nextInt();
        }
    }

    private static void compareHeights() {
        left = new int[mountainCount];
        right = new int[mountainCount];

        count = 0;

        // 왼쪽 -> 오른쪽 방향으로 갈 때, 연속된 오르막 개수를 계산한다.
        for (int idx = 1; idx < mountainCount; idx++) {
            if (mountains[idx - 1] < mountains[idx])
                left[idx] = left[idx - 1] + 1;
        }

        // 오른쪽 -> 왼쪽 방향으로 갈 때, 연속된 내리막 개수를 계산한다.
        for (int idx = mountainCount - 2; idx >= 0; idx--) {
            if (mountains[idx] > mountains[idx + 1])
                right[idx] = right[idx + 1] + 1;
        }

        // 모든 봉우리 위치에 대해 경우의 수 합산
        for (int peak = 1; peak < mountainCount - 1; peak++) {
            if (left[peak] > 0 && right[peak] > 0) {
                count += left[peak] * right[peak];
            }
        }
    }
}
