package com.study.springboot.service;

import java.util.List;
import java.util.Map;

import com.study.springboot.dto.BasicBbsDto;

public interface BasicBbsService {
	//전체 목록 보기
	public List<BasicBbsDto> getList();
	//상세보기
	public BasicBbsDto getView(int id);
	//작성하기
	public int writePost(Map<String, String> map);
	//삭제하기
	public int deletePost(int id);
	//전체 목록 갯수
	public int countPost();

}
