/*
	Suleyman Savas
	2017-08-22
	Halmstad University
*/

package SquareRoot

import chisel3._
import chisel3.util._
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class sqrt() extends Module {
	val io = IO(new Bundle{
		val input  = Input(UInt(32.W))
		val output = Output(UInt(32.W))
	})

	val preProcessor  = Module(new PreProcess())
	val processor     = Module(new Process())
	val postProcessor = Module(new PostProcess())

	when(io.input(30, 23) === 0.U){	//when the input is 0, preprocessing and postprocessing screws
		io.output := 0.U
	}
	.otherwise{
		preProcessor.io.in      := io.input
		processor.io.X_in       := preProcessor.io.out1
		postProcessor.io.Y_in   := processor.io.Y_out
		postProcessor.io.exp_in := preProcessor.io.out2
		io.output               := postProcessor.io.Z_out
	}
	
	//printf("input: %d output: %d\n", io.input, io.output)
}

class SqrtTest(c: sqrt) extends PeekPokeTester(c) {

/*
	In matlab I use typecast(single(4), 'uint32') to get the ieee single precision format value with
	the representation in uint format.
*/
	poke(c.io.input, 1082130432.U);	// 4.0
	//expect(c.io.output, 1073741824.U);	//2.0
	step(1);
	poke(c.io.input, 1130561536.U);	// 227
	//expect(c.io.output, 1097928822.U); //15.066519173319364
	step(1);

	poke(c.io.input, 0.U);	// 227
	//expect(c.io.output, 1097928822.U); //15.066519173319364
	step(1);

	poke(c.io.input, 1018070301.U);	// 0.0213037
	//expect(c.io.output, 0.U); //15.066519173319364
	step(1);

	poke(c.io.input, 1028070301.U);	//
	//expect(c.io.output, 0.U); //
	step(1);

	poke(c.io.input, 1038070301.U);	//	0.1092264
	step(1);

	poke(c.io.input, 1190797887.U);	//	32017.123
	step(1);
}

object sqrt {
  def main(args: Array[String]): Unit = {
    if (!Driver(() => new sqrt())(c => new SqrtTest(c))) System.exit(1)
  }
}
