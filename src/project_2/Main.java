package project_2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------------");
        System.out.println("Starting program...");
        System.out.println("---------------------------");


        ArrayList<FireFighter> fireFighters = FileService.readFile("src/entradas/entrada_1.txt");
        for (FireFighter f : fireFighters) {
            System.out.println(f.toString());
        }

        System.out.println("\nAllocating Fire Fighters...");
        Agenda agenda = new Agenda(fireFighters);
        boolean valid = agenda.allocateFirefighters(0);

        if (valid) {
            System.out.println("Successfully allocated Fire Fighters!");
            FileService.writeFile("src/saidas/saida_1.txt", agenda.getAgenda());
        } else {
            System.out.println("Failed to allocate Fire Fighters!");
        }

    }
}