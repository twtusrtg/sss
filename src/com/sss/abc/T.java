package com.sss.abc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class T {

	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream("D:\\1_work_record\\yidaigou\\1.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					XSSFCell no = xssfRow.getCell(0);
					XSSFCell name = xssfRow.getCell(1);
					XSSFCell age = xssfRow.getCell(2);
					//XSSFCell score = xssfRow.getCell(3);
					
					System.out.println(no+"===="+age);
					/*student.setNo(getValue(no));
					student.setName(getValue(name));
					student.setAge(getValue(age));
					student.setScore(Float.valueOf(getValue(score)));
					list.add(student);*/
					System.out.println(1111111111);
				}
			}
		}

	}

	@SuppressWarnings("static-access")
	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

}
