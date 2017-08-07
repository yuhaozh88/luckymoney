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

public class ImageDAO {
	
	/**
	 * �����û�ѡ���·��ѡ��ͼƬ
	 * @param path �û�ѡ��ͷ���·��
	 * @return ����byte����Ϊ�û�ѡ���ͷ�񣬷���Ϊnull��ѡ��ʧ��
	 */
	public static byte[] getIcon (String path,HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File repository = new File("F:/temp");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
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
			return null;
		}
	}
}
