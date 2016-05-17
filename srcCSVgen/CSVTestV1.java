package tracfin;


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

	public static int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	private static void closeWriter(FileWriter writer) throws IOException {
		writer.flush();
		writer.close();
	}

	private void createCsvContent(FileWriter writer) throws IOException {
		int a = csvConf.getQtFile();
                while (a!= 0){
                if (csvConf.getHeaderOrNot()== true){
                     createCsvHeader(writer);
                     --a;
                     
                 }
		createCsvBody(writer);
                }
	}
        

	private void createCsvBody(FileWriter writer) throws IOException {
		int i = 0;
		while (((i++) < csvConf.getLines())) {
			Object[] header = csvConf.getColumns().keySet().toArray();
			/*if(csvConf.getSeqNum()!=null){
				writer.write(csvConf.getSeqNumIncr()+";");
			}*/
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
	}//

	private void writeValues(FileWriter writer, Object[] header, int pos)
			throws IOException {
		switch (((Column)csvConf.getColumns().get(pos)).getColumnType()) {
		case "DATE":
			writer.write(getRandomDate());

			break;
		case "INTEGER":
			writer.write(Integer.toString(randomWithRange(10, 100)));

			break;
		case "STRING":
			writer.write(getRandomString());
                                
			break;
                case "SEQ_NO":
			if(csvConf.getSeqNum()!=null){
				writer.write(csvConf.getSeqNumIncr()+"");}
                                
			break;
                        
		default:
			writer.write("default");
			break;
		}
	}
        //Traitement en fonction du type de la colonne.

	protected String getRandomString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder str = new StringBuilder();
		Random rnd = new Random();
		int valueSize = randomWithRange(25, 40);
		while (str.length() < valueSize) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			str.append(SALTCHARS.charAt(index));
		}
         
		return str.toString();
	}
        //Génération aléatoire de valeurs de type String parmi l'alphabet

	private String getRandomDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, randomWithRange(1, 30));
		return dateFormat.format(cal.getTime());
	}// génération de date aléatoire.

	private void createCsvHeader(FileWriter writer) throws IOException {
		StringBuilder csvHeader = new StringBuilder();
               
		List<Column> header = new ArrayList<Column>(csvConf.getColumns().values());
		/*if(csvConf.getSeqNum()!=null){
			appendMiddleValue(csvHeader, "Sequence Number");
		}*/
                    for (int i = 0; i < header.size(); i++) {
			String headerValue=((Column) header.get(i)).getColumnName();
			if (i != header.size() - 1) {
				appendMiddleValue(csvHeader, headerValue);

			} else {
				csvHeader.append("\"");
				csvHeader.append(headerValue);
				csvHeader.append("\"\n");
			
                        }
		writer.write(csvHeader.toString());
                }
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
	}//Création du header du fichier en sortie.

	private void loadConfiguration(String confFilePath) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(
				new FileReader(confFilePath))) {
			readHeaders(br);
		}

	}//Chragement des paramètres en fichier de conf.

	private void readHeaders(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", ""); // supprimer les blancs
			if (!line.isEmpty()) {
				switch (line) {
				case "[File Name]":
					loadFileName(br);
					break;
                                case "[QtFile]":
                                        loadQtFile(br);
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
	}// La lecture et l'interpretation du fichier de conf pour utiliser les paramètres correctement
        
        private void loadQtFile(BufferedReader br) throws IOException {
                String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
				csvConf.setQtFile(Integer.parseInt(line));
				return;
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
				if (token.hasMoreTokens()){
					columns.put(position,new Column(token.nextToken(), token.nextToken(),null));
					++position;
				}
				}
		}
		csvConf.setColumns(columns);
	}//Chargement des colonnes.

	private void loadVolume(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
				csvConf.setLines(Integer.parseInt(line));
				return;
			}
		}

	}//Chargement du volume.

	private void loadSeqNum(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
				csvConf.setSeqNum(Integer.parseInt(line));
				return;
			}
		}

	}//Chargement de la valeur de départ du sequence number
        
        private void loadHeader(BufferedReader br) throws IOException {
                String line;
                while ((line = br.readLine())!= null){
                        line.replaceAll("\\s", "");
                        if(!line.isEmpty()){
                            csvConf.setHeaderOrNot(Boolean.parseBoolean(line));
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

	}//Chargement du nom du fichier. mais il est possible de le passer directement par paramètre.

	public static void main(String[] args) throws IOException {
		CSVTest csvTest = new CSVTest();
                VersionDetails VerSion = new VersionDetails();
		String confFilePath = args[0];//Argument fichier de configuration.
		csvTest.loadConfiguration(confFilePath);
		StringBuilder myString = new StringBuilder();
                if ("--version".equals(args[0])){
                VerSion.showCurrentVersion();}
		FileWriter writer = csvTest.openCsvFile(args[1], myString);//Argument fichier de destination.
                csvTest.createCsvContent(writer);
		closeWriter(writer);
                
	}
}

