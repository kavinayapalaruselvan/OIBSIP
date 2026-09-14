import java.util.Random;

public class GameModel {

    public enum Difficulty {
        EASY(50, 10),
        MEDIUM(100, 7),
        HARD(200, 5);

        public final int range;
        public final int maxAttempts;

        Difficulty(int range, int maxAttempts) {
            this.range = range;
            this.maxAttempts = maxAttempts;
        }
    }

    public enum GuessResult {
        TOO_HIGH,
        TOO_LOW,
        CORRECT,
        INVALID_RANGE
    }

    private int randomNumber;
    private int currentAttempts;
    private int roundCount = 1;

    private Difficulty currentDifficulty = Difficulty.MEDIUM;

    public void startNewRound() {
        Random rand = new Random();

        randomNumber =
            rand.nextInt(currentDifficulty.range) + 1;

        currentAttempts = 0;
    }

    public void advanceToNextRound() {
        roundCount++;
        startNewRound();
    }

    public void incrementAttempts() {
        currentAttempts++;
    }

    public GuessResult checkGuess(int guess) {

        if (guess < 1 || guess > currentDifficulty.range) {
            return GuessResult.INVALID_RANGE;
        }

        if (guess == randomNumber) {
            return GuessResult.CORRECT;
        }

        if (guess > randomNumber) {
            return GuessResult.TOO_HIGH;
        }

        return GuessResult.TOO_LOW;
    }

    public boolean isGameOver() {
        return currentAttempts >= currentDifficulty.maxAttempts;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public int getCurrentAttempts() {
        return currentAttempts;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
    }
}