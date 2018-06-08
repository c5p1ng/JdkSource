package com.c5p1ng.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestBlockingQueue {
	static BlockingQueue<Hamburger> queue = new LinkedBlockingQueue<Hamburger>(10);
	
	public static void main(String[] args) throws InterruptedException {
		Producer t1 = new Producer();
		Consumer t2 = new Consumer();
		t1.start();
		Thread.sleep(1000);
		t2.start();
		Thread.sleep(1000);
		t2.interrupt();
		t1.interrupt();
		System.out.println("queue: " + queue.remainingCapacity());
	}
}
class Hamburger {
	int id;
	public Hamburger(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Hamburger: " + id;
	}
}
class Producer extends Thread {
	@Override
	public void run() {
		int i = 0;
		while(i < 100) {
			Hamburger e = new Hamburger(i);
			try {
				System.out.println("Producer Hamburger: " + i);
				TestBlockingQueue.queue.put(e);
			} catch (InterruptedException e2) {
				System.out.println("Hamburger so many,it was closed.");
				return;
			}
			i++;
		}
	}
}
class Consumer extends Thread {
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("Eat Hamburger: " + TestBlockingQueue.queue.take());
			} catch (InterruptedException e2) {
				System.out.println("Hamburger so less,it was stopped.");
				return;
			}
		}
	}
}