package org.intervals.intervals

import java.lang.ArithmeticException

class Interval(ac: Double, bc: Double) {
    var a: Double = ac
    var b: Double = bc
    if (a > b) {
        val tmp = a
        a = b
        b = tmp
    }
 
    def contains(x: Double): Boolean =
        x >= this.a && x <= this.b
    def contains(x: Interval): Boolean = 
        x.a >= this.a && x.b <= this.b

    def +(that: Interval): Interval =
        new Interval(this.a + that.a, this.b + that.b)
    def -(that: Interval): Interval =
        new Interval(this.a – that.b, this.b – that.a)

    def *(that: Interval) : Interval = {
        val all = List(this.a * that.a, this.a * that.b,
                       this.b * that.a, this.b * that.b)
        new Interval(all.min, all.max)
    }

    def /(that: Interval) : Interval = {
        if (that.contains(0.0)) {
            throw new ArithmeticException(“Division by an interval containing zero”)
        }
        val all = List(this.a / that.a, this.a / that.b,
                       this.b / that.a, this.b / that.b)
        new Interval(all.min, all.max)
    }

    def ==(that: Interval): Boolean =
        this.a == that.a && this.b == that.b

    def <(that: Interval): Boolean =
        this.b < that.a

    def >(that: Interval): Boolean =
        this.a > that.b
     
    override def toString(): String =
        "[" + this.a + ", " + this.b + "]"
}
