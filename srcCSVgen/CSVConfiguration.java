package gencsv;

import java.util.Map;

public class CSVConfiguration {
	private String fileName;
	private Integer lines;
        private Boolean HeaderOrNot;
        private Map<Integer,Column> columns;
    public Boolean getHeaderOrNot() {
        return HeaderOrNot;
    }
    public void setHeaderOrNot(Boolean HeaderOrNot) {
        this.HeaderOrNot = HeaderOrNot;
    }
	
	public CSVConfiguration(){
		super();
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getLines() {
		return lines;
	}
	public void setLines(Integer lines) {
		this.lines = lines;
	}
	public Map<Integer,Column> getColumns() {
		return columns;
	}
	public void setColumns(Map<Integer,Column> columns) {
		this.columns = columns;
	}

	
	
}
