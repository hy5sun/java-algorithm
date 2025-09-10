import java.util.*;
import java.io.*;

/**
 * BJ2458. 키 순서
 * 
 * - 학생들의 키는 모두 다르다. 
 * - a 키 < b 키 => a -> b 연결
 * - 자신의 키가 몇 번째인지 알 수 있는 학생들이 모두 몇 명?
 * 
 * [풀이]
 * 플로이드 와샬 사용해서
 * 나를 향하는 학생의 수 + 내가 향하는 학생의 수 = 학생 수 - 1 => 굿!!!
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int studentCnt, edgeCnt;
    static boolean[][] distance;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        studentCnt = Integer.parseInt(st.nextToken());
        edgeCnt = Integer.parseInt(st.nextToken());

        distance = new boolean[studentCnt+1][studentCnt+1];

        for (int student1 = 1; student1 <= studentCnt; student1++) {
            for (int student2 = 1; student2 <= studentCnt; student2++) {
                if (student1 == student2) {
                    distance[student1][student2] = true;
                }
            }
        }

        for (int idx = 0; idx < edgeCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int student1 = Integer.parseInt(st.nextToken());
            int student2 = Integer.parseInt(st.nextToken());

            distance[student1][student2] = true;
        }

        // 플로이드 워샬 용
        for (int stopOver = 1; stopOver <= studentCnt; stopOver++) {
            for (int student1 = 1; student1 <= studentCnt; student1++) {
                for (int student2 = 1; student2 <= studentCnt; student2++) {
                    if (distance[student1][student2]) continue;

                    if (distance[student1][stopOver] && distance[stopOver][student2]) {
                        distance[student1][student2] = true;
                    }
                }
            }
        }

        int count = 0; // 자기 키 위치 아는 학생 수

        for (int student = 1; student <= studentCnt; student++) { // 얘 기준으로 보기
            int shorterThanMe = 0; // 나보다 작은 놈
            int tallerThanMe = 0; // 나보다 큰 놈

            for (int other = 1; other <= studentCnt; other++) {
                if (distance[student][other]) tallerThanMe++;
                if (distance[other][student]) shorterThanMe++;
            }

            // 나를 향하는 학생의 수 + 내가 향하는 학생의 수 = 학생 수 - 1 => 굿!!!
            // -2 는 student == other 인 2가지 상황을 고려하여 빼줌
            if (shorterThanMe + tallerThanMe - 2 == studentCnt - 1) count++;
        }

        System.out.println(count);
    }
}
