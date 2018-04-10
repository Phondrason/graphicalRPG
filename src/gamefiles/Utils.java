package gamefiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utils 
{
	public static int parseInt(String number)
	{
		try
		{
			return Integer.parseInt(number);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public static String loadFileAsString(String path)
	{
		StringBuilder builder = new StringBuilder();
		
		FileReader file = null;
		try
		{
			file = new FileReader(Utils.class.getClass().getResource(path).getFile());
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		if (file != null)
		{
			try
			{
				BufferedReader br = new BufferedReader(file);
				String line;
				while ((line = br.readLine()) != null)
				{
					builder.append(line + "\n");
				}
				br.close();
			}
			catch (IOException e2)
			{
				e2.printStackTrace();
			}
		}
		return builder.toString();
	}
}
