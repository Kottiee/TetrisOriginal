package tetris_original;

public class test {
	private enum shapeEnum {ZSHAPE, SSHAPE, ISHAPE, TSHAPE, OSHAPE, LSHAPE, mLSHAPE};


	public static void main (String [] args) {

		shapeEnum[] vals = shapeEnum.values();
		System.out.println(vals[0]);

		
	}
	public int myf() {
		int i = 2;
		return i;
	}
	int n=myf;
}