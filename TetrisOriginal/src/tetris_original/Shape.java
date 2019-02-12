package tetris_original;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;



public class Shape {



	/** One Piece composition as Coords 4x2 aray*/
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
	
	private Color[] colorList = new Color[7];
	
	private enum Shapes  {ZSHAPE, SSHAPE, ISHAPE, TSHAPE, OSHAPE, LSHAPE, mLSHAPE};
	
	/////////////////////////////////

	public Shape() {
		setRandomPiece();


	}
	
	public void initColor() {
		for(int i = 0; i<colorList.length; i++) {
			int r = new Random().nextInt(255);
			int g = new Random().nextInt(255);
			int b = new Random().nextInt(255);
			colorList[i] = new Color(r,g,b);
		}
		
	}


	public void setPiece(int index) {
		  piece = coordsTable[index];
	}
	public int[][] getPiece(){
		return piece;
	}

	public void setRandomPiece() {
		int x = new Random().nextInt(coordsTable.length);
		setPiece(x);
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
