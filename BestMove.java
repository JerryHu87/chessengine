package Main;

public class BestMove {
	int r1,c1,r2,c2,score;
	public BestMove(Move cur,int score) {
		r1 = cur.r1;
		c1 = cur.c1;
		r2 = cur.r2;
		c2 = cur.c2;
		this.score = score;
	}
	public BestMove() {
		r1 = c1 = r2 = c2 = score = 0;
	}
}
