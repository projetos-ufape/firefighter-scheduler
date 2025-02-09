package project_1;

import java.io.*;
import java.nio.file.*;
import java.sql.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
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
            processarArquivo(arquivo, pastaSaida);
        }
    }

    private static void processarArquivo(File arquivo, String pastaSaida) {
        StringBuilder saida = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha = br.readLine();
            var people = new ArrayList<Person>();
            while ((linha = br.readLine()) != null) {
                people.add(new Person(linha));
            }
            (new Resolution()).execute(people, saida);
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + arquivo.getName());
            return;
        }

        File arquivoSaida = new File(pastaSaida, "saida_" + arquivo.getName().replace("entrada_", ""));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida))) {
            bw.write(saida.toString());
            System.out.println("Saída salva em: " + arquivoSaida.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + arquivoSaida.getName());
        }
    }
}
