package main.scala

import scala.collection.mutable.ListBuffer

class MaxObjectList(maxObject:Int) extends Serializable {
    private val maxObjectList:ListBuffer[Node] = new ListBuffer[Node]()

    def addNode(node:Node): Unit ={
        maxObjectList+=node
    }

    def getList(): ListBuffer[Node] = {
        maxObjectList
    }

    def clearList(): Unit = {
        maxObjectList.clear()
    }
}
