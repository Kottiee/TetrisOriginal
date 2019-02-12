package tetris_original;

import java.awt.Color;
import java.util.Random;

import tetris_original.test.shapeEnum;



public class Shape {



	/** ピースはそれぞれ４ｘ２の配列で表現（４点のXY座標の意味）*/
	private int[][] piece;
	private int[][][] coordsTable = {
//        { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
        { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
        { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
        { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
        { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
        { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
        { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
        { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
    };
	
	//ColorList
	public static final Color[] colorList = new Color[7];

	/**外部からInt値を受け取ってその番号に応じた
	 * @author nomad
	 *
	 */
	public static enum shapeEnum {ZSHAPE, SSHAPE, ISHAPE, TSHAPE, OSHAPE, LSHAPE, mLSHAPE;

		private static final shapeEnum[] VALUES = values();
		
		public static shapeEnum getRandomShapeName(int randomNum) {
			return VALUES[randomNum];
		}
		
	}
	
	private Color curColor;
	private shapeEnum  shapeName;

	/////////////////////////////////
	//Constructor ランダムに７色生成し、ランダムなピースを生成
	public Shape() {
		System.out.println(colorList[0]);
		setRandomPiece();
		
	}
	//Getter
	public shapeEnum getShapeName() {
//		System.out.println("DEBUG :"+shapeName.name());
		return this.shapeName;
	}
	public Color getCurColor() {
		return this.curColor;
	}
	
	
	//ColorListをランダムに初期化
	public static void initColor() {
		for(int i = 0; i<colorList.length; i++) {
			int r = new Random().nextInt(255);
			int g = new Random().nextInt(255);
			int b = new Random().nextInt(255);
			colorList[i] = new Color(r,g,b);
		}

	}
	
	/**引数には shape.name(); でStringを渡す
	 * @param shape
	 * @return
	 */
	public static Color getColors(String shape) {
		switch(shape) {
		case("ZSHAPE"):
			return colorList[0];
			
		case("SSHAPE"):
			return colorList[1];
			
		case("ISHAPE"):
			return colorList[2];
			
		case("TSHAPE"):
			return colorList[3];
			
		case("OSHAPE"):
			return colorList[4];
			
		case("LSHAPE"):
			return colorList[5];
			
		case("mLSHAPE"):
			return colorList[6];
			
		}
		return Color.black;
		//This is debug sign.
	}


	public void setPiece(int index) {
		  piece = coordsTable[index];
	}
	public int[][] getPiece(){
		return piece;
	}

	/**ピースの座標配列、Enum定数、RGB値をランダムに決定*/
	public void setRandomPiece() {
		int x = new Random().nextInt(coordsTable.length);
		setPiece(x);
		shapeName = shapeEnum.getRandomShapeName(x);
		System.out.println("DEBUG: shapeName is"+ shapeEnum.getRandomShapeName(x));
		curColor = colorList[x];
		
	}

//
//	/**coords配列の0番（ｘ座標）の値をセットする
//	 * @param index
//	 * @param x
//	 */
//	private void setX(int index, int x) {piece[index][0] = x; }
//	private void setY(int index, int y) {piece[index][1] = y; }
//
//
//	/**coords配列の0番（ｘ座標）の値を返す
//	 * @param index
//	 * @return
//	 */
//	public int getX(int index) {
//		return piece[index][0];}
//	public int getY(int index) {
//		return piece[index][1];}
//
//
//	/**This methodは、Coordの回転後のXに回転前のY座標を、回転後のY座標に回転前の−Xを代入して回転を表現している。
//     * @return X,y座標を編集したShapeインスタンスObject
//     */
//    public void rotation() {
//        //Rotate結果の変数としてShape型のインスタンス Resultを初期化（
////        Shape result = new Shape();
////        //Current Shape（Tetrominoe定数）がResult側の同じFieldに代入される。
////        result.pieceShape = pieceShape;
//
//        for (int i = 0; i < 4; ++i) {
//
//            setX(i, -getY(i));
//            setY(i, getX(i));
//        }
//
////        return result;
//    }


}
