package com.csmtech.exporter;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.csmtech.model.Candidate;

public class ResultExcelExporter {
	
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Candidate> candidates;

	public ResultExcelExporter(List<Candidate> candidates) {
		this.candidates = candidates;
		workbook = new XSSFWorkbook();
	}
	 private void writeHeaderLine() {
	        sheet = workbook.createSheet("result");
	         
	        Row titleRow = sheet.createRow(0);
	        Cell titleCell = titleRow.createCell(0);
	        titleCell.setCellValue("Result");

	        // Merge cells for the title row
	        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

	        // Create a cell style for the title
	        CellStyle titleStyle = workbook.createCellStyle();
	        XSSFFont titleFont = workbook.createFont();
	        titleFont.setBold(true);
	        titleStyle.setFont(titleFont);
	        titleStyle.setAlignment(HorizontalAlignment.CENTER);

	        // Apply the style to the title cell
	        titleCell.setCellStyle(titleStyle);
	         
	        Row row = sheet.createRow(1);
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setBold(true);
	        style.setFont(font);
	        style.setAlignment(HorizontalAlignment.CENTER);
	               
	        createCell(row, 0, "Candidate College Name", style);
	        createCell(row, 1, "Candidate Id", style); 
	        createCell(row, 2, "Subtest Taker", style); 
	        createCell(row, 3, "Mark Appear", style);
	        createCell(row, 4, "Total Mark", style);
	       
	
	         
	    }
	 private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	        sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        if (value instanceof Integer) {
	            cell.setCellValue((Integer) value);
	        } else if (value instanceof Boolean) {
	            cell.setCellValue((Boolean) value);
	        } else if(value instanceof Double) {
	        	cell.setCellValue((Double)value);
	        }
	        else {
	            cell.setCellValue((String) value);
	        }
	        
	        cell.setCellStyle(style);
	    }
	   private void writeDataLines() {
	        int rowCount = 1;
	 
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setFontHeight(14);
	        style.setFont(font);
	                 
			
			  for (Candidate cand : candidates) 
			  { 
				  int i = 0;
				  i++;
				  
				  Row row = sheet.createRow(rowCount++);
			  int columnCount = 0;
			  
			  // createCell(row, columnCount++, user.getId(), style); //
			  createCell(row,columnCount++, i, style); 
			  createCell(row, columnCount++, cand.getCandidateemail(), style); 
			  createCell(row, columnCount++, cand.getSubTestTaker().getSubTestTakerName(), style); 
			  createCell(row, columnCount++, cand.getMarkAppear(), style); 
			  createCell(row, columnCount++, cand.getTotalMark(), style); 
			  
			  
			  }
			 
	    }
	   public void export(HttpServletResponse response) throws IOException {
	        writeHeaderLine();
	        writeDataLines();
	         
	        ServletOutputStream outputStream = response.getOutputStream();
	        workbook.write(outputStream);
	        workbook.close();
	         
	        outputStream.close();
	         
	    }

}
