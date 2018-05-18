package excelmongo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ExcelReadJsonMongo {

	private static AomUpdateRequest aomUpdateRequest;
	private static Map<String, String> row_out;

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		// TODO Auto-generated method stub

		Workbook workbook = WorkbookFactory.create(new File("sample.xlsx"));
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

		/*
		 * =============================================================
		 * Iterating over all the sheets in the workbook (Multiple ways)
		 * =============================================================
		 */

		System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
		workbook.forEach(sheet -> {
			// System.out.println("=> " + sheet.getSheetName());
			if (sheet.getSheetName().equals("Team Roster")) {
				aomUpdateRequest = new AomUpdateRequest();
				List columnHeaderList = aomUpdateRequest.getColumnHeadersList();
				List rows = aomUpdateRequest.getRows();
				sheet.forEach(row -> {
					if (row.getRowNum() > 0) {
						System.out.println(row.getRowNum());
						row_out = new HashMap();
						row.forEach(cell -> {
							System.out.print("(" + cell.getColumnIndex() + ")" + cell + "\t");
							row_out.put(columnHeaderList.get(cell.getColumnIndex()).toString(), cell.toString());
						});
						rows.add(row_out);
						System.out.println();
					} else {
						row.forEach(cell -> {
							System.out.print("(" + cell.getColumnIndex() + ")" + cell + "\t");
							columnHeaderList.add(cell);
						});
					}
				});
			}
			// System.out.println("\n\nIterating over Rows and Columns using
			// Java 8 forEach with lambda\n");
			System.out.println(aomUpdateRequest);

		});

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String value = objectMapper.writeValueAsString(aomUpdateRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		HttpEntity<String> entity = new HttpEntity<String>(value, headers);
//		URI location = template.postForLocation("http://example.com", entity);
		System.out.println();

	}

}
