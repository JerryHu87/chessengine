package Main;
import java.util.List;
import java.util.ArrayList;

public class Board {
	Piece [][] board = new Piece[8][8];
	byte [][] whiteattack = new byte [8][8];
	byte [][] blackattack = new byte [8][8];
	List<Move> whitemove = new ArrayList<Move>();
	List<Move> blackmove = new ArrayList<Move>();
	//Generic constructor with new board
	public Board(){
		board[0][0] = new Rook(true);
		board[1][0] = new Knight(true);
		board[2][0] = new Bishop(true);
		board[3][0] = new Queen(true);
		board[4][0] = new King(true);
		board[5][0] = new Bishop(true);
		board[6][0] = new Knight(true);
		board[7][0] = new Rook(true);
		board[0][1] = new Pawn(true);
		board[1][1] = new Pawn(true);
		board[2][1] = new Pawn(true);
		board[3][1] = new Pawn(true);
		board[4][1] = new Pawn(true);
		board[5][1] = new Pawn(true);
		board[6][1] = new Pawn(true);
		board[7][1] = new Pawn(true);
		board[0][6] = new Pawn(false);
		board[1][6] = new Pawn(false);
		board[2][6] = new Pawn(false);
		board[3][6] = new Pawn(false);
		board[4][6] = new Pawn(false);
		board[5][6] = new Pawn(false);
		board[6][6] = new Pawn(false);
		board[7][6] = new Pawn(false);
		board[0][7] = new Rook(false);
		board[1][7] = new Knight(false);
		board[2][7] = new Bishop(false);
		board[3][7] = new Queen(false);
		board[4][7] = new King(false);
		board[5][7] = new Bishop(false);
		board[6][7] = new Knight(false);
		board[7][7] = new Rook(false);
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] != null) {updatepiece(i,j);}
			}
		}
		updatemap();
		updatealllegalmove();
	}
	//Dummy constructor to copy board
	public Board(Board cur) {
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(cur.board[i][j] instanceof King) {board[i][j] = new King(cur.board[i][j].white);}
				if(cur.board[i][j] instanceof Queen) {board[i][j] = new Queen(cur.board[i][j].white);}
				if(cur.board[i][j] instanceof Knight) {board[i][j] = new Knight(cur.board[i][j].white);}
				if(cur.board[i][j] instanceof Bishop) {board[i][j] = new Bishop(cur.board[i][j].white);}
				if(cur.board[i][j] instanceof Rook) {board[i][j] = new Rook(cur.board[i][j].white);}
				if(cur.board[i][j] instanceof Pawn) {board[i][j] = new Pawn(cur.board[i][j].white);}
				this.whiteattack[i][j] = cur.whiteattack[i][j];
				this.blackattack[i][j] = cur.blackattack[i][j];
			}
		}
		for(Move e:cur.whitemove) {this.whitemove.add(e);}
		for(Move e:cur.blackmove) {this.blackmove.add(e);}
	}
	//Reset board values
	void updatenewpos() {
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] != null) {
					updatepiece(i,j);
				}
			}
		}
		updatemap();
		updatealllegalmove();
	}
	//Update whitemove, blackmove with all legal moves
	void updatealllegalmove() {
		whitemove.clear();
		blackmove.clear();
		int bkingr = 0,bkingc = 0,wkingr = 0,wkingc = 0;
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] != null && board[i][j] instanceof King && board[i][j].white) {
					wkingr = i;
					wkingc = j;
				}
				if(board[i][j] != null && board[i][j] instanceof King && !board[i][j].white) {
					bkingr = i;
					bkingc = j;
				}
			}
		}
		if(whiteattack[bkingr][bkingc] > 0 || blackattack[wkingr][wkingc] > 0) {
			//System.out.println(true);
			for(int i = 0;i<8;i++) {
				for(int j = 0;j<8;j++) {
					if(board[i][j] != null) {
						if(board[i][j].white) {
							for(Move m:board[i][j].legalmove) {
								//System.out.println(m.r1 + " " + m.c1 + " " + m.r2 + " " + m.c2);
								Board next = new Board(this);
								next.makemove(m);
								for(int p = 0;p<8;p++) {
									for(int q = 0;q<8;q++) {
										if(next.board[p][q] != null) {next.updatepiece(p,q);}
									}
								}
								next.updatemap();
								int t1 = wkingr,t2 = wkingc;
								boolean c = false;
								if(board[m.r1][m.c1] instanceof King) {wkingr = m.r2;wkingc = m.c2;c = true;}
								if(next.blackattack[wkingr][wkingc] == 0) {whitemove.add(m);}
								if(c) {wkingr = t1;wkingc = t2;}
							}
						}
						else {
							for(Move m:board[i][j].legalmove) {
								Board next = new Board(this);
								next.makemove(m);
								for(int p = 0;p<8;p++) {
									for(int q = 0;q<8;q++) {
										if(next.board[p][q] != null) {next.updatepiece(p,q);}
									}
								}
								next.updatemap();
								int t1 = bkingr,t2 = bkingc;
								boolean c = false;
								if(board[m.r1][m.c1] instanceof King) {bkingr = m.r2;bkingc = m.c2;c = true;}
								if(next.whiteattack[bkingr][bkingc] == 0) {blackmove.add(m);}
								if(c) {bkingr = t1;bkingc = t2;}
							}
						}
					}
				}
			}
			//printwhitemoves();
			return;
		}
		if(checkkingcastle(true)) {
			whitemove.add(new Move(1,1,0,0,3));
		}
		if(checkkingcastle(false)) {
			blackmove.add(new Move(1,2,0,0,3));
		}
		if(checkqueencastle(true)) {
			//System.out.println(true);
			whitemove.add(new Move(2,1,2,0,3));
		}
		if(checkqueencastle(false)) {
			blackmove.add(new Move(2,2,2,7,3));
		}
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] != null) {
					if(board[i][j].white) {
						for(Move m:board[i][j].legalmove) {
							//System.out.println((char)(m.r1+'a') + " " + (int)(m.c1+1) + " " + (char)(m.r2+'a') + " " + (int)(m.c2+1));
							whitemove.add(m);
						}
					}
					else {
						for(Move m:board[i][j].legalmove) {
							blackmove.add(m);
						}
					}
				}
			}
		}
	}
	//Update black and white attack maps with all pieces
	void updatemap() {
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				whiteattack[i][j] = 0;
				blackattack[i][j] = 0;
			}
		}
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] != null) {
					for(int p = 0;p<8;p++) {
						for(int q = 0;q<8;q++) {
							if(board[i][j].white && board[i][j].attack[p][q]) {
								whiteattack[p][q]++;
							}
							else if(!board[i][j].white && board[i][j].attack[p][q]) {
								blackattack[p][q]++;
							}
						}
					}
				}
			}
		}
	}
	//Update a particular piece with new attackmove and new legalmove
	void updatepiece(int r,int c) {
		board[r][c].legalmove.clear();
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				board[r][c].attack[i][j] = false;
			}
		}
		if(board[r][c] instanceof Rook) {
			int i = r+1;
			int j = c;
			while(i < 8) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i++;
			}
			i = r-1;
			j = c;
			while(i >= 0) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i--;
			}
			i = r;
			j = c-1;
			while(j >= 0) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				j--;
			}
			i = r;
			j = c+1;
			while(j < 8) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				j++;
			}
		}
		else if(board[r][c] instanceof Knight) {
			if(r+2<8 && c+1<8) {board[r][c].attack[r+2][c+1] = true;}
			if(r+2<8 && c-1>=0) {board[r][c].attack[r+2][c-1] = true;}
			if(r-2>=0 && c+1<8) {board[r][c].attack[r-2][c+1] = true;}
			if(r-2>=0 && c-1>=0) {board[r][c].attack[r-2][c-1] = true;}
			if(r+1<8 && c+2<8) {board[r][c].attack[r+1][c+2] = true;}
			if(r+1<8 && c-2>=0) {board[r][c].attack[r+1][c-2] = true;}
			if(r-1>=0 && c+2<8) {board[r][c].attack[r-1][c+2] = true;}
			if(r-1>=0 && c-2>=0) {board[r][c].attack[r-1][c-2] = true;}
		}
		else if(board[r][c] instanceof Bishop) {
			int i = r+1;
			int j = c+1;
			while(i < 8 && j < 8) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i++;
				j++;
			}
			i = r-1;
			j = c+1;
			while(i >= 0 && j < 8) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i--;
				j++;
			}
			i = r+1;
			j = c-1;
			while(i < 8 && j >= 0) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i++;
				j--;
			}
			i = r-1;
			j = c-1;
			while(i >= 0 && j >= 0) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i--;
				j--;
			}
		}
		else if(board[r][c] instanceof Queen) {
			int i = r+1;
			int j = c;
			while(i < 8) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i++;
			}
			i = r-1;
			j = c;
			while(i >= 0) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i--;
			}
			i = r;
			j = c-1;
			while(j >= 0) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				j--;
			}
			i = r;
			j = c+1;
			while(j < 8) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				j++;
			}
			i = r+1;
			j = c+1;
			while(i < 8 && j < 8) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i++;
				j++;
			}
			i = r-1;
			j = c+1;
			while(i >= 0 && j < 8) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i--;
				j++;
			}
			i = r+1;
			j = c-1;
			while(i < 8 && j >= 0) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i++;
				j--;
			}
			i = r-1;
			j = c-1;
			while(i >= 0 && j >= 0) {
				board[r][c].attack[i][j] = true;
				if(board[i][j] != null) {break;}
				i--;
				j--;
			}
		}
		else if(board[r][c] instanceof King) {
			//board[r][c].printlegalmove();
			if(r+1<8 && c+1<8) {board[r][c].attack[r+1][c+1] = true;}
			if(r+1<8 && c-1>=0) {board[r][c].attack[r+1][c-1] = true;}
			if(r+1<8) {board[r][c].attack[r+1][c] = true;}
			if(c+1<8) {board[r][c].attack[r][c+1] = true;}
			if(c-1>=0) {board[r][c].attack[r][c-1] = true;}
			if(r-1>=0 && c+1<8) {board[r][c].attack[r-1][c+1] = true;}
			if(r-1>=0) {board[r][c].attack[r-1][c] = true;}
			if(r-1>=0 && c-1>=0) {board[r][c].attack[r-1][c-1] = true;}
			for(int p = 0;p<8;p++) {
				for(int q = 0;q<8;q++) {
					if(board[r][c].attack[p][q] && blackattack[p][q] == 0 && (board[p][q] == null || board[p][q].white != board[r][c].white)) {
						board[r][c].legalmove.add(new Move(r,c,p,q,0));
					}
				}
			}
			//cur.printpiece();
			//cur.printlegalmove();
			return;
		}
		else{
			if(board[r][c].white) {
				if(r+1<8 && c+1<8) {board[r][c].attack[r+1][c+1] = true;}
				if(r-1>=0 && c+1<8) {board[r][c].attack[r-1][c+1] = true;}
			}
			else {
				if(r+1<8 && c-1>=0) {board[r][c].attack[r+1][c-1] = true;}
				if(r-1>=0 && c-1>=0) {board[r][c].attack[r-1][c-1] = true;}				
			}
			for(int p = 0;p<8;p++) {
				for(int q = 0;q<8;q++) {
					if(board[r][c].attack[p][q] && board[p][q] != null && board[p][q].white != board[r][c].white) {
						board[r][c].legalmove.add(new Move(r,c,p,q,0));
					}
				}
			}
			if(board[r][c].white) {
				if(r+1 < 8 && board[r+1][c] != null && board[r+1][c] instanceof Pawn && board[r+1][c].firstmove) {
					board[r][c].legalmove.add(new Move(r,c,r+1,c+1,1));
				}
				if(r-1 >= 0 && board[r-1][c] != null && board[r-1][c] instanceof Pawn && board[r-1][c].firstmove) {
					board[r][c].legalmove.add(new Move(r,c,r-1,c+1,1));
				}
				if(!board[r][c].moved && board[r][c+1] == null && board[r][c+2] == null) {
					board[r][c].legalmove.add(new Move(r,c,r,c+2,0));
				}
				if(board[r][c+1] == null) {
					if(c == 7) {
						board[r][c].legalmove.add(new Move(r,c,r,c+1,2,new Queen(board[r][c].white)));
						board[r][c].legalmove.add(new Move(r,c,r,c+1,2,new Bishop(board[r][c].white)));
						board[r][c].legalmove.add(new Move(r,c,r,c+1,2,new Rook(board[r][c].white)));
						board[r][c].legalmove.add(new Move(r,c,r,c+1,2,new Knight(board[r][c].white)));
					}
					else{
						board[r][c].legalmove.add(new Move(r,c,r,c+1,0));
					}
				}
			}
			else {
				if(r+1 < 8 && board[r+1][c] != null && board[r+1][c] instanceof Pawn && board[r+1][c].firstmove) {
					board[r][c].legalmove.add(new Move(r,c,r+1,c-1,1));
				}
				if(r-1 >= 0 && board[r-1][c] != null && board[r-1][c] instanceof Pawn && board[r-1][c].firstmove) {
					board[r][c].legalmove.add(new Move(r,c,r-1,c-1,1));
				}
				if(!board[r][c].moved && board[r][c-1] == null && board[r][c-2] == null) {
					board[r][c].legalmove.add(new Move(r,c,r,c-2,0));
				}
				if(board[r][c-1] == null) {
					if(c == 0) {
						board[r][c].legalmove.add(new Move(r,c,r,c-1,2,new Queen(board[r][c].white)));
						board[r][c].legalmove.add(new Move(r,c,r,c-1,2,new Bishop(board[r][c].white)));
						board[r][c].legalmove.add(new Move(r,c,r,c-1,2,new Rook(board[r][c].white)));
						board[r][c].legalmove.add(new Move(r,c,r,c-1,2,new Knight(board[r][c].white)));
					}
					else {
						board[r][c].legalmove.add(new Move(r,c,r,c-1,0));
					}
				}
			}
			//cur.printpiece();
			//cur.printlegalmove();
			return;
		}
		for(int p = 0;p<8;p++) {
			for(int q = 0;q<8;q++) {
				if(board[r][c].attack[p][q] && (board[p][q] == null || board[p][q].white != board[r][c].white)) {
					board[r][c].legalmove.add(new Move(r,c,p,q,0));
				}
			}
		}
		//cur.printpiece();
		//cur.printlegalmove();
	}
	//Check if kingside castle is legal
	boolean checkkingcastle(boolean white) {
		if(white) {
			if(board[4][0] == null || board[7][0] == null) {return false;}
			if(board[7][0].printpiece()  != 'K' || board[4][0].moved || board[7][0].printpiece()  != 'R' || board[7][0].moved) {return false;}
			if(blackattack[4][0] > 0 || blackattack[5][0] > 0 || blackattack[6][0] > 0) {return false;}
			if(board[5][0] != null || board[6][0] != null) {return false;}
			return true;
		}
		else {
			if(board[4][7] == null || board[7][7] == null) {return false;}
			if(board[4][7].printpiece() != 'K' || board[4][7].moved || board[7][7].printpiece() != 'R' || board[7][7].moved) {return false;}
			if(blackattack[4][7] > 0 || blackattack[5][7] > 0 || blackattack[6][7] > 0) {return false;}
			if(board[5][7] != null || board[6][7] != null) {return false;}
			return true;
		}
	}
	//Check if queenside castle is legal
	boolean checkqueencastle(boolean white) {
		if(white) {
			if(board[4][0] == null || board[0][0] == null) {return false;}
			if(!(board[4][0] instanceof King) || board[4][0].moved || !(board[0][0] instanceof Rook) || board[0][0].moved) {return false;}
			if(blackattack[4][0] > 0 || blackattack[3][0] > 0 || blackattack[2][0] > 0) {return false;}
			if(board[3][0] != null || board[2][0] != null) {return false;}
			return true;
		}
		else {
			if(board[4][7] == null || board[0][7] == null) {return false;}
			if(!(board[4][7] instanceof King) || board[4][7].moved || !(board[0][7] instanceof Rook) || board[0][7].moved) {return false;}
			if(whiteattack[4][7] > 0 || whiteattack[3][7] > 0 || whiteattack[2][7] > 0) {return false;}
			if(board[3][7] != null || board[2][7] != null) {return false;}
			return true;
		}
	}
	//Update board with a kingside castle
	void kingcastle(boolean white) {
		if(white) {
			board[6][0] = board[4][0];
			board[5][0] = board[7][0];
			board[4][0] = null;
			board[7][0] = null;
		}
		else {
			board[6][7] = board[4][7];
			board[5][7] = board[7][7];
			board[4][7] = null;
			board[7][7] = null;
		}
	}
	//Update board with a queenside castle
	void queencastle(boolean white) {
		if(white) {
			board[2][0] = board[4][0];
			board[3][0] = board[0][0];
			board[4][0] = null;
			board[0][0] = null;
		}
		else {
			board[2][7] = board[4][7];
			board[3][7] = board[0][7];
			board[4][7] = null;
			board[0][7] = null;
		}
	}
	//Make a move on current board
	void makemove(Move m) {
		if(m.movetype == 0) {
			if(board[m.r1][m.c1] instanceof King) {board[m.r2][m.c2] = new King(board[m.r1][m.c1]);}
			else if(board[m.r1][m.c1] instanceof Queen) {board[m.r2][m.c2] = new Queen(board[m.r1][m.c1]);}
			else if(board[m.r1][m.c1] instanceof Bishop) {board[m.r2][m.c2] = new Bishop(board[m.r1][m.c1]);}
			else if(board[m.r1][m.c1] instanceof Knight) {board[m.r2][m.c2] = new Knight(board[m.r1][m.c1]);}
			else if(board[m.r1][m.c1] instanceof Rook) {board[m.r2][m.c2] = new Rook(board[m.r1][m.c1]);}
			else{board[m.r2][m.c2] = new Pawn(board[m.r1][m.c1]);}
			if(!board[m.r2][m.c2].moved) {board[m.r2][m.c2].firstmove = true;}
			else {board[m.r2][m.c2].firstmove = false;}
			board[m.r2][m.c2].moved = true;
			board[m.r1][m.c1] = null;
		}
		else if(m.movetype == 1) {
			if(board[m.r1][m.c1] instanceof King) {board[m.r2][m.c2] = new King(board[m.r1][m.c1]);}
			else if(board[m.r1][m.c1] instanceof Queen) {board[m.r2][m.c2] = new Queen(board[m.r1][m.c1]);}
			else if(board[m.r1][m.c1] instanceof Bishop) {board[m.r2][m.c2] = new Bishop(board[m.r1][m.c1]);}
			else if(board[m.r1][m.c1] instanceof Knight) {board[m.r2][m.c2] = new Knight(board[m.r1][m.c1]);}
			else if(board[m.r1][m.c1] instanceof Rook) {board[m.r2][m.c2] = new Rook(board[m.r1][m.c1]);}
			else{board[m.r2][m.c2] = new Pawn(board[m.r1][m.c1]);}
			if(!board[m.r2][m.c2].moved) {board[m.r2][m.c2].firstmove = true;}
			else {board[m.r2][m.c2].firstmove = false;}
			board[m.r2][m.c2].moved = true;
			board[m.r1][m.c1] = null;
			board[m.r1][m.c2] = null;
		}
		else if(m.movetype == 2) {
			if(m.n instanceof Queen) {board[m.r2][m.c2] = new Queen(m.n);}
			else if(m.n instanceof Rook) {board[m.r2][m.c2] = new Rook(m.n);}
			else if(m.n instanceof Bishop) {board[m.r2][m.c2] = new Bishop(m.n);}
			else {board[m.r2][m.c2] = new Knight(m.n);}
			board[m.r1][m.c1] = null;
			board[m.r2][m.c2].moved = true;
		}
		else{
			if(m.r1 == 1) {
				if(m.c1 == 1) {kingcastle(true);}
				else {kingcastle(false);}
			}
			else {
				if(m.c1 == 1) {queencastle(true);}
				else {queencastle(false);}
			}
		}
	}
	//Check if white is mated
	boolean checkwhitemate(Board cur) {
		int kingr = 0,kingc = 0;
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] != null && board[i][j] instanceof King && board[i][j].white) {
					kingr = i;
					kingc = j;
				}
			}
		}
		if(blackattack[kingr][kingc] == 0) {return false;}
		for(Move e:whitemove) {
			Board next = new Board(cur);
			boolean c = false;
			int t1 = kingr,t2 = kingc;
			if(e.r1 == kingr && e.c1 == kingc) {kingr = e.r2;kingc = e.c2;c = true;}
			next.makemove(e);
			next.updatenewpos();
			if(next.blackattack[kingr][kingc] == 0) {return false;}
			if(c) {kingr = t1;kingc = t2;}
		}
		return true;
	}
	//Check if black is mated
	boolean checkblackmate(Board cur) {
		int kingr = 0,kingc = 0;
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] != null && board[i][j] instanceof King && !board[i][j].white) {
					kingr = i;
					kingc = j;
				}
			}
		}
		if(whiteattack[kingr][kingc] == 0) {return false;}
		for(Move e:blackmove) {
			Board next = new Board(cur);
			boolean c = false;
			next.makemove(e);
			next.updatenewpos();
			int t1 = kingr,t2 = kingc;
			if(e.r1 == kingr && e.c1 == kingc) {kingr = e.r2;kingc = e.c2;c = true;}
			if(next.whiteattack[kingr][kingc] == 0) {return false;}
			if(c) {kingr = t1;kingc = t2;}
		}
		return true;
	}
	//Check if board is drawn
	boolean checkdraw(boolean whiteturn) {
		if(whiteturn) {return whitemove.isEmpty();}
		else {return blackmove.isEmpty();}
	}
	//Print current board
	void printboard() {
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] != null) {System.out.print(board[i][j].printpiece());}
				else {System.out.print(".");}
			}
			System.out.println();
		}
	}
	//Printing legal moves for white
	void printwhitemoves() {			
		for(Move m:whitemove) {
			System.out.println((char)(m.r1+'a') + " " + (int)(m.c1+1) + " " + (char)(m.r2+'a') + " " + (int)(m.c2+1));
		}
	}
	//Printing legal moves for black
	void printblackmoves() {
		for(Move m:blackmove) {
			System.out.println((char)(m.r1+'a') + " " + (int)(m.c1+1) + " " + (char)(m.r2+'a') + " " + (int)(m.c2+1));
		}
	}
	void printwhiteattack() {
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				System.out.print(whiteattack[i][j]);
			}
			System.out.println();
		}
	}
	void printblackattack() {
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				System.out.print(blackattack[i][j]);
			}
			System.out.println();
		}
	}
}
