package com.pratiket.common.configuration;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.config.WriteConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import com.pratiket.common.constants.MdepConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.crypto.Encryptor;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pratiksha Deshmukh
 * created on 06-08-2021
 */
public class SparkSessionFactory
{

    private SparkSessionFactory()
    {
    }

    private static SparkSession sparkSession;

    private static JavaSparkContext javaSparkContext;

    static {
        SparkConf sparkConf = new SparkConf();
        sparkConf.set(MdepConstants.SPARK_UI_ENABLED, MdepConstants.FALSE);
        sparkConf.set(MdepConstants.SPARK_DRIVER_HOST, MdepConstants.SPARK_LOCAL_HOST);
        sparkSession = SparkSession.builder()
                .appName(MdepConstants.SPARK_APP_NAME)
                .config(sparkConf)
                .master("local[*]")
                .getOrCreate();
        javaSparkContext = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
    }
    public static SparkSession getSparkSession()
    {
        return sparkSession;
    }

    public static JavaSparkContext getJavaSparkContext() {
        return javaSparkContext;
    }

    public static void mysql_read_write()
    {
//        SparkConf sparkConf = new SparkConf();
//        sparkConf.set(MdepConstants.SPARK_UI_ENABLED, MdepConstants.FALSE);
//        sparkConf.set(MdepConstants.SPARK_DRIVER_HOST, MdepConstants.SPARK_LOCAL_HOST);
//        sparkSession = SparkSession.builder()
//                .appName(MdepConstants.SPARK_APP_NAME)
//                .config(sparkConf)
//                .master("local[*]")
//                .getOrCreate();
        System.out.println("Created successfully.");
        if(null == sparkSession)
            System.out.println("Session is null");
        else {
            System.out.println("Session is not null");
            Dataset<Row> dataSet = sparkSession.read()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://localhost/exam?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false")
                    .option("user", "root")
                    .option("password", "Newuser@123")
                    .option("driver", "com.mysql.cj.jdbc.Driver")
                    .option("dbtable", "EMP")
                    .load();
            dataSet.show();

            dataSet.createOrReplaceTempView("test");
            Dataset<Row> ds = sparkSession.sql("select EMPNO, ENAME, JOB from test");

            dataSet.write()
                    .format("jdbc")
                    .option("url","jdbc:mysql://localhost:3306/exam?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false")
                    .option("dbtable","EMP_OP")
                    .option("user","root")
                    .option("driver", "com.mysql.cj.jdbc.Driver")
                    .option("password","Newuser@123")
                    .mode(SaveMode.Overwrite)
                    .save();

            ds.write()
                    .format("jdbc")
                    .option("url","jdbc:mysql://localhost:3306/exam?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false")
                    .option("dbtable","EMP_FILTER")
                    .option("user","root")
                    .option("driver", "com.mysql.cj.jdbc.Driver")
                    .option("password","Newuser@123")
                    .mode(SaveMode.Overwrite)
                    .save();
        }
    }

    public static void mongodb_read_write()
    {
//        SparkConf sparkConf = new SparkConf();
//        sparkConf.set(MdepConstants.SPARK_UI_ENABLED, MdepConstants.FALSE);
//        sparkConf.set(MdepConstants.SPARK_DRIVER_HOST, MdepConstants.SPARK_LOCAL_HOST);
//        sparkSession = SparkSession.builder()
//                .appName(MdepConstants.SPARK_APP_NAME)
//                .config(sparkConf)
//                .master("local[*]")
//                .getOrCreate();

        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put("uri",
                "mongodb://root:root@localhost:27017");
        readOverrides.put("database", "video");
        readOverrides.put("spark.mongodb.input.collection", "movieDetails");
        ReadConfig readConfig = ReadConfig.create(readOverrides);
        Dataset<Row> dataset = MongoSpark.load(javaSparkContext, readConfig).toDF();

        System.out.println("Count: " + dataset.count());
        dataset.show();

        // Write
        Map<String, String> writeOverrides = new HashMap<>();
        writeOverrides.put("uri",
                "mongodb://root:root@localhost:27017");
        writeOverrides.put("database", "video");
        writeOverrides.put("spark.mongodb.output.collection", "movieDetailsOP1");
        WriteConfig writeConfig = WriteConfig.create(writeOverrides);
        Dataset<Row> filtered = dataset.filter(dataset.col("title").startsWith("Star Trek II: The Wrath of Khan")).persist();
        MongoSpark.save(filtered, writeConfig);
    }
}
