import java.util.*;
import java.io.*;

/**
 * SW5215_3 (Next Permutation 사용)
 *
 * 1. 인풋값을 입력 받는다.
 *  1-1. 테스트 케이스 개수를 입력받는다.
 *  1-2. 재료 개수, 최대 칼로리를 입력 받는다.
 *  1-3. 재료 개수 만큼 햄버거의 점수와 칼로리를 입력받아 배열에 저장한다.
 *
 * 2. next permutation을 적용하기 위해 칼로리를 기준으로 오름차순한다.
 *  - 적은 칼로리부터 계산할 경우, 더 많은 재료를 담을 수 있기 때문에 칼로리를 기준으로 오름차순 정렬했다.
 * 3. nextPermutation()이 돌기 전에 현재 순열을 기준으로 최대 점수값을 계산한다.
 *
 * 4. isFinished 변수를 초기화한다. (순열이 끝까지 다 돌았는지의 여부를 확인할 때 사용된다.)
 * 5. isFinished가 true가 될 때까지 nextPermutation()을 호출한다.
 *  5-1. 뒤에서부터 봤을 때, 왼쪽 인데스 보다 오른쪽 인덱스가 더 크면 왼쪽 인덱스가 pivot이다.
 *  5-2. pivot이 0보다 작으면 순열을 다 돌았다는 것이므로 중지한다.
 *  5-3. pivot 이후의 인덱스 중 operator[pivot] 보다 크면서 작은 원소의 인덱스를 찾는다.
 *  5-4. 해당 원소와 pivot 인덱스의 자리를 바꾼다.
 *  5-5. pivot 이후의 요소를 칼로리를 기준으로 오름차순으로 정렬한다.
 *  5-6. 현재 순열을 기준으로 점수를 계산하고, 최대 점수를 갱신한다.
 *
 * 6. 부분 집합을 사용하여 재료를 포함시키거나 미포함해서 재귀 함수를 호출하며 최대 점수 합을 계산한다.
 *  6-1. 재료 번호가 재료 개수와 같다면 중지한다.
 *  6-2. 칼로리를 넘지 않았다면 재료를 포함시켜서 재귀 함수 호출
 *  6-3. 재료를 미포함해서 재귀 함수 호출
 * 
 * 7. 케이스별 최대 점수를 출력한다.
 */
public class Solution {
    public static final int SCORE_INDEX = 0;
    public static final int CALORIE_INDEX = 1;

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;

    public static int[][] foodInfo;
    public static int foodCnt;
    public static int limitCalorie;
    public static int maxScore;
    public static boolean isFinished;

    public static void main(String[] args) throws IOException {
        // 1-1. 테스트 케이스 개수를 입력받는다.
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // 최대 점수 초기화
            maxScore = Integer.MIN_VALUE;

            // 햄버거의 재료 수와 제한 칼로리, 잴별 점수와 칼로리를 입력받는다.
            saveInputValue();

            // 2. next permutation을 적용하기 위해 칼로리를 기준으로 오름차순한다.
            //   - 적은 칼로리부터 계산할 경우, 더 많은 재료를 담을 수 있기 때문에 칼로리를 기준으로 오름차순 정렬했다.
            Arrays.sort(foodInfo, Comparator.comparingInt((int[] food) -> food[CALORIE_INDEX]));

            // 3. nextPermutation()이 돌기 전에 현재 순열을 기준으로 최대 점수값을 계산한다.
            calculateMaxScore(0, 0, 0);

            // 4. isFinished 변수를 초기화한다. (순열이 끝까지 다 돌았는지의 여부를 확인할 때 사용된다.)
            isFinished = false;

            // 5. isFinished가 true가 될 때까지 nextPermutation()을 호출한다.
            if (!isFinished) {
                nextPermutation();
            }

            // 7. 케이스별 최대 점수를 출력한다.
            System.out.println("#" + testCase + " " + maxScore);
        }
    }

    private static void saveInputValue() throws IOException {
        // 1-2. 재료 개수, 최대 칼로리를 입력 받는다.
        st = new StringTokenizer(br.readLine());

        foodCnt = Integer.parseInt(st.nextToken());
        limitCalorie = Integer.parseInt(st.nextToken());

        // 1-3. 재료 개수 만큼 햄버거의 점수와 칼로리를 입력받아 배열에 저장한다.
        foodInfo = new int[foodCnt][2];

        for (int foodIdx = 0; foodIdx < foodCnt; foodIdx++) {
            st = new StringTokenizer(br.readLine());

            // 0번째 인덱스에는 점수를, 1번째 인덱스에는 칼로리를 저장한다.
            foodInfo[foodIdx][SCORE_INDEX] = Integer.parseInt(st.nextToken());
            foodInfo[foodIdx][CALORIE_INDEX] = Integer.parseInt(st.nextToken());
        }
    }

    private static void nextPermutation() {
        // 5-1. 뒤에서부터 봤을 때, 왼쪽 인데스 보다 오른쪽 인덱스가 더 크면 왼쪽 인덱스가 pivot 이다.
        int pivot = foodCnt - 2;
        while (pivot >= 0 && foodInfo[pivot][CALORIE_INDEX] >= foodInfo[pivot+1][CALORIE_INDEX]) {
            pivot--;
        }

        // 5-2. pivot이 0보다 작으면 순열을 다 돌았다는 것이므로 중지한다.
        if (pivot < 0) {
            isFinished = true;
            return;
        }

        // 5-3. pivot 이후의 인덱스 중 operator[pivot] 보다 크면서 작은 원소의 인덱스를 찾는다.
        int swapIndex = foodCnt - 1;
        while (foodInfo[swapIndex][CALORIE_INDEX] <= foodInfo[pivot][CALORIE_INDEX]) {
            swapIndex--;
        }

        // 5-4. 해당 원소와 pivot 인덱스의 자리를 바꾼다.
        swap(swapIndex, pivot);

        // 5-5. pivot 이후의 요소를 칼로리를 기준으로 오름차순으로 정렬한다.
        Arrays.sort(foodInfo, pivot+1, foodCnt, Comparator.comparingInt((int[] food) -> food[CALORIE_INDEX]));

        // 5-6. 현재 순열을 기준으로 점수를 계산하고, 최대 점수를 갱신한다.
        calculateMaxScore(0, 0, 0);
    }

    /**
     * 두 개의 인덱스 원소 위치를 바꾼다.
     *
     * @param swapIndex : pivot과 바꿀 인덱스
     * @param pivot : pivot 인덱스
     */
    private static void swap(int swapIndex, int pivot) {
        int[] temp = foodInfo[swapIndex];
        foodInfo[swapIndex] = foodInfo[pivot];
        foodInfo[pivot] = temp;
    }

    /**
     * [부분집합 사용]
     * 재료를 포함시키거나 미포함해서 재귀 함수를 호출하며 최대 점수 합을 계산한다.
     *
     * @param foodIdx : 넣을지 고려하고 있는 재료 인덱스
     * @param totalCalorie : 현재까지의 칼로리 합
     * @param totalScore : 현재까지의 점수 합
     */
    private static void calculateMaxScore(int foodIdx, int totalCalorie, int totalScore) {
        // 6-1. 재료 번호가 재료 개수와 같다면 중지한다.
        if (foodIdx == foodCnt) {
            maxScore = Math.max(totalScore, maxScore);
            return;
        }

        // 6-2. 칼로리를 넘지 않았다면 재료를 포함시켜서 재귀 함수 호출
        if (totalCalorie + foodInfo[foodIdx][CALORIE_INDEX] <= limitCalorie) {
            calculateMaxScore(foodIdx+1, totalCalorie + foodInfo[foodIdx][CALORIE_INDEX], totalScore + foodInfo[foodIdx][SCORE_INDEX]);
        }

        // 6-3. 재료를 미포함해서 재귀 함수 호출
        calculateMaxScore(foodIdx+1, totalCalorie, totalScore);
    }
}
