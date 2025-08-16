import java.io.*;
import java.util.*;

public class Solution {
    public static BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
    public static StringTokenizer st;
    public static StringBuilder sb = new StringBuilder();

    public static int totalFoodCount;
    public static int foodCount;
    public static int[][] synergy;
    public static int minSynergyDifference;

    public static boolean[] selectedFood;
    public static boolean[] visited;

    public static void main(String[] args) throws IOException {
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            totalFoodCount = Integer.parseInt(br.readLine());
            foodCount = totalFoodCount / 2;
            
            selectedFood = new boolean[totalFoodCount];
            visited = new boolean[totalFoodCount];
            
            minSynergyDifference = Integer.MAX_VALUE;

            initSynergy();

            setFoodCombination(0, 0);

            getFlavorDifference();

            printSynergy(testCase);
        }
        System.out.println(sb.toString());
    }

    private static void initSynergy() throws IOException {
        synergy = new int[totalFoodCount][totalFoodCount];

        for (int rowIdx = 0; rowIdx < totalFoodCount; rowIdx++) {

            st = new StringTokenizer(br.readLine());

            for (int columnIdx = 0; columnIdx < totalFoodCount; columnIdx++) {
                synergy[rowIdx][columnIdx] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static void setFoodCombination(int selectedFoodIndex, int selectedCount) {
        if (selectedCount == foodCount) {
            getFlavorDifference();
            return;
        }

        for (int nextIndex = selectedFoodIndex; nextIndex < totalFoodCount; nextIndex++) {
            selectedFood[nextIndex] = true;
            setFoodCombination(nextIndex + 1, selectedCount + 1);
            selectedFood[nextIndex] = false;
        }
    }

    private static int getFlavor(int rowIdx, int columnIdx) {
        return synergy[rowIdx][columnIdx] + synergy[columnIdx][rowIdx];
    }

    private static void getFlavorDifference() {
        int ATotalFlavor = 0, BTotalFlavor = 0;

        for (int idx = 0; idx < totalFoodCount; idx++) {
            for (int nextIdx = idx+1; nextIdx < totalFoodCount; nextIdx++) {
                if (selectedFood[idx] && selectedFood[nextIdx]) {
                    ATotalFlavor += getFlavor(idx, nextIdx);
                } else if (!selectedFood[idx] && !selectedFood[nextIdx]) {
                    BTotalFlavor += getFlavor(idx, nextIdx);
                }
            }
        }

        minSynergyDifference = Math.min(minSynergyDifference, Math.abs(ATotalFlavor - BTotalFlavor));
    }

    private static void printSynergy(int testCase) {
        sb.append("#").append(testCase).append(" ").append(minSynergyDifference).append("\n");
    }
}
