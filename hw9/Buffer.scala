
/**
 * A very simple abstraction of a text editor buffer.  A Buffer
 * contains a sequence of characters and a cursor.  The cursor is the
 * position in the sequence where operations are applied.  For
 * example, the insert method inserts a sequence of characters at the
 * cursor position inside the buffer.
 */
class Buffer {
  
  /** 
   * The contents are stored in a StringBuffer object. 
   */
  protected val contents = new StringBuilder("");
  
  /** 
   *  Current location of the cursor.  
   *  Invariant: 0 <= cursor <= contents.size()
   */
  protected var cursor = 0;
  
  /** 
   * Return the current location of the cursor.
   */
  def getCursor() = cursor;
  
  /** 
   * Move the cursor to loc.
   *
   * <pre>pre:  0 <= loc <= size() </pre>
   */
  def setCursor(loc: Int) = {
    assert (0 <= loc && loc <= size(), 
	    { "Bad call to setCursor(loc): " + 
	     "buffer size=" + size() + 
	     ", loc=" + loc } );
    
    cursor = loc;
  }
  
  /** 
   * Insert the given string at the current cursor location.
   */
  def insert(str: String) = {
    contents.insert(cursor, str);
  }
  
  /** 
   * Delete count characters to the right of the cursor.  Thus if
   * cursor is 10 and you delete 5 characters, characters at
   * positions 10-14 are deleted and cursor stays at position 10.
   * You must not delete characters past the end of the buffer.
   *
   * <pre>pre: getCursor() + count <= size()</pre>
   */
  def delete(count: Int) = {
    assert(cursor + count <= size(), 
	   { "Bad call to delete(count): " + 
	    "buffer size=" + size() + 
	    ", cursor=" + cursor + ", count=" + count } );
    
    
    contents.delete(cursor, cursor + count);
  }
  
  /**
   * Return the characters in positions [start..end) from the
   * buffer.
   *
   * <pre>pre: 0 <= start <= end <= size() </pre>
   */
  def getText(start: Int, end: Int): String = {
    assert(0 <= start && start <= end && end <= size(),  
	   { "Bad call to getText(start,end): buffer size=" + size() + 
	    ", start=" + start +", end=" + end } );
    
    contents.substring(start, end);
  }
  
  /**
   * Return the number of characters stored in the buffer.
   */
  def size(): Int = contents.size;
  
  /**
   * Return a string showing the contents of the buffer and the
   * current cursor location.
   */
  override def toString(): String = {
    "Buffer: " + contents.toString() + "\n        " +  // new line + space for "Buffer: "
    "".padTo(cursor, ' ') +
    "^";
  }	
  
  /**
   * Test code for Buffer.
   */
  def main(args: Array[String]) = {
    val b = new Buffer();
    b.insert("moo");
    System.out.println(b);
    b.setCursor(1);
    System.out.println(b);
    b.insert("moo");
    b.setCursor(1);
    System.out.println(b);
    b.delete(2);
    System.out.println(b);
    
    val c = new Buffer();
    c.insert("moo");
    c.setCursor(1);
    c.delete(2);
  }
}
