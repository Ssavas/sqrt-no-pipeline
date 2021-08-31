@Author: Suleyman Savas - Halmstad University, 2017-08-22
contact: suleyman_savas@hotmail.com
 
Square Root implementation based on the Harmonized Parabolic Synthesis method 
by Erik Hertz.

The hardware description language that is used for thihs implementation is Chisel. The implementation is without any pipelines. Therefore it is entirely combinational and produces a result in a single cycle. There is a pipelined version under my git account.

The implementation is synthesized on two different FPGAs. The results and further dicussions can be found in my article title "Using Harmonized Parabolic Synthesis to Implement a Single-Precision Floating-Point SquareRoot Unit" (the link is coming soon). Please refer to it if you use this implementation.

How to compile:

- Go into the folder
- To run the tests and generate harness:
sbt "run --backend c --compile --test --genHarness"

- Generating verilog:
sbt "run --backend v --genHarness"

More info on Chisel: https://www.chisel-lang.org/
