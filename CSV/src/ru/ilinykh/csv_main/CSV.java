package ru.ilinykh.csv_main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CSV {
    public static String changeInvalidCharacters(char c) {
        if (c == '<') {
            return "&lt";
        } else if (c == '>') {
            return "&gt";
        } else if (c == '&') {
            return "&amp";
        }

        return String.valueOf(c);
    }

    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(args[0]), "windows-1251");
             PrintWriter writer = new PrintWriter(args[1])) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            writer.println("<title>Таблица в формате HTML</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<style> table {border-collapse: collapse;} TD {border: 1px solid black;} </style>");
            writer.println("<table>");

            boolean isInsideQuotes = false;

            while (scanner.hasNextLine()) {
                String currentString = scanner.nextLine();

                if (!isInsideQuotes) {
                    writer.print("<tr><td>");
                }

                for (int i = 0; i < currentString.length(); i++) {
                    if (currentString.charAt(i) == '"') {
                        isInsideQuotes = !isInsideQuotes;
                    }

                    if (i == 0 && isInsideQuotes) {
                        writer.print("<br/>");
                        writer.print(changeInvalidCharacters(currentString.charAt(i)));
                    } else if (currentString.charAt(i) == ',' && !isInsideQuotes) {
                        writer.print("</td><td>");
                    } else if (currentString.charAt(i) == '"' && (i == 0 || currentString.charAt(i - 1) == ',' || (i != currentString.length() - 1 && currentString.charAt(i + 1) == ','))) {
                        continue;
                    } else if (currentString.charAt(i) == '"' && i != currentString.length() - 1 && currentString.charAt(i + 1) == '"') {
                        writer.print('"');
                        isInsideQuotes = !isInsideQuotes;
                        i++;
                        continue;
                    } else if (currentString.charAt(i) != '"' || i != currentString.length() - 1) {
                        writer.print(changeInvalidCharacters(currentString.charAt(i)));
                    }

                    if (i == currentString.length() - 1 && !isInsideQuotes) {
                        writer.print("</td></tr>");
                    }
                }
            }
            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
        }
    }
}