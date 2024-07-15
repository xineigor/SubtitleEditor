package tools;

import java.util.ArrayList;
import java.util.List;

public class MenuBuilder {
    private static final String RESET = "\u001B[0m";
    private static final String GOLD = "\u001B[38;5;226m";
    private static final String PURPLE = "\u001B[35m";

    private final List<String> options = new ArrayList<>();
    private int maxLineLength = 0;

    public void addOption(String text, int number) {
        String formattedOption = String.format("%s %s[%d]%s", text, PURPLE, number, RESET);
        options.add(formattedOption);
        maxLineLength = Math.max(maxLineLength, formattedOption.length());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String separator = "=".repeat(maxLineLength - 5); // рамка

        sb.append(separator).append("\n");
        for (String option : options) {
            sb.append(String.format("| %-" + (maxLineLength) + "s |%n", option));
        }

        sb.append(separator).append("\nВвод -> ");
        return sb.toString();
    }
}