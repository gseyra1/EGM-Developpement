package gencsv;

public class Column {

    private String columnName;
    private String columnType;
    private String guillements;
    private UtilValue utils;

    public UtilValue getUtils() {
        return utils;
    }

    public void setUtils(UtilValue utils) {
        this.utils = utils;
    }

    public void setGuillements(String guillements) {
        this.guillements = guillements;
    }

    public Column(String columnName, String columnType, UtilValue utils) {
          this.columnName = columnName;
        this.columnType = columnType;
        this.utils = utils;
    }

    public Column(String columnName, String columnType, String format, String guillements) {
        super();
        this.columnName = columnName;
        this.columnType = columnType;
        this.utils = new UtilValue(format,ConfTypes.valueOf(columnType));
        this.guillements = guillements;
    }

    public Column() {
        super();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }


}
