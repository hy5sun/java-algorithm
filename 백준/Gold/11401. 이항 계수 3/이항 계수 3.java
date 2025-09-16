import java.util.*;
import java.io.*;

/**
 * BJ11401. 이항 계수 3
 */
public class Main {
    static final long MOD = 1000000007;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, R;

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine().trim());

        N = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        System.out.println(combination(N, R));
    }

    static long combination(int N, int R) {
        if (N == 0 || N == R) return 1;

        long denominator = factorial(R) * factorial(N-R) % MOD;

        return factorial(N) * modInverse(denominator) % MOD;
    }

    static long factorial(int n) {
        long result = 1;

        for (int num = 2; num <= n; num++) {
            result = (result * num) % MOD;
        }

        return result;
    }

    // 모듈러의 역원을 구한다.
    static long modInverse(long num) {
        return binaryExponentiation(num, MOD - 2);
    }

    // 거듭 제곱법으로 a*(MOD-2)를 계산하여 역원을 구한다.
    static long binaryExponentiation(long num, long exponents) {
        long result = 1;

        while (exponents > 0) {
            if (exponents % 2 == 1) result = (result * num) % MOD;

            num = (num * num) % MOD;
            exponents /= 2;
        }

        return result;
    }
}