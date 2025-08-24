import java.io.*;
import java.util.*;

/**
 * BJ2252. 줄 세우기
 *
 * [문제]
 * - N명의 학생들을 키 순서대로 줄을 세운다.
 * - 일부 학생들의 키만 비교해서 정렬한다.
 * - 입력으로 A, B의 학생 키가 주어지는데, A가 B 앞에 서야 한다는 뜻이다.
 * - 학생들 번호는 1 ~ N 번이다.
 *
 * [풀이]
 * 1. 입력 받기
 *  - 학생 수, 비교 수를 입력 받는다.
 *  - 비교 수만큼 학생 키 순서를 입력 받는다. (A B -> A > B)
 *      - 정점별로 자신을 향하고 있는 정점의 개수를 저장한다. : connectedVertexCnt
 *      - 정점별로 자신이 향하고 있는 정점들을 인접 리스트로 저장한다. : toVertexes
 *
 * 2. BFS를 통해 위상정렬한다.
 *  - 자신을 향하고 있는 정점의 개수가 0인 애들을 queue에 넣어준다.
 *  - queue가 빌 때까지 다음을 반복한다.
 *      - queue 맨 앞 정점을 꺼낸다.
 *      - sortedStudents에 추가한다.
 *      - 맨 앞 정점이 향하고 있는 정점들을 조회한다.
 *      - connectedVertexCnt[정점]--; 를 한다.
 *      - connectedVertexCnt[정점] == 0 이라면 queue에 추가한다.
 *
 * 3. 출력한다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sortedStudents = new StringBuilder();

    static int studentCnt, compareCnt;
    static List<List<Integer>> toVertexes = new ArrayList<>();
    static int[] connectedVertexCnt;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 학생 수, 비교 수를 입력 받는다.
        st = new StringTokenizer(br.readLine().trim());

        studentCnt = Integer.parseInt(st.nextToken());
        compareCnt = Integer.parseInt(st.nextToken());

        connectedVertexCnt = new int[studentCnt + 1];

        for (int idx = 0; idx <= studentCnt; idx++) {
            toVertexes.add(new ArrayList<>());
        }

        // - 비교 수만큼 학생 키 순서를 입력 받는다. (A B -> A > B)
        //        - 정점별로 자신을 향하고 있는 정점의 개수를 저장한다. : connectedVertexCnt
        //        - 정점별로 자신이 향하고 있는 정점들을 인접 리스트로 저장한다. : toVertexes
        for (int idx = 0; idx < compareCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int studentA = Integer.parseInt(st.nextToken());
            int studentB = Integer.parseInt(st.nextToken());

            toVertexes.get(studentA).add(studentB);
            connectedVertexCnt[studentB]++;
        }

        // 2. BFS를 통해 위상정렬한다.
        sortStudents();

        // 3. 출력한다.
        System.out.println(sortedStudents.toString());
    }

    /**
     * BFS를 통해 위상정렬한다.
     *  *  - 자신을 향하고 있는 정점의 개수가 0인 애들을 queue에 넣어준다.
     *  *  - queue가 빌 때까지 다음을 반복한다.
     *  *      - queue 맨 앞 정점을 꺼낸다.
     *  *      - sortedStudents에 추가한다.
     *  *      - 맨 앞 정점이 향하고 있는 정점들을 조회한다.
     *  *      - connectedVertexCnt[정점]--; 를 한다.
     *  *      - connectedVertexCnt[정점] == 0 이라면 queue에 추가한다.
     */
    private static void sortStudents() {
        Queue<Integer> queue = new LinkedList<>();

        for (int studentIdx = 1; studentIdx <= studentCnt; studentIdx++) {
            if (connectedVertexCnt[studentIdx] == 0) {
                queue.offer(studentIdx);
            }
        }

        while (!queue.isEmpty()) {
            int currentStudent = queue.poll();

            sortedStudents.append(currentStudent).append(" ");

            List<Integer> toVertex = toVertexes.get(currentStudent);

            for (int vertexIdx : toVertex) {
                connectedVertexCnt[vertexIdx]--;

                if (connectedVertexCnt[vertexIdx] == 0) {
                    queue.offer(vertexIdx);
                }
            }
        }
    }
}
