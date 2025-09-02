import java.util.*;
import java.io.*;

/**
 * - 2개의 선거구. 선거구에는 적어도 하나의 지역. 한 선거구에 있으면 무조건 모두 연결
 * - set<parents> 했을 때 2개여야 함.
 * - 두 선거구의 인구 차이를 최소로 해야 함 -> 구 : 인구 차이의 최솟값
 *
 * 
 * 1. 입력 받기
 * 	- 구역 개수
 * 	- 지역별 인구
 * 	- 인접한 구역 개수와 번호
 * 		- 인접 리스트로 저장
 * 
 * 2. 부분집합으로 그룹 나누기
 * 	- (기저조건) idx == areaCnt -> return;
 * 		- selected 된 애들 : Group1 / 안된 애들 group2
 * 		- 된 애들, 안된 애들 비어있는지 확인 후, 안 비어있으면 연결 가능한지 확인
 * 
 * 3. 한 그룹이 다 연결 되어 있는지 확인
 * 
 * 4. 연결 되어 있으면 최소 차이 갱신
 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int areaCnt, answer = Integer.MAX_VALUE;
	static int[] peopleCnt;
	static List<List<Integer>> friends = new ArrayList<>();
	static boolean[] selected;
	
	public static void main(String[] args) throws IOException {
		areaCnt = Integer.parseInt(br.readLine());
		
		peopleCnt = new int[areaCnt+1];
		selected = new boolean[areaCnt+1];
		
		st = new StringTokenizer(br.readLine().trim());
		for (int idx = 1; idx <= areaCnt; idx++) {
			peopleCnt[idx] = Integer.parseInt(st.nextToken());
		}
		
		for (int idx = 0; idx <= areaCnt; idx++) {
			friends.add(new ArrayList<>());
		}
		
		for (int areaIdx = 1; areaIdx <= areaCnt; areaIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int count = Integer.parseInt(st.nextToken());
			
			for (int idx = 0; idx < count; idx++) {
				int otherArea = Integer.parseInt(st.nextToken());
				
				friends.get(areaIdx).add(otherArea);
				friends.get(otherArea).add(areaIdx);
			}
		}
		
		// 그룹 나누기 (부분집합)
		separateGroup(1);
		
		// 출력
		if (answer == Integer.MAX_VALUE) answer = -1;
		System.out.println(answer);
	}
	
	// 2. 부분집합으로 그룹 나누기
//	 * 	- (기저조건) idx == areaCnt -> return;
//	 * 		- selected 된 애들 : Group1 / 안된 애들 group2
//	 * 		- 된 애들, 안된 애들 비어있는지 확인 후, 안 비어있으면 연결 가능한지 확인
	private static void separateGroup(int areaIdx) {
		if (areaIdx == areaCnt) {
			List<Integer> group1 = new ArrayList<>();
			List<Integer> group2 = new ArrayList<>();
			
			for (int idx = 1; idx <= areaCnt; idx++) {
				if (selected[idx]) group1.add(idx);
				else group2.add(idx);
			}
			
			if (group1.isEmpty() || group2.isEmpty()) return;
			else {
				if(isPossible(group1) && isPossible(group2)) { // 연결되어 있으면..
					answer = Math.min(getDiff(group1, group2), answer);
				}
			}
			return;
		}
		
		selected[areaIdx] = true;
		separateGroup(areaIdx+1);
		
		selected[areaIdx] = false;
		separateGroup(areaIdx+1);
	}
	
	// 그룹으로 나뉜 애들이 연결될 수 있는지 보고 싶었음
	private static boolean isPossible(List<Integer> group) {		
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[areaCnt+1];
		
		int count = 1;
		
		queue.offer(group.get(0));
		visited[group.get(0)] = true;
		
		
		while (!queue.isEmpty()) {
			int current = queue.poll();
			
			List<Integer> next = friends.get(current);
			
			for (int nextIdx : next) {
				if (visited[nextIdx]) continue;
				if (group.contains(nextIdx)) {
					count++;
					queue.offer(nextIdx);
					visited[nextIdx] = true;
				}
			}
		}
		
		return count == group.size();
		
	}
	
	// 인구 차 계산하기..
	private static int getDiff(List<Integer> agroup, List<Integer> bgroup) {
		int aTotal = 0, bTotal = 0;
		
		for (int areaIdx : agroup) aTotal += peopleCnt[areaIdx];
		for (int areaIdx : bgroup) bTotal += peopleCnt[areaIdx];
		
		return Math.abs(aTotal - bTotal);
	}
}
