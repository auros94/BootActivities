package excelmongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class AomUpdateRequest {

	List<String> columnHeadersList;
	@JsonBackReference
	List<Map<String, String>> rows;
	String requestor;

	public AomUpdateRequest() {
		this.columnHeadersList = new ArrayList<>();
		this.rows = new ArrayList<>();
	}
	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public List getColumnHeadersList() {
		return columnHeadersList;
	}

	public void setColumnHeadersList(List columnHeadersList) {
		this.columnHeadersList = columnHeadersList;
	}
	public List<Map<String, String>> getRows() {
		return rows;
	}
	public void setRows(List<Map<String, String>> rows) {
		this.rows = rows;
	}
}
