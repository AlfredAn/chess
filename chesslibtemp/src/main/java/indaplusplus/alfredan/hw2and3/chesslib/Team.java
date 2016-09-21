package indaplusplus.alfredan.hw2and3.chesslib;

/**
 * Contains constants for the black and white players in a standard chess game.
 * The team is stored as an int to allow for more than two players.
 */
public final class Team {
  
  private Team() {}
  
  public static final int BLACK = 0, WHITE = 1;
  
  public static String getTeamName(int team) {
    switch (team) {
      case BLACK:
        return "Black";
      case WHITE:
        return "White";
      default:
        return "Unknown";
    }
  }
}
