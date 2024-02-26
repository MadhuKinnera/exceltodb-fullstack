package com.madhu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madhu.entity.ExcelData;

public interface ExcelDataRepository extends JpaRepository<ExcelData, Long> {
}
