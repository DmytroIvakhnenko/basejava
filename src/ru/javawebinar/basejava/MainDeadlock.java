package ru.javawebinar.basejava;

public class MainDeadlock {
    private static final Object RESOURCE_1 = new Object();
    private static final Object RESOURCE_2 = new Object();

    public void printThreadData(final String status, final Object resource) {
        System.out.println(Thread.currentThread().getName() + status + resource);
    }

    public void workWithResource(final Object resource1, final Object resource2) {
        printThreadData(" waiting ", resource1);
        synchronized (resource1) {
            printThreadData(" locked ", resource1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printThreadData(" waiting ", resource2);
            synchronized (resource2) {
                printThreadData(" locked ", resource2);
            }
            printThreadData(" unlocked ", resource2);
        }
        printThreadData(" unlocked ", resource1);
    }

    public static void main(String[] args) {

        final MainDeadlock mainDeadlock = new MainDeadlock();

        Thread thread1 = new Thread(() -> mainDeadlock.workWithResource(RESOURCE_1, RESOURCE_2));
        thread1.start();

        Thread thread2 = new Thread(() -> mainDeadlock.workWithResource(RESOURCE_2, RESOURCE_1));
        thread2.start();
    }

}

