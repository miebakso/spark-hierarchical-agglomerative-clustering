package main.scala
import org.apache.spark.{SparkConf, SparkContext}


object Main {
    def main(args: Array[String]): Unit ={
        var input:String = "file:///home/miebakso/Desktop/input.txt"
        var output:String = "file:///home/miebakso/Desktop/output"
        var numPar:Int = 2
        var maxObj:Int = 6
        var distType:Int = 0
        var cutOffDist:Double = 0.8
        var master:String = "local[2]"
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
        conf.set("spark.executor.memory", "10g")
        val sc = new SparkContext(conf)
        val dataReducer = new DataReducer(sc,numPar, maxObj, distType, cutOffDist, input, output)
        dataReducer.reduceData()

    }
}


