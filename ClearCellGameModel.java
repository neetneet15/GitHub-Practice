package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game. 
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one where 
 * every cell corresponds to BoardCell.EMPTY.
 * 
 * @author Dept of Computer Science, UMCP
 * @Student    Name: 		Praneet Puppala
 * 			   Sect: 		CMSC132 0303
 * 	 Last Worked On:		February 11th, 2013
 */

public class ClearCellGameModel extends GameModel {

	/* Feel free to add any instance variables you may need */
	private int gameScore = 0;
	private Random random;
	
	/**
	 * Defines a board with empty cells.  It relies on the
	 * super class constructor to define the board.
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 */
	public ClearCellGameModel(int maxRows, int maxCols, Random random) 
	{
		super(maxRows, maxCols); /* Yes, we need this call */
		this.random = random;
	}

	/** 
	 * HELPER METHOD
	 * 
	 * Checks if row is empty and returns boolean indicating such.
	 * @param rowToCheck 
	 */
	private boolean isRowEmpty(int rowToCheck)
	{
		boolean isEmpty = true;
		
		// cycle through each column of row to check and check whether or not
		//	cell is empty. If any cell is not empty, row is not empty, change
		//	what will be returned to false.
		for (int cellColIndex = 0; cellColIndex < this.board[rowToCheck].length; 
				cellColIndex++)
		{
			if (getBoardCell(rowToCheck, cellColIndex) != BoardCell.EMPTY)
			{
				isEmpty = false;
			}
		}
		
		return isEmpty;
	}
	
	/**
	 * The game is over when the last board row (row with index board.length -1)
	 * is different from empty row.
	 */
	public boolean isGameOver() 
	{
		// use helper method to check if last board row is empty
		//	if empty, game is not over yet, so return false
		//	if not empty, game IS over, so return true
		if (isRowEmpty(this.board.length - 1))
			return false;
		else
			return true;
	}

	public int getScore() 
	{
		return gameScore;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects
	 * if the last board row (row with index board.length -1) corresponds to
	 * the empty row;  Otherwise no operation will take place.
	 */
	public void nextAnimationStep() 
	{
		if (isRowEmpty(this.board.length - 1))
		{
			// Move all rows and columns after first row dow one row.
			for (int row = board.length - 1; row > 0; row--)
				for (int col = 0; col < board[row].length; col++)
					board[row][col] = this.getBoardCell(row - 1, col);
			
			// Generate and insert new random row at top of new board.
			for (int count = 0; count < this.getMaxCols(); count++)
			{
				BoardCell currentRandom = 
						BoardCell.getNonEmptyRandomBoardCell(random);
				this.setBoardCell(0, count, currentRandom);
			}

		}
	}

	/**
	 * This method will turn to BoardCell.EMPTY any cells immediately surrounding the
	 * cell at position [rowIndex][colIndex] if and only if those surrounding 
	 * cells have the same color as the selected cell.  The selected cell will
	 * also be turned into a BoardCell.EMPTY.  If after processing the surrounding
	 * cells any rows in the board are empty then those rows will collapse, moving non-empty
	 * rows upward. If [rowIndex][colIndex] corresponds to an empty cell no 
	 * action will take place. 
	 * @throws IllegalArgumentException with message "Invalid row index" for invalid row
	 * or "Invalid column index" for invalid column.  We check for row validity first.
	 */
	public void processCell(int rowIndex, int colIndex) 
	{
		// Check if valid indices, starting with rows. If not, 
		//	throw exception
		if (rowIndex < 0 || rowIndex >= this.getMaxRows())
			throw new IllegalArgumentException("Invalid row index");
		else if (colIndex < 0 || colIndex >= this.getMaxCols())
			throw new IllegalArgumentException("Invalid column index");
		
		// Store current board cell in variable.
		BoardCell reference = this.getBoardCell(rowIndex, colIndex);
		BoardCell processing = this.getBoardCell(rowIndex, colIndex);
		
		if (reference != BoardCell.EMPTY)
		{
		// Cycle through surrounding indices and process.
			for (int row = rowIndex-1; row <= rowIndex+1; row++)
			{
				for (int col = colIndex-1; col <= colIndex+1; col++)
				{
					// assure neighbors are also in bounds
					if (row >= 0 && row < this.getMaxRows() && col >= 0 
							&& col < this.getMaxCols())
					{
						BoardCell neighbor = this.getBoardCell(row, col);
						
						/* check if neighboring BoardCell is same as cell being
						 *	processed. If so, change both neighboring and 
						 *	cell being processed to empty.
						 * Includes check to make sure coordinates that are
						 *	from for loop aren't same as parameter coordinates 
						 *	so that the cell being processed isn't checked
						 *	against itself. 
						 **/
						if (neighbor == reference)
						{
							neighbor = BoardCell.EMPTY;
							this.setBoardCell(row, col, neighbor);
							gameScore++;
						}
					}
				}
			}
			
			// check if any rows are empty after processing and there are rows 
			//	underneath checked row. If so, collapse rows and move rows 
			//	underneath up.
			for (int row = 0; row < this.getMaxRows(); row++)
			{
				if (isRowEmpty(row) && row < this.getMaxRows() - 1)
				{
					for (int row2 = row+1; row2 < this.getMaxRows(); row2++)
						for (int col = 0; col < this.getMaxCols(); col++)
						{
							this.setBoardCell(row2-1, col, 
									this.getBoardCell(row2, col));
						}
					
					for (int col = 0; col < this.getMaxCols(); col++)
						this.setBoardCell(this.getMaxRows() -1 , col, 
								BoardCell.EMPTY);
				}
			}
		}
	}
}