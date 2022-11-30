package com.seofriends.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//@RequiredArgsConstructor : final or @Non 필드를 대상으로 생성자 메서드가 생성
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
//@NoArgsConstructor //파라미터가 없는 디폴트 생성자 생성
@AllArgsConstructor //	클래스의 모든 필드를 이용한 생성자 메서드 생성
public class LoginDTO {
	
	private String mem_id;
	private String mem_pw;
	
	
	
	
}
