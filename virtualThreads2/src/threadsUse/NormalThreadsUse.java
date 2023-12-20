package threadsUse;

import taskThreads.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NormalThreadsUse {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        executeTasks(executor);
        executor.shutdown();
    }

    private static void executeTasks(ExecutorService executor) throws InterruptedException {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            tasks.add(new Task(String.valueOf(i)));
        }

        List<Future<Double>> futures;
        long startTime = System.currentTimeMillis();

        try {
            futures = executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        double sum = 0;
        for (Future<Double> future : futures) {
            try {
                sum += future.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("valor final foi: " + sum);
        System.out.println("Tempo total de processamento: " + totalTime + " ms");
    }
}

