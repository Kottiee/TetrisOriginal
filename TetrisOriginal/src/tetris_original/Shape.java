package tetris_original;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;



public class Shape {



	/** ピースはそれぞれ４ｘ２の配列で表現（４点のXY座標の意味）*/
	private int[][] piece;
	/** 全てのピースの形を表している。setRandomPieceメソッドを使ってこの中から一つ選んでpiece変数にセットする*/
	private int[][][] coordsTable = {
        { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
        { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
        { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
        { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
        { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
        { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
        { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
    };
	
//	/**Each pieceに割り当てるランダムなカラー７色を格納するArray*/
//	public static final Color[] colorList = new Color[7];

	/**各ピースの名前をEnumerator定数で管理、Boardクラスでの配列に利用する
	 * それぞれimgPathフィールドに画像ファイルを持たせるようにしてみる */
	public static enum shapeEnum {
		ZSHAPE("/zshape.jpg"),
		SSHAPE("/sshape.jpg"),
		ISHAPE("/line.jpg"),
		TSHAPE("/tshape.jpg"),
		OSHAPE("/square.jpg"),
		LSHAPE("/lshape.jpg"),
		mLSHAPE("/jshape.jpg");
		
		private String imgPath;
		
		private shapeEnum(String path) {
			this.imgPath  = path;
		}
		
		/**定数の imgPathフィールドの値をStreaｍに変換し、BufferedImageを返り値とする
		 * @return
		 */
		public BufferedImage getBlockImage() {

			BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(this.getClass().getResourceAsStream(this.imgPath));
				return bufferedImage;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("failed to create BufferedImage. return null");
				return null;
			}
			
		}

		private static final shapeEnum[] VALUES = values();
		
		/**指定したIndex番号の定数をこのEnumeratorから返す
		 * @param index
		 * @return shapeEnum型*/
		public static shapeEnum pickShape(int index) {
			return VALUES[index];
		}
		
	}
	
//	private Color curColor;
	
	/**ランダムにピックされる定数*/
	private shapeEnum  shapeName;

	/////////////////////////////////
	//Constructor ランダムに７色生成し、ランダムなピースを生成
	public Shape() {
//		System.out.println(colorList[0]);
		setRandomPiece();
		
	}
	
	/**ピースの座標配列、Enum定数、RGB値をランダムに決定*/
	public void setRandomPiece() {
		int x = new Random().nextInt(coordsTable.length);
		setPiece(x);
		shapeName = shapeEnum.pickShape(x);
//		System.out.println("DEBUG: shapeName is"+ shapeEnum.pickShape(x));
//		curColor = colorList[x];
		
	}
	
	//Getter
	public shapeEnum getShapeName() {
//		System.out.println("DEBUG :"+shapeName.name());
		return this.shapeName;
	}
//	public Color getCurColor() {
//		return this.curColor;
//	}
	public int[][] getPiece(){
		return piece;
	}
	
	//Setter
	public void setPiece(int index) {
		  piece = coordsTable[index];
	}
	
	
	//ColorListをランダムに初期化
//	public static void initColor() {
//		for(int i = 0; i<colorList.length; i++) {
//			int r = new Random().nextInt(255);
//			int g = new Random().nextInt(255);
//			int b = new Random().nextInt(255);
//			colorList[i] = new Color(r,g,b);
//		}
//
//	}
	
	/**Shapeの名前に応じてcolorListから色を選んでColor型オブジェクトを返す
	 * @param shape
	 * @return
	 */
//	public static Color getColors(String shape) {
//		switch(shape) {
//		case("ZSHAPE"):
//			return colorList[0];
//			
//		case("SSHAPE"):
//			return colorList[1];
//			
//		case("ISHAPE"):
//			return colorList[2];
//			
//		case("TSHAPE"):
//			return colorList[3];
//			
//		case("OSHAPE"):
//			return colorList[4];
//			
//		case("LSHAPE"):
//			return colorList[5];
//			
//		case("mLSHAPE"):
//			return colorList[6];
//			
//		}
//		return Color.black;
//		//この黒は実際には呼び出されないが、構文上必要なので記述
//	}
}
