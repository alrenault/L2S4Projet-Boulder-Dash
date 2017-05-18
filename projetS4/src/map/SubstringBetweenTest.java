package map;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class SubstringBetweenTest{

	static String readFile(String path, Charset encoding) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void main(String[] args) throws IOException{
		String content = readFile(args[0], Charset.defaultCharset());
		String beginStr = "[map]";
		String endStr = "[/map]";
		content = 
			content.substring(
					content.indexOf(beginStr)+beginStr.length(),
					content.indexOf(endStr));
		System.out.println(content);
	}
}

