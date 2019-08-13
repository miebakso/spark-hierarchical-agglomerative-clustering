package main.scala

class Patern(max:Array[Double], min:Array[Double], avg:Array[Double], SD:Array[Double], objCount:Int) extends Serializable {

    def getMaxArr(): Array[Double] ={
        max
    }

    def getMinArr(): Array[Double] ={
        min
    }

    def getAvgArr(): Array[Double] ={
        avg
    }

    def getSDArr(): Array[Double] ={
        SD
    }

    def getObjCount(): Int ={
        objCount
    }
}
