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
        val result = mapData().aggregateByKey((ListBuffer.empty[Pattern], ListBuffer.empty[Node]))(
            (accumulator: (ListBuffer[Pattern],ListBuffer[Node]), node:Node) => {
                val nodeList = accumulator._2
                nodeList.append(node)
                var hasil = ""
//                print("Node: ")
                node.getData().foreach(data => {
                    hasil=hasil+data+" "
                })
                //println()

                println("Size patern "+accumulator._1.length+" Size list "+accumulator._2.length)
                if(nodeList.length == broadCastMaxObj.value){
                    val patterns:ListBuffer[Pattern] = new ListBuffer[Pattern]()
                    val dendrogram:Dendrogram = new Dendrogram(nodeList,broadCastDistanceType.value)
                    dendrogram.generateDendrogram()
                    val cluster = new Cluster(dendrogram.getDendrogram(),broadCastCutOffDist.value)
                    patterns.appendAll(cluster.computePatern())
                    accumulator._2.clear()
                    (accumulator._1 ++ patterns, accumulator._2)
                } else {
                    (accumulator._1, nodeList)
                }
            },
            (accumulator1, accumulator2) => {
                //println("testttttttttttt")
                println("l1 "+accumulator1._2.length)
                println("L2 "+accumulator2._2.length)
                if(accumulator1._2.nonEmpty){
                    println("masih ada isi acc1 "+accumulator1._2.length)
                    val patterns:ListBuffer[Pattern] = new ListBuffer[Pattern]()
                    val dendrogram:Dendrogram = new Dendrogram(accumulator1._2,broadCastDistanceType.value)
                    dendrogram.generateDendrogram()
                    val cluster = new Cluster(dendrogram.getDendrogram(),broadCastCutOffDist.value)
                    patterns.appendAll(cluster.computePatern())
                    accumulator1._1.appendAll(patterns)
                }
                if(accumulator2._2.nonEmpty){
                    println("masih ada isi acc2 "+accumulator1._2.length)
                    val patterns:ListBuffer[Pattern] = new ListBuffer[Pattern]()
                    val dendrogram:Dendrogram = new Dendrogram(accumulator2._2,broadCastDistanceType.value)
                    dendrogram.generateDendrogram()
                    val cluster = new Cluster(dendrogram.getDendrogram(),broadCastCutOffDist.value)
                    patterns.appendAll(cluster.computePatern())
                    accumulator2._1.appendAll(patterns)
                }
                //print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                (accumulator1._1 ++ accumulator2._1, ListBuffer.empty[Node])
            }
        )

        val parseResult = result.flatMapValues(patterns => {
            patterns._1.map(pattern => pattern)
        })

        val finalResult = parseResult.map( pattern => { pattern._2.getObjCount()+"\n"+
            pattern._2.getMinArr().mkString(",")+"\n"+
            pattern._2.getMaxArr().mkString(",")+"\n"+
            pattern._2.getAvgArr().mkString(",")+"\n"+
            pattern._2.getSDArr().mkString(",")
        })
        finalResult.repartition(numPar).saveAsTextFile(outputPath)
        broadCastMaxObj.destroy()
        broadCastDistanceType.destroy()
        broadCastCutOffDist.destroy()
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
            var hasil =""
            node.getData().foreach(data => hasil = hasil + data +" ")
            println(key+", "+hasil)
            (key,node)
        })
        result
    }


}




