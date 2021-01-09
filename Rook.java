package Main;

public class Rook extends Piece{
	public Rook(boolean white) {
		super(white);
	}
	public Rook(Piece cur) {
		super(cur.white);
	}
	char printpiece() {
		return 'R';
	}
}
