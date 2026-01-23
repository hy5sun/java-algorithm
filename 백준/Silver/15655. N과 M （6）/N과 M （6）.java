import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	static int n, m;
	static int[] input, arr;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine().trim());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine().trim());

		input = new int[n];
		arr = new int[m];

		for (int idx = 0; idx < n; idx++) {
			input[idx] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(input);

		dfs(0, 0);

		System.out.println(sb.toString());
	}

	static void dfs(int cnt, int idx) {
		if (cnt == m) {
			for (int num : arr) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int nextIdx = idx; nextIdx < n; nextIdx++) {
			arr[cnt] = input[nextIdx];
			dfs(cnt+1, nextIdx+1);
		}
	}
}