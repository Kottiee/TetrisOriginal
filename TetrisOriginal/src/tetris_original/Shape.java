package tetris_original;

import java.util.Random;

public class Shape {



	/** One Piece composition as Coords 4x2 aray*/
	private int[][] piece;
	private int[][][] coordsTable = {
        { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
        { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
        { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
        { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
        { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
        { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
        { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
        { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
    };
	/////////////////////////////////

	public Shape() {
		setRandomPiece();

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


}
