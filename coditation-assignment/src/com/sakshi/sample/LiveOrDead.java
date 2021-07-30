package com.sakshi.sample;

public class LiveOrDead {

	public final static int Dead = 0x00;

	public final static int Live = 0x01;

	public final static void main(String[] args) {

		LiveOrDead lOd = new LiveOrDead();
		lOd.test(500);
	}

	private void test(int nrIterations) {
		int[][] board = { { Dead, Dead, Dead, Dead, Dead }, { Dead, Dead, Dead, Live, Dead },
				{ Dead, Dead, Live, Live, Dead }, { Dead, Dead, Dead, Live, Dead }, { Dead, Dead, Dead, Dead, Dead } };

		System.out.println("LiveOrDead");
		printBoard(board);

		for (int i = 0; i < nrIterations; i++) {
			System.out.println();
			board = getNextBoard(board);
			printBoard(board);
		}
	}

	private void printBoard(int[][] board) {

		for (int i = 0, e = board.length; i < e; i++) {

			for (int j = 0, f = board[i].length; j < f; j++) {
				System.out.print(Integer.toString(board[i][j]) + ",");
			}
			System.out.println();
		}
	}

	public int[][] getNextBoard(int[][] board) {

		if (board.length == 0 || board[0].length == 0) {
			throw new IllegalArgumentException("Board must have a positive amount of rows and/or columns");
		}

		int nrRows = board.length;
		int nrCols = board[0].length;

		int[][] buf = new int[nrRows][nrCols];

		for (int row = 0; row < nrRows; row++) {

			for (int col = 0; col < nrCols; col++) {
				buf[row][col] = newStateOFCell(board[row][col], getLiveNeighbours(row, col, board));
			}
		}
		return buf;
	}

	private int getLiveNeighbours(int cellRow, int cellCol, int[][] board) {

		int LiveNeighbours = 0;
		int rowEnd = Math.min(board.length, cellRow + 2);
		int colEnd = Math.min(board[0].length, cellCol + 2);

		for (int row = Math.max(0, cellRow - 1); row < rowEnd; row++) {

			for (int col = Math.max(0, cellCol - 1); col < colEnd; col++) {

				if ((row != cellRow || col != cellCol) && board[row][col] == Live) {
					LiveNeighbours++;
				}
			}
		}
		return LiveNeighbours;
	}

	private int newStateOFCell(int currentState, int LiveNeighbours) {

		int newState = currentState;

		switch (currentState) {
		case Live:
			if (LiveNeighbours < 2) {
				newState = Dead;
			}

			if (LiveNeighbours == 2 || LiveNeighbours == 3) {
				newState = Live;
			}

			if (LiveNeighbours > 3) {
				newState = Dead;
			}
			break;

		case Dead:
			if (LiveNeighbours == 3) {
				newState = Live;
			}
			break;

		default:
			throw new IllegalArgumentException("State of cell must Live Or Dead");
		}
		return newState;
	}

}
