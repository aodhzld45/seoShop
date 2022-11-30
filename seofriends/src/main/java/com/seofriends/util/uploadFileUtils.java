package com.seofriends.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

@Component("uploadfileutils")
public class uploadFileUtils {
//	업로드 작업
//	파일업로드시 날짜 폴더를 생성하여 파일을 관리.
//	Thumnail 이미지 생성. - 업로드한 파일이 이미지 또는 일반파일인지 체크
//	byte[]배열로 다운로드.

//	1)파일 업로드 작업. 리턴값 : 업로드한 실제 파일명(DB저장.)
	public static String uploadFile(String uploadFolder,String uploadDateFolderPath, MultipartFile uploadFile) {
		
		String uploadFileName = ""; //실제 업로드한 파일명.
		
//		1)업로드 날짜폴더 생성 (현재 날짜)
//		String uploadDateFolderPath = getFolder();  // "2022/07/19"
		File uploadPath = new File(uploadFolder, uploadDateFolderPath);
		
//		폴더의 존재 유무 판단
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs(); // 하위 디렉토리 까지 생성.
		}
		
//		클라이언트에서 업로드한 파일명.
		String uploadClientFileName = uploadFile.getOriginalFilename();
		
		//중복되지 않는 고유의 문자열 생성
		UUID uuid = UUID.randomUUID();
		
		//업로드시 중복되지 않는 파일이름을 생성.
		uploadFileName = uuid.toString() + "_" + uploadClientFileName;

		try {
			//유일한 파일이름으로 객체생성
			File saveFile = new File(uploadPath, uploadFileName);
			uploadFile.transferTo(saveFile); //파일 업로드 됨(파일이 복사됨.)
			
//			4)파일정보 : 이미지 또는 일반 파일 여부
			if (checkImageType(saveFile)) {
			//attachFileDTO.setImage(true); // 기본값은 false
//			썸네일 작업 : 원본이미지를 대사응  사본이미지를 해상도의 손실을 줄이고, 크기를 작제 작업한다.
			FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
			Thumbnailator.createThumbnail(uploadFile.getInputStream(), thumbnail, 100, 100);
			thumbnail.close();
			
		}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return uploadFileName; // 실제 업로드한 파일명(날짜 폴더명 제외)
		
	}
	
	
//	1) 날짜를 이용한 업로드 폴더 생성및 폴더이름 반환.
	public static String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date); //"2022-06-29" 형태의 날짜 포맷이 저장되어 들어감
		
		//File.separator : 운영체제에 따라서 파일경로 구분자를 반환. 예) 윈도우 C:\temp\... 리눅스 /home/etc/...
		return str.replace("-", File.separator); // "2022-06-29" -> "2022\06\29"
	}
	
//	이미지 파일여부를 체크.
	private static boolean checkImageType(File saveFile) {
		
		boolean isImage = false;
		
		try {
			String contentType = Files.probeContentType(saveFile.toPath());//text/html, text/plain, 등등
			isImage = contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isImage; 	
	}
	
	//이미지를 바이트배열로 읽어오는 작업
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) {
		File file = new File(uploadPath, fileName); //이미지파일 정보를 이용하여 File 객체를 생성.
		
//		이미지가 존재하지 않을 경우
		if (!file.exists()) {
			uploadPath = "D:\\DEV\\upload\\temp";
			fileName = "no_img.jpg";
			
			file = new File(uploadPath, fileName);
		}
		
		
		ResponseEntity<byte[]> entity = null;

//		브라우저에게 서버에서 보내는 데이타에 대한 설명.
		HttpHeaders headers = new HttpHeaders();
		try {
//			브라우저에게 보낼 데이타의 MIME정보(image/png, image/jpg, img/gif 등) 패킷의 헤더부분에 추가된다.
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return entity;	
	}
	
	//파일 다운로드
	public ResponseEntity<Resource> downloadFile(@RequestHeader("user-Agent") String userAgent, String fileName){
		// user-Agent : 사용자의 브라우저 정보를 확인하여, 선택적인 작업을 할수가 있다.
		ResponseEntity<Resource> entity = null;
		
		Resource resource = new FileSystemResource("D:\\DEV\\upload\\" + fileName);
		
		if (resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		
//		클라이언트가 보낸 파일명.
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		HttpHeaders headers = new HttpHeaders();
		
		String downloadName = null;
		
		try {
			downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return entity = new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		
	}
	
	
//	파일삭제
	public static void deleteFile(String uploadPath, String fileName) {
//		윈도우 운영체제 : 경로 구분자. \(역슬래쉬.) 
//		원본이미지, 썸네일이미지 : 파일 2개를 삭제
		
		/*
		 * uploadpath : c:/dev/upload
		 * filename : 2022/07/21 21197e7d-c93c-4e81-ab3b-d773e3c8162d_super.PNG
		 */
		//index 0부터 11까지 잘라서 가져옴 (날짜정보만)
	
		String front = fileName.substring(0, 11); // 2022/07/21/

		String end = fileName.substring(13);
		
		String origin = front + end; // 원본 파일명 .
		//File.separator : 운영체제에 따라서 파일경로 구분자를 반환. 예) 윈도우 C:\temp\... 리눅스 /home/etc/...
//		원본이미지 파일 삭제
		new File(uploadPath+origin.replace('/', File.separatorChar)).delete();
		
//		썸네일 파일삭제 2022/07/21/s_21197e7d-c93c-4e81-ab3b-d773e3c8162d_super.PNG
		new File(uploadPath+fileName.replace('/', File.separatorChar)).delete();
	
	}
	///////////	
	
	
}