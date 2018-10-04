package nl.sogyo.javaopdrachten.advancedopdrachten;

import java.util.Scanner;
import java.lang.Character;

public class Sudoku {
	int[][] sudokuFieldInitial = new int[9][9]; 
	int[][] sudokuField = new int [sudokuFieldInitial.length][]; 
	int firstEmptyField = -1;
	int counter = 0; 
	boolean goingBackwards = false;
	int mistakesMade = 0;
	
	public Sudoku() {
		this.setField();
		this.printField(this.sudokuFieldInitial);
		this.solveSudoku();
	}
	
	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
	}
	
	private void solveSudoku() {
		while(counter != 81) {
			int rowIndex = counter / 9;
			int columnIndex = counter % 9;
	
				if(!goingBackwards & !isFinalValue(rowIndex, columnIndex, counter)) {
					goingBackwards = forwardsEmptyField(rowIndex, columnIndex);
					}
				else if (goingBackwards & !isFinalValue(rowIndex, columnIndex, counter)){
					goingBackwards = backwardsEmptyField(rowIndex, columnIndex);
				}
				else {
					finalField();
				}
			}
		System.out.println("Sudoku solved!\n" + mistakesMade + " mistakes were made while trying to solve this sudoku.");
		printField(sudokuField);
	}
	
	private void finalField() {
		if(goingBackwards) {
			counter--;
		}
		else {
			counter++;
		}
	}
	
	private boolean backwardsEmptyField(int rowIndex, int columnIndex) {
		for(int a = sudokuField[rowIndex][columnIndex] + 1; a < 11; a++) {
			if(a == 10) {
				if(counter == firstEmptyField) {
				printField(sudokuField);
				}
				sudokuField[rowIndex][columnIndex] = 0;
				counter--;
				mistakesMade += 1;
				return true;			
			}
			else {
				if(isValid(rowIndex, columnIndex, a)) {
					sudokuField[rowIndex][columnIndex] = a;
					counter++;
					return false;
				}
			}
		}
		return false; 
	}
	
	private boolean forwardsEmptyField(int rowIndex, int columnIndex) {
		boolean foundValidNumber = false; 
		for(int a = 1; a < 10; a++) {
			if(isValid(rowIndex, columnIndex, a)) {
				sudokuField[rowIndex][columnIndex] = a;
				foundValidNumber = true;
				break;
			}
		}
		if(!foundValidNumber) { 
			printField(sudokuField);
			counter--;
			mistakesMade += 1;
			return true;
		}
		else {
			counter++;
		}
		return false;
	}
	
	private boolean isFinalValue(int rowIndex, int columnIndex, int counter) {
		if(sudokuFieldInitial[rowIndex][columnIndex] == 0) {
			if(firstEmptyField == -1) {
				firstEmptyField = counter;
			}
			return false;
		}
		else {
			return true;
		}
	}
	
	
	private boolean isValid(int y, int x, int number) {
		if(checkRow(y, number) && checkColumn(x, number) && checkBox(y, x, number)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean checkRow(int y, int number) {
		for(int a = 0; a < 9; a++) {
			if(sudokuField[y][a] == number) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkColumn(int x, int number) {
		for(int a = 0; a < 9; a++) {
			if(sudokuField[a][x] == number) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkBox(int y, int x, int number) {
		int a, b , c , d;
		if(x<3 & y<3) {
			a = 0; b = 0;
			} 
		else if(x>2 & x<6 & y<3) {
			a = 3; b = 0;
		} 
		else if(x>5 & y<3) {
			a = 6; b = 0;
		} 
		else if(x<3 & y>2 & y<6) {
			a = 0; b = 3;
		} 
		else if(x>2 & x<6 & y>2 & y<6) {
			a = 3; b = 3;
		} 
		else if(x>5 & y>2 & y<6) {
			a = 6; b = 3;
		} 
		else if(x<3 & y>5) {
			a = 0; b = 6;
		} 
		else if(x>2 & x<6 & y>5) {
			a = 3; b = 6;
		} 
		else {
			a = 6; b = 6;
		} 
		c = a + 3;
		d = b + 3;
		for(int e = a; e < c; e++) {
			for(int f = b; f < d; f++) {
				if(sudokuField[f][e] == number) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void setField() {
		Scanner input = new Scanner(System.in);
		int inputLength = 0;
		String sudokuInput = "";
		System.out.println("This program will solve your sudoku by brute force.");
		for(int x = 0; x < 9; x++) {
			do {
				System.out.println("Give input of the sudoku-row " + (x+1) + ".\nIt must consist of 9 numbers. Choose a 0 for an empty spot.");
				sudokuInput = input.next();
				inputLength = sudokuInput.length();
			}while(inputLength != 9);
			
			for(int y = 0; y < 9; y++) {
				int number = Character.getNumericValue(sudokuInput.charAt(y));
				sudokuFieldInitial[x][y] = number;
			}
		}
		for(int i = 0; i < sudokuFieldInitial.length; i++)
		    sudokuField[i] = sudokuFieldInitial[i].clone();
	}
	
	private void printField(int[][] sudokuField) {
		
		for(int x = 0; x < 9; x++) {
			System.out.println("-------------------------------------");
			System.out.print("| ");
			for(int y = 0; y < 9; y++) {
				if(sudokuField[x][y] == 0) {
					System.out.print("  | ");
				}
				else {
				System.out.print(sudokuField[x][y] + " | ");
				}
			}
			System.out.println();
		}
		System.out.println("-------------------------------------");
	}
}