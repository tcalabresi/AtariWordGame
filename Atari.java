import java.util.Scanner;

public class Atari {
    // global variables:
    public static int row_pos = 7; // starting position of player is at 7th row, 7th column-
    public static int col_pos = 7; // this is why both row_pos and col_pos are set to 7. It decrements when player moves up or left.
    public static int numdirection; // based on a certain #, we can determine the direction player wants to move in
    public static int num_moves = 0; // counts the total # of moves made in winning the game
    public static boolean hasChalice = false; // when it turns true, the while loop stops and the player wins the game
    public static boolean hasKey = false; // when it turns true, the player has acquired the key and the door is unlocked
    public static char map[][]; // an array that holds the X's and O's which determine where the walls and the free spaces are
    public static int max_rows; // total # of rows
    public static int max_columns; // total # of columns
    public static Scanner userinput;

    public static void main(String[] args) {

        initializeMap(); // calling map

        // Introduction: It explains the game and how to play.
        System.out.println("\nWelcome to 'Adventure!'"
         + "\nIn this game, there are 2 castles."
         + "\nThe 1st castle has 2 rooms,"
         + "\nand the 2nd castle has 1 room."
         + "\nTo win the game, you must"
         + "\nnavigate through the rooms,"
         + "\nfind the key in the first castle,"
         + "\nunlock the second castle,"
         + "\nand find the chalice."
         + "\n\nTo navigate, press the following keys:"
         + "\n- '1' to move left"
         + "\n- '2' to move right"
         + "\n- '3' to move up"
         + "\n- '4' to move down"
         + "\nGood luck!");

        // setting global variables
        max_rows = map.length;
        max_columns = map[0].length;

        userinput = new Scanner(System.in);

        while (hasChalice != true) { // while loop which runs the entire game- switches between the two methods until hasChalice is true
            makeMove(); // gets numdirection
            checkMove(); // uses numdirection in a case statement which allows player to move 4 directions
         }

        System.out.println("\nCongratulations! You won the game in " + num_moves + " moves!"); // Will only execute when the player has the chalice
        userinput.close();
    }

    public static void initializeMap() {
        // (row_pos, col_pos) - demonstrating where the key objects/places are.
        // 'X' represents the "walls"
        // 'O' represents the free space
        // 'Y' represents the chalice (0,7)
        // 'K' represents the key. It changes to an 'O' once the player has found the key (7,0)
        // '#' represents the locked door that must be unlocked with a key. When hasKey turns true, the player is allowed to pass through the door. (3,0)
        // 2D Array of simple chars. There are a total of 8 rows and 8 columns. The player starts at the BOTTOM LEFT side of the screen (7th row, 7th column)
        // When the player finds the chalice, he/she will be at 0th row, 7th column. (0,7)

        map = new char[][]
             {
                 {'O', 'O', 'O', 'X', 'O', 'O', 'O', 'Y'},
                 {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
                 {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X'},
                 {'#', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                 {'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O'},
                 {'X', 'O', 'O', 'X', 'O', 'O', 'O', 'O'},
                 {'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O'},
                 {'K', 'O', 'O', 'X', 'O', 'O', 'O', 'O'}
             };
    }

    public static void makeMove() {
        System.out.println("\nMake your move: "); // asks the player where to move
        
        numdirection = Integer.parseInt(userinput.nextLine()); // converting string input to simple int
        num_moves++; // increments with every move made
        // goes back to main method
    }

    public static void checkMove() {

    // depending on the value of numdirection assigned in makeMove(), one of these cases will execute.
    switch (numdirection) {
        case 1: // player has pressed the left key
            if ( col_pos-1 < 0 ) { // IF you decrement col_pos (move left) and it exceeds the column position (which means col_pos = -1)
                System.out.println("\nYou bumped into a wall!");
            }
            else if ( map[row_pos][col_pos-1] == 'O' ) { // IF you decrement col_pos and there is an 'O' there, then..
                System.out.println("\nYou moved left.");
                col_pos--; // col_pos is actually decremented, means the player has successfully moved left
            }
            else if ( map[row_pos][col_pos-1] == 'X' ) { // because player has bumped into the wall, col_pos will NOT be decremented.
                System.out.println("\nYou bumped into a wall!");
            }
            else if ( map[row_pos][col_pos-1] == 'K' ) { // IF you decrement col_pos and that is where the 'K' is, then...
                System.out.println("\nYou found the key! Good job!");
                hasKey = true; // hasKey turns true, player has the key
                col_pos--; // they can move over to where the key was
                map[row_pos][col_pos] = 'O'; // The key is replaced with an 'O' so player cannot get key again
            }
            break;
        case 2: // player has pressed the right key
            if ( col_pos+1 == max_columns ) { // IF you increment col_pos (move right) and it exceeds the column position (which means col_pos = 8)
                System.out.println("\nYou bumped into a wall!"); // takes care of "out of bounds" exception
                // player stays where it is- the col_pos does not actually change
            }
            else if ( map[row_pos][col_pos+1] == 'O' ) {
                System.out.println("\nYou moved right.");
                col_pos++; // means player has successfully moved right, player is in that spot.
            }
            else if ( map[row_pos][col_pos+1] == 'X' ) { // because player has bumped into the wall, col_pos will NOT be incremented.
                System.out.println("\nYou bumped into a wall!");
            }
            else if ( map[row_pos][col_pos+1] == 'Y' ) { // if player moves right and that is the location of the chalice, then...
                System.out.println("\nAmazing! You found the chalice!");
                col_pos++; // player moves on top of chalice spot
                hasChalice = true; // player now has the chalice, so when this breaks, the code will finally break out of the while loop
            }
            break;
        case 3: // player has pressed the up key
            if ( row_pos-1 < 0 ) { // IF you decrement row_pos (move up) and it exceeds the row position (which means row_pos = -1)
                System.out.println("\nYou bumped into a wall!");
                // player stays where it is
            }
            else if ( map[row_pos-1][col_pos] == 'O' ) {
                System.out.println("\nYou moved up.");
                row_pos--;
            }
            else if ( map[row_pos-1][col_pos] == 'X' ) { // because player has bumped into the wall, row_pos will NOT be decremented.
                System.out.println("\nYou bumped into a wall!");
            }
            else if ( map[row_pos-1][col_pos] == 'Y' ) {
                System.out.println("\nAmazing! You found the chalice!");
                row_pos--;
                hasChalice = true;
            }
            else if ( map[row_pos-1][col_pos] == '#') {
                if (hasKey == true) {
                    System.out.println("\nYou have opened castle 2. You are on your way to find the chalice!");
                    row_pos--;
                    map[row_pos][col_pos] = 'O'; // now the door is a free space
                } else // if hasKey is false, so there is no way the player can go to castle two without getting the key first.
                    System.out.println("\nYou bumped into the door! Find the key first");
            }
            break;
        case 4: // player has pressed the down key
            if ( row_pos+1 == max_rows ) { // IF you increment row_pos (move down) and it exceeds the column position (which means row_pos = 8)
                System.out.println("\nYou bumped into a wall!");
                // player stays where it is
            }
            else if ( map[row_pos+1][col_pos] == 'O' ) {
                System.out.println("\nYou moved down.");
                row_pos++;
            }
            else if ( map[row_pos+1][col_pos] == 'X' ) { // because player has bumped into the wall, row_pos will NOT be incremented.
                System.out.println("\nYou bumped into a wall!");
            }
            else if ( map[row_pos+1][col_pos] == 'K' ) {
                System.out.println("\nYou found the key! Good job!");
                hasKey = true;
                row_pos++;
                map[row_pos][col_pos] = 'O';
            }
            break;
        case 5:
            System.out.println("Illegal move. Please try again: "); // If the player enters any other character (not '1', '2', '3', or '4')
            break;
        }
        // goes back to main method
}
}