import java.io.*;

public class Main {
    static final String LOVE_KOREA_UNIV = "LoveisKoreaUniversity";

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int num = Integer.parseInt(br.readLine().trim());
        
        for (int idx = 0; idx < num; idx++) {
            sb.append(LOVE_KOREA_UNIV).append(" ");
        }

        System.out.println(sb.toString());
    }
}
