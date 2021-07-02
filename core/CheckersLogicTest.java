package core;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.CheckersLogic.CheckersGame;
import core.CheckersLogic.Move;

/**
 * Test cases for CheckersLogic.java class, which contains CheckersComputerPlayer.java
 * Tests all methods and functionality of the class, ensuring accuracy.
 */
	class CheckersLogicTest {

		//Checkerslogic objects being tested
		/**Test default constructor */
		CheckersLogic cLogic1;
		/**Test constructor for CheckersLogic (computer player difficulty1)*/
		CheckersLogic cLogic1a;
		/**Test constructor for CheckersLogic (computer player difficulty2)*/
		CheckersLogic cLogic2;
		/**Test constructor for CheckersLogic (computer player difficulty3)*/
		CheckersLogic cLogic3;
		
		@BeforeEach
		/**
		 * Setup for testing. Initializes CheckersLogic objects.
		 */
		void setup() {
			cLogic1 = new CheckersLogic();
			cLogic2 = new CheckersLogic(2); //test PC at diff level 2
			cLogic1a = new CheckersLogic(1); //test PC at diff level 1
			cLogic3 = new CheckersLogic(3); //test PC player at diff level 3
			
		}
		
		@Test
		@DisplayName("Testing getPlayer...")
		/**
		 * Tests getPlayer() method in CheckersLogic.
		 */
		void testGetPlayer() {
			int testCase1 = cLogic1.getPlayer();
			assertEquals(1,testCase1, "getPlayer failed at testCase1.");
			
			cLogic1.switchPlayer();
			int testCase2 = cLogic1.getPlayer();
			assertEquals(0, testCase2, "getPlayer failed at testCase2.");
		}
		
		@Test
		@DisplayName("Testing startNewGame()...")
		/**
		 * Tests the CheckersLogic method startNewGame().
		 */
		void testStartNewGame() {
			cLogic1.startNewGame();
			assertEquals(true, cLogic1.gameInProgress, "startNewGame failed at testCase1.");
		}

		
		@Test
		@DisplayName("Testing helperMove(player, Move)...")
		/**
		 * This test only takes the standard valid case of helperMove, since helperMove relies 
		 * heavily on all other methods. Branches will just not execute if the move is invalid, rendering them useless.
		 */
		void testHelperMove1() {
			
			cLogic1.helperMove(0, "3b-4c");
			char testCase1 = cLogic1.board.board[4][2];
			assertEquals('o', testCase1, "helperMove failed at testCase1.");
		}
		
		@Test
		@DisplayName("Testing helperMove(player, r1, c1, r2, c2)...")
		/** 
		 * Tests helperMove(player, row1, col1, row2, col2) by moving a piece,
		 * then asserting that the piece has actually been moved to the new location..
		 */
		void testHelperMove2() {
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.resetBoard();
			cLogic1.helperMove(1, 2, 0, 3, 1);
			assertEquals('x', cLogic1.board.board[3][1], "helperMove2 failed at testCase1.");
			assertEquals('_', cLogic1.board.board[2][0], "helperMove failed at testCase1.");
		}
		
		@Test
		@DisplayName("Testing gameInProgress()...")
		/**
		 * Tests gameInProgress getter method for gameInProgress field.
		 */
		void testGameInProgress() {
			assertEquals(true, cLogic1.gameInProgress(), "gameInProgress failed at testingCase1.");
		}
		
		@Test
		@DisplayName("Testing validMove1...")
		/**tests CheckersLoigc method validMove(player, oldRow, oldCol, newRow, newCol)
		 * tests valid cases and edge cases where moves are improperly formatted or 
		 * move to invalid locations to ensure that moves are being checked properly
		 * before being sent to helperMove() method. */
		void testValidMove1() {
			//Player O testing:
			boolean testCase1 = cLogic1.validMove(0, "3b-4c"); //valid move
			assertEquals(true, testCase1, "validMove failed at testCase1.");
			
			boolean testCase2 = cLogic1.validMove(0, "3b-2a");
			assertEquals(false, testCase2, "validMove failed at testCase2."); //invalid move: backwards
			
			boolean testCase3 = cLogic1.validMove(0, "33-4b");
			assertEquals(false, testCase3,"validMove faield at testCase3."); //invalid move: wrong format
			
			boolean testCase4 = cLogic1.validMove(0, "3b-4b"); 
			assertEquals(false, testCase4,"validMove failed at testCase4"); //invalid move: vertical
			
			boolean testCase5 = cLogic1.validMove(0, "3b-3a");
			assertEquals(false, testCase5,"validMove failed at testCase5."); //invalid move: horizontal
			
			
			//Player X testing:
			boolean testCase6 = cLogic1.validMove(1, "6a-5b");
			assertEquals(true, testCase6,"validMOve failed at testCase6."); //valid move
			
			boolean testCase7 = cLogic1.validMove(1, "6a-6b");
			assertEquals(false, testCase7,"validMove failed at testCase7."); //invalid move: horizontal
			
			boolean testCase8 = cLogic1.validMove(1, "6a-7b");
			assertEquals(false, testCase8, "validMove failed at testCase8."); //invalid move: backwards
			
			boolean testCase9 = cLogic1.validMove(1, "3b-4c");
			assertEquals(false, testCase9, "validMove failed at testCase9."); //invalid move: wrong piece selected
			
			boolean testCase10 = cLogic1.validMove(1,  "33-3b");
			assertEquals(false, testCase10,"validMove failed at testCase10."); //invalid move: improper format
			
		}
		
		@Test
		@DisplayName("Testing validMove(player, Move)...")
		/**
		 * tests validMove(player, row1, col1, row2, col2)
		 * Checks valid cases and invalid cases where
		 * moves are backwards, sideways/vertical/moved to wrong location,
		 * and opponent pieces are selected. Asserts that a valid move returns
		 * true and an invalid move returns false.
		 */
		void testValidMove2() {
			//Player O
			boolean testCase1 = cLogic1.validMove(0,5,1,4,2); //valid move
			assertEquals(true, testCase1, "validMove failed at testCase1.");
			
			//invalid: horizontal
			boolean testCase2 = cLogic1.validMove(0, 5, 2, 5, 3);
			assertEquals(false, testCase2,"validMove failed at testCase2.");
			
			//invalid: backwards
			boolean testCase3 = cLogic1.validMove(0, 5, 2, 6, 3);
			assertEquals(false, testCase3,"validMove failed at testCase3.");
			
			//invalid: 
			boolean testCase4 = cLogic1.validMove(0, 2, 0, 3, 1);
			assertEquals(false, testCase4,"validMove failed at testCase4.");
			
			//Player X 
			boolean testCase5 = cLogic1.validMove(1, 2, 2, 3, 3);
			assertEquals(true, testCase5, "validMove failed at testCase5.");
			
			boolean testCase6 = cLogic1.validMove(1, 1, 1, 0, 0);
			assertEquals(false, testCase6,"validMove failed at testCase6.");
			
			boolean testCase7 = cLogic1.validMove(1, 1, 1, 1, 0);
			assertEquals(false, testCase7, "valdidMove failed at testCase7");
			
			boolean testCase8 = cLogic1.validMove(1,  7, 1, 6, 2);
			assertEquals(false, testCase8, "validMove failed at testCase8.");
			
			
		}
		

		
		@Test
		@DisplayName("Testing PositionChecker...")
		/**
		 * Tests the method positionChecker. contains one valid case and two invalid cases.
		 */
		void testPositionChecker() {

			boolean testCase1 = cLogic1.positionChecker(5, 1, 4, 2);
			assertEquals(true, testCase1, "positionChecker failed at valid case: (5,1)(4,3)");
			
			boolean testCase2 = cLogic1.positionChecker(5, 1, 4, 1);
			assertEquals(false, testCase2, "positionChecker failed at invalid case: (5,1), (4,1)");
			
			boolean testCase3 = cLogic1.positionChecker(5, 1, 5, 0);
			assertEquals(false, testCase3, "PositionChecker failed at invalid case: (5,1)(5,0)");
			
			
		}
		
		@Test
		@DisplayName("Testing formatChecker...")
		/**
		 * Format checker test tests various string inputs for moves.
		 * Moves are tested for a valid string, a string with the wrong format of 
		 * characters, the wrong separating character, out of bounds numbers, 
		 * and out of bounds characters to ensure only the correct input format
		 * gets converted to array indices for the checkerboard..
		 */
		void testFormatChecker() {
			boolean testCase1 = cLogic1.formatChecker("3b-4c");
			assertEquals(true, testCase1, "formatChecker failed at 3b-4c");
			
			boolean testCase2 = cLogic1.formatChecker("33-bb");
			assertEquals(false, testCase2, "formatChecker failed at 33-bb");
			
			boolean testCase3= cLogic1.formatChecker("3b/4c");
			assertEquals(false, testCase3, "formatChecker failed at 3b/4c");
			
			boolean testCase4 = cLogic1.formatChecker("3bb-4c");
			System.out.println("Testing formatChecker: invalid format. Expected result: true");
			assertEquals(false, testCase4, "formatChecker failed at 3bb-4c");
			
			boolean testCase5 = cLogic1.formatChecker("9b-0c");
			assertEquals(false, testCase5, "formatChecker failed at 9b-0c");
		
			boolean testCase6 = cLogic1.formatChecker("4z-5p");
			assertEquals(false, testCase6, "formatChecker failed at testCase6.");
		
			boolean testCase7 = cLogic1.formatChecker("3b+4c");
			assertEquals(false, testCase7, "formatChecker failed at testCase7.");
			
			boolean testCase8 = cLogic1.formatChecker("9a-9b");
			assertEquals(false, testCase8, "formatChecker failed at testCase8.");
			
			boolean testCase9 = cLogic1.formatChecker("0b-4c");
			assertEquals(false, testCase9, "formatChecker failed at testCase9.");
			
			boolean testCase10 = cLogic1.formatChecker("4b-0c");
			assertEquals(false, testCase10, "formatChecker failed at testCase10.");
			
			boolean testCase11 = cLogic1.formatChecker("4O-4O");
			assertEquals(false, testCase11, "formatChecker failed at testCase11.");
		}
		
		
		@Test
		@DisplayName("Testing moveSelection...")
		/**
		 * Method to test moveSelection. Tests a valid case, cases of invalid 
		 * jumps, grabbing the wrong player's piece, and selecting invalid 
		 * indices (either non-moveable squares or negative indices.) Uses
		 * the twoPieceTest stub method to extend coverage to edge cases.
		 */
		void testMoveSelection() {
			boolean testCase1 = cLogic1.moveSelection(0, 5, 1, 4, 3);
			assertEquals(true, testCase1, "moveSelection failed at testCase1.");
			
			boolean testCase2 = cLogic1.moveSelection(0, 5, 1, 5, 3);
			assertEquals(false, testCase2, "moveSelection failed at testCase2.");
			
			boolean testCase3 = cLogic1.moveSelection(0, 5, 1, 2, 0);
			assertEquals(false, testCase3 , "moveSelection failed at testCase3.");
			
			boolean testCase4 = cLogic1.moveSelection(0, 5, 1, 1, -1);
			assertEquals(false, testCase4, "moveSelection failed at testCase4.");
			
			boolean testCase5 = cLogic1.moveSelection(1, 2, 0 , 3, 1);
			assertEquals(true, testCase5, "moveSelection failed at testCase5.");
			
			boolean testCase6 = cLogic1.moveSelection(1, 2, 0, 2, 2);
			assertEquals(false, testCase6,  "moveSelection failed at testCase6.");
			
			boolean testCase7 = cLogic1.moveSelection(1, 2, 0, 5, 1);
			assertEquals(false, testCase7, "moveSelection failed at testCase7.");
			
			boolean testCase8 = cLogic1.moveSelection(1, 2, 0, -1, -1);
			assertEquals(false, testCase8, "moveSelection failed at testCase8.");
			
			boolean testCase9 = cLogic1.moveSelection(1, 2, 0, -1, 1);
			assertEquals(false, testCase9, "moveSelection failed at testCase9.");
			
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.twoPieceTest(); 
			boolean testCase10 = cLogic1.moveSelection(0, 1, 0, 1, 1);
			assertEquals(false, testCase10, "moveSelection failed at testCase10.");
			boolean testCase11 = cLogic1.moveSelection(0, 6, 6, 7, 7);
			assertEquals(false, testCase11, "moveSelection failed at testCase11.");
			boolean testCase12 = cLogic1.moveSelection(1, 5, 5, 4, 4);
			assertEquals(false, testCase12, "moveSelection failed at testCase12.");
		}
		
		@Test
		@DisplayName("Testing checkMoves...")
		/**
		* Tests the boolean method checkMoves. Checks a valid move
		* as well as invalid moves such as sideways and vertical moves,
		* backwards moves for a player (depending on player number).
		* Creates a stub for edge cases to check that a jump move
		* is assumed valid by the method.
		*
		*/
		void testCheckMoves() {
			boolean testCase1 = cLogic1.checkMoves(0,  5, 1, 4, 2);
			assertEquals(true, testCase1, "checkMoves failed at testCase1.");
			
			boolean testCase2 = cLogic1.checkMoves(0, 5, 1, 5, 0);
			assertEquals(false, testCase2, "checkMoves failed at testCase2.");
			
			boolean testCase3 = cLogic1.checkMoves(0, 5, 1, 4, 1);
			assertEquals(false, testCase3, "checkMoves failed at testCase3.");
			
			boolean testCase4 = cLogic1.checkMoves(1, 2, 0, 3, 1);
			assertEquals(true, testCase4, "checkMoves failed at testCase4.");
			
			boolean testCase5 = cLogic1.checkMoves(1, 2, 0, 2, 1);
			assertEquals(false, testCase5, "checkMoves failed at testCase5.");
			
			boolean testCase6 = cLogic1.checkMoves(1, 2, 0, 3, 0);
			assertEquals(false, testCase6, "checkMoves failed at testCase6.");
		
		
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.jumpTest2();
			boolean testCase7 = cLogic1.checkMoves(1, 2,2,4,0);
			assertEquals(true, testCase7, "checkMoves failed at testCase7.");
		
			stub.jumpTest2();
			boolean testCase8 = cLogic1.checkMoves(0,3,5,1,3);
			assertEquals(true, testCase8, "checkMoves failed at testCase8.");
		}

		
		@Test
		/** Tests takePiece method for an interactive player. 
		 * Creates a stub for a twoPieceTest in order to test
		 * specific cases of taking a piece for each player. */
		void testTakePiece() {
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.twoPieceTest();
			cLogic1.numPiecesO = 1;
			cLogic1.numPiecesX = 1;
			cLogic1.takePiece(1, 6, 6, 4, 4);
			assertEquals('x', cLogic1.board.board[4][4], "takePiece failed at testCase1.");
			
			stub.twoPieceTest();
			cLogic1.gameInProgress = true; //set back from last test case.
			cLogic1.numPiecesO = 1;
			cLogic1.numPiecesX = 1; 
			cLogic1.takePiece(0, 5, 5, 7, 7);
			assertEquals('o', cLogic1.board.board[7][7], "takePiece failed at testCase2.");
			
			
		}
		
		
		
		@Test
		@DisplayName("Testing convertToInt(String)...")
		/** convertToInt(String) tests conversion from string to Int, used in converting user
		 * input to correct array indices. Tests every valid case and an invalid case of 
		 * a number being passed instead of a letter, verifying that a negative index is passed
		 * to assert an invalid entry.*/
		void testConvertToInt1() {
			int testCase1 = cLogic1.convertToInt("a");
			assertEquals(0, testCase1, "convertToInt1 failed at testCase1.");
			int testCase2 = cLogic1.convertToInt("b");
			assertEquals(1, testCase2,"convertToInt1 failed at testCase2.");
			int testCase3 = cLogic1.convertToInt("c");
			assertEquals(2, testCase3,"convertToInt1 failed at testCase3.");
			int testCase4 = cLogic1.convertToInt("d");
			assertEquals(3, testCase4,"convertToInt1 failed at testCase4.");
			int testCase5 = cLogic1.convertToInt("e");
			assertEquals(4, testCase5, "convertToInt1 failed at testCase5.");
			int testCase6 = cLogic1.convertToInt("f");
			assertEquals(5, testCase6, "convertToInt1 failed at testCase6.");
			int testCase7 = cLogic1.convertToInt("g");
			assertEquals(6, testCase7, "convertToInt1 failed at testCase7.");
			int testCase8 = cLogic1.convertToInt("h");
			assertEquals(7, testCase8, "convertToInt1 failed at testCase8.");
			int testCase9 = cLogic1.convertToInt("z");
			assertEquals(-1, testCase9, "convertToInt1 failed at testCase9.");
			int testCase10 = cLogic1.convertToInt("1");
			assertEquals(-1, testCase10,"convertToInt1 failed at testCase10.");		
		}
		
		@Test
		@DisplayName("Testing ConvertToInt(int)...")
		/** Tests conversion from an integer to integer for correct array 
		 * indexing conversion. Tests all valid cases and edge cases (input <1 or >8)
		 * Ensures all conversions made in the program output the expected value. */
		void testConvertToInt2() {
			int testCase1 = cLogic1.convertToInt(1);
			assertEquals(7, testCase1, "convertToInt failed at testCase1.");
			int testCase2 = cLogic1.convertToInt(2);
			assertEquals(6, testCase2, "convertToInt failed at testCase2.");
			int testCase3 = cLogic1.convertToInt(3);
			assertEquals(5, testCase3, "convertToInt failed at testCase3.");
			int testCase4 = cLogic1.convertToInt(4);
			assertEquals(4, testCase4, "convertToInt failed at testCase4.");
			int testCase5 = cLogic1.convertToInt(5);
			assertEquals(3, testCase5, "convertToInt failed at testCase5.");
			int testCase6 = cLogic1.convertToInt(6);
			assertEquals(2, testCase6, "convertToInt failed at testCase6.");
			int testCase7 = cLogic1.convertToInt(7);
			assertEquals(1, testCase7, "convertToInt failed at testCase7.");
			int testCase8 = cLogic1.convertToInt(8);
			assertEquals(0, testCase8, "convertToInt failed at testCase8.");
			
			int testCase9 = cLogic1.convertToInt(100);
			assertEquals( -1, testCase9, "convertToInt failed at testCase9.");
			int testCase10 = cLogic1.convertToInt(-50);
			assertEquals(-1, testCase10,"convertToInt failed at testCase10.");
		}
		
		@Test
		@DisplayName("Testing moveIsJump...")
		/** Tests method moveIsJump (boolean). Creates a stub to test a 
		 * jumpTest configuration. Contains a valid jump test and multiple invalid 
		 * jump tests to test that edge cases output the expected value (False)
		 * when the incorrect move configuration is passed.
		 */
		void testMoveIsJump() {
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.setJumpTest();
			boolean testCase1 = cLogic1.moveIsJump(0, 3, 1, 5, 3);
			assertEquals(true, testCase1, "moveIsJump failed at testCase1.");
			boolean testCase2 = cLogic1.moveIsJump(0, 6, 2, 4, 4);
			assertEquals(false, testCase2,"moveIsJump failed at testCase2.");
			boolean testCase3 = cLogic1.moveIsJump(0, 5, 3, 4, 4);
			assertEquals(false, testCase3,"moveIsJump failed at testCase3.");
			boolean testCase4 = cLogic1.moveIsJump(0, 7, 1, 4, 4);
			assertEquals(false, testCase4,"moveIsJump failed at testCase4.");
			boolean testCase4b = cLogic1.moveIsJump(0, 7, 7, 7, 7);
			assertEquals(false, testCase4b,"moveIsJump failed at testCase4b.");
			
			boolean testCase5 = cLogic1.moveIsJump(1, 2, 2, 4, 0);
			assertEquals(true, testCase5, "moveIsJump failed at testCase5.");
			boolean testCase6 = cLogic1.moveIsJump(1, 1, 3, 3, 1);
			assertEquals(false, testCase6, "moveIsJump failed at testCase6.");
			boolean testCase7 = cLogic1.moveIsJump(1,2,2,3,1);
			assertEquals(false, testCase7, "moveIsJump failed at testCase7.");
			boolean testCase8 = cLogic1.moveIsJump(1,0,4,3,1);
			assertEquals(false, testCase8, "moveIsJump failed at testCase8.");
		
			//all 4 out of bounds
			boolean testCase9 = cLogic1.moveIsJump(1, 9, 9, 9, 9);
			assertEquals(false, testCase9, "moveIsJump failed at testCase9.");
			
			//only rows out of bounds
			boolean testCase10 = cLogic1.moveIsJump(0, 9, 0, 9, 0);
			assertEquals(false, testCase10, "moveIsJump failed at testCase10.");
		
			//only cols out of bounds
			boolean testCase11 = cLogic1.moveIsJump(0, 0, 9, 0, 9);
			assertEquals(false, testCase11, "moveIsJump failed at testCase11.");
		
			//negative rows/cols
			boolean testCase12 = cLogic1.moveIsJump(0, -1, -1, -2, -2);
			assertEquals(false, testCase12, "moveIsJump failed at testCase12.");
		
			boolean testCase13 = cLogic1.moveIsJump(1, 88, -5, 99, -9);
			assertEquals(false, testCase13, "moveIsJump failed at testCase13.");
		
		}
		
		@Test
		@DisplayName("Testing gameOver...")
		/** 
		 * Test that game has been ended by checking that
		 * gameInprogress is false when gameOver has been called. 
		 * params String gameOvermsg */
		void testGameOver() {
			cLogic1.gameOver("Game over!");
			boolean testCase1 = cLogic1.gameInProgress;
			assertEquals(false, testCase1, "gameOver failed at testCase1.");
			
			cLogic1.gameInProgress = false;
			testCase1 = cLogic1.gameInProgress;
			cLogic1.gameOver("This shouldn't be able to happen but we're testing it anyways!");
			assertEquals(false, testCase1, "gameOver failed at testCase1.");
			
			//reset value so the rest of testing doesn't bug out.
			cLogic1.gameInProgress = true;
		}
		
		@Test
		@DisplayName("Testing resign...")
		/**
		 * Tests the resign method by calling it and then checking that gameInProgress
		 * has been set to false by the method.
		 */
		void testResign() {
			cLogic1.resign(1);
			assertEquals(false, cLogic1.gameInProgress, "resign failed at testCase1.");
			//Restart game to make game in progress true again
			setup();
			cLogic1.resign(0);
			assertEquals(false, cLogic1.gameInProgress, "resign failed at testCase2.");
		}
		
		/****************** TESTING CHECKERBOARD CLASS*************/
		
		@Test
		@DisplayName("Testing isOccupied...")
		/**
		 * Tests that a position on the board is occupied by testing both 
		 * occupied and unoccupied positions, as well as edge cases where values
		 * are outside of valid ranges.
		 */
		void testIsOccupied() {
			boolean testCase1 = cLogic1.board.isOccupied(0, 1);
			assertEquals(false, testCase1, "isOccupied failed at testCase1.");
			boolean testCase2 = cLogic1.board.isOccupied(0, 0);
			assertEquals(true, testCase2, "isOccupied failed at testCase2.");
			
			boolean testCase3 = cLogic1.board.isOccupied(100,100);
			assertEquals( false, testCase3, "isOccupied failed at testCase3.");
			
			boolean testCase4 = cLogic1.board.isOccupied(-100, -100);
			assertEquals(false, testCase4, "isOccupied failed at testCase1.");
		}
		
		@Test
		@DisplayName("Testing setupGame...")
		/**Tests the setup of a game by checking every row and column index of
		 * the board and asserting the correct valued piece is at that location after setup. */
		void testSetupGame() {		
			cLogic1.board.setupGame(cLogic1.board.board);
			for(int row = 0; row < 8; row++) {
				for(int col = 0; col < 8; col++) {
					if(row%2 == col%2) {
						if(row < 3) assertEquals('x', cLogic1.board.board[row][col], "setupGame failed at testCase1.");
						else if(row > 4) assertEquals('o', cLogic1.board.board[row][col], "setupGame failed at testCase2.");
						else assertEquals(cLogic1.board.board[row][col], '_', "setupGame failed at testCase3.");
					}
					else {
						assertEquals('_', cLogic1.board.board[row][col], "setupGame failed at testCase4.");
					}
				
				}
			}
		}
		
		@Test
		@DisplayName("Testing Move class Jump method...")
		/** Tests the boolean jump() method inside of Move class using a jumpTest stub configuration.
		 *  tests when distances are +-2 at different configurations, as well as invalid configurations/Moves
		 *   that are not jumps.*/
		void testJump() {
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.setJumpTest();
			//check where dist row == -2  and dist col == 2
			CheckersLogic.Move validJumpO = cLogic1.new Move(0, cLogic1.board.board, 5, 1, 3, 3);
			//set up a valid move to put on the board to make both dist +2
			CheckersLogic.Move validJumpX1 = cLogic1.new Move(1, cLogic1.board.board, 4, 2, 2, 0);
			//check where dist row == 2 dist col == -2
			CheckersLogic.Move validJumpX2 = cLogic1.new Move(1, cLogic1.board.board, 2, 2, 4, 0);
			
			stub.setJumpTest();
			//set up a valid move to make both distances -2. Then execute some invalid jumps.
			CheckersLogic.Move validJumpX3 = cLogic1.new Move(1, cLogic1.board.board, 2, 4, 4, 6);
			CheckersLogic.Move invalidJumpO2 = cLogic1.new Move(0, cLogic1.board.board, 7, 1, 4, 4);
			CheckersLogic.Move invalidJumpX = cLogic1.new Move(1, cLogic1.board.board, 2, 6, 4, 4);
			CheckersLogic.Move invalidJumpX2 = cLogic1.new Move(1, cLogic1.board.board, 1, 3, 3, 1);
		
		boolean testCase1 = validJumpO.jump(validJumpO);
		assertEquals(true, testCase1, "jump failed at testCase1.");
		boolean testCase2 = validJumpX1.jump(validJumpX1);
		assertEquals(true, testCase2, "jump failed at testCase2.");
		boolean testCase2a = validJumpX2.jump(validJumpX2);
		assertEquals(true, testCase2a, "jump failed at testCase2a.");
		boolean testCase2b = validJumpX3.jump(validJumpX3);
		assertEquals(true, testCase2b, "jump failed at testCase2b.");
		boolean testCase3 = invalidJumpO2.jump(invalidJumpO2);
		assertEquals(false, testCase3,"jump failed at testCase3.");
		boolean testCase4 = invalidJumpX.jump(invalidJumpX);
		assertEquals(true, testCase4, "jump failed at testCase4.");
		boolean testCase5 = invalidJumpX2.jump(invalidJumpX2);
		assertEquals(false, testCase5, "jump failed at testCase5.");
		
		}
		
		@Test
		/** Tests that two moves are equal by creating various move objects and comparing them.*/
		void testEquals() {
			CheckersLogic.Move move1 = cLogic1.new Move(0, cLogic1.board.board, 5, 1, 4,2);
			CheckersLogic.Move move2 = cLogic1.new Move(0, cLogic1.board.board, 5, 1, 4, 2);
			CheckersLogic.Move move3 = cLogic1.new Move(1, cLogic1.board.board, 5, 1, 4, 2);
			CheckersLogic.Move move4 = cLogic1.new Move(0, cLogic1.board.board, 5, 7, 4, 6);
			CheckersLogic.Move move5 = cLogic1.new Move(1, cLogic1.board.board, 5, 1, 4, 2);
			CheckersLogic.Move move6 = cLogic1.new Move(1, cLogic1.board.board, 3, 0, 4, 2);
			
			System.out.println("Testing equals method of move class...");
			assertEquals(true, move2.equals(move1), "Move.equals failed at testCase1.");
			assertNotEquals(true, move1.equals(move3), "Move.equals failed at testCase2.");
			assertEquals(true, move3.equals(move5), "Move.equals failed at testCase3.");
			assertNotEquals(true, move2.equals(move4), "Move.equals failed at testCase4.");
			assertNotEquals(true, move6.equals(move5), "Move.equals failed at testCase5.");
		
		}
		
		@Test
		/** 
		 * Test that switch player functions by setting a current player, switching it,
		 * and asserting that the player has been changed.
		 */
		void testSwitchPlayer() {
			cLogic1.currentPlayer = cLogic1.PLAYER_O;
			cLogic1.switchPlayer();
			assertEquals(1, cLogic1.currentPlayer, "switchPlayer failed at testCase1.");
			
			cLogic1.switchPlayer();
			assertEquals(0, cLogic1.currentPlayer, "switchPlayer failed at testCase2.");
		}
		
		
		
		/******************** TESTING CHECKERSCOMPUTERPLAYERCLASS *************/
		
		@Test
		/** Test PC player version of checkMoves by asserting a few valid cases and numerous 
		 * edge cases to ensure the wrong input does not make it through as true.
		 * Create a jumpTest stub configuration to ensure checkMovesPC works for jump moves.*/
		void testCheckMovesPC() {
			CheckersLogic.CheckersComputerPlayer pc1 = cLogic1.new CheckersComputerPlayer(1);
			CheckersLogic.CheckersComputerPlayer pc0 = cLogic1.new CheckersComputerPlayer(0);
			
			boolean testCase1 = pc0.checkMoves(0,  5, 1, 4, 2);
			assertEquals(true, testCase1, "checkMovesPC failed at testCase1.");
			
			
			boolean testCase2 = pc0.checkMoves(0, 5, 1, 5, 0);
			assertEquals(false, testCase2, "checkMovesPC failed at testCase2.");
			
			boolean testCase3 = pc0.checkMoves(0, 5, 1, 4, 1);
			assertEquals(false, testCase3, "checkMovesPC failed at testCase3.");
			
			boolean testCase4 = pc1.checkMoves(1, 2, 0, 3, 1);
			assertEquals(true, testCase4, "checkMovesPC failed at testCase4.");
			
			boolean testCase5 = pc1.checkMoves(1, 2, 0, 2, 1);
			assertEquals(false, testCase5, "checkMovesPC failed at testCase5.");
			
			boolean testCase6 = pc1.checkMoves(1, 2, 0, 3, 0);
			assertEquals(false, testCase6, "checkMovesPC failed at testCase6.");
		
			boolean testCase7 = pc1.checkMoves(1, 2, 0, 5, 3);
			assertEquals(false, testCase7, "checkMovesPC failed at testCase7.");
			
			boolean testCase8 = pc1.checkMoves(1, 2, 0, 3, 3);
			assertEquals(false, testCase8, "checkMovesPC failed at testCase8.");		
			
			boolean testCase9 = pc1.checkMoves(0,  1, 1, 2, 0);
			assertEquals(false, testCase9, "checkMovesPC failed at testCase9.");
			
			boolean testCase10 = pc0.checkMoves(0, 5, 1, 3, 3);
			assertEquals(false, testCase10, "checkMovesPC failed at testCase10.");
			
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.setJumpTest();
			boolean testCase11 = pc0.checkMoves(0, 5, 1, 3, 3);
			assertEquals(true, testCase11, "checkMovesPC failed at testCase11.");
			
			boolean testCase12 = pc1.checkMoves(1, 2 , 2, 4, 0);
			assertEquals(true, testCase12, "checkMovesPC failed at testCase12.");
		
			boolean testCase13 = pc1.checkMoves(1, 4, 4, 2, 2);
			assertEquals(false, testCase13, "checkMovesPC failed at testCase13.");
		}
		
		
		
		
		@Test
		/**Check the format of a PC move. Ensure that rows and columns are in the proper format,
		 * which makes sure that negative indices do not get passed to an array access,
		 * throwing an index out of bounds error.*/
		void testFormatCheckerPC() {
			CheckersLogic.CheckersComputerPlayer pc1 = cLogic1.new CheckersComputerPlayer(1);
			CheckersLogic.CheckersComputerPlayer pc0 = cLogic1.new CheckersComputerPlayer(0);
			System.out.println("Checking CheckersComputerPlayer format checker... ");
			boolean testCase1 = pc1.formatChecker(0, 0); //valid
			boolean testCase2 = pc0.formatChecker(1,0);
			boolean testCase3 = pc1.formatChecker(-1,-1);
			boolean testCase4 = pc0.formatChecker(-100, -100);
			boolean testCase5 = pc1.formatChecker(20,20);
			boolean testCase6 = pc0.formatChecker(100,100);
			assertEquals(true, testCase1, "formatCheckerPC failed at testCase1.");
			assertEquals(true, testCase2, "formatCheckerPC failed at testCase2.");
			assertEquals(false, testCase3, "formatCheckerPC failed at testCase3.");
			assertEquals(false, testCase4, "formatCheckerPC failed at testCase4.");
			assertEquals(false, testCase5, "formatCheckerPC failed at testCase5.");
			assertEquals(false, testCase6, "formatCheckerPC failed at testCase6.");
			
		}
		
		@Test
		@DisplayName("Testing validMovePC...")
		/** Test various moves to confirm a move's validity for PC player. Tests
		 * valid moves for each player and multiple invalid moves to cover edge cases.*/
		void testValidMovePC() {
			CheckersLogic.CheckersComputerPlayer pc1 = cLogic2.new CheckersComputerPlayer(1);
			CheckersLogic.CheckersComputerPlayer pc0 = cLogic2.new CheckersComputerPlayer(0);
			//Player O
			cLogic2.computerPlayer= 0;
			boolean testCase1 = pc0.validMove(0,5,1,4,2);
			assertEquals(true, testCase1, "validMovePC failed at testCase1.");
			
			boolean testCase2 = pc0.validMove(0, 5, 2, 5, 3);
			assertEquals(false, testCase2, "validMovePC failed at testCase2.");
			
			boolean testCase3 = pc0.validMove(0, 5, 2, 6, 3);
			assertEquals(false, testCase3, "validMovesPC failed at testCase3.");
			
			boolean testCase4 = pc0.validMove(0, 2, 0, 3, 1);
			assertEquals(false, testCase4, "validMovesPC failed at testCase4.");
			
			//Player X 
			cLogic2.computerPlayer=1;
			boolean testCase5 = pc1.validMove(1, 2, 2, 3, 3);
			assertEquals(true, testCase5, "validMovesPC failed at testCase5.");
			
			boolean testCase6 = pc1.validMove(1, 1, 1, 0, 0);
			assertEquals(false, testCase6, "validMovesPC failed at testCase6.");
			
			boolean testCase7 = pc1.validMove(1, 1, 1, 1, 0);
			assertEquals(false, testCase7, "validMovesPC failed at testCase7.");
			
			boolean testCase8 = pc1.validMove(1,  7, 1, 6, 2);
			assertEquals( false, testCase8, "validMovesPC failed at testCase8.");
			
			
		}
		
		
		@Test
		/**
		 * Test getLegalJumps for CheckersLogic class. Ensure that jumps are added to
		 * the Vector, and invalid moves are not added to the vector.
		 */
		void testGetLegalJumps() {
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.jumpTest2();

			Vector<Move> pMoves1 = cLogic2.getLegalJumps(0, 3, 1);
			Vector<Move> pMoves2 = cLogic2.getLegalJumps(0,1,5);
			Vector<Move> pMoves3 = cLogic2.getLegalJumps(0,3,5);
			assertNotEquals(0, pMoves1.size(),"getLegalJumps failed at testCase1.");
			assertNotEquals(0, pMoves2.size(), "getLegalJumps failed at testCase2.");
			assertNotEquals(0, pMoves3.size(), "getLegalJumps failed at testCase3.");
			
			Vector<Move> pMoves4 = cLogic2.getLegalJumps(1, 2, 2);
			Vector<Move> pMoves5 = cLogic2.getLegalJumps(1, 2, 4);
			Vector<Move> pMoves6 = cLogic2.getLegalJumps(1, 4, 2);
			
			assertNotEquals(0, pMoves4.size(), "getLegalJumps failed at testCase4.");
			assertNotEquals(0, pMoves5.size(), "getLegalJumps failed at testCase5.");
			assertNotEquals(0, pMoves6.size(), "getLegalJumps failed at testCase6.");
			
		}
		
		@Test
		@DisplayName("testing computerMove...")
		/**
		 * Tests the execution of computer player moves. Ensures a piece is moved from the default
		 * configuration if a computer move is executed. Tests computer moves for out of moves
		 * locations, ensuring the number of moves for these locations is zero.
		 */
	void testComputerMove() {
			CheckerBoardStub stub = new CheckerBoardStub();
			stub.resetBoard();
			//testing level 2 difficulty
			CheckersLogic.CheckersComputerPlayer pc2x = cLogic2.new CheckersComputerPlayer(1);
			
			//testing difficulty level 1.
			CheckersLogic.CheckersComputerPlayer pc1x = cLogic1a.new CheckersComputerPlayer(1);
			
			//testing difficulty level 3.
			CheckersLogic.CheckersComputerPlayer pc3x = cLogic3.new CheckersComputerPlayer(1);
			CheckersLogic.CheckersComputerPlayer pc3o = cLogic3.new CheckersComputerPlayer(0);
			
			Vector<Move> pMoves1 = cLogic1a.getLegalMoves(1);
			Vector<Move> pMoves2 = cLogic2.getLegalMoves(1);
			Vector<Move> pMoves3 = cLogic3.getLegalMoves(1);
			assertNotEquals(0,pMoves2.size(), "computerMove failed at testCase1.");
			pc2x.computerMove(1);
			pc1x.computerMove(1);
			pc3x.computerMove(1);
			//test case 2
			assert(cLogic3.board.board[2][0] == '_' || cLogic3.board.board[2][2] == '_' || cLogic3.board.board[2][4] == '_' || cLogic3.board.board[2][6] == '_'): "ComputerMove failed at testCase2.";
			assert(cLogic1a.board.board[2][0] == '_' || cLogic1a.board.board[2][2] == '_' || cLogic1a.board.board[2][4] == '_' || cLogic1a.board.board[2][6] == '_') :"ComputerMove failed at testCase2.";

			//assert a move has been made by the computer by checking front row of pc pieces. 
			assert(cLogic2.board.board[2][0] == '_' || cLogic2.board.board[2][2] == '_' || cLogic2.board.board[2][4] == '_' || cLogic2.board.board[2][6] == '_'): "ComputerMove failed at testCase3.";
			
			stub.outOfMovesTestX();
			 pMoves1 = cLogic1a.getLegalMoves(1);
			 pMoves2 = cLogic2.getLegalMoves(1);
			 pMoves3 = cLogic3.getLegalMoves(1);
			pc2x.computerMove(1);
			pc1x.computerMove(1);
			pc3x.computerMove(1);
			assertEquals(0, pMoves1.size(), "computerMove failed at testCase4.");
			assertEquals(0, pMoves2.size(), "computerMove failed at testCase4.");
			assertEquals(0, pMoves3.size(), "computerMove failed at testCase4.");
			
		
			stub.outOfMovesTestO();
			pMoves3 = cLogic3.getLegalMoves(0);
			pc3o.computerMove(0);
			assertEquals(0, pMoves3.size(), "computerMove failed at testCase6.");
			
			stub.twoPieceTest();
			cLogic3.numPiecesO=1;
			cLogic3.numPiecesX = 1;
			pc3x.computerMove(1);
			assertNotEquals('x', cLogic3.board.board[6][6], "computerMove faield at testCase7.");
			
		}
		
		@Test
		/**Ensure the functionality of takePiece for PC players by creating
		 * stubs and various PC players (Different player numbers/pieces), making sure that
		 * for a jumpTest, pieces are actually taken.*/
		void testTakePiecePC() {
			CheckerBoardStub stub = new CheckerBoardStub();
			CheckersLogic.CheckersComputerPlayer pc1 = cLogic2.new CheckersComputerPlayer(1);
			CheckersLogic.CheckersComputerPlayer pc0 = cLogic2.new CheckersComputerPlayer(0);
			
			stub.setJumpTestPC();
			cLogic2.computerPlayer =1;
			pc1.takePiece(1,2,2,4,0);
			assertEquals('_',cLogic2.board.board[2][2], "takePiecePC failed at testCase1a.");
			assertEquals('_', cLogic2.board.board[3][1], "takePiecePC failed at testCase1b.");
			assertEquals('x', cLogic2.board.board[4][0], "takePiecePC failed at testCase1c.");
			
			stub = new CheckerBoardStub();
			stub.setJumpTestPC();
			//valid takepiece
			cLogic2.computerPlayer=0;
			pc0.takePiece(0, 3, 1, 5, 3);
			assertNotEquals( 'o', cLogic2.board.board[3][1], "takePiecePC failed at testCase3a.");
			assertEquals( '_' ,cLogic2.board.board[3][1], "takePiecePC failed at testCase3b.");
			assertEquals('_', cLogic2.board.board[4][2],  "takePiecePC failed at testCase3c.");

		}
		
		/**
		 * Class CheckerBoardStub is a stub object to configure the checkerboard in various ways
		 * for all test cases of CheckersLogic. Updates all objects, allows testing of out of moves,
		 * jump cases, game over (2 piece test), and default cases.
		 */
		class CheckerBoardStub {
			
			//Creating a checkerboardStub object resets all boards to their default state, new game.
			public CheckerBoardStub() {
				resetBoard();
			}
			
			/**
			 * Modify all boards to perform jumps.
			 */
			public void setJumpTest() {
				cLogic1.board.setupGame(cLogic1.board.board);
				cLogic1.board.board[5][3] = '_';
				cLogic1.board.board[2][0] = '_';
				cLogic1.board.board[3][1] = 'o';
				cLogic1.board.board[4][2] = 'x';
				cLogic1.board.board[5][3] = '_';
				cLogic1.board.board[2][0] = '_';
				cLogic1.board.board[3][1] = 'o';
				cLogic1.board.board[4][2] = 'x';
				cLogic1.board.board[5][5] = '_';
				cLogic1.board.board[3][5] = 'o';
			}
			
			public void setJumpTestPC() {
				cLogic2.board.setupGame(cLogic2.board.board);
				cLogic2.board.board[5][3] = '_';
				cLogic2.board.board[2][0] = '_';
				cLogic2.board.board[3][1] = 'o';
				cLogic2.board.board[4][2] = 'x';
				cLogic2.board.board[5][3] = '_';
				cLogic2.board.board[2][0] = '_';
				cLogic2.board.board[3][1]= 'o';
				cLogic2.board.board[4][2] = 'x';
				cLogic2.board.board[5][5] = '_';
				cLogic2.board.board[3][5] = 'o';
				
				cLogic3.board.setupGame(cLogic3.board.board);
				cLogic3.board.board[5][3] = '_';
				cLogic3.board.board[2][0] = '_';
				cLogic3.board.board[3][1] = 'o';
				cLogic3.board.board[4][2] = 'x';
				cLogic3.board.board[5][3] = '_';
				cLogic3.board.board[2][0] = '_';
				cLogic3.board.board[3][1]= 'o';
				cLogic3.board.board[4][2] = 'x';
				cLogic3.board.board[5][5] = '_';
				cLogic3.board.board[3][5] = 'o';
				
				cLogic1a.board.setupGame(cLogic1a.board.board);
				cLogic1a.board.board[5][3] = '_';
				cLogic1a.board.board[2][0] = '_';
				cLogic1a.board.board[3][1] = 'o';
				cLogic1a.board.board[4][2] = 'x';
				cLogic1a.board.board[5][3] = '_';
				cLogic1a.board.board[2][0] = '_';
				cLogic1a.board.board[3][1]= 'o';
				cLogic1a.board.board[4][2] = 'x';
				cLogic1a.board.board[5][5] = '_';
				cLogic1a.board.board[3][5] = 'o';
				}
			
			/**
			 * Double verify that resetting the board has gone through
			 * to both references of cLogic1's board object.
			 */
			public void resetBoard() {
				cLogic1.board.setupGame(cLogic1.board.board);
				cLogic1a.board.setupGame(cLogic1a.board.board);
				cLogic2.board.setupGame(cLogic2.board.board);
				cLogic3.board.setupGame(cLogic3.board.board);
				
			}
			
			/**
			 * Tests that X is out of moves.
			 */
			public void outOfMovesTestX() {
				for(int i = 0; i < 8; i++)
					for(int j = 0; j<8; j++) {
						cLogic1.board.board[i][j] = '_';
						cLogic1a.board.board[i][j] = '_';
						cLogic2.board.board[i][j] = '_';
						cLogic3.board.board[i][j] = '_';
					}
				cLogic1.board.board[0][1] = 'x';
				cLogic1.board.board[1][0] = 'o';
				cLogic1.board.board[1][2] = 'o';
				cLogic1.board.board[2][3] = 'o';
				
				cLogic2.board.board[0][1] = 'x';
				cLogic2.board.board[1][0] = 'o';
				cLogic2.board.board[1][2] = 'o';
				cLogic2.board.board[2][3] = 'o';
				
				cLogic1a.board.board[0][1] = 'x';
				cLogic1a.board.board[1][0] = 'o';
				cLogic1a.board.board[1][2] = 'o';
				cLogic1a.board.board[2][3] = 'o';
				
				cLogic3.board.board[0][1] = 'x';
				cLogic3.board.board[1][0] = 'o';
				cLogic3.board.board[1][2] = 'o';
				cLogic3.board.board[2][3] = 'o';
						
			}
			
			/**
			 * More in depth jump test for all configurations of CheckersLogic objects.
			 */
			public void jumpTest2() {
				for(int i = 0; i < 8; i++)
					for(int j = 0; j<8; j++) {
						cLogic1.board.board[i][j] = '_';
						cLogic1a.board.board[i][j] = '_';
						cLogic2.board.board[i][j] = '_';
						cLogic3.board.board[i][j] = '_';
					}
				cLogic1.board.board[2][2] = 'x';
				cLogic1.board.board[3][1] = 'o';
				cLogic1.board.board[4][2] = 'x';
				cLogic1.board.board[1][5] = 'o';
				cLogic1.board.board[2][4] = 'x';
				cLogic1.board.board[3][5] = 'o';
				
				cLogic1a.board.board[2][2] = 'x';
				cLogic1a.board.board[3][1] = 'o';
				cLogic1a.board.board[4][2] = 'x';
				cLogic1a.board.board[1][5] = 'o';
				cLogic1a.board.board[2][4] = 'x';
				cLogic1a.board.board[3][5] = 'o';
				
				cLogic2.board.board[2][2] = 'x';
				cLogic2.board.board[3][1] = 'o';
				cLogic2.board.board[4][2] = 'x';
				cLogic2.board.board[1][5] = 'o';
				cLogic2.board.board[2][4] = 'x';
				cLogic2.board.board[3][5] = 'o';
				
				cLogic3.board.board[2][2] = 'x';
				cLogic3.board.board[3][1] = 'o';
				cLogic3.board.board[4][2] = 'x';
				cLogic3.board.board[1][5] = 'o';
				cLogic3.board.board[2][4] = 'x';
				cLogic3.board.board[3][5] = 'o';
			}
			
			
			/**
			 * Board for test case where two pieces are left--
			 * allows each player to test a gameOver case.
			 */
			void twoPieceTest() {
				for(int i = 0; i < 8; i++)
					for(int j = 0; j<8; j++) {
						cLogic1.board.board[i][j] = '_';
						cLogic1a.board.board[i][j] = '_';
						cLogic2.board.board[i][j] = '_';
						cLogic3.board.board[i][j] = '_';
					}
				
				cLogic1.board.board[6][6] = 'x';
				cLogic1.board.board[5][5] = 'o';
				
				cLogic1a.board.board[6][6] = 'x';
				cLogic1a.board.board[5][5] = 'o';
			
				cLogic2.board.board[6][6] = 'x';
				cLogic2.board.board[5][5] = 'o';
			
				cLogic3.board.board[6][6] = 'x';
				cLogic3.board.board[5][5] = 'o';
			}
			
			/** 
			 * Test that O is out of moves for all game types.
			 */
			public void outOfMovesTestO() {
				for(int i = 0; i < 8; i++)
					for(int j = 0; j<8; j++) {
						cLogic1.board.board[i][j] = '_';
						cLogic1a.board.board[i][j] = '_';
						cLogic2.board.board[i][j] = '_';
						cLogic3.board.board[i][j] = '_';
					}
				cLogic1.board.board[7][1] = 'o';
				cLogic1.board.board[6][0] = 'x';
				cLogic1.board.board[6][2] = 'x';
				cLogic1.board.board[5][3] = 'x';
				
				cLogic1a.board.board[7][1] = 'o';
				cLogic1a.board.board[6][0] = 'x';
				cLogic1a.board.board[6][2] = 'x';
				cLogic1a.board.board[5][3] = 'x';
				
				cLogic2.board.board[7][1] = 'o';
				cLogic2.board.board[6][0] = 'x';
				cLogic2.board.board[6][2] = 'x';
				cLogic2.board.board[5][3] = 'x';
				
				cLogic3.board.board[7][1] = 'o';
				cLogic3.board.board[6][0] = 'x';
				cLogic3.board.board[6][2] = 'x';
				cLogic3.board.board[5][3] = 'x';
			}
			
		}
		
	
}
