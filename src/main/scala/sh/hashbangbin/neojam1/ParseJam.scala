package sh.hashbangbin.neojam1

import scala.Char
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import scala.io.Source._

object ParseJam extends App{
  val FILE_NAME = "A-large-practice.in"

  //get the test groups
  def findMatch(wordLength: Int, word: String, testStr: String): Boolean = {

    //iterate over the word string, char by char
    var buff = new ArrayBuffer[Char] //mutable buffer to collect my matches
    val wcIter = word.iterator
    var testCharIter = testStr iterator

    @tailrec def runAlong (wcIter: Iterator[Char], testCharIter: Iterator[Char]): Unit = {
        //break out if we reach end of our word iterator
        if (! wcIter.hasNext)
          return

        if (!testCharIter.hasNext)
          return

        val wc = wcIter next
        val tc = testCharIter.next //always longer than wcIter
        var newIterHead = testCharIter

        if (tc == '('){

          //fast-forward over the optional letters searching for a match
          //returns a new iterator with position on the closing ")"
         @tailrec def evalOptionalChars(searchChar: Char, iter: Iterator[Char]): Iterator[Char] = {
            val nextChar = iter.next

            if (nextChar == ')')
              return iter

            if (nextChar == searchChar){
              buff += nextChar
              return iter.dropWhile{ c => c != ')' }
            }else{
              evalOptionalChars(searchChar, iter)
            }
          }

          newIterHead = evalOptionalChars(wc, newIterHead)
          if (newIterHead.hasNext)
            newIterHead.next //discard the ')' and catch up

        }else if (tc == wc) {
          buff += tc
          //wc = wcIter.next
        }

        runAlong(wcIter, newIterHead)
    }
    runAlong(wcIter, testCharIter)

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
