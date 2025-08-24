import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int rowCnt, columnCnt, distanceLimit;
    static int[][] map;
    static List<int[]> initialAttackers = new ArrayList<>(); // 초기 적 위치 저장
    static int maxRemovedCnt = 0;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        rowCnt = Integer.parseInt(st.nextToken());
        columnCnt = Integer.parseInt(st.nextToken());
        distanceLimit = Integer.parseInt(st.nextToken());

        initMap();

        // 3. 적의 수가 많아봤자 45명이니까 3개의 궁수를 기준으로 거리가 D 이하면서도 가장 적은 애를 찾음
        // *  - 적이 빌 때까지 반복
        // *  - limit 보다 작으면서도 가장 적은 거리에 있는 좌표값을 찾아 삭제함
        // *  - {row, column} 값을 set에 추가한다.
        int[] archers = new int[3];
        setArcher(0, 0, archers);

        // 4. 최대 제거 수를 갱신해서 출력한다.
        sb.append(maxRemovedCnt);
        System.out.println(sb.toString());
    }

    private static void initMap() throws IOException {
        map = new int[rowCnt][columnCnt];

        for (int rowIdx = 0; rowIdx < rowCnt; rowIdx++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int columnIdx = 0; columnIdx < columnCnt; columnIdx++) {
                map[rowIdx][columnIdx] = Integer.parseInt(st.nextToken());

                if (map[rowIdx][columnIdx] == 1) {
                    initialAttackers.add(new int[]{rowIdx, columnIdx});
                }
            }
        }
    }

    private static void setArcher(int idx, int columnIdx, int[] archers) {
        if (idx == 3) {
            simulate(archers);
            return;
        }

        for (int nextIdx = columnIdx; nextIdx < columnCnt; nextIdx++) {
            archers[idx] = nextIdx;
            setArcher(idx + 1, nextIdx + 1, archers);
        }
    }

    private static void simulate(int[] archers) {
        // 시뮬레이션을 위해 초기 적 리스트 복사
        List<int[]> attackers = new ArrayList<>();

        for (int[] attacker : initialAttackers) {
            attackers.add(attacker.clone());
        }

        int removedCnt = 0;

        // 적이 모두 사라질 때까지 턴 진행
        while (!attackers.isEmpty()) {
            Set<int[]> targets = new HashSet<>(); // 이번 턴에 제거될 적들 (중복 제거)

            // 1. 각 궁수가 공격할 적 찾기
            for (int archerCol : archers) {
                int[] target = findClosestArcher(attackers, archerCol);
                if (target != null) {
                    targets.add(target);
                }
            }

            // 2. 찾은 적들 제거
            if (!targets.isEmpty()) {
                // Set에 있는 타겟을 실제 리스트에서 제거
                // Iterator를 사용해야 순회 중 삭제 에러가 발생하지 않음
                Iterator<int[]> iterator = attackers.iterator();
                while (iterator.hasNext()) {
                    int[] attacker = iterator.next();
                    for (int[] target : targets) {
                        if (attacker[0] == target[0] && attacker[1] == target[1]) {
                            iterator.remove();
                            removedCnt++;
                            break;
                        }
                    }
                }
            }

            // 3. 살아남은 적들 아래로 한 칸 이동
            Iterator<int[]> iterator = attackers.iterator();
            while (iterator.hasNext()) {
                int[] attacker = iterator.next();
                attacker[0]++; // 행(row) 증가
                // 성에 도달한(맵을 벗어난) 적은 제거
                if (attacker[0] == rowCnt) {
                    iterator.remove();
                }
            }
        }

        // 최대값 갱신
        maxRemovedCnt = Math.max(maxRemovedCnt, removedCnt);
    }

    private static int[] findClosestArcher(List<int[]> attackers, int archerColumn) {
        // 최소 거리
        int minDistance = Integer.MAX_VALUE;
        int[] target = null;

        // 공격자 돌면서 최소 거리에 있는 적 박살내기
        for (int[] attacker : attackers) {
            // 거리 계산하기
            int distance = Math.abs(rowCnt - attacker[0]) + Math.abs(archerColumn - attacker[1]);

            if (distance > distanceLimit) continue;

            if (distance < minDistance) {
                minDistance = distance;
                target = attacker;
            } else if (distance == minDistance) {
                // 거리가 같으면 더 왼쪽에 있는 걸 선택
                if (target != null && attacker[1] < target[1]) {
                    target = attacker;
                }
            }
        }

        // 추가하기
        return target;
    }
}