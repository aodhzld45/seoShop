package com.seofriends.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/*
 * 	brd_num				NUMBER								CONSTRAINT PK_BOARD_NUM PRIMARY KEY, 
	mem_id				VARCHAR2(15)						NOT NULL,
	brd_title			VARCHAR2(100)						NOT NULL,
	brd_content			VARCHAR2(4000)					    NOT NULL,
	brd_file_folder		VARCHAR2(50)						NOT NULL,
    brd_file			VARCHAR2(50)						NOT NULL,
	brd_date_reg		DATE DEFAULT SYSDATE			    NOT NULL,
    REPLYCNT NUMBER(*,0) DEFAULT '0' 
 * */
@Data
public class BoardVO {
	
	private Long brd_num;
	private String mem_id;
	private String brd_title;
	private String brd_content;
//	private String brd_file_folder;
//	private String brd_file;
//	private boolean brd_file_type;
	private Date brd_date_reg;
	
	private int replycnt;
	
// 	상품 이미지 파일(업로드)
	//	<input type="file"  class="form-control" id="" name="uploadFile"  >
	private MultipartFile uploadFile; //첨부된 파일을 name 값을 가져옴. 파일이 여러개면 배열로 생성 -> input type에 multiple 속성 추가.
	

	
	
}
