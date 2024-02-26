package com.madhu.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.madhu.entity.ExcelData;
import com.madhu.repository.ExcelDataRepository;
import com.madhu.util.Utility;

@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private ExcelDataRepository excelDataRepository;

	@SuppressWarnings("resource")
	@Override
	public Pair<List<ExcelData>, List<String>> processExcelFile(MultipartFile file) throws Exception {
		List<ExcelData> dataList = new ArrayList<>();

		List<String> errorMessages = new ArrayList<>();

		int errorCount = 1;

		try {

			InputStream inputStream = file.getInputStream();

			Workbook workbook;
			if (file.getOriginalFilename().endsWith(".xls")) {
				workbook = new HSSFWorkbook(inputStream); // For .xls files
			} else if (file.getOriginalFilename().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream); // For .xlsx files
			} else {
				throw new IllegalArgumentException("Invalid file format. Please upload a valid Excel file.");
			}
			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {

				try {
					ExcelData data = new ExcelData();
					data.setMarket(row.getCell(0).getStringCellValue());
					data.setCountry(row.getCell(1).getStringCellValue());
					data.setProduct(row.getCell(2).getStringCellValue());
					data.setDiscountBand(row.getCell(3).getStringCellValue());
					data.setUnitsSold(row.getCell(4).getNumericCellValue());
					data.setManufacturing(row.getCell(5).getNumericCellValue());

					dataList.add(data);
				} catch (Exception e) {
					errorMessages.add(errorCount++ + "." + e.getMessage());
				}
			}
		} catch (Exception e) {
			throw new Exception("Error While Uploading File");
		}

		return Pair.of(dataList, errorMessages);
	}

	@Override
	public void saveExcelData(List<ExcelData> dataList) {
		excelDataRepository.saveAll(dataList);
	}

	@Override
	public List<ExcelData> getAllData() throws Exception {
		List<ExcelData> excelDataList = excelDataRepository.findAll();

		if (excelDataList.isEmpty())
			throw new Exception("Data Not Found");

		return excelDataList;

	}

	@Override
	public ExcelData deleteById(Long id) throws Exception {

		ExcelData deletedData = excelDataRepository.findById(id)
				.orElseThrow(() -> new Exception("Excel Data Id Not Found "));

		excelDataRepository.delete(deletedData);

		return deletedData;

	}

	@Override
	public ExcelData updateExcelData(Long id, ExcelData data) throws Exception {

		Optional<ExcelData> excelDataOptional = excelDataRepository.findById(id);

		if (excelDataOptional.isPresent()) {

			data.setId(id);

			return excelDataRepository.save(data);

		} else {
			throw new Exception("Updating " + id + " Excel Data Failed");
		}
	}

	@Override
	public Page<ExcelData> getPaginatedRecords(Pageable pageable) {
		return excelDataRepository.findAll(pageable);
	}
}
