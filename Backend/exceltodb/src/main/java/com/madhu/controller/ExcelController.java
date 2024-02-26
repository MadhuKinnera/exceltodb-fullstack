package com.madhu.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.madhu.entity.ExcelData;
import com.madhu.service.ExcelService;

@RestController
@RequestMapping("/excel")
@CrossOrigin("*")
public class ExcelController {

	@Autowired
	private ExcelService excelService;

	@PostMapping("/upload")
	public ResponseEntity<List<String>> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		System.out.println("Inside Uploading File ");
		try {
			Pair<List<ExcelData>, List<String>> pairResponse = excelService.processExcelFile(file);
			List<ExcelData> dataList = pairResponse.getFirst();
			List<String> errorMessages = pairResponse.getSecond();
			excelService.saveExcelData(dataList);

			if (errorMessages.isEmpty())
				errorMessages.add("File uploaded and data stored successfully.");
			return ResponseEntity.ok(errorMessages);
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<ExcelData>> getAllExcelData() throws Exception {
		List<ExcelData> excelDataList = excelService.getAllData();
		return ResponseEntity.ok(excelDataList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ExcelData> deleteExcelDataById(@PathVariable Long id) throws Exception {
		ExcelData data = excelService.deleteById(id);
		return ResponseEntity.ok(data);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ExcelData> updateExcelData(@PathVariable Long id, @RequestBody ExcelData data)
			throws Exception {

		ExcelData response = excelService.updateExcelData(id, data);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/paginate/{page}")
	public Page<ExcelData> getPaginatedRecords(@PathVariable int page,
			@RequestParam(defaultValue = "50") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return excelService.getPaginatedRecords(pageRequest);
	}

}
