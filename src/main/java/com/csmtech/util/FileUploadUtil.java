package com.csmtech.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
//	@Value("${file.path}")
//	private static String filePath;

	public static List<String> getFilePath(MultipartFile[] attachment) {
	
		 List<String> filePaths = new ArrayList<>();
		 
		 String filePath="d:/utf/";

		    try {
		        for (MultipartFile attachments : attachment) {
		            String fileName = attachments.getOriginalFilename();
		            System.out.println(fileName);
		            File fileDirectory = new File(filePath);
		            System.out.println(fileDirectory);
		            if (!fileDirectory.isDirectory()) {
		                fileDirectory.mkdir();
		            }
			
		            String finalFilePath = filePath + fileName;
		            File file = new File(finalFilePath);
		            InputStream in = attachments.getInputStream();
		            FileOutputStream fos = new FileOutputStream(file);
		            byte[] bytes = new byte[(int) attachments.getSize()];
		            in.read(bytes);
		            fos.write(bytes);
		            in.close();
		            fos.close();

		            filePaths.add(finalFilePath);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return filePaths;
		}



}
