package gamefiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		
		InputStream in = Utils.class.getClass().getResourceAsStream(path);
		if (in != null)
		{
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
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
	
	public static boolean containsBlock(int[][] touched) 
	{
		for(int j = 0; j < touched.length; j++) 
		{
			for(int i = 0; i < touched[j].length; i++) 
		    {
				if(touched[j][i] > 65535) return true;
		    }
		}
		return false;
	}
}
