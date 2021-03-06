import java.sql.*;
import java.util.Iterator;
import java.util.Map;

public class DateSource {

    Connection c;

    private static String URL = "jdbc:hsqldb:hsql://localhost/testdb;shutdown=true";
    private static String USER = "SA";
    private static String PASSWORD = "";

    private static String CREATE_LOG_TABLE_QUERY =
            "CREATE TABLE LOG " +
            "(id VARCHAR(255) not NULL, " +
            " duration NUMERIC , " +
            " type VARCHAR(255), " +
            " host VARCHAR(255), " +
            " alert BOOLEAN, " +
            " PRIMARY KEY ( id ))";

    private static final String SQL_INSERT =
            "INSERT INTO " +
            "LOG(ID, DURATION, TYPE, HOST, ALERT) " +
            "VALUES (?,?,?,?,?)";


    public DateSource() {
        try {
            this.c = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean logTableExist(){

        try {
            DatabaseMetaData meta = c.getMetaData();
            ResultSet res = meta.getTables(null, null, "LOG",
                    new String[] {"TABLE"});
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return false;
    }

    public void createLogTable(){
        Statement statement = null;
        try {
            statement = c.createStatement();
            statement.executeUpdate(CREATE_LOG_TABLE_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertValue(Map<String, Log> logMap){
        Iterator<Map.Entry<String, Log>> iterator = logMap.entrySet().iterator();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = c.prepareStatement(SQL_INSERT);
            while(iterator.hasNext()){

                Log log = iterator.next().getValue();
                preparedStatement.setString(1, log.getId());
                preparedStatement.setLong(2, log.getDuration());
                preparedStatement.setString(3, log.getType());
                preparedStatement.setString(4, log.getHost());
                preparedStatement.setBoolean(5, log.isAlert());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void destroy(){
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
