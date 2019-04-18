package main.scala

import org.apache.spark.util.AccumulatorV2

class PaternAccumulator(var patterns:List[Patern]) extends AccumulatorV2[List[Patern],List[Patern]]{
    override def isZero: Boolean = patterns.isEmpty

    override def copy(): AccumulatorV2[List[Patern], List[Patern]] = new PaternAccumulator(patterns)

    override def reset(): Unit = {
        patterns = List[Patern]()
    }

    override def add(v: List[Patern]): Unit = {
        patterns = patterns ++  v
    }

    override def merge(other: AccumulatorV2[List[Patern], List[Patern]]): Unit = {
        patterns = patterns ++ other.value
    }

    override def value: List[Patern] = patterns
}
