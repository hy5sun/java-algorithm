import java.io.*;
import java.util.*;

/**
 * 1. N, 열, 행을 입력 받는다.
 *
 * 2. 한 변의 길이가 2^N 이므로 비트 연산을 통해 한 변의 길이를 계산한다.
 *
 * 3. 배열을 4등분하여 재귀 메서드를 통해 Z 모양으로 순회한다.
 *  3-1. 배열을 절반으로 나누어 4개의 산분면 중 row 와 column이 어디에 있는지 탐색한다.
 *  3-2. 속한 사분면 내부에서 다시 나누어 진행할 수 있도록, 재귀 함수를 호출하는데,
 *  3-3. 그 전에 (지나친 사분면 개수) * (사분면 넓이)를 count에 더해줘야 한다.
 *  3-4. size == 1 이 되면 rowIdx와 columnIdx 가 row와 column과 동일한 것이다.
 *
 * 4. 답을 출력한다.
 */
public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;

    public static int number, length, row, column, miniSize;
    public static int count = 0;

    public static void main(String[] args) throws IOException {
        // 1. N, 열, 행을 입력 받는다.
        st = new StringTokenizer(br.readLine().trim());

        number = Integer.parseInt(st.nextToken());
        row = Integer.parseInt(st.nextToken());
        column = Integer.parseInt(st.nextToken());

        // 2. 한 변의 길이가 2^N 이므로 비트 연산을 통해 한 변의 길이를 계산한다.
        length = 1 << number; // 2^N

        // 3. 배열을 4등분하여 재귀 메서드를 통해 Z 모양으로 순회한다.
        getSequence(length, 0, 0);

        // 4. 답을 출력한다.
        System.out.println(count);
    }

    /**
     *  배열을 4등분하여 재귀 메서드를 통해 Z 모양으로 순회한다.
     *
     *  1. 배열을 절반으로 나누어 4개의 산분면 중 row 와 column이 어디에 있는지 탐색한다.
     *  2. 속한 사분면 내부에서 다시 나누어 진행할 수 있도록, 재귀 함수를 호출하는데,
     *  3. 그 전에 (지나친 사분면 개수) * (사분면 넓이)를 count에 더해줘야 한다.
     *  4. size == 1 이 되면 rowIdx와 columnIdx 가 row와 column과 동일한 것이다.
     *
     * @param size : 팀섹 증인 정사각형의 한 변 길이
     * @param rowIdx : 정사각형의 시작 row 인덱스
     * @param columnIdx : 장시긱형의 시작 column 인덱스
     */
    private static void getSequence(int size, int rowIdx, int columnIdx) {
        // 더이상 쪼갤 수 없으면 그만한다.
        if (size == 1) return;

        int half = size / 2;
        int quadrantSize = half * half;

        // 찾고자 하는 인덱스가 제 1사분면 내부에 있다면 제 1사분면 내부에서 처리
        if (row < rowIdx + half && column < columnIdx + half) {
            getSequence(half, rowIdx, columnIdx);
        }
        // 찾고자 하는 인덱스가 제 2사분면 내부에 있다면 2사분면 내부에서 처리
        else if (row < rowIdx + half && column >= columnIdx + half) {
            // 1 사분면을 지났으므로 그만큼 더해줌
            count += quadrantSize;
            getSequence(half, rowIdx, columnIdx + half);
        }
        // 찾고자 하는 인덱스가 제 3사분면 내부에 있다면
        else if (row >= rowIdx + half && column < columnIdx + half) {
            // 1, 2 사분면을 지났으므로 그만큼 더해줌
            count += 2 * quadrantSize;
            getSequence(half, rowIdx + half, columnIdx);
        }
        // 찾고자 하는 인덱스가 제 4사분면 내부에 있다면
        else {
            // 1, 2, 3사분면을 지났으므로 그만큼 더해줌
            count += 3 * quadrantSize;
            getSequence(half, rowIdx + half, columnIdx + half);
        }
    }
}
