package test.scala

import main.scala.Node
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class MethodTest() {


    def calculateAverageLinkage(firstList:ListBuffer[Node], secondList:ListBuffer[Node]): Double ={
        val length = firstList(0).getData().length
        var firstArr = new Array[Double](length)
        var secondArr = new Array[Double](length)
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
            println(firstArr(i))
            secondArr(i) /= secondList.length
            println(secondArr(i))
            i+=1
        }
        calculateDistance(firstArr,secondArr)
    }

    def calculateSingleLinkage(firstList:ListBuffer[Node], secondList:ListBuffer[Node]): Double = {
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

    def calculateCompleteLinkage(firstList:ListBuffer[Node], secondList:ListBuffer[Node]): Double = {
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

    def calculateDistance(firstArr:Array[Double], secondArr:Array[Double]): Double = {
        val n = firstArr.length-1
        var total:Double = 0
        for(i <- 0 to n){
            total +=Math.pow(firstArr(i)-secondArr(i),2)
        }
        Math.sqrt(total)
    }




}
