package javaBasicsTest.basicsTest.src.task2;

public class MultiThreadSumCalculator {
    private int[] array;
    private int threadCount;

    public MultiThreadSumCalculator(int[] array, int threadCount) {
        this.array = array;
        this.threadCount = threadCount;
    }

    public long calculateSum() {
        long sum = 0;

        long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[threadCount];

        int elementsPerThread = array.length / threadCount;
        int startIndex = 0;

        for (int i = 0; i < threadCount; i++) {
            int endIndex = startIndex + elementsPerThread;

            if (i == threadCount - 1) {
                endIndex = array.length;
            }

            threads[i] = new SumThread(array, startIndex, endIndex);
            threads[i].start();

            startIndex = endIndex;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < threadCount; i++) {
            sum += ((SumThread) threads[i]).getPartialSum();
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Sum: " + sum);
        System.out.println("Calculation time: " + elapsedTime + " milliseconds");

        return sum;
    }
}