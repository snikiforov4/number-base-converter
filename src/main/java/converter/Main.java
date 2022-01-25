package converter;

import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            String userInput = SCANNER.nextLine();
            if ("/exit".equals(userInput)) {
                break;
            }
            String[] tokens = userInput.split("\\s+");
            int sourceBase = Integer.parseInt(tokens[0]);
            int targetBase = Integer.parseInt(tokens[1]);
            conversionLoop(sourceBase, targetBase);
        }
    }

    private static void conversionLoop(int sourceBase, int targetBase) {
        String formattedPrompt = String.format(
                "Enter number in base %d to convert to base %d (To go back type /back)",
                sourceBase, targetBase
        );
        Converter converter = new Converter(sourceBase, targetBase);
        while (true) {
            System.out.println(formattedPrompt);
            String userInput = SCANNER.nextLine();
            if ("/back".equals(userInput)) {
                break;
            }
            String result = converter.convert(userInput);
            System.out.printf("Conversion result: %s%n%n", result);
        }
    }
}
