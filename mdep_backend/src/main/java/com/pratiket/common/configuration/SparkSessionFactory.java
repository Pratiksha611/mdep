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

}
