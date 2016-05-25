/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencsv;

/**
 *
 * @author AMG
 */
public class UtilValue {

	private Integer maxSize;
	private Integer startSeqNum;
	private String dateFormat;

	public UtilValue(String format, ConfTypes type)
			throws NumberFormatException {
		switch (type) {
		case INTEGER:
			this.setMaxSize(Integer.valueOf(format));
			return;
		case SEQ_NO:
			this.startSeqNum = Integer.valueOf(format);
			return;
		case DATE:
			this.setDateFormat(format);
		}
	}

	public Integer getStartSeqNum() {
		return startSeqNum;
	}

	public void setStartSeqNum(Integer seqNum) {
		this.startSeqNum = seqNum;
	}

	public Integer getSeqNumIncr() {
		startSeqNum = startSeqNum == null ? 0 : startSeqNum;// just in case
															// method called
															// without a check
		return ++startSeqNum;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

}
