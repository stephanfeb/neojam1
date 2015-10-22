package sh.hashbangbin.neojam1

import scala.Char
import scala.collection.mutable.ArrayBuffer
import scala.io.Source._

object ParseJam extends App{
  val FILE_NAME = "A-large-practice.in"

  //get the test groups
  def findMatch(wordLength: Int, word: String, testStr: String): Boolean = {

    //iterate over the word string, char by char
    var buff = new ArrayBuffer[Char] //mutable buffer to collect my matches
    val wcIter = word.iterator

    while (wcIter hasNext) {
      var testCharIter = testStr iterator

      while (testCharIter hasNext) {

        //break out if we reach end of our word iterator
        if (! wcIter.hasNext){
          return false
        }

        var wc = wcIter next
        var tc = testCharIter.next
        //wc = wcIter.next
        if (tc == '('){
           //ff to next ")"
            do {
              tc = testCharIter.next
              if (tc == wc ) {
                buff += tc
                //ff iterator to ')'
                testCharIter = testCharIter.dropWhile { c => c != ')'} //skip ahead
              }

            } while (tc != ')' )

        }else if (tc == wc) {
          buff += tc
          //wc = wcIter.next
        }

      }

    }

//    if (buff.length == 10)
//      print(".")

    buff.length == wordLength

  }

  def readFiles = {

    val lineIter = fromFile(FILE_NAME).getLines()

    //consume the first line and extract LDN
    val firstLine = lineIter.next()
    val Array(wordLength, lineCount , testCount) = firstLine.split(" ").map(_.toInt)

    //println(s"${wordLength} : ${lineCount} : ${testCount}")

    //read next lineCount lines

    val wordsBuffer = new ArrayBuffer[String]
    val lineCountIter = Range(0, lineCount, 1).iterator
    lineCountIter.foreach { x =>
        wordsBuffer += lineIter.next
    }

    val testBuffer = new ArrayBuffer[String]
    while(lineIter.hasNext){
        testBuffer += lineIter.next
    }

    //println(testBuffer.length)
    //println(wordsBuffer.length)

    var testCounter = 0
    testBuffer.foreach { test =>
      var matches = 0
      testCounter += 1
      wordsBuffer.foreach { word =>

          if (findMatch(wordLength, word, test))
            matches += 1
      }
      println (s"Case #${testCounter}: ${matches}")
    }

  }

  readFiles

}
