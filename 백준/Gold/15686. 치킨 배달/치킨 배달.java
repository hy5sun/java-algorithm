import java.io.*;
import java.util.*;

/**
 * BJ15868. 치킨 배달
 *
 * [도시 정보]
 * 0 : 빈 칸
 * 1 : 집
 * 2 : 치킨집
 *
 * 1. 입력 받는다.
 *  1-1. 도시 길이 length와 치킨집 최대 개수 chickenMaxCnt를 입력 받는다.
 *  1-2. 입력값에 따라 집과 치킨집의 좌표를 저장한다.
 * 
 * 2. 치킨집과 집 사이의 거리를 구한다.
 *  2-1. 치킨집 좌표를 기준으로 가까운 집을 찾는다.
 *      - distance[치킨집 인덱스][집 인덱스] = |home[집 인덱스][x] - chicken[치킨 인덱스][x]| + |home[집 인덱스][y] - chicken[치킨 인덱스][y]|
 * 
 * 3. distance를 순회하면서 치킨 인덱스를 기준으로 재귀를 돌려본다.
 *  3-1. (기저조건) 현재 인덱스가 최대 개수와 동일해지면 최소 거리 값을 계산한다. return;
 *      - [집 인덱스(1)]를 기준으로 distance 값 중 가장 작은 값을 더한다.
 *      - 다 돌리면 min 값을 계산한다.
 *  3-2. 아직 방문하지 않았으면 visited 처리를 해주고, survivedChicken 배열 idx 번째에 distance[치킨집 인덱스] 를 넣어준다.
 *  
 * 4. min 값을 출력한다.
 */
public class Main {

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;

    public static int length;
    public static int chickenMaxCnt;

    public static List<List<Integer>> home = new ArrayList<>();
    public static List<List<Integer>> chicken = new ArrayList<>();

    public static int[][] distance;
    public static boolean[] isVisited;
    public static int[][] survivedChicken;
    public static int minDistance = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받는다. 
        // 1-1. 도시 길이 length와 치킨집 최대 개수 chickenMaxCnt를 입력 받는다.
        st = new StringTokenizer(br.readLine().trim());

        length = Integer.parseInt(st.nextToken());
        chickenMaxCnt = Integer.parseInt(st.nextToken());

        // 1-2. 입력값에 따라 집과 치킨집의 좌표를 저장한다.
        initMap();

        isVisited = new boolean[chicken.size()+1];
        survivedChicken = new int[chickenMaxCnt][home.size()];

        // 2. 치킨집과 집 사이의 거리를 구한다.
        calculateDistance();

        // 3. distance를 순회하면서 치킨 인덱스를 기준으로 재귀를 돌려본다.
        getMinimumDistance(0, 0);

        // 4. min 값을 출력한다.
        System.out.println(minDistance);
    }

    /**
     * 입력값이 1이면 집, 2면 치킨집 배열에 좌표를 저장한다.
     * 
     * @throws IOException
     */
    private static void initMap() throws IOException {
        for (int rowIdx = 0; rowIdx < length; rowIdx++) {

            st = new StringTokenizer(br.readLine().trim());

            for (int columnIdx = 0; columnIdx < length; columnIdx++) {

                int info = Integer.parseInt(st.nextToken());

                if (info == 1) {
                    home.add(new ArrayList<>(Arrays.asList(rowIdx, columnIdx)));
                } else if (info == 2) {
                    chicken.add(new ArrayList<>(Arrays.asList(rowIdx, columnIdx)));
                }
            }
        }
    }

    /**
     * 2. 치킨집과 집 사이의 거리를 구한다.
     *   - distance[치킨집 인덱스][집 인덱스] = |home[집 인덱스][x] - chicken[치킨 인덱스][x]| + |home[집 인덱스][y] - chicken[치킨 인덱스][y]|
     */
    private static void calculateDistance() {
        distance = new int[chicken.size()][home.size()];

        for (int chickenIdx = 0; chickenIdx < chicken.size(); chickenIdx++) {
            for (int houseIdx = 0; houseIdx < home.size(); houseIdx++) {
                distance[chickenIdx][houseIdx] = 
                    Math.abs(home.get(houseIdx).get(0) - chicken.get(chickenIdx).get(0)) 
                        + Math.abs(home.get(houseIdx).get(1) - chicken.get(chickenIdx).get(1));
            }
        }
    }

    /**
     * 3. distance를 순회하면서 치킨 인덱스를 기준으로 재귀를 돌려본다.
     * 
     * @param index : survivedChicken 배열의 인덱스
     * @param startChickenIdx : 살아남을 것을 고려하고 있는 치킨집의 인덱스
     */
    private static void getMinimumDistance(int index, int startChickenIdx) {
        // 3-1. (기저조건) 현재 인덱스가 최대 개수와 동일해지면 최소 거리 값을 계산한다. return;
        if (index == chickenMaxCnt) {
            // - [집 인덱스(1)]를 기준으로 distance 값 중 가장 작은 값을 더한다.
            int totalDistance = calculateMinimunDistance();

            // - 다 돌리면 min 값을 계산한다.
            minDistance = Math.min(totalDistance, minDistance);
            return;
        }

        // 3-2. 아직 방문하지 않았으면 visited 처리를 해주고, survivedChicken 배열 idx 번째에 distance[치킨집 인덱스] 를 넣어준다.
        for (int chickenIndex = startChickenIdx; chickenIndex < chicken.size(); chickenIndex++) {
            if (!isVisited[chickenIndex]) {
                isVisited[chickenIndex] = true;
                survivedChicken[index] = distance[chickenIndex];
                getMinimumDistance(index+1, chickenIndex+1);
                isVisited[chickenIndex] = false;
            }
        }
    }

    /**
     * 집 기준 가장 짧은 치킨 거리를 계산하여 반환한다.
     * 
     * @return 가장 짧은 치킨 거리 값
     */
    private static int calculateMinimunDistance() {
        int totalDistance = 0;

        for (int homeIdx = 0; homeIdx < home.size(); homeIdx++) {
            int currentMinDistance = Integer.MAX_VALUE;
            
            for (int chickenIdx = 0; chickenIdx < survivedChicken.length; chickenIdx++) {
                currentMinDistance = Math.min(currentMinDistance, survivedChicken[chickenIdx][homeIdx]);
            }
            totalDistance += currentMinDistance;
        }

        return totalDistance;
    }
}

