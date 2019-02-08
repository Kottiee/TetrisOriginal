package tetris_original;

public class test {

	public static void main(String[] args) {
//		test test = new test();
//
//		int[][] samples = {{1,0},{0,2},{3,0},{2,3}};
//		ArrayList<int[][]> sampleArr = new ArrayList<int[][]>();
//
//		sampleArr.add(samples);
//		sampleArr.add(test.exchange(sampleArr.get(0)));
//
//		samples = sampleArr.get(1);
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

		boolean f = true;
		int x = 0;
		while (f) {
			System.out.println("input");
			x = new java.util.Scanner(System.in).nextInt();
			switch(x) {
			case 1:
				System.out.println(1);
				break;
			case 2:
				System.out.println("exit");
				f = false;
				break;
			}
		}




		// TODO 自動生成されたメソッド・スタブ

	}
	public int[][] exchange(int[][] sample) {
		for(int i=0;i<sample.length;i++) {
			int temp = sample[i][0];
			sample[i][0] = sample[i][1];
			sample[i][1] = temp;
		}
		return sample;
	}

}
