package Main;

public class Queen extends Piece{
	public Queen(boolean white) {
		super(white);
	}
	public Queen(Piece cur) {
		super(cur.white);
	}
	char printpiece() {
		return 'Q';
	}
}
