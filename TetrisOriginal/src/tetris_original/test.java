package tetris_original;

import java.util.Random;

public class test {
//	private enum shapeEnum {ZSHAPE(1), SSHAPE(2), ISHAPE(3), TSHAPE(4), OSHAPE(5), LSHAPE(6), mLSHAPE(7);
//		
//		public final int num;
//		
//		shapeEnum(int num){
//			this.num = num;
//		}
//		public int num() {
//			return num;
//		}
//	}
	public enum shapeEnum {ZSHAPE, SSHAPE, ISHAPE, TSHAPE, OSHAPE, LSHAPE, mLSHAPE;
		
		private static final shapeEnum[] VALUES = values();
		private static final int SIZE = VALUES.length;
		
		public static shapeEnum getRandomShapeName() {
			return VALUES[new Random().nextInt(SIZE)];
		}
		
	}

	
	


	public static void main (String [] args) {

		shapeEnum[] vals = shapeEnum.values();
		System.out.println(shapeEnum.getRandomShapeName());
		
	}
//	public int myf() {
//		int i = 2;
//		return i;
//	}
	
}