package com.chinasoft.project.DAO;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.runner.Request;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.http.HttpRequest;

public class ImageDAO {
	
	
	public static byte[] getIcon (HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File repository = new File("resources/img/");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items;
			try {
				items = upload.parseRequest(request);
				if (items != null) {
					byte[] icon = null;
					for (int i = 0; i < items.size();i += 2) {
						FileItem item = items.get(i);
						icon = item.get();
					}
					return icon;
				} else {
					return null;
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return null;
			}
	}
	public static byte[] getDefaultIcon(HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		//System.out.println(rootPath);
		File file = new File(rootPath+"resources/img/default.jpg");
		try {
			FileInputStream input = new FileInputStream(file);
			byte[] icon = new byte[(int)file.length()];
			input.read(icon);
			input.close();
			return icon;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	}
}
