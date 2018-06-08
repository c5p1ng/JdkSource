package com.c5p1ng.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	private static final int THREAD_COUNT_NUM = 7;
	
	public static void main(String[] args) {
		final CyclicBarrier callMasterBarrier = new CyclicBarrier(THREAD_COUNT_NUM, new Runnable() {
			@Override
			public void run() {
				System.out.println("7名法师召集完毕，同时出发，去往不同地方寻找龙珠！");
				summonDragon();
			}
		});
		
		for(int i = 1; i <= THREAD_COUNT_NUM; i++) {
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("召集第" + index + "个法师");
						callMasterBarrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	private static void summonDragon() {
		final CyclicBarrier summonDragonBarrier = new CyclicBarrier(THREAD_COUNT_NUM, new Runnable() {
			@Override
			public void run() {
				System.out.println("集齐七龙珠！召唤神龙！");
			}
		});
		for(int i = 1; i <= THREAD_COUNT_NUM; i++) {
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("第" + index + "颗龙珠已经收集到！");
						summonDragonBarrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
