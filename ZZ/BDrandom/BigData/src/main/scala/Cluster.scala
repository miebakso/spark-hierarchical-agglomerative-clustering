package main.scala

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class Cluster(dendrogram:Node, cutOffDistance:Double) extends Serializable {
    private val clusters:ListBuffer[Node] = new ListBuffer[Node]()

    private def formClusterFromDendrogram(): Unit = {
        val bfs:ListBuffer[Node] = new ListBuffer[Node]
        bfs+=dendrogram
        val distance = cutOffDistance * dendrogram.getDistance()
        while(bfs.length!=0){
            var node = bfs.remove(0)
            if(node.getDistance() <= distance){
                clusters+=node
            } else {
                var left = node.getLeftNode()
                var right = node.getRightNode()
                if(left!=null){
                    bfs+=left
                }
                if(right!=null){
                    bfs+=right
                }
            }
        }
    }

    def computePatern(): ListBuffer[Pattern] = {
        formClusterFromDendrogram()
        val paterns:ListBuffer[Pattern] = new ListBuffer[Pattern]()
        clusters.foreach( cluster => {
            paterns += processCluster(cluster)
        })
        paterns
    }

    private def processCluster(cluster: Node): Pattern ={
        val bfs:ListBuffer[Node] = new ListBuffer[Node]()
        val min:ArrayBuffer[Double] = new ArrayBuffer[Double]()
        val max:ArrayBuffer[Double] = new ArrayBuffer[Double]()
        val avg:ArrayBuffer[Double] = new ArrayBuffer[Double]()
        val SD:ArrayBuffer[Double] = new ArrayBuffer[Double]()
        bfs+=cluster
        var count = 0
        var i=0
        while(bfs.length!=0){
            val node = bfs.remove(0)
            val data = node.getData()
            if(data!=null){
                if(min.length==0){
                    data.foreach(value => {
                        min+=value
                        max+=value
                        avg+=value
                    })
                } else {
                    i=0
                    data.foreach(value => {
                        if(value < min(i)) min(i) = value
                        if(value > max(i)) max(i) = value
                        avg(i) += value
                        i+=1
                    })
                }
                count+=1
            } else {
                val leftNode = node.getLeftNode()
                val rightNode = node.getRightNode()
                if(leftNode!=null){
                    bfs+=leftNode
                }
                if(rightNode!=null){
                    bfs+=rightNode
                }
            }
        }
        i =0;
        avg.foreach( value => {
            avg(i) /= count
            i+=1
        })

        bfs+=cluster
        while(bfs.length!=0){
            val node = bfs.remove(0)
            val data = node.getData()
            if(data!=null){
                if(SD.length==0){
                    i=0
                    data.foreach(value => {
                        SD += Math.pow((value - avg(i)),2)
                        i+=1
                    })
                } else {
                    i=0
                    data.foreach(value => {
                        SD(i) += Math.pow((value - avg(i)),2)
                        i+=1
                    })
                }
            } else {
                val leftNode = node.getLeftNode()
                val rightNode = node.getRightNode()
                if(leftNode!=null){
                    bfs+=leftNode
                }
                if(rightNode!=null){
                    bfs+=rightNode
                }
            }
        }
        i =0;
        SD.foreach( value => {
            if(count == 1){
                SD(i) = 0
            } else {
                SD(i) = Math.sqrt((SD(i) / (count - 1)));
                i += 1
            }
        })


        new Pattern(max.toArray,min.toArray,avg.toArray,SD.toArray,count)
    }


}
