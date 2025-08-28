import java.util.*;
import java.io.*;

/**
 * BJ25542. 약속 장소
 * 
 * 
 * [문제]
 * - 가게 이름 길이 L
 * - 알파벳 대문자로만 구성
 * - 현재 기억나는 가게 이름 후보들과 최대한 이름이 비슷한 가게를 찾을 것임
 * - N개의 가게 이름 후보가 주어지고, N개의 이름 각각과 많아봐야 한 글자만 다르고 길이가 L인 가게 이름을 찾아야 함
 * - 찾은 가게 이름은 반드시 가게 이름 후보들에 포함될 필요는 없음
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 가게 후보 수, 가게 이름 길이
 *  - N개만큼 가게 이름 후보가 주어짐 (길이 L, 모두 서로 다르며, 알파벳 대문자로만 구성)
 * 
 * 2. 현재 맨 앞 후보가 정답이 될 수 있는지 확인한다.
 * 
 * 3. 맨 앞 후보를 기준으로 0 ~ 1개 차이나는 문자열을 만들고, 만드는 족족 기존 후보들(not 0번째)과 비교해본다.
 * 
 * 4. 만든 문자열들과 (idx 1부터) 나머지 후보들을 비교한다.
 * 
 * 5. 만약 종료되지 않은 채, 모든 문자열 리스트를 돌았다면 "CALL FRIEND"를 출력한다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int length, candidateCnt; // 제목 길이
    static String[] candidates; // 입력값으로 주어지는 예상 제목들
    static List<String> guess = new ArrayList<>(); // 칸별 문자 후보

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 가게 후보 수, 가게 이름 길이
        st = new StringTokenizer(br.readLine().trim());

        candidateCnt = Integer.parseInt(st.nextToken());
        length = Integer.parseInt(st.nextToken());

        //*  - N개만큼 가게 이름 후보가 주어짐 (길이 L, 모두 서로 다르며, 알파벳 대문자로만 구성)
        candidates = new String[candidateCnt];

        for (int idx = 0; idx < candidateCnt; idx++) 
            candidates[idx] = br.readLine();

        // 2. 현재 맨 앞 후보가 정답이 될 수 있는지 확인한다.
        if (compare(candidates[0].toCharArray())) {
            success(candidates[0].toCharArray());
        }

        // 3. 맨 앞 후보를 기준으로 0 ~ 1개 차이나는 문자열을 만들어보고,
        //      만드는 족족 기존 후보들(not 0번째)과 비교해본다.
        guessTitles();

        // 실패..
        fail();
    }

    private static void guessTitles() {
        // 하나씩 틀리게 만들 문자열을 저장할 리스트
        char[] candidate = candidates[0].toCharArray();
        
        for (int idx = 0; idx < length; idx++) {
            // 원래 문자열 저장하기
            char original = candidate[idx];

            for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
                // 기존 문자열과 같으면 pass.
                if (original == alphabet) {
                    continue;
                }

                // 하나만 다른 걸로 바꿔보기
                candidate[idx] = alphabet;
                
                // 비교 했을 때, false 반환 -> 2개 이상 틀림!!! => 돌려놓고, 다음으로 넘어가.
                if(compare(candidate)) {
                    // true 반환 -> 1개 이하로 틀림!!! => 답 출력하고 끗. 
                    success(candidate);
                }
            }

            candidate[idx] = original;
        }
    }

    // 4. 만든 문자열들과 (idx 1부터) 나머지 후보들을 비교한다.
    private static boolean compare(char[] guessedTitle) {
        // 1번째 인덱스부터 비교 시작
        for (int candidateIdx = 1; candidateIdx < candidateCnt; candidateIdx++) {
            // 틀린 개수
            int wrongCount = 0;

            // 나머지 후보들을 비교한다.
            for (int idx = 0; idx < length; idx++) {
                if (guessedTitle[idx] != candidates[candidateIdx].charAt(idx)) {
                    wrongCount++;

                    // 2개 이상 차이나면 false를 반환한다.
                    if (wrongCount >= 2) return false;
                }
            }
        }
        // 다 1개씩만 차이나면 true 반환한다.
        return true;
    }

    // 정답 출력 후 시스템 종료
    private static void success(char[] candidate) {
        for (int answerIdx = 0; answerIdx < length; answerIdx++) {
                sb.append(candidate[answerIdx]);
            }
        System.out.println(sb.toString());
        System.exit(0);
    }

    // 실패 출력
    private static void fail() {
        System.out.println("CALL FRIEND");
    }
}
