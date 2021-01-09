package Main;

public class Pawn extends Piece{
	public Pawn(boolean white) {
		super(white);
	}
	public Pawn(Piece cur) {
		super(cur.white);
	}
	char printpiece() {
		return 'P';
	}
}
