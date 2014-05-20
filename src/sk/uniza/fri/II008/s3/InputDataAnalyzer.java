package sk.uniza.fri.II008.s3;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputDataAnalyzer
{
	public static void main(String[] args)
	{
		try (BufferedReader reader = new BufferedReader(new FileReader("input_data.txt")))
		{
			HashMap<Integer, ArrayList<Date>> storages = new HashMap<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy hh:mm:ss");
			String line;

			while ((line = reader.readLine()) != null)
			{
				String[] lineParts = line.split(";", 2);
				Date date = dateFormat.parse(lineParts[0]);
				int storage = Integer.parseInt(lineParts[1]);

				if (!storages.containsKey(storage))
				{
					storages.put(storage, new ArrayList<Date>());
				}

				storages.get(storage).add(date);
			}

			for (Integer index : storages.keySet())
			{
				ArrayList<Date> dates = storages.get(index);
				Collections.sort(dates);

				writeDurationsToFile("d" + index + ".txt", dates);
			}
		}
		catch (FileNotFoundException ex)
		{
			Logger.getLogger(InputDataAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (IOException | ParseException ex)
		{
			Logger.getLogger(InputDataAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void writeDurationsToFile(String filename, ArrayList<Date> dates) throws FileNotFoundException
	{
		try (PrintStream file = new PrintStream(
			new BufferedOutputStream(new FileOutputStream(filename))))
		{
			for (int i = 0; i < dates.size() - 1; i++)
			{
				file.printf("%d\n", (dates.get(i + 1).getTime() - dates.get(i).getTime()) / 1000);
			}
		}
	}
}
