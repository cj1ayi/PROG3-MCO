package model;

import static utils.FileHelper.fromSafe;
import static utils.FileHelper.safe;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code MovesFileHandler} class is part of MODEL.
 *
 * It's responsible for saving and loading {@code Moves} objects 
 * to and from a text file in {@code model/db/Moves.txt}.
 *
 * Each line in the file represents one {@code Moves} object with fields 
 * separated by a "|" delimiter.
 */
public class MovesFileHandler
{
	public void saveAppend(Moves m, FileWriter fileAppender)
	{
		PrintWriter writer = new PrintWriter(fileAppender);
			
		//TAG
		writer.write("MOV|");
		//DATA
      writer.write(safe(m.getMoveName()));
		writer.write(safe(m.getMoveType1()));
		writer.write(safe(m.getMoveClassification()));
		writer.write(safe(m.getMoveDesc()));
		writer.write("\n");	
	}

	/**
    * Saves the given list of {@code Moves} to a file into the db or database of moves.txt.
    * Each move is written in one line with its fields separated by a "|".
    * Null values are handled using the {@code safe()} helper method found in the utils folder.
    *
    * @param moves The list of {@code Moves} to be saved.
    */
	public void save(ArrayList<Moves> moves)
   {
		try
		{
			FileWriter fileAppender = new FileWriter("model/db/Moves.txt", true);

	   	for(Moves m : moves)
   	   {
      		if(m == null) { continue; }
				saveAppend(m, fileAppender);
			}
			fileAppender.close();
		} catch (IOException e)
		{
			System.out.println("An error occurred in moves save.");
			e.printStackTrace();
		}
   }

	/**
    * Loads {@code Moves} objects from the txt file assuming its in the correct format.
    * Each line in the file represents a move and is tokenized with the use of the "|" delimiter.
    *
    * @return An {@code ArrayList} of {@code Moves} loaded from the file.
    */
	public ArrayList<Moves> load()
	{
		ArrayList<Moves> moves = new ArrayList<>();
		try
		{
			File load = new File("model/db/Moves.txt");
			Scanner scanner = new Scanner(load);
			
			while(scanner.hasNextLine())
			{
				String tokens[] = scanner.nextLine().split("\\|");
				String name = fromSafe(tokens[1]);
				String type1 = fromSafe(tokens[2]);
				String classification = fromSafe(tokens[3]);
				String desc = fromSafe(tokens[4]);
				
				Moves move = new Moves(name, type1, classification, desc);
				moves.add(move);
			} 
			
			System.out.println("Successfully loaded!");
			scanner.close();
		} catch (IOException e)
		{
			System.out.println("An error occurred.");       
			e.printStackTrace();
		}
		
		return moves;
	}
}
    
