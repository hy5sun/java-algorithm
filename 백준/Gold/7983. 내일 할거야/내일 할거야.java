
import java.util.*;
import java.io.*;

/**
 * BJ7983. 내일 할거야
 * 
 * 
 * [문제]
 * - n개의 과제가 존재한다.
 * - 과제 i는 di일 걸리고, 오늘로부터 ti 일 안에 끝내야 함
 * - 과제는 한 번 시작하면 쉬지 않고 계속 한다.
 * - 내일부터 연속으로 최대 며칠 동안 놀 수 있을지 출력한다.
 * - 답이 0이면 내일 과제 해야 되고, 1이면 모레 과제 해야 한다.
 * 
 * [풀이]
 * "자고로 과제란. 마감일 직전에 끝내는 것."
 * 
 * 1. 입력 받기
 *  - 과제 수
 *  - 과제 걸리는 데 걸리는 일 수, 오늘로부터 며칠 내로 끝내야 하는지 -> 배열에 저장
 * 
 * 2. 마감일을 기준으로 내림차순 정렬을 한다.
 * 
 * 3. 가장 늦은 마감일을 playDay 변수에 저장한다. : 놀 수 있는 남은 날짜
 * 
 * 4. 모든 과제를 도는데,
 *    - 현재 마감일과 playDay 중에 더 이른 날짜까지는 끝내야 함
 *    - 여기서 과제 하는 데 걸리는 날짜를 빼면 남은 날짜 동안은 놀 수 있음
 * 
 * 5. playDay을 출력한다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int assignmentCnt;
    static int[][] assignments; // 과제 걸리는 데 걸리는 일 수, 오늘로부터 며칠 내로 끝내야 하는지

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        assignmentCnt = Integer.parseInt(br.readLine());

        assignments = new int[assignmentCnt][2];

        for (int idx = 0; idx < assignmentCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            assignments[idx][0] = Integer.parseInt(st.nextToken());
            assignments[idx][1] = Integer.parseInt(st.nextToken());
        }

        // 2. 마감일을 기준으로 내림차순 정렬을 한다.
        Arrays.sort(assignments, (Comparator.comparingInt((int[] a) -> a[1]).reversed()));

        // 3. 가장 늦은 마감일을 playDay 변수에 저장한다. : 놀 수 있는 남은 날짜
        int playDay = assignments[0][1];

        // 4. 모든 과제를 도는데,
        //    *  현재 마감일과 playDay 중에 더 이른 날짜까지는 끝내야 함
        //    *  여기서 과제 하는 데 걸리는 날짜를 빼면 남은 날짜 동안은 놀 수 있음
        for (int[] assignment : assignments) {
            int assignDay = assignment[0];
            int deadLine = assignment[1];

            // 현재 마감일과 playDay 중에 더 이른 날짜까지는 끝내야 함
            // 여기서 과제 하는 데 걸리는 날짜를 빼면 남은 날짜 동안은 놀 수 있음
            playDay = Math.min(playDay, deadLine) - assignDay;
        }
        
        // 5. playDay을 출력한다.
        System.out.println(playDay);
    }
}
