package Main;

public class Bishop extends Piece{
	public Bishop(boolean white) {
		super(white);
	}
	public Bishop(Piece cur) {
		super(cur.white);
	}
	char printpiece() {
		return 'B';
	}
}
