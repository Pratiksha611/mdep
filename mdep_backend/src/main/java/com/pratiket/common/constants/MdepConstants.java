package com.pratiket.common.constants;

/**
 * @author Pratiksha Deshmukh
 * created on 06-08-2021
 */
public final class MdepConstants {

    public static final String SPARK_UI_ENABLED = "spark.ui.enabled";
    public static final String SPARK_APP_NAME = "mdep-backend-spark";
    public static final String SPARK_MASTER = "spark://infpu7432:7077";
    public static final String SPARK_DRIVER_HOST = "spark.driver.host";
    public static final String SPARK_LOCAL_HOST = "localhost";
    public static final String FALSE = "false";

    public final class ErrorMessageConstants {
        public static final String CONNECTION_ID_NULL = "Connection ID should not be blank or null";
        public static final String CONNECTION_CONFIGURATION_NULL = "Connection Configuration should not be blank or null";
        public static final String CONNECTION_FAILED_MSG = "Connection failed to test";
        public static final String STATUS = "STATUS";
        public static final String MESSAGE = "MESSAGE";
        public static final String DETAIL_MESSAGE = "DETAIL_MESSAGE";
        public static final String CONNECTION_SUCCESS_MSG = "Connection tested successfully.";
    }

    public final class MysqlConstants
    {
        public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
        public static final String CONNECTION_URL = "jdbc:mysql://%s:%s?useSSL=false";
        public static final String SPARK_CONNECTION_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
        public static final String SHOW_DATABASE_QUERY = "SELECT schema_name  FROM information_schema.schemata;";
        public static final String GET_TABLE_QUERY = "SELECT table_name FROM information_schema.tables WHERE table_schema = '%s';";
        public static final String JDBC = "jdbc";
        public static final String URL = "url";
        public static final String USER = "user";
        public static final String PASSWORD = "password";
        public static final String DRIVER = "driver";
        public static final String DBTABLE = "dbtable";

    }

    public final class MongodbConstants
    {
        public static final String CONNECTION_URL = "mongodb://%s:%s@%s:%s";
        public static final String URI = "uri";
        public static final String DATABASE = "database";
        public static final String IP_COLLECTION = "spark.mongodb.input.collection";
        public static final String OP_COLLECTION = "spark.mongodb.output.collection";

    }
}
