
import java.util.*;
import java.io.*;

/**
 * BJ20955. 민서의 응급 수술
 * 
 * 
 * [문제]
 * - 모든 뉴런을 하나의 트리 형태로 연결한다.
 * - 할 수 있는 행위
 *  1) 연결되지 않은 두 뉴런을 연결
 *  2) 이미 연결된 두 뉴런의 연결을 끊음
 * - 연결 정보가 주어졌을 때, 하나의 트리 형태로 연결하기 위해 필요한 최소 연산 횟수
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 뉴런의 개수 N, 시냅스 개수 M
 *  - (M만큼) 시냅스로 연결된 두 뉴런의 번호 u, v
 * 
 * 2. union-find를 수행한다.
 *  2-1. make() : parent와 rank를 초기화하고, 각각의 원소의 부모를 자기 자신으로 설정한다. 
 *  2-2. find() : element의 부모를 찾는다.
 *  2-3. union() : 부모가 다르면 두 집합을 합친다.
 * 
 * 3. 이미 연결되어 있는 애들을 union() 해준다.
 *   - 단, 사이클을 막기 위해 부모가 같으면 cutCount를 카운트해주고, 넘어간다.
 * 
 * 4. 대표값을 set에 넣어, 최종 집합 개수를 구한다.
 *
 * 5. 최종적으로 연결이 필요한 연산의 수는 집합 개수 - 1 + cutCount이다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int vertexCnt, edgeCnt;
    static int[] parents;
    static int[] rank;
    static Set<Integer> parentSet = new HashSet<>();
    static int cutCount = 0;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 뉴런의 개수 N, 시냅스 개수 M
        // *  - (M만큼) 시냅스로 연결된 두 뉴런의 번호 u, v
        st = new StringTokenizer(br.readLine().trim());

        vertexCnt = Integer.parseInt(st.nextToken());
        edgeCnt = Integer.parseInt(st.nextToken());

        // 2-1. make() : parent와 rank를 초기화하고, 각각의 원소의 부모를 자기 자신으로 설정한다. 
        make();

        // 3. 이미 연결되어 있는 애들을 union() 해준 뒤,
        for (int idx = 0; idx < edgeCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // 같은 부모라면 (사이클) => 끊어주기
            if (find(u) == find(v)) {
                cutCount++;
                continue;
            }

            union(u, v);
        }

        // 4. 대표값을 set에 넣어, 최종 집합 개수를 구한다.
        for (int idx = 1; idx <= vertexCnt; idx++) {
            parentSet.add(find(idx));
        }

        // 5. 최종적으로 연결이 필요한 연산의 수는 집합 개수 - 1이다.
        System.out.println(parentSet.size() - 1 + cutCount);
    }

    private static void make() {
        rank = new int[vertexCnt+1];
        parents = new int[vertexCnt+1];

        for (int idx = 1; idx <= vertexCnt; idx++)
            parents[idx] = idx;
    }

    private static int find(int element) {
        if (parents[element] == element) return element;
        return parents[element] = find(parents[element]);
    }

    private static void union(int element1, int element2) {
        int parent1 = find(element1);
        int parent2 = find(element2);

        if (parent1 == parent2) return;

        if (rank[parent1] > rank[parent2]) {
            parents[parent2] = parent1;
            return;
        }

        parents[parent1] = parent2;

        if (rank[parent1] == rank[parent2])
            rank[parent2]++;
    }
}