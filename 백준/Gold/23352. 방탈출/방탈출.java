import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;
    
    public static int width;
    public static int height;
    public static int maxSum = 0;
    public static int maxDistance = Integer.MIN_VALUE;

    public static int[][] map;
    public static boolean[][] visited;
    public static int[][] direction = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());

        initMap();
        
        for (int rowIdx = 0; rowIdx < height; rowIdx++) {
            for (int columnIdx = 0; columnIdx < width; columnIdx++) {
                if (map[rowIdx][columnIdx] != 0) {
                    int[] result = BFS(rowIdx, columnIdx);
                    // 최장거리 보다 클 때 혹은 최장거리랑 같은데 숫자 합은 더 클 때 갱신
                    if (maxDistance < result[0] || (maxDistance == result[0] && result[1] > maxSum)) {
                        maxDistance = result[0];
                        maxSum = result[1];
                    }
                }
            }
        }

        System.out.println(maxSum);
    }

    private static void initMap() throws IOException {
        map = new int[height][width];

        for (int rowIdx = 0; rowIdx < height; rowIdx++) {
            st = new StringTokenizer(br.readLine().trim());

            for (int columnIdx = 0; columnIdx < width; columnIdx++) {
                map[rowIdx][columnIdx] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static int[] BFS(int startX, int startY) {
        Queue<int[]> path = new LinkedList<>();
        visited = new boolean[height][width];
        int[][] distances = new int[height][width];

        int sum = 0;
        int maxDistancePerStartLocation = Integer.MIN_VALUE;

        // 시작점 queue에 추가하기
        path.offer(new int[]{startX, startY});

        // queue가 빌 때까지 아래 과정을 반복함
        while (!path.isEmpty()) {

            // queue의 맨 앞 원소를 꺼내고, currentX와 currentY를 저장함
            int[] current = path.poll();

            int currentX = current[0];
            int currentY = current[1];
            
            // 상하좌우 와리가리
            for (int idx = 0; idx < 4; idx++) {
                int nextX = currentX + direction[idx][0];
                int nextY = currentY + direction[idx][1];

                // 지도에서 나가면 나가고
                if (nextX < 0 || nextX >= height || nextY < 0 || nextY >= width) continue;

                // 0 이면 들어갈 수 없는 방이니까 나가고
                if (map[nextX][nextY] == 0) continue;

                // 아직 방문 안 했으면 얘로 라스고
                if (!visited[nextX][nextY]) {
                    // 방문처리
                    visited[nextX][nextY] = true;
                    // 다음으로 갈 곳까지의 거리 저장
                    distances[nextX][nextY] = distances[currentX][currentY] + 1;
                    // 다음에 갈 거니까 큐에 추가하기
                    path.offer(new int[]{nextX, nextY});
                }
            }
        }

        // 다 돌았나요? 그럼 최대 거리와 최대 합을 찾아보아요
        for (int rowIdx = 0; rowIdx < height; rowIdx++) {
            for (int columnIdx = 0; columnIdx < width; columnIdx++) {
                // 최장 거리보다 더 큰 게 있으면
                if (maxDistancePerStartLocation <= distances[rowIdx][columnIdx]) {
                    // 최장 거리 갱신
                    maxDistancePerStartLocation = distances[rowIdx][columnIdx];

                    // 시작 지점 == 끝 지점일 경우를 고려하여 일단 sum에 출발 지점 저장
                    sum = map[startX][startY];
                    
                    // 시작 지점 != 끝 지점이면 sum에 끝 위치 (현재 위치) 까지 더해주기
                    if (!(startX == rowIdx && startY == columnIdx))
                        sum += map[rowIdx][columnIdx];
                }
            }
        }
        
        // [최장 거리, 합] 반환
        return new int[]{maxDistancePerStartLocation, sum};
    }
}
