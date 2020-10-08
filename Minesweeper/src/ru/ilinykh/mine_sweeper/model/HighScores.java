package ru.ilinykh.mine_sweeper.model;

import ru.ilinykh.mine_sweeper.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HighScores {
    private final ArrayList<String> leaders;
    private final File highScores;
    private final int maxHighScoresLength;

    public HighScores() {
        this.leaders = new ArrayList<>(10);
        maxHighScoresLength = 10;

        File currentJavaJarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath().replace("\\", "/")
                .replace("Minesweeper.jar", "") + "/Leaders.txt";

        highScores = new File(currentJavaJarFilePath);
        if (!highScores.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                highScores.createNewFile();
            } catch (IOException ignored) {
            }
        } else {
            try (Scanner scanner = new Scanner(highScores, "windows-1251")) {
                while (scanner.hasNextLine()) {
                    leaders.add(scanner.nextLine());
                }
            } catch (FileNotFoundException ignored) {
            }
        }
    }

    public ArrayList<String> getLeaders() {
        return leaders;
    }

    public void setLeaders(String newLeader) {
        if (leaders.size() == maxHighScoresLength) {
            leaders.remove(maxHighScoresLength - 1);
        }

        leaders.add(newLeader);
        Collections.sort(leaders);

        try (PrintWriter writer = new PrintWriter(highScores, "windows-1251")) {
            for (String s : leaders) {
                writer.println(s);
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ignored) {
        }
    }
}