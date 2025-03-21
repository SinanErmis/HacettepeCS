public class DiceGame {
    public static void main(String[] args) {
        // ! Assert input file is not corrupted and is containing correct input
        String[] inputLines = FileUtilities.readFile(args[0], true, true);
        Game game = new Game(args[1], inputLines);
        game.play();
    }
}

