package javaBasicsTest.basicsTest.src.task2;

public class SumThread extends Thread {
    private int[] array;
    private int startIndex;
    private int endIndex;
    private long partialSum;

    public SumThread(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        partialSum = 0;

        for (int i = startIndex; i < endIndex; i++) {
            partialSum += array[i];
        }
    }

    public synchronized long getPartialSum() {
        return partialSum;
    }
}

