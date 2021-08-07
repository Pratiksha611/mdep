package com.pratiket.common.configuration;

import com.pratiket.common.constants.MdepConstants;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @author Pratiksha Deshmukh
 * created on 06-08-2021
 */
public class SparkSessionFactory
{

    private SparkSessionFactory()
    {
    }

    private static final SparkSession sparkSession;

    static {
        synchronized (SparkSessionFactory.class) {
            SparkConf sparkConf = new SparkConf();
            sparkConf.set(MdepConstants.SPARK_UI_ENABLED, MdepConstants.FALSE);
            sparkConf.set(MdepConstants.SPARK_DRIVER_HOST, MdepConstants.SPARK_LOCAL_HOST);
            sparkSession = SparkSession.builder()
                    .appName(MdepConstants.SPARK_APP_NAME)
                    .config(sparkConf)
                    .master("local[*]")
                    .getOrCreate();
            System.out.println("Created successfully.");
        }
    }

    public static SparkSession getSparkSession()
    {
        return sparkSession;
    }

    public static void test()
    {
        if(null == sparkSession)
            System.out.println("Session is null");
        else {
            System.out.println("Session is not null");
            Dataset<Row> dataSet = sparkSession.read()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://localhost/EXAM?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false")
                    .option("user", "root")
                    .option("password", "Newuser@123")
                    .option("driver", "com.mysql.jdbc.Driver")
                    .option("dbtable", "SELECT * FROM EMP")
                    .load();
            dataSet.show();
        }
    }
}
