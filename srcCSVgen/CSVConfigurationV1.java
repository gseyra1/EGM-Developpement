package tracfin;


import java.util.Map;
// cette classe permet de définir les caractéristiques du fichier de conf qui sera en input.
public class CSVConfiguration {
	private String fileName;
	private Integer lines,seqNum,QtFile;
        private Boolean HeaderOrNot;
	private Map<Integer,Column> columns;
        
	public CSVConfiguration(){
		super();
	}
        
        public Integer getQtFile() {
        return QtFile;
        }
        public void setQtFile(Integer QtFile) {
        this.QtFile = QtFile;
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

	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
        public Integer getSeqNumIncr() {
		seqNum= seqNum==null ? 0 : seqNum;//just in case method called without a check
		return ++seqNum;
	}
        
        public Boolean getHeaderOrNot(){
                return HeaderOrNot;
        }
        public void setHeaderOrNot (Boolean HeaderOrNot){
                this.HeaderOrNot = HeaderOrNot;
                }
	
}
