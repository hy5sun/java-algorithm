import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int treeCnt, neededTree;
    static long maxHeight, answer;
    static long[] trees;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        treeCnt = Integer.parseInt(st.nextToken());
        neededTree = Integer.parseInt(st.nextToken());

        trees = new long[treeCnt];

        st = new StringTokenizer(br.readLine().trim());

        maxHeight = 0;
        for (int idx = 0; idx < treeCnt; idx++) {
            trees[idx] = Integer.parseInt(st.nextToken());
            maxHeight = Math.max(maxHeight, trees[idx]);
        }

        tryToCut();

        System.out.println(answer);
    }

    private static void tryToCut() {
        long left = 0;
        long right = maxHeight;
        answer = 0;

        while (left <= right) {
            long midHeight = (left + right) / 2;
            long cutTreeLength = 0;

            // 다 돌아야 함
            for (long tree : trees) {
                if (tree > midHeight) {
                    cutTreeLength += tree - midHeight;
                }
            }

            // 만약 초과된다면
            if (cutTreeLength >= neededTree) {
                answer = midHeight;
                left = midHeight + 1;
            } else {
                right = midHeight - 1;
            }
        }
    }
}
