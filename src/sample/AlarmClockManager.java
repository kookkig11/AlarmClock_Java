package sample;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AlarmClockManager {
    private ArrayList<AlarmClock> alarmClocks = new ArrayList<>();

    public AlarmClockManager() throws IOException {
        loadFileToAlarmTable();
    }

    public ArrayList<AlarmClock> getAlarmClocks() {
        return alarmClocks;
    }

    public File getFile() {
        try {
            String jarDirectory = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            File file = new File(jarDirectory + "/AlarmTime.txt");
            file.createNewFile();
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public void appendAlarmTimeToFile() {
        StringBuilder allAlarm = new StringBuilder();

        for (AlarmClock i : alarmClocks) {
            allAlarm.append(i.getHourAlarm() + "-" + i.getMinuteAlarm() + "-" + i.getDayOfWeekAlarm() + "\n");
        }
        try (PrintWriter printWriter = new PrintWriter(getFile())) {
            printWriter.print(allAlarm.toString());
        } catch (Exception e) {
            System.out.println("File not found!!");
        }
    }

    public void loadFileToAlarmTable() throws IOException {
        this.alarmClocks.clear();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] timeToAlarm = line.split("-");
            alarmClocks.add(new AlarmClock(Integer.parseInt(timeToAlarm[0]), Integer.parseInt(timeToAlarm[1]), timeToAlarm[2]));
            line = bufferedReader.readLine();
        }
    }

    public void addAlarm(AlarmClock alarmClock) {
        alarmClocks.add(alarmClock);
        appendAlarmTimeToFile();
    }

    public void deleteAlarm(AlarmClock alarmClock) {
        ArrayList<AlarmClock> test = new ArrayList<>(alarmClocks);
        alarmClocks.clear();

        for (AlarmClock i : test) {
            if (!i.getTimeFormat().equals(alarmClock.getTimeFormat())
                    || !i.getDayOfWeekAlarm().equals(alarmClock.getDayOfWeekAlarm())) {
                alarmClocks.add(i);
            }
        }
        appendAlarmTimeToFile();
    }

    public boolean alarmWhenCheckTrue(String day, int hour, int minute) {
        for (AlarmClock i : alarmClocks) {
            if (LocalDateTime.now().getSecond() == 0) {
                if (i.getDayOfWeekAlarm().equals("EVERYDAY")) {
                    if (i.getHourAlarm() == hour && i.getMinuteAlarm() == minute) {
                        return true;
                    }
                } else {
                    if (i.getHourAlarm() == hour && i.getMinuteAlarm() == minute
                            && i.getDayOfWeekAlarm().startsWith(day)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}