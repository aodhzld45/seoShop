package com.seofriends.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.extern.log4j.Log4j;
@Log4j
@Component("qrutil")

public class QRUtil {

	public static String MakeQR(String content, String filePath) {
			
		String fileName = "";
		
	try {
		File file = new File(content); //저장할 파일경로 객체 생성
		if (!file.exists()) { //파일 경로가 존재하지 않으면 폴더 생성
			file.mkdir();
		}
		
		//QR코드 그리기
		QRCodeWriter qr = new QRCodeWriter(); 
		
		BitMatrix bitMatrix = qr.encode(content, BarcodeFormat.QR_CODE, 200, 200);
		
		BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		
		fileName = UUID.randomUUID().toString() + ".png"; //랜덤 이름값 부여
		
		ImageIO.write(bufferedImage, "png", new File(filePath + fileName));//.png이미지 파일로 생성
		
		} catch (Exception e) {
//			log.info(e.getMessage());
			e.printStackTrace();
		}
		return fileName;		
	}
	
	
	
	
	
	// 생성된 QR 코드를 읽는 readQR Method
	public static String readQR(String filePath) throws IOException, NotFoundException {
		BufferedImage bufferedImage = ImageIO.read(new File(filePath));
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		
		Result result = new MultiFormatReader().decode(bitmap);
		
		return result.getText();
	
	} 
	

	
}