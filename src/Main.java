import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        // Criando um pool de threads para processar os arquivos em paralelo
        int numThreads = Math.min(arquivos.length, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (File arquivo : arquivos) {
            executor.execute(() -> processarArquivo(arquivo, pastaEntrada, pastaSaida));
        }

        // Finaliza o executor após as tarefas terminarem
        executor.shutdown();
    }

    private static void processarArquivo(File arquivo, String pastaEntrada, String pastaSaida) {
        ArrayList<FireFighter> fireFighters = FileService.readFile(pastaEntrada + "/" + arquivo.getName());

        synchronized (System.out) {
            System.out.println("\n----------------------------");
            System.out.println("Processing file: " + arquivo.getName());
            System.out.println("----------------------------");
            for (FireFighter f : fireFighters) {
                System.out.println(f.toString());
            }
        }

        Agenda agenda = new Agenda(fireFighters);
        boolean valid = agenda.allocateFirefighters(0);

        if (valid) {
            FileService.writeFile(
                    pastaSaida + "/saida_" + arquivo.getName().replace("entrada_", ""),
                    agenda.getAgenda()
            );
            synchronized (System.out) {
                System.out.println("✅ Successfully allocated Fire Fighters for: " + arquivo.getName());
            }
        } else {
            synchronized (System.out) {
                System.out.println("❌ Failed to allocate Fire Fighters for: " + arquivo.getName());
            }
        }
    }
}
