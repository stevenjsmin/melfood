/** 
 * 2016 FileUtils.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.uitl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * @author steven.min
 *
 */
public class FileUtils {

	/**
	 * Convert InputStream to File Object
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static File convertInputStreamToFile(InputStream is) {

		File file = null;
		try {

			String fileName = randomString(20);
			file = File.createTempFile(fileName, ".temp");
			file.deleteOnExit(); // delete this on JVM exist

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileWriter.write(line + "\n");
			}
			fileWriter.flush();
			fileWriter.close();
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	private static String randomString(int len) {

		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}
