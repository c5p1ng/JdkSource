package com.c5p1ng.suanfa;

public class QuickSort {
	/**
	 * 快速排序是找出一个元素作为基准，然后对数组进行分区操作，使基准左边元素的值都不大于基准值，基准右边的元素值都不小于基准值，
	 * 如此作为基准的元素调整到排序后的正确位置。
	 * @param array
	 * @param left
	 * @param right
	 */
	private static void quickSort(int[] array, int left, int right) {
		if(left < right) {
			int i = left,j = right,x = array[left];
			while(i < j) {
				while(i < j && array[j] >= x) {//从右向左找第一个小于x的数
					j--;
				}
				if(i < j) {
					array[i++] = array[j];
				}
				while(i < j && array[i] < x) {//从左向右找第一个大于x的数
					i++;
				}
				if(i < j) {
					array[j--] = array[i];
				}
			}
			array[i] = x;
			quickSort(array, left, i-1);
			quickSort(array, i + 1, right);
		}
		
	}
	
	public static void main(String[] args) {
		int array[] = {72,6,57,88,60,42,83,73,48,85};
		System.out.println("排序前：");
		for (int i : array) {
			System.out.print(i + " ");
		}
		quickSort(array, 0, array.length - 1);
		System.out.println();
		System.out.println("排序后：");
		for (int i : array) {
			System.out.print(i + " ");
		}
	}

}
