package ru.javawebinar.basejava;

public class MainDeadlock {
    private static final Object RESOURCE_1 = new Object();
    private static final Object RESOURCE_2 = new Object();

    public void workWithResource1() {
        synchronized (RESOURCE_1) {
            System.out.println(Thread.currentThread().getName() + " locked resource #1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (RESOURCE_2) {
                System.out.println(Thread.currentThread().getName() + " locked resource #2");
            }
        }
        System.out.println(Thread.currentThread().getName() + " unlocked resource #1");
    }

    public void workWithResource2() {
        synchronized (RESOURCE_2) {
            System.out.println(Thread.currentThread().getName() + " locked resource #2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (RESOURCE_1) {
                System.out.println(Thread.currentThread().getName() + " locked resource #1");
            }
        }
        System.out.println(Thread.currentThread().getName() + " unlocked resource #2");
    }

    public static void main(String[] args) {

        final MainDeadlock mainDeadlock = new MainDeadlock();

        Thread thread1 = new Thread(mainDeadlock::workWithResource1);
        thread1.start();

        Thread thread2 = new Thread(mainDeadlock::workWithResource2);
        thread2.start();
    }

}

