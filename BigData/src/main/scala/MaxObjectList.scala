package main.scala

import scala.collection.mutable.ListBuffer

class MaxObjectList(maxObject:Int) extends Serializable {
    private val singleTree:ListBuffer[Node] = new ListBuffer[Node]()

    def addNode(node:Node): Unit ={
        singleTree+=node
    }

    def getList(): ListBuffer[Node] = {
        singleTree
    }

    def clearList(): Unit = {
        singleTree.clear()
    }
}
