package ru.ilinykh.mine_sweeper.model;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameTimer {
    private final Timer timer;
    private int time;

    public GameTimer() {
        timer = new Timer(1000, e -> time++);
        time = 1;
    }

    public void timerStart() {
        timer.start();
    }

    public void timerStop() {
        timer.stop();
    }

    public int getTime() {
        return time;
    }

    public void addTimerListener(ActionListener timerListener) {
        timer.addActionListener(timerListener);
    }
}
