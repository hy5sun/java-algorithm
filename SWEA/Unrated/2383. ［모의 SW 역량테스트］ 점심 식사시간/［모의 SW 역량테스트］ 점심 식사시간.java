import java.util.*;
import java.io.*;

/**
 * SW2383. 점심 식사시간
 * 
 * [문제]
 * - N * N 크기의 정사각형 모양 방
 * - 아래 층으로 내려가야 하는데, 최대한 빠른 시간 내에 내려가야 함
 * - 사람들 : P / 계단 입구 : S
 * - 이동 완료 시간 = 모든 사람들이 계단을 내려가 아래 층으로 이동을 마친 시간
 * - 계단 입구까지 이동 시간 = |사람Row - 계단Row| + |사람Column - 계단Column|
 * - 계단 위에는 동시에 최대 3명까지만 올라갈 수 있다.
 * - 계단마다 길이 K가 주어지는데, 계단에서 완전히 내려가는데 K분 걸린다.
 * 
 * 목적 : 모든 사람들이 계단을 내려가 이동이 완료되는 최소 시간 구하기
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 테스트 케이스 수
 *  - 지도의 한 변 길이
 *  - 지도 정보
 *      - 계단 위치 및 시간 정보 따로 저장
 *      - 사람 위치 따로 저장
 * 
 * 2. 어떤 계단으로 갈건지 정하기..?
 *  - 부분집합..?
 * 
 * 3. 가기로 한 계단 기준 상대 좌표 계산하기
 *  - x 상대좌표 + y 상대좌표 = 계단 입구까지 가는 데 걸리는 시간
 *  - 가기로 한 계단 인덱스에 Person(사람Idx, 가는 데 걸리는 시간 + 1) 저장 (PriorityQueue에 저장하기. 시간 = 가중치)
 * 
 * 4. 계단 내려가기
 * 
 * 5. Queue에 아무것도 남지 않게 됐을 때, 그때 시간을 구함
 *  - 3번에서 큐에 저장할 때, 최소 시간 + 큐에 아무것도 남지 않게 됐을 때까지 걸린 시간 = 최종 시간
 * 
 * 6. 시간 최솟값 갱신
 * 
 */
public class Solution {
    static final int STAIR_COUNT = 2;
    static final int PERSON = 1;
    static final int EMPTY = 0;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int testCaseCnt, length, peopleCnt, totalTime, minTotalTime;
    static List<int[]> peoplePosition = new ArrayList<>();
    static Stair[] stairPosition;
    static boolean[] isSelected;
    static PriorityQueue<Person> stair1 = new PriorityQueue<>();
    static PriorityQueue<Person> stair2 = new PriorityQueue<>();

    static class Person implements Comparable<Person> {
        int idx, time, lastTime; // 사람 인덱스, 계단까지 도착하는 시간, 밑으로 내려갈 때까지 남은 시간

        Person(int idx, int time, int lastTime) {
            this.idx = idx;
            this.time = time;
            this.lastTime = lastTime;
        }

        public void minusLastTime() {
            this.lastTime--;
        }

        @Override
        public int compareTo(Person other) {
            return this.time - other.time;
        }
    }

    static class Stair {
        int row, column, time;

        Stair(int row, int column, int time) {
            this.row = row;
            this.column = column;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            length = Integer.parseInt(br.readLine());

            peoplePosition.clear();
            stairPosition = new Stair[STAIR_COUNT];

            peopleCnt = 0;
            int stairIdx = 0;

            for (int rowIdx = 0; rowIdx < length; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int columnIdx = 0; columnIdx < length; columnIdx++) {
                    int info = Integer.parseInt(st.nextToken());

                    if (info == PERSON) {
                        peoplePosition.add(new int[]{rowIdx, columnIdx});
                        peopleCnt++;
                    }

                    else if (info != PERSON && info != EMPTY) {
                        stairPosition[stairIdx++] = new Stair(rowIdx, columnIdx, info);
                    }
                }
            }

            //2. 어떤 계단으로 갈건지 정하기 (부분 집합)
            isSelected = new boolean[peopleCnt];
            minTotalTime = Integer.MAX_VALUE;
            setStairs(0);

            sb.append("#").append(testCase).append(" ").append(minTotalTime).append("\n");
        }

        System.out.println(sb.toString());

    }

    // 계단 내려가기
    private static int goDown(PriorityQueue<Person> stairQueue) {
        int currentTime = 0;
        int arrivedTime = 0;

        List<Person> onStairs = new ArrayList<>(); // 지금 계단 위에 있는 사람들

        // 4. 앞에서부터 3개만 꺼내서 시간을 1씩 빼줌.
        // *  -> 시간이 0이 되면 해당 Person 데이터는 큐에서 제외시킴
        while (!stairQueue.isEmpty() || !onStairs.isEmpty()) {

            // 계단 입구에 도착해있고, 지금 내려가고 있는 놈이 3명 미만이면 계단에 올림
            while (!stairQueue.isEmpty() && stairQueue.peek().time <= currentTime && onStairs.size() < 3)
                onStairs.add(stairQueue.poll());
            
            // 아무도 계단 위에 없으면 다음 사람 도착 시간으로 점프
            if (onStairs.isEmpty()) {
                currentTime = stairQueue.peek().time;
                continue;
            }

            List<Person> arrivedPeople = new ArrayList<>();

            // 계단 내려 가는 사람들 lastTime 감소시키기
            for (Person person : onStairs) {
                person.minusLastTime();

                // 계단 다 내려간 시간 갱신
                if (person.lastTime == 0) {
                    arrivedTime = Math.max(arrivedTime, currentTime + 1);
                    arrivedPeople.add(person);
                }
            }

            currentTime++;
            
            // 계단 다 내려간 사람들 제거
            onStairs.removeAll(arrivedPeople);
        }

        return arrivedTime;
    }

    // 어떤 계단으로 갈지 정하기 (부분집합)
    private static void setStairs(int idx) {
        if (idx == peopleCnt) {
            stair1.clear();
            stair2.clear();

            saveStairQueue(isSelected);

            int stair1Time = (stair1.isEmpty()) ? 0 : goDown(stair1);
            int stair2Time = (stair2.isEmpty()) ? 0 : goDown(stair2);

            totalTime = Math.max(stair1Time, stair2Time);
            minTotalTime = Math.min(minTotalTime, totalTime);
            return;
        }

        isSelected[idx] = true;
        setStairs(idx+1);

        isSelected[idx] = false;
        setStairs(idx+1);
    }

    // 큐에 저장하기
    // true -> stair1 / false -> stair2
    private static void saveStairQueue(boolean[] isSelected) {
        for (int personIdx = 0; personIdx < peopleCnt; personIdx++) {
            if (isSelected[personIdx]) {
                stair1.offer(new Person(personIdx, calculateTime(peoplePosition.get(personIdx), stairPosition[0]), stairPosition[0].time));
            } else {
                stair2.offer(new Person(personIdx, calculateTime(peoplePosition.get(personIdx), stairPosition[1]), stairPosition[1].time));
            }
        }
    }

    // 계단까지 가는 데 걸리는 시간 계산
    private static int calculateTime(int[] person, Stair stair) {
        return Math.abs(person[0] - stair.row) + Math.abs(person[1] - stair.column) + 1;
    }
}