package com.poscodx.fileupload.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.fileupload.exception.FileUploadServiceException;

/**
 * 클라이언트가 업로드한 파일을 저장하고,
 * 해당 파일에 대한 URL을 생성하여 반환
 */

@Service
public class FileUploadService {
	private static String SAVE_PATH = "/mysite-uploads";
	private static String URL_PATH = "/images";
	
	public String restore(MultipartFile file) {
		String url = null;
		
		try {
			File uploadDirectory = new File(SAVE_PATH); // 업로드할 파일을 저장할 디렉토리 지정
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}
			
			if(file.isEmpty()) { // 업로드한 파일이 비어있는지 확인
				return url;
			}
			
			String originFilename = file.getOriginalFilename(); // 업로드한 파일의 원본 파일 이름
			String extName = originFilename.substring(originFilename.lastIndexOf(".")); // 확장자는 뒤에서 찾기
			String saveFilename = generateSaveFilename(extName); // 저장될 파일의 이름
			Long fileSize = file.getSize();
			
			System.out.println("########" + originFilename);
			System.out.println("########" + saveFilename);
			System.out.println("########" + fileSize);
			
			byte[] data = file.getBytes(); // 업로드할 파일의 데이터를 바이트 배열로 읽어오기
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename); // 파일을 디스크에 저장하기 위한 OutputStream 생성
			os.write(data); // OutputStream을 통해 파일로 데이터를 저장
			os.close();
			
			url = URL_PATH + "/" + saveFilename; // 파일이 저장된 경로를 기반으로 파일에 접근할 수 있는 URL 생성
			
		} catch (IOException ex) {
			throw new FileUploadServiceException(ex.toString());
		}
		
		return url;
	}

	// 파일의 이름을 현재 시간과 확장자를 조합
	private String generateSaveFilename(String extName) { 
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += extName;

		return filename;
	}
	
}
