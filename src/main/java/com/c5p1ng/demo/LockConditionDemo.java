package com.c5p1ng.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionDemo {
	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	
	private void await(Condition condition) {
		try {
			lock.lock();
			System.out.println("开始等待await！ThreadName: " + Thread.currentThread().getName());
			condition.await();
			System.out.println("等待await结束！ThreadName：" + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	private void signal(Condition condition) {
		lock.lock();
		System.out.println("发送通知signal，threadName: " + Thread.currentThread().getName());
		condition.signal();
		lock.unlock();
	}
	
	public static void main(String[] args) throws InterruptedException {
		final LockConditionDemo demo = new LockConditionDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				demo.await(demo.condition1);
			}
		}, "thread1_condition1").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				demo.await(demo.condition2);
			}
		}, "thread2_condition2").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				demo.signal(demo.condition1);
			}
		}, "thread3_condition1").start();
		
		System.out.println("稍等5秒再通知其他线程");
		Thread.sleep(5000);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				demo.signal(demo.condition2);
			}
		}, "thread4_condition2").start();
	}
}
