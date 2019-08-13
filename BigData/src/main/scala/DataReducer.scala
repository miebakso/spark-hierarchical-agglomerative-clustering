package main.scala

import org.apache.spark.{SparkContext}
import org.apache.spark.rdd.RDD
import scala.collection.mutable.{ListBuffer}


class DataReducer(sc: SparkContext, numPar:Int, maxObj:Int, distanceType:Int, cutOffDistance:Double, inputPath: String, outputPath:String) extends Serializable {

    def reduceData():Unit = {
        val data = mapData()
        val repartitionData = data.repartition(numPar)
        val broadCastMaxObj = sc.broadcast(maxObj)
        val broadCastDistanceType = sc.broadcast(distanceType)
        val broadCastCutOffDist = sc.broadcast(cutOffDistance)
        val results = repartitionData.mapPartitions(partitions => {
            var paterns:ListBuffer[Patern] = new ListBuffer[Patern]()
            var i:Int = 0
            var isProcessed:Boolean = false
            var objectList:ListBuffer[Node] = new ListBuffer[Node]()
            partitions.foreach(record => {
                isProcessed = false
                i+=1
                objectList+=record
                if(i==broadCastMaxObj.value){
                    val dendrogram:Dendrogram = new Dendrogram(objectList,broadCastDistanceType.value)
                    dendrogram.generateDendrogram()
                    val cluster = new Cluster(dendrogram.getDendrogram(),broadCastCutOffDist.value)
                    paterns = paterns ++ cluster.computePatern()
                    isProcessed = true
                    i=0
                    objectList.clear()
                }
            })
            if(isProcessed==false){
                val dendrogram:Dendrogram = new Dendrogram(objectList,broadCastDistanceType.value)
                dendrogram.generateDendrogram()
                val cluster = new Cluster(dendrogram.getDendrogram(),broadCastCutOffDist.value)
                paterns = paterns ++ cluster.computePatern()
            }
            paterns.toIterator

        })
        results.map(x => x.getObjCount()+"\n"
            +x.getMinArr().mkString(",")+"\n"
            +x.getMaxArr().mkString(",")+"\n"
            +x.getAvgArr().mkString(",")+"\n"
            +x.getSDArr().mkString(",")
        ).saveAsTextFile(outputPath)
        broadCastMaxObj.destroy()
        broadCastDistanceType.destroy()
        broadCastCutOffDist.destroy()
        sc.stop()
    }


    private def loadData():RDD[String] = {
        sc.textFile(inputPath)
    }

    private def mapData():RDD[Node] = {
        loadData().map(lines => {
            val node = new Node()
            node.setData(lines.split(",").map(_.toDouble))
            node
        })
    }


}




