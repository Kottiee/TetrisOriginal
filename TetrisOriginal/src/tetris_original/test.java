package tetris_original;

import java.util.ArrayList;

public class test {

	public static void main(String[] args) {
		
		
		ArrayList<int[]> sample = new ArrayList<int[]>();
		sample.add(new int[2]);
		sample.get(0)[0]=1;
		System.out.println(sample.get(0)[0]);
		sample.add(sample.get(0));
		sample.get(0)[0]+=1;
		System.out.println(sample.get(1)[0]);

		
	}
//		test test = new test();
//
//		int[][] samples = {{1,0},{0,2},{3,0},{2,3}};
//		ArrayList<int[][]> sampleArr = new ArrayList<int[][]>();
//		sampleArr.add(samples);
//		for(int i=0; i<3; i++) {
//			
//			sampleArr.add(test.exchange(sampleArr.get(i)));
//
//		}
//		
//		samples = sampleArr.get(0);
//
//		
//
//		for(int[] i:samples) {
//			System.out.print("(");
//			for(int n : i) {
//				System.out.print(n+",");
//			}
//			System.out.print(")");
//		}
//
//	
//
//
//
//
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//	public int[][] exchange(int[][] sample) {
//		for(int i=0;i<sample.length;i++) {
//			int temp = sample[i][0];
//			sample[i][0] = sample[i][1];
//			sample[i][1] = temp;
//		}
//		return sample;
//	}

}
