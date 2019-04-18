package main.scala

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark._
import org.apache.spark.rdd.RDD

import scala.collection.mutable.ListBuffer


class DataReducer(sc: SparkContext, numPar:Int, maxObj:Int, distanceType:Int, cutOffDistance:Double, inputPath: String, outputPath:String) extends Serializable {

    def reduceData():Unit = {
        val data = mapData()
        //sbt pprintln(data.partitions.length)
        val repartitionData = data.repartition(numPar)
        repartitionData.persist()
        val broadCastMaxObj = sc.broadcast(maxObj)
        val broadCastDistanceType = sc.broadcast(distanceType)
        val broadCastCutOffDist = sc.broadcast(cutOffDistance)
        val patternsAcc = sc.collectionAccumulator[List[Patern]]("Paterns Accumulator")
        val paternAccumulator = new PaternAccumulator(List[Patern]())
        sc.register(paternAccumulator, "Patern Accumulator")
        repartitionData.foreachPartition(partition => {
            var i:Int = 0;
            var isProcessed:Boolean = false
            val maxObjList:MaxObjectList = new MaxObjectList(broadCastMaxObj.value)
            partition.foreach(record => {
                isProcessed = false
                i+=1
                maxObjList.addNode(record)
                if(i==broadCastMaxObj.value){
                    val dendrogram:Dendrogram = new Dendrogram(maxObjList.getList(),broadCastDistanceType.value)
                    dendrogram.generateDendrogram()
                    val cluster = new Cluster(dendrogram.getDendrogram(),broadCastCutOffDist.value)
                    paternAccumulator.add(cluster.computePatern())
                    isProcessed = true
                    i=0
                    maxObjList.clearList()
                }

            })
            if(isProcessed==false){
                val dendrogram:Dendrogram = new Dendrogram(maxObjList.getList(),broadCastDistanceType.value)
                dendrogram.generateDendrogram()
                val cluster = new Cluster(dendrogram.getDendrogram(),broadCastCutOffDist.value)
                paternAccumulator.add(cluster.computePatern())
                maxObjList.clearList()
            }

        })
        repartitionData.unpersist()
        broadCastMaxObj.destroy()
        broadCastDistanceType.destroy()
        broadCastCutOffDist.destroy()

        val result:RDD[Patern] = sc.parallelize(paternAccumulator.value, numPar-)
        val formatedResult = result.map( x => x.getObjCount()+"\n"
            +x.getMinArr().mkString(",")+"\n"
            +x.getMaxArr().mkString(",")+"\n"
            +x.getAvgArr().mkString(",")+"\n"
            +x.getSDArr().mkString(",")
        )
        formatedResult.saveAsTextFile(outputPath)

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




