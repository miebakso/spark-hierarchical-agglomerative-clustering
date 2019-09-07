package main.scala

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class Dendrogram(nodeList:ListBuffer[Node], distType:Int) extends Serializable {
    private var dendrogram = new ArrayBuffer[Node]()
    private var nodeListCluster= new ArrayBuffer[ListBuffer[Node]]()
    private var distanceMatrix =  new ArrayBuffer[ArrayBuffer[Double]]()

    def getDendrogram(): Node = {
        dendrogram(0)
    }

    def generateDendrogram(): Unit ={
        var i = 0
        nodeList.foreach(node => {
            dendrogram += node
            nodeListCluster += new ListBuffer[Node]
            nodeListCluster(i) += node
            distanceMatrix += new ArrayBuffer[Double]()
            i+=1
        })
        i = 1
        var x = 0
        for(i <- 1 until distanceMatrix.length){
            for(x <- 0 until i){
                distanceMatrix(i) += findMinimumDistance(nodeListCluster(i),nodeListCluster(x))
            }
        }

        while(dendrogram.length !=1){
            var x = 1
            var y = 0
            var result = Double.MaxValue
            var coordinateX = 0
            var coordinateY = 0
            var temp = 0.0

            for(x <- 1 until distanceMatrix.length){
                for(y <- 0 until x){
                    temp = distanceMatrix(x)(y)
                    if(temp < result){
                        result = temp
                        coordinateX = x
                        coordinateY = y
                    }
                }
            }
            formClusterBetweenNearestNeighbour(coordinateX,coordinateY)
            recalculateMatrix(coordinateX,coordinateY)
        }
    }

    private def formClusterBetweenNearestNeighbour(x:Int,y:Int): Unit = {
        nodeListCluster(y) = nodeListCluster(y) ++ nodeListCluster(x)
        nodeListCluster.remove(x)
        val cluster = new Node()
        cluster.setDistance(distanceMatrix(x)(y))
        cluster.setLeftNode(dendrogram(y))
        cluster.setRightNode(dendrogram(x))
        dendrogram(y) = cluster
        dendrogram.remove(x)

    }

    private def recalculateMatrix(x:Int,y:Int): Unit ={
        distanceMatrix.remove(x)
        for(i <- x+1 until distanceMatrix.length){
            distanceMatrix(i).remove(x)
        }
        for(i <- y+1 until distanceMatrix.length){
            distanceMatrix(i)(y) = findMinimumDistance(nodeListCluster(i), nodeListCluster(y))
        }

    }

    private def findMinimumDistance(firstList:ListBuffer[Node],secondList:ListBuffer[Node]): Double ={
        if(distType == 0) calculateSingleLinkage(firstList,secondList)
        else if (distType == 1) calculateCompleteLinkage(firstList, secondList)
        else calculateCentroidLinkage(firstList,secondList)
    }

    private def calculateCentroidLinkage(firstList:ListBuffer[Node], secondList:ListBuffer[Node]): Double ={
        val length = firstList(0).getData().length
        val firstArr = new Array[Double](length)
        val secondArr = new Array[Double](length)
        var i = 0
        var max = firstList.length
        if(secondList.length > max) max = secondList.length
        while(i < max){
            if(i < firstList.length ){
                var index = 0;
                firstList(i).getData().foreach( data => {
                    firstArr(index) += data
                    index+=1
                })
            }
            if(i < secondList.length){
                var index = 0;
                secondList(i).getData().foreach( data => {
                    secondArr(index) += data
                    index+=1
                })
            }
            i+=1
        }
        i=0
        while(i<firstArr.length){
            firstArr(i) /= firstList.length
            secondArr(i) /= secondList.length
            i+=1
        }
        calculateDistance(firstArr,secondArr)
    }

    private def calculateSingleLinkage(firstList:ListBuffer[Node], secondList:ListBuffer[Node]): Double = {
        var min:Double = Double.MaxValue
        var result:Double = 0
        firstList.foreach( nodeA => {
            secondList.foreach( nodeB => {
                result = calculateDistance(nodeA.getData(), nodeB.getData())
                if(result < min) min = result
            })
        })
        min
    }

    private def calculateCompleteLinkage(firstList:ListBuffer[Node], secondList:ListBuffer[Node]): Double = {
        var max:Double = Double.MinValue
        var result:Double = 0
        firstList.foreach( nodeA => {
            secondList.foreach( nodeB => {
                result = calculateDistance(nodeA.getData(), nodeB.getData())
                if(result > max) max = result
            })
        })
        max
    }

    private def calculateDistance(firstArr:Array[Double], secondArr:Array[Double]): Double = {
        val n = firstArr.length-1
        var total:Double = 0
        for(i <- 0 to n){
            total +=Math.pow(firstArr(i)-secondArr(i),2)
        }
        Math.sqrt(total)
    }

}
