import tech.coinflip.utils.jiblib.CoinFlipJibberishDetector;
import tech.coinflip.utils.jiblib.JibberishDetector;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a name to test (or 'quit' to exit): ");
            String line = reader.nextLine();
            if (line.equalsIgnoreCase("quit")) {
                System.out.println("\n\nfinished.");
                break;
            } else {
                JibberishDetector.DetectionResult result = CoinFlipJibberishDetector.getInstance().detectIsJibberish(line);
                System.out.println();
                if (result.isValid()) {
                    System.out.print("PASS - ");
                } else {
                    System.out.print("FAIL - ");
                }
                System.out.printf("%s\n\n", result);
            }
        }
    }

}
