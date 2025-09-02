import java.io.*;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 위상정렬.. 
 * 
 * 위상정렬 진행하기
 * - 자신 앞에 있어야 하는 정점의 개수가 0인 애들을 모두 Queue에 넣는다.
 * - Queue가 빌 때까지 다음을 반복한다.
 * 	- Queue의 맨 앞 정점을 꺼낸다.
 * 	- 출력문에 포함시킨다.
 * 	- 해당 정점의 앞에 있어야 하는 정점들을 조회한다.
 * 		- 조회한 정점의 (앞에 있어야 하는 정점 개수) 를 1 빼준다.
 * 		- 조회한 정점의 (앞에 있어야 하는 정점 개수) == 0이면 queue에 추가한다.
 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int studentCnt, compareCnt;
	static List<List<Integer>> compare = new ArrayList<>();
	static int[] count;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		
		studentCnt = Integer.parseInt(st.nextToken());
		compareCnt = Integer.parseInt(st.nextToken());
		
		count = new int[studentCnt+1];
		
		for (int idx = 0; idx <= studentCnt; idx++)
			compare.add(new ArrayList<>());
		
		for (int idx = 0; idx < compareCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int studentA = Integer.parseInt(st.nextToken());
			int studentB = Integer.parseInt(st.nextToken());
			
			compare.get(studentA).add(studentB); // 내 뒤에 와야 하는 놈들 저장
			count[studentB]++; // 내 앞에 와야 하는 놈들의 수
		}
		
		bfs();
		
		System.out.println(sb.toString());
	}
	
	private static void bfs() {
		Queue<Integer> queue = new LinkedList<>();
		
		for (int studentIdx = 1; studentIdx <= studentCnt; studentIdx++) {
			if (count[studentIdx] == 0) // 내 앞에 올 애들이 아무도 ㄷ없다?
				queue.add(studentIdx);
		}
		
		while (!queue.isEmpty()) {
			int currentStudentIdx = queue.poll();
			
			sb.append(currentStudentIdx).append(" ");
			
			// 나는 넣었으니, 내 뒤에 있기로 한 애들 넣어줘야지
			List<Integer> behindMe = compare.get(currentStudentIdx);
			
			for (int behindMeStudentIdx : behindMe) {
				
				// 니 앞에 있어야 하는 내가 사라졌으니까 1빼주기
				count[behindMeStudentIdx]--;
				
				if (count[behindMeStudentIdx] == 0)
					queue.offer(behindMeStudentIdx);
			}
			
		}
	}

}
