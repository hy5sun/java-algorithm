import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine().trim());

		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		boolean[] isComposite = new boolean[end + 1];

		isComposite[0] = true;
		isComposite[1] = true;

		for (int i = 2; i * i <= end; i++) {
			if (!isComposite[i]) {
				for (int j = i * i; j <= end; j += i) {
					isComposite[j] = true;
				}
			}
		}

		for (int num = start; num <= end; num++) {
			if (!isComposite[num]) sb.append(num).append("\n");
		}

		System.out.println(sb.toString());
	}
}