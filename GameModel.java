package model;

/**
 * This class represents the logic of a game where a
 * board is updated on each step of the game animation.
 * The board can also be updated by selecting a board cell.
 * 
 * @author Dept of Computer Science, UMCP
 * @Student    Name: 		Praneet Puppala
 * 			   Sect: 		CMSC132 0303
 * 	 Last Worked On:		February 11th, 2013
 */

public abstract class GameModel {
	protected BoardCell[][] board;

	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 * @param maxRows
	 * @param maxCols
	 */
	public GameModel(int maxRows, int maxCols) 
	{
		// define max rows for 'board'
		this.board = new BoardCell[maxRows][];
		
		// use for loop to define columns for each row and set each cell 
		//	to empty.
		for (int row = 0; row < this.board.length; row++)
		{
			board[row] = new BoardCell[maxCols];
			
			for (int col = 0; col < maxCols; col++)
			{
				setBoardCell(row, col, BoardCell.EMPTY);
			}
		}
	}

	public int getMaxRows() 
	{
		return board.length;
	}

	public int getMaxCols() 
	{
		return board[0].length;
	}
	
	public BoardCell[][] getBoard() 
	{
		return board;
	}

	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) 
	{
		this.board[rowIndex][colIndex] = boardCell;
	}
	
	public BoardCell getBoardCell(int rowIndex, int colIndex) 
	{
		return this.board[rowIndex][colIndex];
	}
	
	/** Provides a string representation of the board 
	 * We have implemented this method for you.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Board(Rows: " + board.length + ", Cols: " + board[0].length + ")\n");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++)
				buffer.append(board[row][col].getName());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	public abstract boolean isGameOver();
	
	public abstract int getScore();

	/**
	 * Advances the animation one step. 
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board
	 * state and the selected cell.
	 * @param rowIndex 
	 * @param colIndex
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}