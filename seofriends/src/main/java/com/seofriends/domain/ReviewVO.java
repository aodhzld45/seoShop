package com.seofriends.domain;

import java.util.Date;

import lombok.Data;

/*rv_num, mem_id, pdt_num, rv_content, rv_score, rv_date_reg*/
@Data
public class ReviewVO {
	
	private Integer rv_num;
	private String mem_id;
	private Integer pdt_num;
	private String rv_content;
	private int rv_score;
	private Date rv_date_reg;

}
