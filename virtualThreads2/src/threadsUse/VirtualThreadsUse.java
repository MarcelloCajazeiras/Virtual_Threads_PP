package threadsUse;

import taskThreads.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VirtualThreadsUse {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        executeTasks(executor);
    }

    private static void executeTasks(ExecutorService executor) {
        File folder = new File("images");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            List<Task> tasks = new ArrayList<>();

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".jpeg")) {
                    tasks.add(new Task(file.getPath()));
                }
            }

            long startTime = System.currentTimeMillis();

            List<Future<Double>> futures = new ArrayList<>();

            for (Task task : tasks) {
                futures.add(executor.submit(task));
            }

            double sum = 0;
            for (Future<Double> future : futures) {
                try {
                    sum += future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            System.out.println("Média do percentual de pixels claros: " + (sum / tasks.size()) + "%");
            System.out.println("Tempo total de processamento: " + totalTime + " ms");

        } else {
            System.out.println("Nenhuma imagem encontrada no diretório 'images'.");
        }
    }
}

