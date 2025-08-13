import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;
    public static StringBuilder sb = new StringBuilder();

    public static int number;
    public static int[] array;
    public static boolean isNextArray;

    public static void main(String[] args) throws IOException {
        number = Integer.parseInt(br.readLine().trim());

        initArray();

        nextPermutation();
    }

    private static void initArray() throws IOException {
        array = new int[number];

        st = new StringTokenizer(br.readLine());
        
        int sameCnt = 0;
        for (int idx = 0; idx < number; idx++) {
            array[idx] = Integer.parseInt(st.nextToken().trim());

            if (array[idx] == number - idx) {
                sameCnt++;
            }
        }

        if (sameCnt == number) {
            System.out.println(-1);
            System.exit(0);
        }
    }

    private static void nextPermutation() {
        int pivot = number-1;

        // 1. 뒤부터 오름차순을 깨는 pivot 인덱스를 찾아요.
        for (int idx = number-1; idx > 0; idx--) {
            if (array[idx-1] < array[idx]) {
                pivot = idx - 1;
                break;
            }
        }

        // 피벗 포인트의 뒷 부분 원소에서 initialArray[pivot] 보다 크면서 가장 작은 애를 찾음.
        for (int idx = number-1; idx > pivot; idx--) {
            if (array[idx] > array[pivot]) {
                // 걔랑 바꿔줌
                swap(pivot, idx);
                break;
            }
        }

        // pivot 뒤에 있는 애들을 정렬해줌
        Arrays.sort(array, pivot+1, number);

        // 출력함
        for (int element : array) {
            sb.append(element).append(" ");
        }
        System.out.println(sb.toString());
    }

    private static void swap(int pivot, int minIndex) {
        int tmp = array[pivot];
        array[pivot] = array[minIndex];
        array[minIndex] = tmp;
    }
}
