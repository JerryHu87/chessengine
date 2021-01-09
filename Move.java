package Main;

public class Move {
	int r1,c1,r2,c2,movetype;
	Piece n;
	public Move(int r1,int c1,int r2,int c2,int movetype) {
		this.r1 = r1;
		this.c1 = c1;
		this.r2 = r2;
		this.c2 = c2;
		this.movetype = movetype;
	}
	public Move(int r1,int c1,int r2,int c2,int movetype,Piece n) {
		if(n instanceof Queen) {this.n = new Queen(n.white);}
		if(n instanceof Rook) {this.n = new Rook(n.white);}
		if(n instanceof Bishop) {this.n = new Bishop(n.white);}
		if(n instanceof Knight) {this.n = new Knight(n.white);}
	}
	void printmove() {
		System.out.println((char)(r1+'a')+""+(int)(c1+1)+"-"+(char)(r2+'a')+""+(int)(c2+1));
	}
}
