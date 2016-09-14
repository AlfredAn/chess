package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.pieces.MoveSet;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;

/**
 * Represents a chess piece.
 * Can be placed and moved on a ChessBoard.
 * 
 * <p>Note for subclassers: You cannot make a custom piece that can capture the king without directly moving to it.
 */
public abstract class ChessPiece {

  /**
   * The ChessBoard that this piece belongs to.
   * Set by the ChessBoard when the piece is added or moved.
   * May be null if the piece is not currently on a board.
   */
  ChessBoard board;
  
  /**
   * The x position of this piece on the board.
   * Set by the ChessBoard when the piece is added or moved.
   * Undefined if the piece is not currently on a board.
   */
  int xPos;
  
  /**
   * The y position of this piece on the board.
   * Set by the ChessBoard when the piece is added or moved.
   * Undefined if the piece is not currently on a board.
   */
  int yPos;
  
  /**
   * The team that this piece belongs to.
   * Normally this can only be either Team.BLACK or Team.WHITE, but any number is valid.
   */
  public int team;
  
  /**
   * Used to cache the available moves to avoid having to compute them more than once per turn.
   */
  private MoveSet cachedMoveSet;
  
  public ChessPiece(int team) {
    this.team = team;
  }
  
  public final int getX() {
    return xPos;
  }
  
  public final int getY() {
    return yPos;
  }
  
  /**
   * Returns a MoveSet containing all the possible squares that this piece can move to.
   */
  public final MoveSet getAvailableMoves() {
    if (cachedMoveSet == null) {
      cachedMoveSet = new MoveSet(listAvailableMoves());
    }
    return cachedMoveSet;
  }
  
  /**
   * Returns a list of all the possible squares that this piece can move to.
   * This method must be overridden by all subclasses to define the piece's behavior.
   * getAvailableMoves() will convert the returned list to a MoveSet and cache it for the duration of the turn.
   */
  protected abstract List<IntVector2> listAvailableMoves();
  
  /**
   * Returns whether this piece can move to the specified square.
   */
  public boolean isValidMove(int x, int y) {
    checkInitialized();
    for (IntVector2 validMove : getAvailableMoves()) {
      if (validMove.x == x && validMove.y == y) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Returns whether this piece can move to the specified square.
   */
  public final boolean isValidMove(IntVector2 destination) {
    return isValidMove(destination.x, destination.y);
  }
  
  /**
   * Makes the specified move.
   * @param x The x position to move to.
   * @param y The y position to move to.
   * @throws IllegalArgumentException If the move is invalid.
   */
  public final void makeMove(int x, int y) {
    if (isValidMove(x, y)) {
      performMove(x, y);
    } else {
      throw new IllegalArgumentException(
              "Invalid move of " + getClass().getSimpleName()
                      + " from " + getBoard().getSquareText(getX(), getY())
                      + " to "   + (getBoard().isValidPosition(x, y) ? getBoard().getSquareText(x, y) : ("(" + x + ", " + y + ")")));
    }
  }
  
  /**
   * Makes the specified move.
   * @param pos The position to move to.
   * @throws IllegalArgumentException If the move is invalid.
   */
  public final void makeMove(IntVector2 pos) {
    makeMove(pos.x, pos.y);
  }
  
  /**
   * Performs the specified move.
   * This method is called by the makeMove() method if it determines that the move is valid,
   * and is intended to be overridden by subclasses for custom behavior.
   * Should never be called manually.
   * If you override this (and don't call super.performMove()), you need to either call signalMoveCompleted()
   * after performing the move to let the board know that you made a move.
   */
  protected void performMove(int x, int y) {
    board.placePiece(this, x, y);
    signalMoveCompleted();
  }
  
  /**
   * Lets the board know that you finished a move.
   */
  protected final void signalMoveCompleted() {
    board.signalEndOfTurn();
  }
  
  void handleBoardEvent(BoardEvent event) {
    if (event instanceof BoardEvent.EndOfTurnEvent) {
      cachedMoveSet = null;
    }
    boardEvent(event);
  }
  
  /**
   * Called whenever an event occurs on the board.
   * ChessPiece gets the events this way instead of implementing BoardEventListener
   * because then we won't have to keep track of adding and removing them from
   * the ChessBoard's list of listeners.
   */
  protected void boardEvent(BoardEvent event) {}
  
  /**
   * Returns the ChessBoard that this piece belongs to,
   * or null if it hasn't been added to one yet.
   */
  public final ChessBoard getBoard() {
    return board;
  }
  
  /**
   * Throws an IllegalStateException if this piece has not been added to a board.
   */
  protected final void checkInitialized() {
    if (board == null) {
      throw new IllegalStateException("The piece must first be added to a board!");
    }
  }
}
