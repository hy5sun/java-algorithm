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
	static int[] nums, arr;
	static boolean[] isSelected;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine().trim());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		nums = new int[n];
		arr = new int[m];

		isSelected = new boolean[n];

		st = new StringTokenizer(br.readLine().trim());

		for (int idx = 0; idx < n; idx++) {
			nums[idx] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(nums);

		dfs(0);

		System.out.println(sb.toString());
	}

	static void dfs(int cnt) {
		if (cnt == m) {
			for (int num : arr) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int idx = 0; idx < n; idx++) {
			if (!isSelected[idx]) {
				isSelected[idx] = true;
				arr[cnt] = nums[idx];

				dfs(cnt + 1);
				isSelected[idx] = false;
			}
		}
	}
}
