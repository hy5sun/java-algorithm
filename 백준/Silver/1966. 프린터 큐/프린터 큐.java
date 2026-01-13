import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	static int testCaseCnt, paperCnt, paperIdx, cnt;
	static Queue<QueueItem> queue;
	static PriorityQueue<Integer> priorityQueue;

	public static void main(String[] arsg) throws IOException {
		testCaseCnt = Integer.parseInt(br.readLine().trim());

		for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
			st = new StringTokenizer(br.readLine().trim());

			paperCnt = Integer.parseInt(st.nextToken());
			paperIdx = Integer.parseInt(st.nextToken());

			queue = new LinkedList<>();
			priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());

			st = new StringTokenizer(br.readLine().trim());
			for (int idx = 0; idx < paperCnt; idx++) {
				int priority = Integer.parseInt(st.nextToken());
				queue.offer(new QueueItem(idx, priority));
				priorityQueue.offer(priority);
			}

			cnt = 0;

			while (!queue.isEmpty()) {
				QueueItem paper = queue.poll();

				if (paper.priority == priorityQueue.peek()) {
					cnt++;
					priorityQueue.poll();

					if (paper.index == paperIdx) {
						sb.append(cnt).append("\n");
						break;
					}
				} else {
					queue.offer(paper);
				}
			}
		}

		System.out.println(sb.toString());
	}

	static class QueueItem {
		private int index;
		private int priority;

		public QueueItem(int index, int priority) {
			this.index = index;
			this.priority = priority;
		}
	}
}
