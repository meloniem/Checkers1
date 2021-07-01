package core;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;



/**
 * @author Melonie Miller
 * {@summary Logic based component of Checkers game. Validates game moves
 * and contains information for movement of pieces. Contains multiple logic 
 * validation methods (boolean) to validate moves, as well as methods to make 
 * and display moves on the board.}
 * 
 * @version 6.07.21.2
 * Completion time: 22.5 hours (Version 1)
 * Completion time: 23.75 hours (Version 2)
 *
 */
public class CheckersLogic {


	
	/**	 PLAYER_O: constant value for player O (0) */
	
	/**PLAYER_O: constant value for player O (0) */
	public final int PLAYER_O = 0;
	
	/** PLAYER_X: constant value for player X (1) 	 */
	public final int PLAYER_X = 1;
	
	/** SYMBOL_O: symbol 'o' for player o checker	 */
	public final char SYMBOL_O = 'o';
	
	/** SYMBOL_X: symbol 'x' for player x checker */
	public final char SYMBOL_X = 'x';
	
	/** startingPieces: starting number of pieces in game	 */
	private int startingPieces = 0;

	/** number of pieces player o has */
	public int numPiecesO;
	
	/** number of pieces player x has */
	public int numPiecesX;
	
	/** Current player for moving pieces	 */
	public int currentPlayer;
	
	/** boolean t/f if game is in progress already.	 */
	public boolean gameInProgress;
	
	/** Represents checker board and functionality to populate board. */
	public CheckersGame board;
	
	/** Game difficulty level declared at start of game.*/
	public final int difficultyLevel;

	public boolean isComputerOpp;
	
	public int computerPlayer;
	
	/**
	 * Default constructor for CheckersLogic class used for multiplayer game.
	 */
	public CheckersLogic() {
		board = new CheckersGame();
		currentPlayer = PLAYER_X;
		numPiecesX = 12;
		numPiecesO = 12;
		startingPieces = 12;
		this.difficultyLevel = 0; //Field not needed for multiplayer 
		isComputerOpp = false;
		startNewGame();

	}
	
	
	/**
	 * Parameterized Constructor for CheckersLogic Class.
	 * Initializes CheckersGame object (board) for a computer opponent game, which contains the checker board.
	 * Initialize number of pieces for each player, and starting pieces. Calls
	 * startNewGame() to display board.
	 * @param difficultyLevel 1-3, difficulty level for AI game
	 */
	public CheckersLogic(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
		board = new CheckersGame();
		currentPlayer = PLAYER_X;
		numPiecesX = 12;
		numPiecesO = 12;
		startingPieces = 12;
		isComputerOpp = true;
		startNewGame();
	
	}
	
	/**
	 * Starts the game. Checks if game is already in progress. Should not happen.
	 * Otherwise, sets gameInProgress to ture and displays board.
	 */
	public void startNewGame() {
		if(gameInProgress==true) {
			System.out.println("Cannot start game while game is already running");
			return;
		}
		
		else {
			gameInProgress= true;
			board.displayBoard();
		}
		
	}
	
	/**
	 * Returns the current player
	 * @return int player type (0 or 1)
	 */
	public int getPlayer() {
		return currentPlayer;
	}
	
		
	
	
		/**
		 * Takes a player and their move and decides to jump or move.
		 * Splits string for move into rows and cols.
		 * @param player int, player number corresponding to x or o
		 * @param move String  representation of move (oldRowOldCol, newRowNewCol)
		 */
		public void helperMove(int player, String move) {
				
				String oldRow = move.substring(0,1);
				String oldCol = move.substring(1,2);
				String newRow = move.substring(3,4);
				String newCol = move.substring(4,5);
				int oldRowInt = convertToInt(Integer.parseInt(oldRow));
				int oldColInt = convertToInt(oldCol);
				int newRowInt = convertToInt(Integer.parseInt(newRow));
				int newColInt = convertToInt(newCol);
				if(moveIsJump(player, oldRowInt, oldColInt, newRowInt, newColInt))
					takePiece(player, oldRowInt, oldColInt, newRowInt, newColInt);
				else {
					helperMove(player, oldRowInt, oldColInt, newRowInt, newColInt);
				}
		}
			
			
		
		/**
		 * Helper method for helperMove, responsible for actually moving checker
		 * Takes the player number, starting row and col and ending row and col,
		 * modifies the board. Handles jump case.
		 * @param player player piece being moved
		 * @param fromR int starting row
		 * @param fromC int starting col
		 * @param toR int row to move to
		 * @param toC int col to move to
		 */
		public void helperMove(int player, int fromR, int fromC, int toR, int toC) {
			try {

		
				if(numPiecesO == 0) gameOver("Player O has no pieces. X wins!");
				else if(numPiecesX == 0) gameOver("Player X has no pieces. O wins!");
				else if(!hasMove(player)) gameOver("Player X has no moves left. O wins!");
				else if(!hasMove(player)) gameOver("Player O has no moves left. X wins!");
				
				else {	
			//Set new position to char at old position
			board.board[toR][toC] = board.board[fromR][fromC];
			//Set old position to "_"
			board.board[fromR][fromC] = '_';
			
			//Jump 
			if(fromR-toR == 2 || fromR-toR == -2) {
				int jumpC = (fromC + toC)/2;
				int jumpR = (fromR + toR)/2;
			
				//Set intermed. jump space empty
				board.board[jumpR][jumpC] = '_';
				if(player == PLAYER_X) {
					numPiecesO-= 1;
					System.out.println("O pieces left: " + numPiecesO);

				}
				else if(player == PLAYER_O) {
					numPiecesX -= 1;
					System.out.println("X pieces left: " + numPiecesX);

				}
				
				if(player == PLAYER_X) player = PLAYER_O;
				if(player == PLAYER_O) player = PLAYER_X;
			}

			}
			}
			catch(IndexOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println("An out of bounds index was entered: ");
				if(fromR <0) System.out.println(fromR);
				if(fromC <0) System.out.println(fromC);
				if(toR <0) System.out.println(toR);
				if(toC <0) System.out.println(toC);
			}
		}

		
		/**
		 * Check if game is in progress
		 * @return boolean 
		 */
		public boolean gameInProgress() {
			return gameInProgress;
		}
		
		/**
		 * Boolean to determine if a move is valid. Uses a helper boolean, validMove,
		 * with different parameters.
		 * @param player current player
		 * @param row1 beginning row (before move)
		 * @param col1 beginning column (before move)
		 * @param row2 ending row (after move)
		 * @param col2 ending column (after move)
		 * @return boolean t/f if it is a valid move.
		 */
		public boolean validMove(int player, int row1, int col1, int row2, int col2){
			
			if(positionChecker(row1, col1, row2, col2))
					if(checkMoves(player, row1, col1, row2, col2))
						if(moveSelection(player, row1, col1, row2, col2))
							return true;
			
			return false;
		}
		
		
		/**
		 * Called to check that the checker can be moved legally. Uses multiple
		 * helpers to validate that a move is legal.
		 * from row1,col1 to row2,col2
		 * @param player current player
		 * @param move String, concatenated string form of move
		 * @return boolean t/f if move is valid, passes to above validMove method.
		 */
		public boolean validMove(int player, String move) {
			if(player == PLAYER_O) startingPieces = numPiecesO;
			
			if(player == PLAYER_X) startingPieces = numPiecesX;
			
			
			String oldRow = move.substring(0,1);
			String oldCol = move.substring(1,2);
			String newRow = move.substring(3,4);
			String newCol = move.substring(4,5);
			
			int oldRowInt = convertToInt(Integer.parseInt(oldRow));
			int oldColInt = convertToInt(oldCol);
			int newRowInt = convertToInt(Integer.parseInt(newRow));
			int newColInt = convertToInt(newCol);

			return validMove(player, oldRowInt, oldColInt, newRowInt, newColInt);
		}
		

		/**
		 * Verify that user is not moving to an invalid square. Checks both old and new
		 * positions for correct value.
		 * @param oldRow row position moved from
		 * @param oldCol col position moved from
		 * @param newRow row position moved to
		 * @param newCol col position moved to
		 * @return boolean true if move position is valid
		 */
		public boolean positionChecker(int oldRow, int oldCol, int newRow, int newCol){
		
			if((oldRow%2 == 0 && oldCol%2 !=0) || (oldRow%2 != 0 && oldCol%2 == 0)) {
				System.out.println("Invalid move: cannot move to this square.");
				return false;
			}
			if((newRow%2 == 0 && newCol%2 != 0) || (newRow%2 != 0 && newCol%2 == 0)){
				System.out.println("Invalid move: cannot move to this square.");
				return false;
			}
			
			return true;
		}
			
		/**
		 * Verifies move is in the right format (length of string is 5, format: (row,col),(row,col))
		 * @param move string move passed to method
		 * @return boolean true if move string is in the right format
		 */
			public boolean formatChecker(String move) {
				int length = move.length();
				if(length != 5) { System.out.println("Input is not the correct length.");
				return false;
				}
				
				char oldRowC = move.charAt(0);
				char oldColC = move.charAt(1);
				char dash = move.charAt(2);
				char newRowC = move.charAt(3);
				char newColC = move.charAt(4);
				
				//Check row inputs are in correct format
				if(oldRowC < '1' || oldRowC > '8' || newRowC < '1' || newRowC > '8') {
					System.out.println("Enter a row between 1 and 8.");
					return false;
				}
				
				//Check column inputs are in correct format
				if(oldColC < 'a' || oldColC > 'h' || newColC < 'a' || newColC > 'h') {
					System.out.println("Enter a column value between a and h.");
					return false;
				}
				
				//Check that column is inputed correctly.
				if(dash < '-' || dash > '-') {
					System.out.println("Middle of inputs must be a '-'. Please try again.");
					return false;
				}
				
				return true;
			}
			
			/**
			 * Check that player is not attempting to move opponent pieces or empty space.
			 * @param player current player
			 * @param oldRow position being moved from (row)
			 * @param oldCol position being moved from (column)
			 * @param newRow position being moved to (row) 
			 * @param newCol position being moved to (column)
			 * @return boolean true if move is valid/not moving opponent pieces/empty spaces.
			 */
			public boolean moveSelection(int player, int oldRow, int oldCol, int newRow, int newCol) {
				if(newRow < 0 || newCol < 0) return false; 
				
				char symbol = board.board[oldRow][oldCol];
				char symbol2 = board.board[newRow][newCol];
				
				if(player == PLAYER_X && symbol2 != '_') {
					if(!isComputerOpp) System.out.println("Cannot move onto another piece!");
					return false;
				}
				
				if(player == PLAYER_O && symbol2 != '_') {
					if(!isComputerOpp) System.out.println("Cannot move onto another piece!");
					return false;
				}
				
				if(player == PLAYER_X && symbol == SYMBOL_O) {
					System.out.println("Invalid move: cannot select opponent pieces.");
					return false;
				}
				if(player == PLAYER_O && symbol == SYMBOL_X) {
					System.out.println("Invalid move: cannot select opponent piece.");
					return false;
				}
				
				if(symbol == '_') {
					System.out.println("Invalid move: Cannot select empty space.");
					return false;
				}
				
				return true;
			}
			
			/**
			 * Checks pieces are only moved diagonally. Also checks that player does
			 * not move an invalid distance. Also updates number of pieces when
			 * appropriate.
			 * @param player current player
			 * @param oldRow row moved from
			 * @param oldCol column moved from
			 * @param newRow row moved to
			 * @param newCol column moved to
			 * @return boolean true if move passes conditions.
			 */
			public boolean checkMoves(int player, int oldRow, int oldCol, int newRow, int newCol) {
				
			if(oldRow > 7 || oldCol > 7 || newRow > 7 || newCol > 7 || oldRow < 0 || oldCol < 0 || newRow < 0 || newCol < 0)
				return false;
			
				//Check move is diagonal
				if(oldRow == newRow || oldCol == newCol) {
					System.out.println("Invalid move: moves must be to diagonal spaces.");
					return false;
				}
				
				int distRow = Math.abs(oldRow - newRow);
				int distCol = Math.abs(oldCol - newCol);
				
				//Check move is not more than 2 spaces.
				if(distRow > 2 || distCol > 2) {
					System.out.println("Invalid move: Must be between 1 and 2 spaces.");
					return false;
				}
				
				//Check move is not backwards
					 if((player == PLAYER_O && oldRow < newRow &&
							(board.board[Math.abs((newRow+oldRow)/2)][Math.abs((newCol + oldCol)/2)]) != SYMBOL_X) ||
							(player == PLAYER_X && newRow < oldRow && (board.board[Math.abs((newRow+oldRow)/2)][Math.abs((newCol + oldCol)/2)]) != SYMBOL_O)) {
					System.out.println("Invalid move: Cannot go back unless taking opponent piece.");
					return false;
				}
				
					//Check O jump moves
				if(player == PLAYER_O && distRow > 1) {
						char symbol = board.board[Math.abs((newRow + oldRow)/2)][Math.abs((newCol+oldCol)/2)];
						if(symbol != SYMBOL_X) {
							System.out.println("Invalid move: cannot move that far with given input.");
							return false;
						}
						else 
							return true;
				}
				
				if(player == PLAYER_X) {
					if(distRow > 1) {
						char symbol = board.board[Math.abs((newRow + oldRow)/2)][Math.abs((newCol+oldCol)/2)];
						if(symbol != SYMBOL_O) {
							System.out.println("Invalid move: cannot move that far with given input.");
							return false;
						}
						else {
							return true;
						}
					}
				}
				return true;
			}
			
		/**
		 * Method to take a piece from opponent and move player's
		 * piece to new position.
		 * @param player type of player (1 or 0 representing x or o)
		 * @param oldRow starting row
		 * @param oldCol starting col
		 * @param row row player's checker is moving to
		 * @param col col player's checker is moving to
		 */
		public void takePiece(int player, int oldRow, int oldCol, int row, int col) {
	
			helperMove(player,oldRow, oldCol, row,col);				
			
		}
		
		
		/**
		 * Takes indices of checker board and converts them 
		 * to proper integer form to use in selecting 2D array
		 * location.
		 * @param input String, input "index" split from input by validMove 
		 * and/or takePiece
		 * @return int, index to be used with array
		 */
		public int convertToInt(String input){
	        int k=-1;
	        if (input.equals("a"))       k = 0;
	        else if (input.equals("b") ) k = 1;
	        else if (input.equals("c") ) k = 2;
	        else if (input.equals("d") ) k = 3;
	        else if (input.equals("e") ) k = 4;
	        else if (input.equals("f") ) k = 5;
	        else if (input.equals("g") ) k = 6;
	        else if (input.equals("h") ) k = 7;
	        return k;
	    }
		
		/**
		 * Converts user input values to correct array values 
		 * for rows-- row 8 should have int value 0, 7 should have
		 * 1, and so on.
		 * @param input, an integer
		 * @return int correct array value
		 */
		 public int convertToInt(int input){
		        int i=-1;
		        if (input==1)         i = 7;
		        else if (input == 2)  i = 6;
		        else if (input == 3)  i = 5;
		        else if (input == 4)  i = 4;
		        else if (input == 5)  i = 3;
		        else if (input == 6) i = 2;
		        else if (input == 7)  i = 1;
		        else if (input == 8)  i = 0;
		        return i;
		    }

			


		
		/**
		 * [Formatted method] Checks if a player can legally jump from (row1, col1) to (row3, col3)
		 * @param player int, player number (1 or 0, as def with PLAYER_O and PLAYER_X)
		 * @param oldRow int row being moved from
		 * @param oldCol int col being moved from
		 * @param midRow int intermediate row between jump
		 * @param midCol int intermediate col between jump
		 * @param newRow int new row being moved to
		 * @param newCol int new col being moved to 
		 * @return boolean true if move is a jump, false if not
		 */
		boolean moveIsJump(int player, int oldRow, int oldCol,int newRow, int newCol) {
			if(newRow < 0 || newRow >= 8 || newCol < 0 || newCol >=8)
				return false; //not on board
			if(Math.abs(newRow-oldRow) <2 || Math.abs(newCol-oldCol) <2) return false; //not a jump 
			
			if(board.board[newRow][newCol] != '_') {
				return false; //already occupied
			}
			
			if(player == PLAYER_O && board.board[oldRow][oldCol] == SYMBOL_O) {
					if(board.board[Math.abs((oldRow+newRow)/2)][Math.abs((oldCol+newCol)/2)] != SYMBOL_X) return false;
					 //O's can only move up
				
				else return true;
				}
			
			else {
				if(player==PLAYER_X && board.board[oldRow][oldCol] == SYMBOL_X )
					if(board.board[Math.abs((oldRow+newRow)/2)][Math.abs((oldCol+newCol)/2)] != SYMBOL_O) 
						return false;
			 return true; //x can only move down
				}

			}
		
		/**
		 * Starting method for hasMove. Checks if player has moves for any pieces.
		 * @param player current player.
		 * @return true if player has any moves left.
		 */
		public boolean hasMove(int player) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if((player == PLAYER_X && board.board[i][j] == 'x') || (player == PLAYER_O && board.board[i][j] == 'o'))
						if(hasMove(player, i, j)) return true;
				}
			}
			return false;
		}
		
		/**
		 * Check if a player has any moves in the surrounding tiles.
		 * @param player current player
		 * @param row current row
		 * @param col current column
		 * @return true if player has a move left at a specific row and column..
		 */
		public boolean hasMove(int player, int row, int col) {
			if(!validMove(player, row, col, row+1, col+1) && !validMove(player, row, col, row-1, col+1) && !validMove(player, row, col, row+1, col-1) && !validMove(player, row, col, row-1, col-1)
					&& !validMove(player, row, col, row+2, col+2) && !validMove(player, row, col, row+2, col-2) && !validMove(player, row, col, row-2, col+2) && !validMove(player, row, col, row-2, col-2))
				return false;
			else return true;
				
		}
		
	
	
	/**
	 * Switches the user: if player is 'o', goes to 'x'
	 * and vice versa
	 * 
	 */
	public void switchPlayer() {
		if(currentPlayer == 0) 
			currentPlayer = 1;
		else currentPlayer = 0;
	}

	/**
	 * Called when either player runs out of pieces or runs out of moves.
	 * @param s String, message when game over is called.
	 */
	public void gameOver(String s) {
		gameInProgress = false;
		System.out.println(s);
		return;
		
	}
	
	public void resign(int player) {
		if(player == PLAYER_X) {
			gameOver("Player X resigns. Player O wins!");
		}
		else {
			gameOver("Player O resigns. Player X wins!");
		}
	}
	

	/**
	 * Contains the data for a new checkers game.
	 * Responsible for setting up the board.
	 * @author Melonie Miller
	 *
	 */
	public class CheckersGame{
		public static final char EMPTY = ' ', O = 'o', X = 'x';
		public char[][] board;
		
		/*
		 * Constructor, creates the board and sets it up for 
		 * new game
		 */
		public CheckersGame() {
			board = new char[8][8];
			setupGame(board);
		}
		
		/**
		 * Logic to check if a place on the board is occupied
		 * @param row int position of piece (row)
		 * @param col int position of piece (col)
		 * @return true if space has a piece.
		 */
		public boolean isOccupied(int row, int col) {
			if(row < 0 || col < 0 || row >=8 || col >=8) {
				System.out.println("Illegal choice of indices. Returning false.");
				return false;
			}
			if(row >= 0 && col >= 0 && row < 8 && col < 8) {
				if(board[row][col] == '_') 
					return false;
			
			
			else if(board[row][col] == 'x' || board[row][col] == 'o')
			 return true;		
			
			}
			 return false;	
			
			
		}
		
		
		/**
		 * Sets up the current game, populates board with pieces
		 * @param board2 char[][], allows checkerboard to be passed into method for setup
		 */
		public void setupGame(char[][] board2) {
			for(int row = 0; row < 8; row++) {
				for(int col = 0; col < 8; col++) {
					if(row%2 == col%2) {
						if(row < 3) board2[row][col] = 'x';
						else if(row > 4) board2[row][col] = 'o';
						else board2[row][col] = '_';
					}
					else {
						board2[row][col] = '_';
					}
				}
			}


		} //end setupGame
		
		/**
		 * Displays the board for the user.
		 */
		public void displayBoard() {
			System.out.print("\n");
			char colName = 'a';
			System.out.print("    ");
			for(int i = 4; i < 28; i+=3) System.out.print(colName++ + "   ");
			System.out.print("\n");
			for(int row = 0; row < 8; ++row) {
				System.out.print((8-row)+" ");
				for(int col = 0; col < 8; ++col) {
					System.out.print("| " + board[row][col] + " ");
				}
				System.out.println("|\n");
			}
		}	 //end CheckersGame class
			}
		
	

/**
 * ****************************CLASS: MOVE************************************
 * Class Move: Isolates row/col of a move (May no longer be needed by end of 
 * implementation?)
 * @author Melonie Miller
 *
 */
class Move{
	/**
	 * Current row and current column piece is at for instantiating a Move object.
	 */
	public int currRow;
	public int currCol;
	
	/** New row and column for piece to move to for instantiating Move Object. */
	public int newCol;
	public int newRow;
	public int currentPlayer;
	public final char currentSymbol;

	private char[][] board;
	
	/**
	 * Move class constructor
	 * @param player player whos move it is
	 * @param board board move is played on 
	 * @param row1 starting row
	 * @param col1 starting col
	 * @param row2 ending row
	 * @param col2 ending col 
	 */
	public Move(int player, char[][] board, int row1, int col1, int row2, int col2) {
		this.board = board;
		currentPlayer = player;
		currRow = row1;
		currCol = col1;
		newRow = row2;
		newCol = col2;
		if(currentPlayer == 0) currentSymbol = 'o';
		else currentSymbol = 'x';
		
	}
	

	/** Determines if move is a jump.
	 * @param move current move object.
	 * @return boolean t/f if move is a jump or not.
	 */
	public boolean jump(Move move) {

		//If intermediate space contains an opponent space, move is a jump.
		if(Math.abs(move.newRow-move.currRow)== 2 && Math.abs(move.newCol - move.currCol)== 2)  {
			if((currentPlayer == 1 &&
					this.board[Math.abs((move.currRow+move.newRow)/2)][Math.abs((move.currCol + move.newCol)/2)] == 'o') || 
					currentPlayer == 0 && this.board[Math.abs((move.currRow+move.newRow)/2)][Math.abs((move.currCol+ move.newCol)/2)] == 'x')
				return true;
		}
	
			return false;	
	}
	
	/**
	 * Implementation to compare two moves.
	 * @param move move being compared
	 * @return true if the moves are equal.
	 */
	public boolean equals(Move move) {
		if(this.currRow == move.currRow && this.currCol == move.currCol && this.newRow == move.newRow && this.newCol == move.newCol && this.currentPlayer == move.currentPlayer )
			return true;
		else return false;
	}
} //end Move Class





/**
 **************** CHECKERSCOMPUTERPLAYER CLASS*********************
 * @author Melonie Miller
 *
 */
public class CheckersComputerPlayer {

	/** isComputerPlayer is true for CheckersComputerPlayer objects. Allows
	 * methods in CheckersLogic to check that CheckersComputerPlayer object
	 * is a computer player for testing. */
	public boolean isComputerPlayer = true;
	public int playerNum;
	
	/** Stores the value of the computer player's symbol (x or o)*/
	public final char symbol;
	
	
	/**
	 * Initializes a computer player using the type of player (PLAYER_X
	 * or PLAYER_O) as a parameter. Sets the respective symbols and 
	 * assigns the respective player type to currentPlayer.
	 * @param player the current player.
	 */
	public CheckersComputerPlayer(int player) {

		if(player == PLAYER_X) {
			computerPlayer = 1;
			playerNum = PLAYER_X;
			symbol = SYMBOL_X;
		}
		else if(player == PLAYER_O) {
			computerPlayer = 0;
			playerNum = PLAYER_O;
			symbol = SYMBOL_O;
		}
		else {
			symbol = ' ';
			computerPlayer = -1;//player does not exist
			player = -1; 		//player does not exist
			
		}
	}
	
	/**
	 * Computer player version of checkMoves, virtually identical to 
	 * above check moves method.
	 * @param player computer's player type
	 * @param oldRow current row
	 * @param oldCol current col
	 * @param newRow new row
	 * @param newCol new col
	 * @return true if move is valid
	 */
	public boolean checkMoves(int player, int oldRow, int oldCol, int newRow, int newCol) {
		
		//Check move is diagonal
		if(oldRow == newRow || oldCol == newCol) {
			return false;
		}
		
		int distRow = Math.abs(oldRow - newRow);
		int distCol = Math.abs(oldCol - newCol);
		
		//Check move is not more than 2 spaces.
		if(distRow > 2 || distCol > 2) {
			return false;
		}
		
		
		
		//Check move is not backwards
			if((player == PLAYER_O && oldRow < newRow &&
					(board.board[Math.abs((newRow+oldRow)/2)][Math.abs((newCol + oldCol)/2)]) != SYMBOL_X) ||
					(player == PLAYER_X && newRow < oldRow && (board.board[Math.abs((newRow+oldRow)/2)][Math.abs((newCol + oldCol)/2)]) != SYMBOL_O)) 
			return false;
		
		
			//Check O jump moves
		if(player == PLAYER_O) {
			if(distRow > 1) {
				char symbol = board.board[Math.abs((newRow + oldRow)/2)][Math.abs((newCol+oldCol)/2)];
				if(symbol != SYMBOL_X) 
					return false;
				else {
					return true;
				}
			}
		}
		
		if(playerNum == PLAYER_X) {
			if(distRow > 1) {
				char symbol = board.board[Math.abs((newRow + oldRow)/2)][Math.abs((newCol+oldCol)/2)];
				if(symbol != SYMBOL_O) return false;
				else {
					return true;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks if the checker is on a valid position of the board.
	 * @param row position's row for checking
	 * @param col position's column for checking
	 * @return true if the checker is in a valid position. (On the board)
	 */
	public boolean formatChecker(int row, int col) {
		if(row < 0 || col < 0 || row > 7 || col > 7) return false;
		return true;
	}
	
	/**
	 * Local checker for validating moves in CheckersComputerPlayer. Moves already
	 * in converted format when they enter this method.
	 * @param player current player
	 * @param oldRow row being moved from (converted)
	 * @param oldCol col being moved from (converted)
	 * @param newRow row being moved to (converted)
	 * @param newCol col being moved to (converted)
	 * @return true if move is valid.
	 */
	public boolean validMove(int player, int oldRow, int oldCol, int newRow, int newCol) {
		
		if(formatChecker(oldRow, oldCol) && formatChecker(newRow, newCol))
			if(positionChecker(oldRow, oldCol, newRow, newCol))
				if(moveSelection(player, oldRow, oldCol, newRow, newCol))
						if(!board.isOccupied(newRow, newCol) && Math.abs(oldRow-newRow) <=2 && Math.abs(oldCol-newCol) <=2)
							if(checkMoves(player, oldRow, oldCol, newRow, newCol))
								return true;
		
		return false;
		

	}
	
	
	
	/**
	 * Chooses and moves computer pieces based on difficulty level. For level 1, a random move will always be selected from the first 
	 * 3/4ths of the vector of moves. For level 2, a move will be selected from the first half of the vector-- however, if a jump is 
	 * present, the jump will be executed.
	 * For level 3, a move will be selected from the first 1/3rd of the vector of moves, but a jump will always be executed first. 
	 * @param player current player
	 */
	public void computerMove(int player) {
		Vector<Move> pMoves = getLegalMoves(player);
		if(pMoves.size() == 0) gameOver("Computer out of moves. Player wins!");
		else{
			Random randInt = new Random();

		int rand1 = randInt.nextInt(Math.abs(pMoves.size()));
		int rand2 = randInt.nextInt(Math.abs(pMoves.size()));
		int rand3 = randInt.nextInt(Math.abs(pMoves.size()));

		if(rand1 !=0) rand1 = rand1*3/4;
		if(rand2 != 0) rand2 = rand2/2;
		if(rand3 !=0) rand3 = rand3/3;
		
		if(difficultyLevel == 1) {
			
			//Level 1 difficulty will not automatically take jumps, will just pick from first 3/4 of moves.
			if(validMove(player, pMoves.get(rand1).currRow, pMoves.get(rand1).currCol, pMoves.get(rand1).newRow, pMoves.get(rand1).newCol)) 
				helperMove(player, pMoves.get(rand1).currRow, pMoves.get(rand1).currCol, pMoves.get(rand1).newRow, pMoves.get(rand1).newCol);
		}
		
		if(difficultyLevel == 2) {
			if(moveIsJump(player, pMoves.get(0).currRow, pMoves.get(0).currCol, pMoves.get(0).newRow, pMoves.get(0).newCol))
				takePiece(player, pMoves.get(0).currRow, pMoves.get(0).currCol, pMoves.get(0).newRow, pMoves.get(0).newCol);
			else 
				if(validMove(player, pMoves.get(rand2).currRow, pMoves.get(rand2).currCol, pMoves.get(rand2).newRow, pMoves.get(rand2).newCol))
					helperMove(player, pMoves.get(rand2).currRow, pMoves.get(rand2).currCol, pMoves.get(rand2).newRow, pMoves.get(rand2).newCol);
		}
		
		if(difficultyLevel == 3) {
			if(moveIsJump(player, pMoves.get(0).currRow, pMoves.get(0).currCol, pMoves.get(0).newRow, pMoves.get(0).newCol))
				takePiece(player, pMoves.get(0).currRow, pMoves.get(0).currCol, pMoves.get(0).newRow, pMoves.get(0).newCol);
			else 
				if(validMove(player, (char)pMoves.get(rand3).currRow, pMoves.get(rand3).currCol, pMoves.get(rand3).newRow, pMoves.get(rand3).newCol))
					helperMove(player, pMoves.get(rand3).currRow, pMoves.get(rand3).currCol, pMoves.get(rand3).newRow, pMoves.get(rand3).newCol);
		}
		
		switchPlayer();
		}
		
		
	}
	

	
	/**
	 * Helper method to allow computer player to take a piece
	 * @param player computer player's int value
	 * @param oldRow row being moved from
	 * @param oldCol col being moved from
	 * @param newRow row being moved to
	 * @param newCol col being moved to 
	 */
	public void takePiece(int player, int oldRow, int oldCol, int newRow, int newCol) {
		helperMove(player, oldRow, oldCol, newRow, newCol);
	}

} //end CheckersComputerPlayer class

/**
 * Returns the legal moves available for a player.
 * If any jumps exist, only jumps will be returned in vector, as jump moves
 * should be made first. Looks at each space on board and determines if the move
 * is valid for the specified player.
 * @param player
 * @return Vector of Moves vector of moves available to player.
 */
public Vector<Move> getLegalMoves(int player){
	
	Vector<Move> pMovesTotal = new Vector<Move>();
	
	//Iterate through each position on the board for player's pieces
	for(int row = 0; row < 8; row++) {
		for(int col = 0; col < 8; col++) {
			Vector<Move> pMoves = getLegalJumps(player, row, col);
			
			//Return all moves for piece at position (row,col)
			pMoves = getLegalMoves(player, row, col);
		
			//Add all members of pMoves to pMovesTotal
			for(int i = 0; i < pMoves.size(); i++) {
				pMovesTotal.add(pMoves.get(i));
			}
		}
	}
	return pMovesTotal;
	
}

/**
 * getLegalMoves(player, row, col) verifies if the given position (row,col)
 * has legal moves for the player. If so, the moves are stored in the Move vector
 * and returned to the previous getLegalMoves method.
 *** PARAMS MUST BE IN CONVERTED VERSIONS!
 * @param player current player
 * @param row current piece position (row) being checked
 * @param col current piece position (col) being checked 
 * @return Vector of Moves - a list of possible moves.
 */
public Vector<Move> getLegalMoves(int player, int row, int col){
	Vector<Move> pMoves = new Vector<Move>();
	
	//For player o, checks if piece is an o piece, then checks
	//If the there are legal moves for that piece.
	//Inputs char values of row,col into validMove.
	if(player == PLAYER_O) {
		if(board.board[row][col] == SYMBOL_O) {
			if(validMove(player, row,col,row-1, col-1))
				pMoves.add(new Move(player, board.board, row, col, row-1, col-1));
			if(validMove(player, row, col, row-1,row+1))
				pMoves.add(new Move(player, board.board, row, col, row-1, col+1));
		}
	
	}
	
	//For player x, checks if the piece is an x piece, then checks
	//If there are legal moves for that piece.
	else if (player == PLAYER_X) {
		if(board.board[row][col] == SYMBOL_X) {
			if(validMove(player, row, col, row+1,col+1))
				pMoves.add(new Move(player, board.board, row, col, row+1, col+1));
			if(validMove(player, row, col, row+1, col-1))
				pMoves.add(new Move(player, board.board, row, col, row+1, col-1));

		}
	}
	return pMoves;
}

/**
 * Adds legal jumps to a vector of moves. Verifies that a jump is legal by checking that it is the correct symbol,
 * the move is valid, and the jump is valid before adding to the list. 
 * **Params must be in converted versions!!!!***
 * @param player current player
 * @param row current piece position (row)
 * @param col current piece position (column)
 * @return Vector of Moves pMoves, all possible jumps. 
 */
public Vector<Move> getLegalJumps(int player, int row, int col){
	Vector<Move> pMoves = new Vector<Move>();
	if(player == PLAYER_O) {
		if(board.board[row][col] == SYMBOL_O) {
			if(validMove(player, row, col, row+2, col+2))
				if(moveIsJump(player, row, col, row+2, col+2))
					pMoves.addElement(new Move(player, board.board, row, col, row+2, col+2));
			if(validMove(player, row, col, row-2, col-2))
				if(moveIsJump(player, row, col, row-2,col-2))
					pMoves.addElement(new Move(player, board.board, row, col, row-2, col-2));
			if(validMove(player, row, col, row+2, col-2))
				if(moveIsJump(player, row, col, row+2, col-2))
					pMoves.addElement(new Move(player, board.board, row, col, row+2, col-2));
			if(validMove(player, row, col, row-2, col+2))
				if(moveIsJump(player, row, col, row-2, col+2))
					pMoves.addElement(new Move(player, board.board, row, col, row-2, col+2));
		}
	}
	
	if(player == PLAYER_X) {
		if(board.board[row][col] == SYMBOL_X) {
			if(validMove(player, row, col, row+2, col+2))
				if(moveIsJump(player, row, col, row+2, col+2))
					pMoves.add(new Move(player, board.board, row, col, row+2, col+2));
			if(validMove(player, row, col, row-2, col-2))
				if(moveIsJump(player, row, col, row-2,col-2))
					pMoves.add(new Move(player, board.board, row, col, row-2, col-2));
			if(validMove(player, row, col, row+2, col-2))
				if(moveIsJump(player, row, col, row+2, col-2))
					pMoves.add(new Move(player, board.board, row, col, row+2, col-2));
			if(validMove(player, row, col, row-2, col+2))
				if(moveIsJump(player, row, col, row-2, col+2))
						pMoves.add(new Move(player, board.board, row, col, row-2, col+2));
		}
	}
	
	//player X jumps implementation			
	
	return pMoves;
}



} //end CheckersLogic class