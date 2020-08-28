package ru.ilinykh.csv_main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CSV {
    public static String changeSpecialCharacters(char c) {
        if (c == '<') {
            return "&lt;";
        }

        if (c == '>') {
            return "&gt;";
        }

        if (c == '&') {
            return "&amp;";
        }

        return String.valueOf(c);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Аргументы программы указаны неверно. Должно быть два аргумента: первый аргумент - путь к исходному" +
                    " CSV файлу," + System.lineSeparator() + "второй аргумент - путь к сохраняемому файлу в формате HTML.");

            return;
        }

        try (Scanner scanner = new Scanner(new FileInputStream(args[0]), "windows-1251");
             PrintWriter writer = new PrintWriter(args[1])) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            writer.println("<title>Таблица в формате HTML</title>");
            writer.println("<style> table {border-collapse: collapse;} TD {border: 1px solid black;} </style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<table>");

            boolean isInsideQuotes = false;

            while (scanner.hasNextLine()) {
                String currentString = scanner.nextLine();

                if (!isInsideQuotes && currentString.length() > 0) {
                    writer.print("<tr><td>");
                }

                for (int i = 0; i < currentString.length(); i++) {
                    if (currentString.charAt(i) == '"') {
                        isInsideQuotes = !isInsideQuotes;
                    }

                    if (currentString.charAt(i) == '"' && (i == 0 || currentString.charAt(i - 1) == ',' ||
                            (i != currentString.length() - 1 && currentString.charAt(i + 1) == ','))) {
                        continue;
                    }

                    if (currentString.charAt(i) == '"' && i != currentString.length() - 1 &&
                            currentString.charAt(i + 1) == '"') {
                        writer.print('"');
                        isInsideQuotes = !isInsideQuotes;
                        i++;
                        continue;
                    }

                    if (i == 0 && isInsideQuotes) {
                        writer.print("<br/>");
                        writer.print(changeSpecialCharacters(currentString.charAt(i)));
                    } else if (currentString.charAt(i) == ',' && !isInsideQuotes) {
                        writer.print("</td><td>");
                    } else if (currentString.charAt(i) != '"' || i != currentString.length() - 1) {
                        writer.print(changeSpecialCharacters(currentString.charAt(i)));
                    }

                    if (i == currentString.length() - 1 && !isInsideQuotes) {
                        writer.print("</td></tr>");
                    }
                }
            }

            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
        } catch (FileNotFoundException e) {
            System.out.println("Файл, указанный в аргументах, не найден.");
        }
    }
}