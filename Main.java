package Main;
import java.util.Scanner;

public class Main {
	public static Board org = new Board();
	public static void main(String [] args) {
		org.updatenewpos();
		Scanner input = new Scanner(System.in);
		String in = "";
		System.out.println("Hello, would you like to play white or black? (W) (B)");
		in = input.next();
		while(!in.equals("w") && !in.equals("W") && !in.equals("b") && !in.equals("B")) {
			System.out.println("Please input a valid value. (W) (B)");
			in = input.next();
		}
		boolean aiwhite = true,whiteturn = true;
		if(in.equals("w") || in.equals("W")) {aiwhite = false;}
		Engine eng = new Engine(aiwhite);
		while(!org.checkwhitemate(org) && !org.checkblackmate(org) && !org.checkdraw(whiteturn)) {
			//org.board[4][0].printlegalmove();
			if(whiteturn != aiwhite) {
				System.out.println("Please input your move(oldrow(a-h) oldcol(1-8) newrow(a-h) newcol(1-8))");
				in = input.next();
				if(in.equals("O-O") || in.equals("o-o") || in.equals("O-o") || in.equals("o-O")) {
					org.kingcastle(whiteturn);
				}
				else if(in.equals("O-O-O") || in.equals("o-o-o")) {
					org.queencastle(whiteturn);
				}
				else if(in.length() == 5) {
					int r1 = in.charAt(0)-'a',c1 = in.charAt(1)-'1', r2 = in.charAt(2)-'a',c2 = in.charAt(3)-'1';
					org.board[r1][c1] = null;
					if(in.charAt(4) == 'Q' || in.charAt(4) == 'q') {org.board[r2][c2] = new Queen(whiteturn);}
					else if(in.charAt(4) == 'N' || in.charAt(4) == 'n') {org.board[r2][c2] = new Knight(whiteturn);}
					else if(in.charAt(4) == 'B' || in.charAt(4) == 'b') {org.board[r2][c2] = new Bishop(whiteturn);}
					else {org.board[r2][c2] = new Rook(whiteturn);}
					//org.printboard();
				}
				else {
					int r1 = in.charAt(0)-'a',c1 = in.charAt(1)-'1', r2 = in.charAt(2)-'a',c2 = in.charAt(3)-'1';
					if(org.board[r1][c1] != null && org.board[r1][c1] instanceof Pawn && org.board[r2][c2] == null && c1 != c2) {
						if(org.board[r1][c2] != null && org.board[r1][c2] instanceof Pawn && org.board[r1][c2].firstmove) {
							org.board[r1][c2] = null;
						}
					}
					org.makemove(new Move(in.charAt(0)-'a',in.charAt(1)-'1',in.charAt(2)-'a',in.charAt(3)-'1',0));
				}
				org.updatenewpos();
			}
			else {
				Move temp = eng.gen(org);
				//temp.printmove();
				org.makemove(temp);
				//org.printboard();
				for(int i = 0;i<8;i++) {
					for(int j = 0;j<8;j++) {
						if(org.board[i][j] != null) {org.updatepiece(i, j);}
					}
				}
				org.updatemap();
				org.updatealllegalmove();
				if(!(org.board[temp.r2][temp.c2] instanceof Pawn)){
					System.out.print(org.board[temp.r2][temp.c2].printpiece());
				}
				System.out.println((char)(temp.r1+'a')+""+ (int)(temp.c1+1)+"-"+(char)(temp.r2+'a')+ (int)(temp.c2+1));
			}
			whiteturn = !whiteturn;
		}
		if(org.checkwhitemate(org)) {
			System.out.println("White wins!");
		}
		else if(org.checkblackmate(org)) {
			System.out.println("Black wins!");
		}
		else {
			System.out.println("Draw!");
		}
		input.close();
	}
}
 