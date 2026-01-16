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
	static int[] input, nums;
	static boolean[] selected;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine().trim());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		input = new int[n];
		nums = new int[m];
		selected = new boolean[n];

		st = new StringTokenizer(br.readLine().trim());

		for (int idx = 0; idx < n; idx++) {
			input[idx] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(input);

		dfs(0);

		System.out.println(sb.toString());
	}

	static void dfs(int cnt) {
		if (cnt == m) {
			for (int num : nums) {
				sb.append(num).append(" ");
			}
			sb.append("\n");

			return;
		}

		int prev = 0;
		
		for (int idx = 0; idx < n; idx++) {
			if (selected[idx]) continue;
			if (input[idx] == prev) continue;

			selected[idx] = true;
			nums[cnt] = input[idx];
			prev = input[idx];

			dfs(cnt+1);

			selected[idx] = false;
		}
	}
}
