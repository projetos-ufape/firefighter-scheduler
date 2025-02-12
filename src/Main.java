import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------------");
        System.out.println("Starting program...");
        System.out.println("---------------------------");

        String pastaEntrada = "src/entradas";
        String pastaSaida = "src/saidas";

        File dirSaida = new File(pastaSaida);
        if (!dirSaida.exists()) {
            dirSaida.mkdir();
        }

        File dirEntrada = new File(pastaEntrada);
        File[] arquivos = dirEntrada.listFiles((dir, name) -> name.endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo .txt encontrado na pasta de entrada.");
            return;
        }

        for (File arquivo : arquivos) {
            ArrayList<FireFighter> fireFighters = FileService.readFile(pastaEntrada + "/" + arquivo.getName());
            for (FireFighter f : fireFighters) {
                System.out.println(f.toString());
            }

            System.out.println("\nAllocating Fire Fighters...");
            Agenda agenda = new Agenda(fireFighters);
            boolean valid = agenda.allocateFirefighters(0);

//            if (valid) {
                FileService.writeFile(
                    pastaSaida + "/saida_" + arquivo.getName().replace("entrada_", ""),
                    agenda.getAgenda()
                );
                System.out.println("Successfully allocated Fire Fighters!");
//            } else {
//                System.out.println("Failed to allocate Fire Fighters!");
//            }
        }
    }
}