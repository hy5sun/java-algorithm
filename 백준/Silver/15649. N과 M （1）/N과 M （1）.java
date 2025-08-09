import java.io.*;
import java.util.*;

public class Main {

    public static int N, M;
    public static boolean[] checkIsExist;
    public static int[] sequence;
    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        /**
         * checkIsExist : int[]
         * 
         * 만들고 있는 수열에 숫자가 포함되는지의 여부를 0과 1로 표현하여 저장한다.
         * 1부터 N까지의 숫자 중 하나를 num 이라고 하면,  checkIsExist[num]이 포함 여부를 나타낸다.
         * 
         * 0 : 미포함된 상태
         * 1 : 포함된 상태
         */ 
        checkIsExist = new boolean[N+1];

        /**
         * sequence : ArrayList<Integer>
         * 
         * 만들고 있는 수열을 저장한다.
         */
        sequence = new int[M];
        
        printSequence(0);
    }

    /**
     * printSequence() : void
     * 
     * 수열을 만들고, 출력하는 함수
     */
    private static void printSequence(int index) {
        // sequence의 길이가 M이 되면 해당 리스트 내부에 있는 순서대로 원소들을 출력한다.
        if (index >= M) {
            for (int num : sequence) {
                sb.append(num).append(" ");
            }
            System.out.println(sb.toString());
            sb.setLength(0);
        } else {
            for (int num = 1; num <= N; num++) {
                // 현재 만들고 있는 수열에 미포함된 원소라면
                if (checkIsExist[num] == false) {
                    // sequence에 추가한다.
                    sequence[index] = num;
                    checkIsExist[num] = true;

                    printSequence(index + 1);

                    // sequence에서 다시 제외시킨다.
                    checkIsExist[num] = false;
                }
            }
        }
    }
}
