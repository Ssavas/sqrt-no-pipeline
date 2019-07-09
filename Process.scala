/*
	Suleyman Savas
	2017-09-04
	Halmstad University

	Main processing part of the square root method.
	
*/

package SquareRoot

import chisel3._
import chisel3.util._

class Process extends Module{
	val io = IO(new Bundle{
		val X_in = Input(Bits(width = 26.W))
		val Y_out = Output(Bits(width = 23.W))
	})

	// instantiate lookup tables
	val tableC = Module(new lookupC())
	val tableL = Module(new lookupL())
	val tableJ = Module(new lookupJ())

	val x_w                   = Wire(Bits(20.W))
	val s1                    = Wire(Bits(26.W))
	val jx                    = Wire(Bits(42.W))
	val jx_adjusted           = Wire(Bits(22.W))
	val x_w2                  = Wire(Bits(40.W))
	val x_w2_adjusted         = Wire(Bits(27.W))
	val cx_w2                 = Wire(Bits(43.W))
	val cx_w2_adjusted        = Wire(Bits(16.W))
	val l_minus_jx            = Wire(Bits(28.W))
	val l_minus_jx_plus_cx_w2 = Wire(Bits(28.W))
	val s1s2                  = Wire(Bits(55.W))

	val coeffAddr   = io.X_in(25, 20)
	tableC.io.addr := coeffAddr
	tableL.io.addr := coeffAddr
	tableJ.io.addr := coeffAddr

	val tmp = Wire(Bits(7.W))
	x_w := io.X_in(19, 0)		//x_20
	s1  := io.X_in

	jx          := tableJ.io.out * x_w
	jx_adjusted := jx(41, 20)

	x_w2          := x_w * x_w
	x_w2_adjusted := x_w2(39, 13)

	cx_w2          := tableC.io.out * x_w2_adjusted
	cx_w2_adjusted := cx_w2(42, 27)

	l_minus_jx            := tableL.io.out - jx_adjusted
	l_minus_jx_plus_cx_w2 := l_minus_jx + cx_w2_adjusted

	s1s2 := s1 * l_minus_jx_plus_cx_w2

	io.Y_out := s1s2(52, 30)

//printf("process in: %d out: %d\n", io.X_in, io.Y_out)

//printf("L    : %d J    : %d C    : %d squarer    :%d x_w     : %d s1    : %d \n", tableL.io.out, tableJ.io.out, tableC.io.out, x_w2_adjusted, x_w, s1)
	
}
