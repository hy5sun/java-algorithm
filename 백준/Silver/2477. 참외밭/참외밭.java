import java.io.*;
import java.util.*;

public class Main {
    private static final int LENGTH_CNT = 6;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int fruitsCnt = Integer.parseInt(st.nextToken());

        List<Integer> lengthList = new ArrayList<>();

        int maxWidth = 0, maxHeight = 0;

        for (int idx = 0; idx < LENGTH_CNT; idx++) {
            st = new StringTokenizer(br.readLine());

            int direction = Integer.parseInt(st.nextToken());
            int length = Integer.parseInt(st.nextToken());

            lengthList.add(length);

            if (direction == 1 || direction == 2) {
                maxWidth = Math.max(maxWidth, length);
            } else {
                maxHeight = Math.max(maxHeight, length);
            }
        }
    
        int maxWidthIndex = lengthList.indexOf(maxWidth);
        int maxHeightIndex = lengthList.indexOf(maxHeight);

        int bigBoxSize = maxHeight * maxWidth;
        int holeBoxSize = lengthList.get((maxWidthIndex+3) % LENGTH_CNT) * lengthList.get((maxHeightIndex+3) % LENGTH_CNT);

        int totalSize = bigBoxSize - holeBoxSize;

        System.out.println(totalSize * fruitsCnt);
    }
}