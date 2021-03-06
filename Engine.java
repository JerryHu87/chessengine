package Main;

public class Engine {
	boolean white;
	int [][] pawntable = {{0,5,5,0,5,10,50,0},
						  {0,10,-5,0,5,10,50,0},
						  {0,10,-10,0,10,20,50,0},
						  {0,-20,0,20,25,30,50,0},
						  {0,-20,0,20,25,30,50,0}, 
						  {0,10,-10,0,10,20,50,0},				
						  {0,10,-5,0,5,10,50,0},
						  {0,5,5,0,5,10,50,0},};
	int [][] knighttable = {{-50,-40,-30,-30,-30,-30,-40,-50},
							{-40,-20,5,0,5,0,-20,-40},
							{-30,0,10,15,15,10,0,-30},
							{-30,5,15,20,20,15,0,-30},
							{-30,5,15,20,20,15,0,-30},
							{-30,0,10,15,15,10,0,-30},
							{-40,-20,5,0,5,0,-20,-40},
							{-50,-40,-30,-30,-30,-30,-40,-50}};
	int [][] bishoptable = {{-20,-10,-10,-10,-10,-10,-10,-20},
							{-10,5,10,0,5,0,0,-10},
							{-10,0,10,10,5,5,0,-10},
							{-10,0,10,10,10,10,0,-10},
							{-10,0,10,10,10,10,0,-10},
							{-10,0,10,10,5,5,0,-10},
							{-10,5,10,0,5,0,0,-10},
							{-20,-10,-10,-10,-10,-10,-10,-20},};
	int [][] rooktable = {{0,-5,-5,-5,-5,-5,-5,0},
						  {0,0,0,0,0,0,10,0},
						  {0,0,0,0,0,0,10,0},
						  {5,0,0,0,0,0,10,0},
						  {5,0,0,0,0,0,10,0},
						  {0,0,0,0,0,0,10,0},
						  {0,0,0,0,0,0,10,0},
						  {0,-5,-5,-5,-5,-5,-5,0},};
	int [][] queentable = {{-20,-10,-10,0,-5,-10,-10,-20},
						   {-10,0,5,0,0,0,0,-10},
						   {-10,5,5,5,5,5,0,-10},
						   {-5,0,5,5,5,5,0,-5},
						   {-5,0,5,5,5,5,0,-5},
						   {-10,5,5,5,5,5,0,-10},
						   {-10,0,5,0,0,0,0,-10},
						   {-20,-10,-10,0,-5,-10,-10,-20},};
	int [][] kingtable = {{20,20,-10,-20,-30,-30,-30,-30},
						  {30,20,-20,-30,-40,-40,-40,-40},
						  {10,0,-20,-30,-40,-40,-40,-40},
						  {0,0,-20,-40,-50,-50,-50,-50},
						  {0,0,-20,-40,-50,-50,-50,-50},
						  {10,0,-20,-30,-40,-40,-40,-40},
						  {30,20,-20,-30,-40,-40,-40,-40},
						  {20,20,-10,-20,-30,-30,-30,-30}};
	public Engine(boolean white) {
		this.white = white;
	}
	int dfs(Board cur,boolean max,int depth,int alpha,int beta) {
		if(depth == 4) {
			return score(cur);
		}
		int ret = 0;
		if(max) {
			ret = Integer.MIN_VALUE;
			for(Move e:cur.whitemove) {
				Board next = new Board(cur);
				next.makemove(e);
				next.updatenewpos();
				int temp = dfs(next,!max,depth+1,alpha,beta);
				alpha = Math.max(alpha,temp);
				if(alpha >= beta) {break;}
				ret = Math.max(temp, ret);
			}		
		}
		else {
			ret = Integer.MAX_VALUE;
			for(Move e:cur.blackmove) {
				Board next = new Board(cur);
				next.makemove(e);
				next.updatenewpos();
				int temp = dfs(next,!max,depth+1,alpha,beta);
				beta = Math.min(beta, temp);
				if(beta <= alpha) {break;}
				ret = Math.min(temp, ret);
			}
		}
		return ret;
	}
	int score(Board cur) {
		int ret = 0;
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(cur.board[i][j] instanceof Pawn) {
					ret += (cur.board[i][j].white ? 10:-10);
					if(cur.board[i][j].white) {ret += pawntable[i][j];}
					else {ret -= pawntable[i][7-j];}
				}
				else if(cur.board[i][j] instanceof Knight) {
					ret += (cur.board[i][j].white ? 30:-30);
					if(cur.board[i][j].white) {ret += knighttable[i][j];}
					else {ret -= knighttable[i][7-j];}
				}
				else if(cur.board[i][j] instanceof Bishop) {
					ret += (cur.board[i][j].white ? 30:-30);
					if(cur.board[i][j].white) {ret += bishoptable[i][j];}
					else {ret -= bishoptable[i][7-j];}
				}
				else if(cur.board[i][j] instanceof Rook) {
					ret += (cur.board[i][j].white ? 50:-50);
					if(cur.board[i][j].white) {ret += rooktable[i][j];}
					else {ret -= rooktable[i][7-j];}
				}
				else if(cur.board[i][j] instanceof Queen) {
					ret += (cur.board[i][j].white ? 90:-90);
					if(cur.board[i][j].white) {ret += queentable[i][j];}
					else {ret -= queentable[i][7-j];}
				}
				else if(cur.board[i][j] instanceof King){
					ret += (cur.board[i][j].white ? 900:-900);
					if(cur.board[i][j].white) {ret += kingtable[i][j];}
					else {ret -= kingtable[i][7-j];}
				}
			}
		}
		return ret;
	}
	Move gen(Board cur) {
		Move ret = new Move(0,0,0,0,0);
		if(white) {
			int curscore = Integer.MIN_VALUE;
			for(Move e:cur.whitemove) {
				Board next = new Board(cur);
				next.makemove(e);
				next.updatenewpos();
				if(whitematingsequence(next,false,0)) {
					ret = e;
					break;
				}
				int temp = dfs(next,true,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
				if(temp > curscore) {
					ret = e;
					curscore = temp;
				}
			}
		}
		else {
			int curscore = Integer.MAX_VALUE;
			for(Move e:cur.blackmove) {
				Board next = new Board(cur);
				next.makemove(e);
				next.updatenewpos();
				if(blackmatingsequence(next,true,0)) {
					ret = e;
					break;
				}
				int temp = dfs(next,false,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
				if(temp < curscore) {
					ret = e;
					curscore = temp;
				}
			}
		}
		return ret;
	}
	boolean whitematingsequence(Board cur,boolean turn,int depth) {
		if(depth == 5) {return false;}
		if(!turn && cur.checkblackmate(cur)) {return true;}
		if(turn) {
			int bkingr = 0,bkingc = 0;
			for(int i = 0;i<8;i++) {
				for(int j =0;j<8;j++) {
					if(cur.board[i][j] instanceof King && !cur.board[i][j].white) {
						bkingr = i;bkingc = j;
					}
				}
			}
			boolean mate = false;
			for(Move e:cur.whitemove) {
				Board next = new Board(cur);
				next.makemove(e);
				next.updatenewpos();
				if(next.whiteattack[bkingr][bkingc] > 0) {
					mate = (mate || whitematingsequence(next,!turn,depth+1));
				}
			}
			return mate;
		}
		else {
			for(Move e:cur.blackmove) {
				Board next = new Board(cur);
				next.makemove(e);
				next.updatenewpos();
				if(!whitematingsequence(next,!turn,depth+1)) {return false;}
			}
			return true;
		}
	}
	boolean blackmatingsequence(Board cur,boolean turn,int depth) {
		if(depth == 5) {return false;}
		if(!turn && cur.checkwhitemate(cur)) {return true;}
		if(!turn) {
			int wkingr = 0,wkingc = 0;
			for(int i = 0;i<8;i++) {
				for(int j =0;j<8;j++) {
					if(cur.board[i][j] instanceof King && cur.board[i][j].white) {
						wkingr = i;wkingc = j;
					}
				}
			}
			boolean mate = false;
			for(Move e:cur.blackmove) {
				Board next = new Board(cur);
				next.makemove(e);
				next.updatenewpos();
				if(cur.blackattack[wkingr][wkingc] > 0) {
					mate = (mate || blackmatingsequence(next,!turn,depth+1));
				}
			}
			return mate;
		}
		else {
			for(Move e:cur.whitemove) {
				Board next = new Board(cur);
				next.makemove(e);
				next.updatenewpos();
				if(!blackmatingsequence(next,!turn,depth+1)) {return false;}
			}
			return true;
		}
	}
}
