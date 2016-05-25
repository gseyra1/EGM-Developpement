package gencsv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class CSVTest {

	private CSVConfiguration csvConf = new CSVConfiguration();

	public static int randomWithRange(Integer min, Integer max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}
        protected String getRandomString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder str = new StringBuilder();
		Random rnd = new Random();
		int valueSize = randomWithRange(25, 75);
		while (str.length() < valueSize) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			str.append(SALTCHARS.charAt(index));
		}

		return str.toString();

	}
	private String getRandomDate(UtilValue value) {
		DateFormat dateFormat = new SimpleDateFormat(value.getDateFormat());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, randomWithRange(1, 30));
		return dateFormat.format(cal.getTime());
	}



	private void createCsvContent(FileWriter writer) throws IOException {
		if (csvConf.getHeaderOrNot()== true){
                     createCsvHeader(writer);  
                    }
		createCsvBody(writer);

	}
	private void createCsvBody(FileWriter writer) throws IOException {
		int i = 0;
		while (((i++) < csvConf.getLines())) {
			Object[] header = csvConf.getColumns().keySet().toArray();
                        //if(csvConf.getSeqNum()!=null){
			//	writer.write(csvConf.getSeqNumIncr()+";");
			//}
			for (int column = 0; column < header.length; column++) {
				if (column != header.length - 1) {
					writeValues(writer, header, column);
					writer.write(";");
				} else {
					writeValues(writer, header, column);
				}

			}
			writer.write("\n");
		}
	}
        private void createCsvHeader(FileWriter writer) throws IOException {
		StringBuilder csvHeader = new StringBuilder();
		List<Column> header = new ArrayList<Column>(csvConf.getColumns().values());
		//if(csvConf.getSeqNum()!=null){
			//appendMiddleValue(csvHeader, "Sequence Number");
		//}
		for (int i = 0; i < header.size(); i++) {
			String headerValue=((Column) header.get(i)).getColumnName();
			if (i != header.size() - 1) {
				appendMiddleValue(csvHeader, headerValue);

			} else {
				csvHeader.append("\"");
				csvHeader.append(headerValue);
				csvHeader.append("\"\n");
			}
		}
		writer.write(csvHeader.toString());
	}
        
	private void writeValues(FileWriter writer, Object[] header, int pos) throws IOException {
            Column column=((Column)csvConf.getColumns().get(pos));
		switch (column.getColumnType()) {
		case "DATE":
			writer.write(getRandomDate(column.getUtils()));

			break;
		case "INTEGER":
			writer.write(Integer.toString(randomWithRange(Integer.valueOf(10), column.getUtils().getMaxSize())));

			break;
		case "STRING":
			writer.write(getRandomString());

			break;
                case "SEQ_NO":
                    if(column.getUtils().getStartSeqNum()!=null){
				writer.write(column.getUtils().getSeqNumIncr()+"");}
                                
			break;
		default:
			writer.write("default");
			break;
		}
	}
	private static void closeWriter(FileWriter writer) throws IOException {
		writer.flush();
		writer.close();
	}

	
	private void appendMiddleValue(StringBuilder csvHeader, String value) {
		csvHeader.append("\"");
		csvHeader.append(value);
		csvHeader.append("\";");
	}
        
	private FileWriter openCsvFile(String sFileName, StringBuilder fileContent) {
		try {
			FileWriter writer = new FileWriter(sFileName);
			return writer;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void loadConfiguration(String confFilePath) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(
				new FileReader(confFilePath))) {
			readHeaders(br);
		}

	}

	private void readHeaders(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", ""); // supprimer les blancs
			if (!line.isEmpty()) {
				switch (line) {
				case "[File Name]":
					loadFileName(br);
					break;
				case "[Volume]":
					loadVolume(br);
					break;
				case "[Columns]":
					loadColumns(br);
					break;
				case "[Seq_Num]":
					loadSeqNum(br);
                                        break;
                                case "[Header]":
                                        loadHeader(br);
                                        break;
				default:
					readHeaders(br);
                                        break;
				}
			}
		}
	}

	private void loadColumns(BufferedReader br) throws IOException {
		Map<Integer,Column> columns = new HashMap<Integer,Column>();
		String line;
		StringTokenizer token;
		int position =0;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
				token = new StringTokenizer(line, ";");// columnName ;
														// columnType
				while (token.hasMoreTokens()){
					columns.put(position,new Column(token.nextToken(), token.nextToken(), token.nextToken(),token.nextToken()));
					++position;
				}
				}
		}
		csvConf.setColumns(columns);
	}
	private void loadVolume(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
				csvConf.setLines(Integer.parseInt(line));
				return;
			}
		}

	}
        private void loadHeader(BufferedReader br) throws IOException{
        String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
				csvConf.setHeaderOrNot(Boolean.parseBoolean(line));
				return;
			}
		}

	}
	private void loadSeqNum(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
			//	csvConf.setSeqNum(Integer.parseInt(line));
				return;
			}
		}

	}
	private void loadFileName(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
				csvConf.setFileName(line);
				return;
			}
		}

	}

	public static void main(String[] args) throws IOException {
		CSVTest csvTest = new CSVTest();
                VersionDetails VerSion = new VersionDetails();
		String confFilePath = args[0];
		csvTest.loadConfiguration(confFilePath);
		StringBuilder myString = new StringBuilder();

		FileWriter writer = csvTest.openCsvFile(args[1], myString);
                if ("--version".equals(args[0])){
                    VerSion.showCurrentVersion();
                }
		csvTest.createCsvContent(writer);
		closeWriter(writer);
	}
}
