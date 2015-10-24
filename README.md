# neojam1
Neo's 1st Code Jam - My Scala Code

The Google Code Jam practice is here: 
https://code.google.com/codejam/contest/90101/dashboard#s=p0 

I'm taking a stab at doing this in Scala, and hoping to learn some functional tricks along the way. 

# Interesting learnings
- Original naive implementation was not very "functional". 32s runtime against "large" dataset
- Second iteration refactored loops with tail-recursive functions. 10s runtime against "large" dataset
- Third iteration. Small change to use "parallel" lists. 5s runtime against "large" dataset

# TODO
Tail recursive functions are still not "pure". i.e. they have side-effects on the iterators *and* an accumulator buffer outside their immediate scope. Perhaps use the "accumulator" pattern to resolve this. 
