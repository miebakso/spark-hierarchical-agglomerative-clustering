package test.scala

import main.scala.{Dendrogram, Node}

import scala.collection.mutable.ListBuffer

class DataReducereTest {
    val test = new MethodTest
    val firstArr = Array(10.0,20.0,30.0)
    val secondArr = Array(5.0,6.0,7.0)
    var firstList:ListBuffer[Node] = new ListBuffer[Node]
    var secondList:ListBuffer[Node] = new ListBuffer[Node]

    def run():Unit = {
        val arr11 = Array(4.0,5.0)
        val arr12 = Array(3.0,7.0)
        //3.5 6.0

        val arr21 = Array(4.0,3.0)
        val arr22 = Array(10.0,7.0)
        val arr23 = Array(10.0,10.0)
        //8.0 20/3

        val node1 = new Node()
        node1.setData(arr11)
        val node2 = new Node()
        node2.setData(arr12)

        val node3 = new Node()
        node3.setData(arr21)
        val node4 = new Node()
        node4.setData(arr22)
        val node5 = new Node()
        node5.setData(arr23)

        firstList+=node1
        firstList+=node2

        secondList+=node3
        secondList+=node4
        secondList+=node5
        testSingleLingkageTest()
        testCompleteLingkageTest()
        testAverageLingkageTest()
        testMinumumDistance()
    }


    private def testSingleLingkageTest(): Unit = {
        println("SINGLE LINGKAGE "+test.calculateSingleLinkage(firstList,secondList))
    }

    private def testCompleteLingkageTest(): Unit = {
        println("COMPLETE LINGKAGE "+test.calculateCompleteLinkage(firstList,secondList))
    }

    private def testAverageLingkageTest(): Unit = {
        println("AVERAGE LINGKAGE "+test.calculateAverageLinkage(firstList,secondList))
    }

    private def testMinumumDistance(): Unit = {
        println("DISTANCE "+test.calculateDistance(firstArr,secondArr))
    }


}
