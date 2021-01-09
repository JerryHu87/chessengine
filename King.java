package Main;

public class King extends Piece{
	public King(boolean white) {
		super(white);
	}
	public King(Piece cur) {
		super(cur.white);
	}
	char printpiece(){
		return 'K';
	}
}
