package com.madhu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

import com.madhu.entity.ExcelData;

public interface ExcelService {

	public Pair<List<ExcelData>, List<String>> processExcelFile(MultipartFile file) throws Exception;

	public void saveExcelData(List<ExcelData> dataList);

	public List<ExcelData> getAllData() throws Exception;

	public ExcelData deleteById(Long id) throws Exception;

	public ExcelData updateExcelData(Long id, ExcelData data) throws Exception;
	
	public Page<ExcelData> getPaginatedRecords(Pageable pageable);

}
