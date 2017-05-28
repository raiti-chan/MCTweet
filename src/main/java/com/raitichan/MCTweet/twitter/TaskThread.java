package com.raitichan.MCTweet.twitter;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <br>Created by Raiti-chan on 2017/05/25.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class TaskThread extends Thread {
	
	private final Queue<TaskObject> taskQueue = new ArrayDeque<>(16);
	
	private boolean isWaiting = false;
	
	public TaskThread (){
		this.setDaemon(false);
		this.setName("TaskThread");
	}
	
	@Override
	public void run () {
		synchronized (taskQueue) {
			//noinspection InfiniteLoopStatement
			do {
				if (taskQueue.isEmpty()) this.threadWait();//タスクキューが空っぽだったら処理を一時停止
				
				TaskObject task = this.taskQueue.poll();//タスクをキューから取得
				
				task.run();//タスクの実行
				
				
			} while (true);
			
		}
	}
	
	private synchronized void threadWait() {
		this.isWaiting = true;
		try {
			this.wait();
		} catch (InterruptedException ignored) {
		
		}
		
	}
	
	private void restart() {
		this.isWaiting = false;
		this.interrupt();
	}
	
	public void offerTask(TaskObject task) {
		synchronized (this.taskQueue) {
			this.taskQueue.offer(task);
			if (this.isWaiting) this.restart();//処理が一時停止状態だったら再開
		}
	}
	
	
}
