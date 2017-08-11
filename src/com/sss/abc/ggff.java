package com.sss.abc;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ggff {
	public void readExcel(String filePath) {

		try {

			Workbook workBook = null;
			FileInputStream in = new FileInputStream(filePath);
			try {
				workBook = new XSSFWorkbook(filePath); // ֧��2007
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(in); // ֧��2003����ǰ
			}

			// ���Excel�й��������
			//System.out.println("��������� :" + workBook.getNumberOfSheets());

			// ѭ��ÿ��������
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				// ����������
				Sheet sheet = workBook.getSheetAt(i);

				int rows = sheet.getPhysicalNumberOfRows(); // �������
				if (rows > 0) {
					sheet.getMargin(Sheet.TopMargin);
					for (int r = 0; r < rows; r++) { // ��ѭ��
						Row row = sheet.getRow(r);
						if (row != null) {

							int cells = row.getLastCellNum();// �������
							for (short c = 0; c < cells; c++) { // ��ѭ��
								Cell cell = row.getCell(c);

								if (cell != null) {
									String value = getValue(cell);

									System.out.print(value);
								}
							}
							System.out.println("");
						}
					}
				}

				new FileInputStream(filePath).close();

				// ��ѯ�ϲ��ĵ�Ԫ��
				for (i = 0; i < sheet.getNumMergedRegions(); i++) {
					// System.out.println("��" + i + "���ϲ���Ԫ��");
					CellRangeAddress region = sheet.getMergedRegion(i);
					int row = region.getLastRow() - region.getFirstRow() + 1;
					int col = region.getLastColumn() - region.getFirstColumn()
							+ 1;
					System.out.println("��ʼ��:" + region.getFirstRow());
					System.out.println("��ʼ��:" + region.getFirstColumn());
					System.out.println("��ռ��:" + row);
					System.out.println("��ռ��:" + col);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getValue(Cell cell) {

		String value = "";
		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_NUMERIC: // ��ֵ��
			if (DateUtil.isCellDateFormatted(cell)) {
				// �����date������ ����ȡ��cell��dateֵ
				value = DateUtil.getJavaDate(cell.getNumericCellValue())
						.toString();
			} else {// ������
				value = String.valueOf(cell.getNumericCellValue());
			}
			break;
		/* ���б�ʾ��Ԫ�������Ϊstring���� */
		case Cell.CELL_TYPE_STRING: // �ַ�����
			value = cell.getRichStringCellValue().toString();
			break;
		case Cell.CELL_TYPE_FORMULA:// ��ʽ��
			// ����ʽ����ֵ
			value = String.valueOf(cell.getNumericCellValue());
			if (value.equals("NaN")) {// �����ȡ������ֵΪ�Ƿ�ֵ,��ת��Ϊ��ȡ�ַ���
				value = cell.getRichStringCellValue().toString();
			}
			// cell.getCellFormula();����ʽ
			break;
		case Cell.CELL_TYPE_BOOLEAN:// ����
			value = " " + cell.getBooleanCellValue();
			break;
		/* ���б�ʾ�õ�Ԫ��ֵΪ�� */
		case Cell.CELL_TYPE_BLANK: // ��ֵ
			value = "";
			break;
		case Cell.CELL_TYPE_ERROR: // ����
			value = "";
			break;
		default:
			value = cell.getRichStringCellValue().toString();
		}

		return value;
	}

	public static void main(String args[]) {

		ggff im = new ggff();

		// im.readExcel("d:/123.xls");
		im.readExcel("d:/aa.xlsx");
		// im.readExcel("F:/2007.xls"); //2007�±���Ϊ2003

	}

}
