package model;

import static utils.FileHelper.fromSafe;
import static utils.FileHelper.safe;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovesFileHandler
{
    public void save(ArrayList<Moves> moves)
    {
        try
        {
            PrintWriter writer = new PrintWriter("model/db/Moves.txt");

            for(Moves m : moves)
            {
                if(m != null)
                {
                    writer.write(safe(m.getMoveName()));
                    writer.write(safe(m.getMoveType1()));
                    writer.write(safe(m.getMoveClassification()));
                    writer.write(safe(m.getMoveDesc()));

                }
            }
            writer.close();
        } catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<Moves> load()
    {
        ArrayList<Moves> moves = new ArrayList<>();
        try
        {
            File load = new File("model/db/Moves.txt");
            Scanner scanner = new Scanner(load);

            while(scanner.hasNextLine())
            {
                String name = fromSafe(scanner.nextLine());
                String type1 = fromSafe(scanner.nextLine());
                String classification = fromSafe(scanner.nextLine());
                String desc = fromSafe(scanner.nextLine());

                Moves move = new Moves(name, type1, classification, desc);
                moves.add(move);
            }
        } catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return moves;
    }
}