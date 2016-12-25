package de.uulm.in.vs.grn.sockagram.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.uulm.in.vs.grn.sockagram.filter.SockagramFilter;

public class FileExample {
	
	public static void main(String[] args) throws IOException {
		
		//Convert the content of image.jpg into an byte array
		File file = new File("image.jpg");
		FileInputStream fileInputStream = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fileInputStream.read(data);
        fileInputStream.close();
        
        
        //apply filter
        byte[] result = SockagramFilter.NOFILTER.apply(data);
        
		//Store the resulting byte array into a file
        FileOutputStream fos = new FileOutputStream("result3.png");
        fos.write(result);
        fos.close();
        
		
	}
	
	
}
