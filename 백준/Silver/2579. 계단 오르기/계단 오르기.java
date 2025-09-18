import java.util.*;
import java.io.*;

/**
 * BJ2579. 계단 오르기
 * 
 * [문제]
 * - 맨 마지막 계단을 밟아야 함
 * - 각 계단에 있는 점수를 받게 됨
 * - 한 번에 한 계단 / 두 계단씩 오를 수 있음. 
 * - 연속된 세 개의 계단을 밟으면 안됨
 * - 마지막 계단은 무조건 밟아야 함
 * - 얻을 수 있는 최대 점수
 * 
 * [풀이]
 * 1. 입력 받기
 * 
 * 2. 4번째 계단을 기준으로 생각해보면 OXO[O] vs XOX[O] 둘 중 하나의 경로로 올 수 있슴
 *  -> score[i] = max(stair[i] + stair[i-1] + score[i-3], stair[i] + score[i-2]);
 * 
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    
    static int stairCnt;
    static int[] stairs;
    static int[] score;

    public static void main(String[] args) throws IOException {
        stairCnt = Integer.parseInt(br.readLine().trim());

        stairs = new int[stairCnt+1];
        score = new int[stairCnt+1];

        for (int idx = 1; idx <= stairCnt; idx++) {
            stairs[idx] = Integer.parseInt(br.readLine().trim());
        }

        score[1] = stairs[1];

        if (stairCnt >= 2)
            score[2] = stairs[1] + stairs[2];
        
        if (stairCnt >= 3) {
            for (int idx = 3; idx <= stairCnt; idx++) {
                score[idx] = Math.max(stairs[idx] + stairs[idx-1] + score[idx-3], stairs[idx] + score[idx-2]);
            }
        }

        System.out.println(score[stairCnt]);
    }
}
