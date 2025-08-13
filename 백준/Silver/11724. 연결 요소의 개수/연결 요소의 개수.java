import java.io.*;
import java.util.*;

public class Main {
	
	public static BufferedReader br;
	public static StringTokenizer st;
	
	public static int serverCnt;
	public static int networkConnectionCnt;
	
	public static HashMap<Integer, List<Integer>> network = new HashMap<>(); // 네트워크
	public static List<Integer> serverGroup = new ArrayList<>(); // 그룹별 서버 저장 리스트
	public static boolean[] isVisited; // 서버 방문 여부
	public static int groupCnt = 0; // 그룹 수

	public static void main(String[] args) throws FileNotFoundException, IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		
		serverCnt = Integer.parseInt(st.nextToken());
		networkConnectionCnt = Integer.parseInt(st.nextToken());
		
		isVisited = new boolean[serverCnt+1];
		int u, v;
		
		for (int idx = 0; idx < networkConnectionCnt; idx++) {
			st = new StringTokenizer(br.readLine());
			
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			
			initNetwork(u, v);
			initNetwork(v, u);
			
			List<Integer> tmp;
			
			if (network.get(u) == null) {
				tmp = new ArrayList<>();
			} else {
				tmp = network.get(u);
			}
			
			tmp.add(v);
			network.put(u, tmp);
		}
				
		for (int idx = 1; idx <= serverCnt; idx++) {
			getGroup(idx);
			
			// 연결된 서버 그룹이 있다면 그룹 수를 1 늘린다.
			if (serverGroup.size() != 0) {
				groupCnt++;
				
				// 서버 그룹 내에 있던 서버 초기화
				serverGroup.clear();
			}
		}
		
		System.out.println(groupCnt);
	}
	
	// 네트워크 생성 및 값 지정을 수행한다.
	private static void initNetwork(int key, int value) {
		List<Integer> tmp;
		
		if (network.get(key) == null) {
			tmp = new ArrayList<>();
		} else {
			tmp = network.get(key);
		}
		
		tmp.add(value);
		network.put(key, tmp);
	}
	
	private static void getGroup(int idx) {
		// 현재 서버가 이미 방문한 서버라면 패스하고, 아니라면 로직을 수행한다.
		if (!isVisited[idx]) {
			// 방문처리한다.
			isVisited[idx] = true;
			
			// value로 들어있는 서버들 내부도 돌면서 연결 짓고, 방문 처리한다.
            if (network.get(idx) != null) {
                for (int nextIdx : network.get(idx)) {
				    getGroup(nextIdx);
                }
			}
			// 서버 그룹에 추가한다.
			serverGroup.add(idx);
		}
	}

}
