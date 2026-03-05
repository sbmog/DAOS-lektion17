import java.util.Random;

public class PrimeCounter {

    private static final int SIZE = 10_000_000;
    private static final int MAX_VALUE = 1000;
    private static final int THREADS = 4;
    private static final int[] numbers = new int[SIZE];

    public static void main(String[] args) throws InterruptedException {
        fillArray();

        // Sekventiel optælling
        long startTime = System.nanoTime();
        int primeCountSequential = countPrimesSequential();
        long endTime = System.nanoTime();
        System.out.println("Sekventiel: " + primeCountSequential + " primtal fundet på " + (endTime - startTime) / 1_000_000.0 + " ms");

        // Parallel optælling med tråde
        startTime = System.nanoTime();
        countPrimesParallel();
        endTime = System.nanoTime();
        System.out.println("Parallel: " + primeCountParallel + " primtal fundet på " + (endTime - startTime) / 1_000_000.0 + " ms");
    }

    private static void fillArray() {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = random.nextInt(MAX_VALUE) + 1;
        }
    }

    private static int countPrimesSequential() {
        int count = 0;
        for (int num : numbers) {
            if (isPrime(num)) {
                count++;
            }
        }
        return count;
    }

    private static void countPrimesParallel() throws InterruptedException {
        Thread[] threads = new Thread[THREADS];
        int chunkSize = SIZE / THREADS;

        for (int i = 0; i < THREADS; i++) {
            final int start = i * chunkSize;
            final int end = (i == THREADS - 1) ? SIZE : (i + 1) * chunkSize;

            threads[i] = new Thread(() -> {
                int count = 0;
                for (int j = start; j < end; j++) {
                    if (isPrime(numbers[j])) {
                        count++;
                    }
                }
                addPrimes(count);
            });

            threads[i].start();
        }

        // Vent på at alle tråde er færdige
        for (Thread thread : threads) {
            thread.join();
        }
    }

        int totalPrimes = 0;
        for (Future<Integer> future : futures) {
            totalPrimes += future.get();
        }

        executor.shutdown();
        return totalPrimes;
    }

    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}

}
