package main.scala
import org.apache.spark.{SparkConf, SparkContext}


object Main {
    def main(args: Array[String]): Unit ={
        var input:String = "file:///home/miebakso/Desktop/input.txt"
        var output:String = "file:///home/miebakso/Desktop/output"
        var numPar = 20
        var maxObj = 30
        var distType = 2
        var cutOffDist = 0.6
        var master = "local"
        if(args.length > 0){
            master = "yarn-cluster"
            input = args(0)
            output = args(1)
            numPar = args(2).toInt
            maxObj = args(3).toInt
            distType = args(4).toInt
            cutOffDist = args(5).toDouble
        }

        val conf = new SparkConf()
        conf.setMaster(master)
        conf.setAppName("Reduce Data Spark")
        conf.set("spark.executor.instances","3")
        conf.set("spark.executor.cores", "5")
        val sc = new SparkContext(conf)


        val dataReducer = new DataReducer(sc,numPar, maxObj,0, 0.6, input, output)
        dataReducer.reduceData()

    }
}


