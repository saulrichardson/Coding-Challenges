/**
 * An abstract super class of all Edit Commands on Buffers.
 *
 * This class simply specifies the interface to all commands
 * and stores the target buffer of the command.
 */
abstract class EditCommand(protected val target: Buffer) {
	
	/** Perform the command on the target buffer */
	def execute(): Unit;

	/** Undo the command on the target buffer */
	def undo(): Unit;
	
	/** Print out what this command represents */
	def toString(): String;
	
}

class InsertCommand(b: Buffer, val text : String) extends EditCommand(b)  {
  override def execute(): Unit = {
    b.insert(text)
    b.setCursor(b.getCursor() + text.length())
}
  override def undo(): Unit = {
    val cPos = b.getCursor()
    b.setCursor(cPos - text.length())
    b.delete(text.length())
}
  override def toString():String = {
    "insert " + text 
}
}  

class DeleteCommand(b: Buffer, val count: Int) extends EditCommand(b)  {
  val toAdd = b.getText(b.getCursor, b.getCursor() + count)
  override def execute(): Unit = {
    b.delete(count)
}
  override def undo(): Unit = {
    b.insert(toAdd)
}
  override def toString(): String = {
    "delete " + count.toString()
  }
}

class MoveCommand(b: Buffer, val loc: Int) extends EditCommand(b)  {
  val oldPos = b.getCursor()
  var newPos = 0
  override def execute(): Unit = {
    val l = if (loc < 0) {
      0
    } else if (loc>b.size()) {
      b.size()
    } else { 
      loc 
    }
    newPos = loc
    b.setCursor(l)
}
  override def undo(): Unit = {
    b.setCursor(oldPos)
}
  override def toString(): String = {
    "move cursor to " + newPos.toString()
}
}
