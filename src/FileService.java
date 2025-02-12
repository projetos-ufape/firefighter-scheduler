import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class FileService {
    public static ArrayList<FireFighter> readFile(String fileName) {

        ArrayList<FireFighter> fireFighters = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String linha;
            br.readLine(); // Ignorar cabeçalho

            while ((linha = br.readLine()) != null) {
                String[] lineSplit = linha.split(",");

                FireFighter fireFighter = new FireFighter(lineSplit[0].trim(), Integer.parseInt(lineSplit[1]),
                        Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]));

                fireFighters.add(fireFighter);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("Filed to read file: " + fileName + " | " + e.getMessage());
        }

        return fireFighters;
    }

    static void writeFile(String fileName, Map<Task, Map<String, FireFighter[]>> agenda) {

        Task[] tasks = Agenda.TASKS;
        String[] days = Agenda.DAYS;

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

            for (Task task : tasks) {
                bw.write(task.getValue() + "\n");
                for (String day : days) {
                    bw.write(String.format("%-10s", day));
                }
                bw.write("\n");

                for (String day : days) {
                    bw.write(String.format("%-10s", agenda.get(task).get(day)[0].getName()));
                }
                bw.write("\n");

                for (String day : days) {
                    bw.write(String.format("%-10s", agenda.get(task).get(day)[1].getName()));
                }
                bw.write("\n\n");
            }

            bw.close();
        } catch (IOException e) {
            throw new RuntimeException("Filed to write file: " + fileName + " | " + e.getMessage());
        }

    }
}
