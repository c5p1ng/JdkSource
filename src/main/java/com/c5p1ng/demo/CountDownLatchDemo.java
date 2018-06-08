package com.c5p1ng.demo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
	private static final int THREAD_COUNT_NUM = 7;
	private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT_NUM);
	
	public static void main(String[] args) throws InterruptedException {
		for(int i = 0; i <= THREAD_COUNT_NUM; i++) {
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("第" + index + "颗龙珠已经收集！");
						Thread.sleep(new Random().nextInt(3000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					countDownLatch.countDown();
				}
			}).start();
		}
		countDownLatch.await();
		System.out.println("集齐七颗龙珠！召唤神龙！");
	}
}
