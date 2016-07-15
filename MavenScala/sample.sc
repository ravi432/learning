object sample {
// sum of integers between two numberds a and b
def sumInts(a:Int,b:Int):Int={
  if(a>b)0 else a+sumInts(a+1, b)
}
def cube(x:Int):Int=x*x*x

// sum of cubes between numbers

def sumCubes(a:Int,b:Int):Int={

if(a>b)0 else cube(a)+sumCubes(a+1, b)
}
// sum of factorial of all numbers between a and b

def fact(x:Int):Int = if(x==0)1 else x*fact(x-1)

def sumFactorial(a:Int,b:Int):Int={
if(a>b)0 else fact(a)+sumFactorial(a+1, b)
}
sumInts(1, 2)
sumCubes(1, 2)
sumFactorial(1, 2)
// generalizing sum funcxtion as below

//def sum(f:Int => Int,a:Int,b:Int):Int=
//{
 //if(a>b)0 else f(a)+sum(f,a+1,b)
//}
def id(a:Int):Int=a;
//val x = sum(id,1, 4)
//val y= sum(cube,1, 4)
//val z=sum(fact,1, 4)

//def ssumInts(a:Int,b:Int)=sum(x=>x,a,b)
//val r= ssumInts(1, 2)
//def sssumInts=sum(x=>x)
//def sssumCubes=sum(x=>x*x*x)
//def ssssFact=sum(fact)
def sum(f: Int => Int) (a:Int, b:Int): Int = {
if (a > b) 0
else f(a) + sum(f)(a + 1, b)
//sumF
}
sum(x=>x,1,2)
 sum (cube)(1,9)









  

}