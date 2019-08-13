package main.scala

class Node() extends Serializable {
    private var data:Array[Double] = null
    private var distance:Double = -1
    private var rightNode:Node = null
    private var leftNode:Node = null

    def setData(data: Array[Double]): Unit ={
        this.data = data
    }

    def setDistance(distance: Double): Unit = {
        this.distance = distance
    }

    def setRightNode(node:Node): Unit = {
        this.rightNode = node
    }

    def setLeftNode(node:Node): Unit = {
        this.leftNode = node
    }

    def getData(): Array[Double] ={
        this.data
    }

    def getDistance():Double = {
        this.distance
    }

    def getRightNode(): Node = {
        this.rightNode
    }

    def getLeftNode(): Node = {
        this.leftNode
    }


}
