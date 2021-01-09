package Main;

public class Knight extends Piece{
	public Knight(boolean white) {
		super(white);
	}
	public Knight(Piece cur) {
		super(cur.white);
	}
	char printpiece() {
		return 'N';
	}
}
