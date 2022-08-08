import java.util.InputMismatchException
import java.util.Scanner
import scala.collection.mutable.ArrayStack

/**
 * Simple Edit Buffer
 */
object TextEditor {
  protected var history = new ArrayStack[EditCommand]() //comand history
  protected var history2 = new ArrayStack[EditCommand]() //comands recently undone
  protected val input = new Scanner(System.in);
  
  /** The "document" that the editor operates on. */
  protected val buffer = new Buffer();
  /**
   * Return the current cursor position in the buffer.
   */
  def getCursor() = {
    buffer.getCursor();
  }
  
  /**
   * Set the current cursor position in the buffer.  
   * <p> 
   * Clips the cursor movement to the ends of the buffer.  Ie, if loc
   * < 0, move cursor to 0, and if loc > buffer.size() move cursor
   * to buffer.size().
   */
  def setCursor(loc: Int) = {
    val l = if (loc < 0) { 
      0 
    } else if (loc > buffer.size()) { 
      buffer.size() 
    } else {
      loc
    }
    buffer.setCursor(l);
  }
  
  /**
   * Insert the given text into the buffer at the current cursor
   * position and move the cursor to the end of the inserted text.
   */
  def insert(text: String) = {
    val command = new InsertCommand(buffer, text)
    history.push(command)
    history2.clear()   //empty redo stack after editing buffer
    command.execute() 
  }
  
  /**
   * Delete count characters to the right of the cursor.
   *
   * <pre> pre: You must not delete past end of buffer </pre>
   */
  def delete(count: Int) =  {
    val command = new DeleteCommand(buffer, count)
    history.push(command)
    history2.clear()  //don't allow redo once buffer is edited 
    command.execute()
  }
  
  /**
   * Undo the previous editing command
   * (either a move, insert, or delete).
   */
  def undo() = {
    if (history.isEmpty) {println("\nno moves to undo\n")}
    else {
      val last = history.pop()
      last.undo()
      history2.push(last)   //push undo onto redo stack
    }
  }

  /**
   * Undo the previous editing command
   * (either a move, insert, or delete).
   */
  def redo() = { 
    if (history2.isEmpty) {println("\nno move to redo\n")}
    else {
      val redoo = history2.pop()
      redoo.execute()
      history.push(redoo)  //push redo back onto undo stack
    }
  }
  
  
  /**
   * Print the commands on the undo stack. Most recent command that was executed
   * and will be undone with undo call is on top of stack. Order of commands is in the order
   * of the instructions that would get undone via repeated calls to undo.
   */
  protected def printHistory() = {
    println("\nCommand History:")
    history.foreach(com => println(com))
    println()
  }
  
  
  /**
   * Return the contents of the buffer.
   */
  override def toString(): String = {
    buffer.toString();
  }
  
  /**
   * Read the next piece of input from Scanenr in as an integer, if
   * it is in fact an integer.  Otherwise, return 1 if there is no
   * input or generate an exception if there is something
   * unexpected.
   */
  def readOptionalInt(in: Scanner): Int = {
    if (in.hasNextInt()) {
      in.nextInt();
    } else if (in.hasNext()) {
      throw new InputMismatchException("Missing number");
    } else {
      1;
    }
  }
  
  /**
   * Read and process one command from the user.  Returns true if
   * additional commands should be read, or false if the user has
   * quit.
   */    
  def processOneCommand(): Boolean = {
    print("? ");
    if (!input.hasNext()) return false;
    val commandStr = input.nextLine();
    val commandScanner = new Scanner(commandStr);
    val letter = commandScanner.next().toUpperCase();
    try {
      letter match {
        case "I" => {
          commandScanner.skip(" ");  // skip space after 'I'
          val text = commandScanner.nextLine();
          insert(text);
        }
        case "D" => {
	  delete(readOptionalInt(commandScanner))
	}
        case "<" => {
	  val comm = new MoveCommand(buffer, getCursor() - readOptionalInt(commandScanner))
	  history.push(comm)
	  history2.clear()
	  comm.execute()
	}
        case ">" => {
	  val comm = new MoveCommand(buffer, getCursor() + readOptionalInt(commandScanner))
	  history.push(comm)
	  history2.clear()
	  comm.execute()
	}
        case "U" => undo();
        case "R" => redo();
        case "P" => printHistory();
        case "Q" => return false;
        case _ => println("Invalid command: '" + commandStr + "'");
      }
    } catch {
      case e : Throwable => println("Invalid Command: '" + commandStr + "' " + e);
    }
    return true;
  }
  
  
  /**
   * Create a new TextEditor that reads commands from the terminal
   * window.  Process commands until the user enters "Q".
   */
  def main(args: Array[String]) = {
    println(toString());
    while (processOneCommand()) {
      println(toString());
    }
  }
}
