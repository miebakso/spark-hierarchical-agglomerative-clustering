package main.scala

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD


import scala.collection.mutable.ListBuffer
import scala.util.Random


class DataReducer(sc: SparkContext, numPar:Int, maxObj:Int, distanceType:Int, cutOffDistance:Double, inputPath: String, outputPath:String) extends Serializable {

    def reduceData():Unit = {
        val broadCastMaxObj = sc.broadcast(maxObj)
        val broadCastDistanceType = sc.broadcast(distanceType)
        val broadCastCutOffDist = sc.broadcast(cutOffDistance)
        val totalAcc = sc.doubleAccumulator("total")
        val nAcc = sc.longAccumulator("n")
        val allAcc = sc.doubleAccumulator("all")
        val n2Acc = sc.longAccumulator("n2")
        val result = mapData.groupByKey(numPar).map(record => {
            var paterns:ListBuffer[Pattern] = new ListBuffer[Pattern]()
            var i:Int = 0
            var isProcessed:Boolean = false
            var objectList:ListBuffer[Node] = new ListBuffer[Node]()
            record._2.foreach(record => {
                isProcessed = false
                i+=1
                objectList+=record
                if(i==broadCastMaxObj.value){
                    val dendrogram:Dendrogram = new Dendrogram(objectList,broadCastDistanceType.value)
                    dendrogram.generateDendrogram()
                    val cluster = new Cluster(dendrogram.getDendrogram(),broadCastCutOffDist.value)
                    paterns = paterns ++ cluster.computePatern()
                    totalAcc.add(cluster.getTotal())
                    allAcc.add(cluster.getAll())
                    nAcc.add(cluster.getN())
                    n2Acc.add(cluster.getN2())
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
                totalAcc.add(cluster.getTotal())
                allAcc.add(cluster.getAll())
                nAcc.add(cluster.getN())
                n2Acc.add(cluster.getN2())
            }
            paterns.toIterator
        })

        val parseResult = result.flatMap(patterns => {
            patterns.map(pattern => pattern)
        })

        val finalResult = parseResult.map( pattern => { pattern.getObjCount()+"\n"+
            pattern.getMinArr().mkString(",")+"\n"+
            pattern.getMaxArr().mkString(",")+"\n"+
            pattern.getAvgArr().mkString(",")+"\n"+
            pattern.getSDArr().mkString(",")
        }).saveAsTextFile(outputPath)

        broadCastMaxObj.destroy()
        broadCastDistanceType.destroy()
        broadCastCutOffDist.destroy()
        println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        println("TOTAL "+totalAcc.value/nAcc.value)
        println("BRANCHES "+allAcc.value/n2Acc.value)
        sc.stop()

    }

    private def loadData():RDD[String] = {
        sc.textFile(inputPath)
    }

    private def mapData():RDD[(Int,Node)] = {
        val broadCastNpar = sc.broadcast(numPar)
        val result = loadData().map(lines => {
            val node = new Node()
            node.setData(lines.split(",").map(_.toDouble))
            val key = Random.nextInt(broadCastNpar.value)
            (key,node)
        })
        result
    }


}




