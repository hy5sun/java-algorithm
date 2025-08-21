import java.io.*;
import java.util.*;

/**
 * BJ16974. 레벨 햄버거
 * 
 * [문제]
 * (B = 햄버거번, P = 패티)
 * 레벨-0 : P -> 길이 1 => 패티 1
 * 레벨-1 : B[P]P[P]B -> 길이 3 + (레벨-0) * 2 = 5 => 패티 1 + (레벨-0 패티) * 2 = 3
 * 레벨-2 : B[BPPPB]P[BPPPB]B -> 길이 3 + (레벨-1) * 2 = 13 => 패티 1 + (레벨-1 패티) * 2 = 7
 * 레벨-L : B + 레벨-(L-1) 버거 + P + 레벨-(L-1) 버거 + B (L >= 1)
 * 상도가 레벨-N 버거를 시켜서 아래 X장을 먹었을 때, 먹은 패티는 몇 장?
 * 
 * [풀이]
 * 1. 레벨(N)과 먹은 장 수(X)를 입력 받는다.
 * 2. 길이를 저장한다.
 * 3. 패티 개수를 저장한다.
 * 4. 점화식 사용해서 먹은 패티 몇 장인지 구한다.
 * 5. 상도가 먹은 패티 수를 출력한다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int level;
    static long eatCount, pattyCount = 0L;
    static long[] lengths, patties;

    public static void main(String[] args) throws IOException {
        // 1. 레벨(N)과 먹은 장 수(X)를 입력 받는다.
        st = new StringTokenizer(br.readLine().trim());

        level = Integer.parseInt(st.nextToken());
        eatCount = Long.parseLong(st.nextToken());

        // 2. 길이를 저장한다.
        lengths = new long[level+1];
        for (int lv = 0; lv <= level; lv++) {
            lengths[lv] = getLength(lv);
        }

        // 3. 패티 개수를 저장한다.
        patties = new long[level+1];
        for (int lv = 0; lv <= level; lv++) {
            patties[lv] = getPatty(lv);
        }

        // 4. 먹은 패티 몇 장인지 구한다.
        pattyCount = getPatty(level, eatCount);

        // 5. 상도가 먹은 패티 수를 출력한다.
        sb.append(pattyCount);
        System.out.println(sb.toString());
    }

    /**
     * 먹은 패티 수 구하기
     * 
     *  P(N, X) = 0 (1<= X <=N)
     *          = P(N-1, X-1) (N < X <= L(N)/2)
     *          = patties[N-1] + 1 (X = L(N)/2)
     *          = patties[lv-1] + 1 + P(N-1, X-L(N)/2) (L(N)/2 < X < L(N))
     *          = P(N-1) * 2 + 1 (X = L(N))
     * 
     *  P(0, 1) = 1
     */
    private static long getPatty(int lv, long eat) {
        if (lv == 0) return 1L;

        long nowLength = lengths[lv];
        long center = nowLength / 2 + 1;
        
        if (eat <= lv) return 0;
        else if (lv < eat && eat < center) return getPatty(lv-1, eat-1);
        else if (eat == center) return patties[lv-1] + 1;
        else if (center < eat && eat < nowLength) return patties[lv-1] + 1 + getPatty(lv-1, eat-center);
        else return patties[lv-1] * 2 + 1;
    }

    /**
     * 레벨별 햄버거 길이 구하기
     * 
     * L(N) = 3 + L(N-1) * 2
     * L(0) = 1
     */
    private static long getLength(int lv) {
        if (lv == 0) return 1L;
        return 3 + lengths[lv-1] * 2;
    }

    /**
     * 레벨별 패티 수 구하기
     * 
     * P(N) = P(N-1) * 2 + 1
     * P(0) = 1
     */
    private static long getPatty(int lv) {
        if (lv == 0) return 1;
        return 1 + patties[lv-1] * 2;
    }
}
