package de.uulm.in.vs.grn.sockagram.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uulm.in.vs.grn.sockagram.filter.SockagramFilter;

public class MultiTest {
	
	public static void main(String[] args) throws IOException {
	
		List<String> files = new ArrayList<String>(){{
			add("image.jpg");
			add("image2.jpg");
			add("image3.jpg");
		}};
		
		for(String file : files){
			for(SockagramFilter filter: SockagramFilter.values()){
				apply(file, file.replace(".", "_")+"-"+filter.name()+".png", filter);
			}
		}
	}
	
	public static void apply (String src, String dest, SockagramFilter filter) throws IOException{
		
		File file = new File(src);
		FileInputStream fileInputStream = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fileInputStream.read(data);
        fileInputStream.close();
        
        byte[] result = filter.apply(data);
        
        FileOutputStream fos = new FileOutputStream(dest);
        fos.write(result);
        fos.close();
	}
}
