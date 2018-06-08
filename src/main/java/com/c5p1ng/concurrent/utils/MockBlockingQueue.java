package com.c5p1ng.concurrent.utils;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class MockBlockingQueue {
	//装载元素集合
	private final LinkedList<Object> list = new LinkedList<>();
	//计数器
	private final AtomicInteger count = new AtomicInteger(0);
	//上限下限
	private final int maxSize = 5;
	private final int minSize = 0;
	//锁对象
	private final Object lock = new Object();
	
	/**
	 * put方法
	 */
	public void put(Object obj) {
		synchronized (lock) {
			while(count.get() == maxSize) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.add(obj);
			count.getAndIncrement();
			System.out.println("元素 "+obj+" 被添加");
			lock.notify();//通知另外一个阻塞的进程方法
		}
	}
	
	/**
	 * get
	 */
	public Object get() {
		Object temp;
		synchronized(lock) {
			while(count.get() == minSize) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			count.getAndDecrement();
			temp = list.removeFirst();
			System.out.println("元素 "+temp+" 被消费");
			lock.notify();
		}
		return temp;
	}
	
	private int size() {
		return count.get();
	}
	
	public static void main(String[] args) throws InterruptedException {
		final MockBlockingQueue mQueue = new MockBlockingQueue();
		initMyQueue(mQueue);
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mQueue.put("h");
				mQueue.put("i");
			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mQueue.get();
					Thread.sleep(2000);
					mQueue.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t2");
		
		t1.start();
		Thread.sleep(1000);
		t2.start();
	}

	private static void initMyQueue(MockBlockingQueue mQueue) {
		mQueue.put("a");
		mQueue.put("b");
		mQueue.put("c");
		mQueue.put("d");
		mQueue.put("e");
		System.out.println("当前元素个数：" + mQueue.size());
		
	}
	
}
