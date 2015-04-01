In the first step program solves nonlinear system of equations defined by the iterative approach X_{i+1} = A * X_i +B where X_k is k-th iteration of the nonlinear system and prints the results after 1-10 iterations. 
The starting values are given as (1,3).

In the second step the program iterates through the solution values from the first step and calculates the logarithm of the max norm of a matrix |x(n)-x(n-1)|.
The Max-norm of a matrix definition can be found at: http://en.wikipedia.org/wiki/Matrix_norm#Max_norm


Compile with:
javac NonlinearSystem.java

Execute  with:
java NonlinearSystem
