import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	static int N, M;
	static int x1, y1, x2, y2;
	static int[][] map;
	static int[][] sum;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine().trim());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		sum = new int[N+1][N+1];

		for (int rowIdx = 0; rowIdx < N; rowIdx++) {
			st = new StringTokenizer(br.readLine().trim());

			for (int columnIdx = 0; columnIdx < N; columnIdx++) {
				map[rowIdx][columnIdx] = Integer.parseInt(st.nextToken());
			}
		}

		for (int rowIdx = 1; rowIdx <= N; rowIdx++) {
			for (int columnIdx = 1; columnIdx <= N; columnIdx++) {
				sum[rowIdx][columnIdx] = sum[rowIdx-1][columnIdx] + sum[rowIdx][columnIdx-1] - sum[rowIdx-1][columnIdx-1] + map[rowIdx-1][columnIdx-1];
			}
		}

		for (int idx = 0; idx < M; idx++) {
			st = new StringTokenizer(br.readLine().trim());

			x1 = Integer.parseInt(st.nextToken());
			y1 = Integer.parseInt(st.nextToken());
			x2 = Integer.parseInt(st.nextToken());
			y2 = Integer.parseInt(st.nextToken());

			int answer = sum[x2][y2] - sum[x2][y1 - 1] - sum[x1-1][y2] + sum[x1-1][y1-1];

			sb.append(answer).append("\n");
		}

		System.out.println(sb.toString());
	}
}
