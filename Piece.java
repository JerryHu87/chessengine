package Main;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece{
	boolean moved = false,white,firstmove = false;
	boolean [][] attack = new boolean [8][8];
	List<Move> legalmove = new ArrayList<Move>();
	public Piece(boolean white) {
		this.white = white;
	}
	abstract char printpiece();
	void printattackmap() {
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(attack[i][j]) {System.out.print(1);}
				else {System.out.print(0);}
			}
			System.out.println();
		}
	}
	void printlegalmove() {
		for(Move m:legalmove) {
			System.out.println(printpiece() + " " + (char)(m.r1+'a') + " " + (int)(m.c1+1) + " " + (char)(m.r2+'a') + " " + (int)(m.c2+1));
		}
	}
}