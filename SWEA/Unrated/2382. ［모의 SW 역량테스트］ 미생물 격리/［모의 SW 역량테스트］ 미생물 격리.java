import java.util.*;
import java.io.*;

/**
 * SW2382. 미생물 격리
 * 
 * @author 김효선
 * 
 * [문제]
 * - N * N 크기 안에 K개의 미생물 군집 존재
 * - 테두리로 이동 X
 * - 1시간 마다 이동방향에 있는 다음 셀로 이동한다.
 * - 테두리에 도착하면 군집 내 미생물의 절반이 죽고, 이동 방향이 반대로 바뀐다.
 *      - 미생물 수가 홀수 -> 원래 미생물 수 / 2 (소수점 버림)
 * - 이동 후 두 개 이상의 군집이 한 셀에 모이면 군집들이 합쳐진다.
 *      - 합쳐진 군집의 미생물 수는 군집들의 미생물 수의 합.
 *      - 이동 방향은 군집들 중 미생물 수가 가장 많은 군집의 이동방향이 된다.
 *      (미생물 수가 동일한 경우는 없다.)
 * - M시간 후 남아있는 미생물 수의 총합 구하기
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 테스트 케이스 수
 *  - 한 변 길이(N), 격리 시간(M), 미생물 군집 개수(K)
 *  - K개 만큼 row, column, 미생물 수, 이동방향 (상: 1, 하: 2, 좌: 3, 우: 4)
 * 
 * 2. M 만큼 이동한다.
 *  - 모든 군집 for문 돌리기
 *      - (조건) 좌표가 (-1, -1) 이면 이동하지 않는다.
 *      - a 메서드를 실행한다.
 *      - b 메서드 실행한다.
 *          - b 메서드가 true를 반환하면 c 메서드를 실행한다.
 *      - e 메서드를 실행한다.
 *          - e 메서드가 true를 반환하면 d 함수를 실행한다.
 *          - 다 이동시켰을 때, 해당 위치의 visited의 배열에 2개 이상의 값이 저장되어 있다면 d 함수를 실행한다.
 * 
 * 3. 현재 남아있는 군집의 총합을 구한다.
 *  - (조건) 좌표가 (-1, -1)이면 계산하지 않는다.
 * 
 * 4. 총합을 출력한다.
 * 
 * [필요한 메서드]
 * a. 특정 방향으로 이동하기
 *  - DIRECTIONS[인덱스] 만큼 이동한다.
 *  - 현재 위치를 currentPosition 배열에 저장한다.
 *  
 * b. 약에 위치한 방향으로 이동했는지의 여부 반환
 *  - row = 0 / length-1 혹은 column = 0 / length - 1 이면 true 반환
 *  - 아니면 false 반환
 * 
 * c. 약에 위치하면
 *  - 군집 개수 반으로 줄이기
 *  - 방향을 반대로 설정하기 (1 <-> 2, 3 <-> 4)
 * 
 * d. 동일한 위치에 여러 군집이 모였을 경우, 합치기
 *  - 해당 칸에 모여있는 놈들의 군집 수를 다 더한다.
 *  - 방향 수 중 가장 많이 분포한 방향을 찾아낸다.
 *  - 한 놈만을 남겨두고, 나머지는 좌표를 (-1, -1) 처리해준다. 추후 좌표가 (-1, -1) 인 애들에 대해서는 움직이지 않도록 한다.
 *  - 남은 한 놈의 방향과 군집 수를 설정해준다.
 * 
 * e. 동일한 위치에 2개 이상 있는지의 여부
 *  - 모든 currentPosition를 돌면서 2개 이상 같은 값이 저장되어 있으면 true를 반환한다.
 *  - visited (List<List<Integer>>)에 군집 인덱스를 저장하여 판단한다.
 * 
 */
public class Solution {
	static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static List<Integer>[][] visited;
    static int length, time, associationCnt;
    static int[][] associations;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        //*  - 테스트 케이스 수
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            //*  - 한 변 길이(N), 격리 시간(M), 미생물 군집 개수(K)
            st = new StringTokenizer(br.readLine().trim());
            length = Integer.parseInt(st.nextToken());
            time = Integer.parseInt(st.nextToken());
            associationCnt = Integer.parseInt(st.nextToken());

            associations = new int[associationCnt][4];
            visited = new ArrayList[length][length];

            setVisited();

            //*  - K개 만큼 row, column, 미생물 수, 이동방향 (상: 1, 하: 2, 좌: 3, 우: 4)
            for (int idx = 0; idx < associationCnt; idx++) {
                st = new StringTokenizer(br.readLine().trim());

                associations[idx][0] = Integer.parseInt(st.nextToken());
                associations[idx][1] = Integer.parseInt(st.nextToken());
                associations[idx][2] = Integer.parseInt(st.nextToken());
                associations[idx][3] = Integer.parseInt(st.nextToken()) - 1;
            }

            // 2. M 만큼 이동한다.
            //*  - 모든 군집 for문 돌리기
            //*      - visited 초기화하기 
            //*      - (조건) 좌표가 (-1, -1) 이면 이동하지 않는다.
            //*         - a 메서드를 실행한다.
            //*         - b 메서드 실행한다.
            //*             - b 메서드가 true를 반환하면 c 메서드를 실행한다.
            //*      - e 메서드를 실행한다.
            for (int hour = 0; hour < time; hour++) {
                setVisited();
                for (int idx = 0; idx < associationCnt; idx++) {
                    move(idx);
                }
                validateSamePositions();
            }

            int totalAssocation = 0;

            // 3. 현재 남아있는 군집의 총합을 구한다.
            // *  - (조건) 좌표가 (-1, -1)이면 계산하지 않는다.
            for (int[] association : associations) {
                if (association[0] == -1 && association[1] == -1) continue;
                totalAssocation += association[2];
            }

            sb.append("#").append(testCase).append(" ").append(totalAssocation).append("\n");
        }

        System.out.println(sb.toString());
        
    }

    private static void setVisited() {
        for (int rowIdx = 0; rowIdx < length; rowIdx++)
                for (int columnIdx = 0; columnIdx < length; columnIdx++)
                    visited[rowIdx][columnIdx] = new ArrayList<>();
    }

    // a. 특정 방향으로 이동하기
    //*  - DIRECTIONS[인덱스] 만큼 이동한다.
    //*  - 현재 위치를 visited 배열에 저장한다.
    private static void move(int idx) {
        if (associations[idx][0] == -1 && associations[idx][1] == -1) return;

        int directionIdx = associations[idx][3];

        int nextRow = associations[idx][0] + DIRECTIONS[directionIdx][0];
        int nextColumn = associations[idx][1] + DIRECTIONS[directionIdx][1];

        if (validateOnMedicine(nextRow, nextColumn))
            isOnMedicine(idx);

        associations[idx][0] = nextRow;
        associations[idx][1] = nextColumn;

        visited[nextRow][nextColumn].add(idx);
    }

    // b. 약에 위치한 방향으로 이동했는지의 여부 반환
    //*  - row = 0 / length-1 혹은 column = 0 / length - 1 이면 true 반환
    //*  - 아니면 false 반환
    private static boolean validateOnMedicine(int row, int column) {
        if (row == 0 || row == length-1 || column == 0 || column == length-1) return true;
        return false;
    }

    // c. 약에 위치하면
    //*  - 군집 개수 반으로 줄이기
    //*  - 방향을 반대로 설정하기 (1 <-> 2, 3 <-> 4)
    private static void isOnMedicine(int idx) {
        associations[idx][2] /= 2;

        if (associations[idx][3] == 0) associations[idx][3] = 1;
        else if (associations[idx][3] == 1) associations[idx][3] = 0;
        else if (associations[idx][3] == 2) associations[idx][3] = 3;
        else if (associations[idx][3] == 3) associations[idx][3] = 2;
    }

    //* d. 동일한 위치에 여러 군집이 모였을 경우, 합치기
    //*  - 해당 칸에 모여있는 놈들의 군집 수를 다 더한다.
    //*  - 미생물을 가장 많이 가지고 있는 놈의 방향을 구한다.
    //*  - 한 놈만을 남겨두고, 나머지는 좌표를 (-1, -1) 처리해준다. 추후 좌표가 (-1, -1) 인 애들에 대해서는 움직이지 않도록 한다.
    //*  - 남은 한 놈의 방향과 군집 수를 설정해준다.
    private static void add(List<Integer> same) {
        // 해당 칸에 모여있는 놈들의 군집 수를 다 더한다.
        int total = 0;

        // 미생물을 가장 많이 가지고 있는 놈의 방향을 구한다.
        int microMaxCnt = 0;
        int nextDirectionIdx = 0;

        for (int associationIdx : same) {
            total += associations[associationIdx][2];

            if (microMaxCnt < associations[associationIdx][2]) {
                microMaxCnt = associations[associationIdx][2];
                nextDirectionIdx = associations[associationIdx][3];
            }
        }

        // 남은 한 놈의 방향과 군집 수를 설정해준다.
        int firstIdx = same.get(0);
        associations[firstIdx][2] = total;
        associations[firstIdx][3] = nextDirectionIdx;

        // 한 놈만을 남겨두고, 나머지는 좌표를 (-1, -1) 처리해준다. 추후 좌표가 (-1, -1) 인 애들에 대해서는 움직이지 않도록 한다.
        for (int idx : same) {
            if (idx == same.get(0)) continue;
            associations[idx][0] = -1;
            associations[idx][1] = -1;
        }
    }


    // e. 동일한 위치에 2개 이상 있는지 확인
    //*  - 모든 visited를 돌면서 2개 이상의 값이 저장되어 있다면 리스트에 저장한다.
    private static void validateSamePositions() {
        List<List<Integer>> same = new ArrayList<>();

        for (int rowIdx = 0; rowIdx < length; rowIdx++) {
            for (int columnIdx = 0; columnIdx < length; columnIdx++) {
                if (visited[rowIdx][columnIdx].size() >= 2) {
                    same.add(visited[rowIdx][columnIdx]);
                }
            }
        }

        if (same.isEmpty()) return;

        for (List<Integer> samePosition : same)
            add(samePosition);
    }
}
