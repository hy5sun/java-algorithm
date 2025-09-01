import java.util.*;
import java.io.*;

/**
 * 
 * @author SSAFY
 *
 * 연결하다가 사이클이 처음 만들어지는 시점에 프로젝트 즉시 중단
 * 0 ~ n -1 유적지 (n개)
 * m개의 유적지 연결 정보
 * 유적지를 순환 형태로 연결되게 입력이 주어질 수 있음. 그때 중단.
 * 그때까지 설치한 통로 개수 출력. 만약 한 번도 사이클 형성되지 않으면 0 출력
 * 
 * 1.입력받기
 * 	- 유적지 개수, 통로 개수
 * 	- a, b 연결
 * 
 * 2. 하핫
 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int vertexCnt, edgeCnt, connectedCnt;
	static boolean isCycle;
	static int[] parents;
	static int[] rank;

	public static void main(String[] args) throws IOException {
		//--------------솔루션 코드를 작성하세요.---------------------------
		st = new StringTokenizer(br.readLine().trim());
		vertexCnt = Integer.parseInt(st.nextToken());
		edgeCnt = Integer.parseInt(st.nextToken());
		
		make();
		
		isCycle = false;
		connectedCnt = 0;
		
		for (int idx = 0; idx <edgeCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			int vertexA = Integer.parseInt(st.nextToken());
			int vertexB = Integer.parseInt(st.nextToken());
			
			connectedCnt++;
			
			if (find(vertexA) == find(vertexB)) {
				isCycle = true;
				break;
			}
			
			union(vertexA, vertexB);
		}
		
		if (isCycle) sb.append(connectedCnt);
		else sb.append(0);
		
		System.out.println(sb.toString());
	}

	private static void make() {
		rank = new int[vertexCnt];
		parents = new int[vertexCnt];
		
		for (int idx = 0; idx < vertexCnt; idx++) {
			parents[idx] = idx;
		}
	}
	
	private static int find(int element) {
		if (element == parents[element]) return element;
		return parents[element] = find(parents[element]);
	}
	
	private static void union(int elementA, int elementB) {
		int parentA = find(elementA);
		int parentB = find(elementB);
		
		if (parentA == parentB) return;
		
		if (rank[parentA] > rank[parentB]) {
			parents[parentB] = parentA;
			return;
		}
		
		parents[parentA] = parentB;
		
		if (rank[parentA] == rank[parentB]) rank[parentB]++;
	}
}
