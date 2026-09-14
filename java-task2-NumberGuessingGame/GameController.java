import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    private GameModel model;
    private GameView view;

    public GameController(
            GameModel model,
            GameView view) {

        this.model = model;
        this.view = view;

        // Add listeners
        view.addGuessListener(
                new GuessListener()
        );

        view.addDifficultyListener(
                new DifficultyListener()
        );

        view.addPlayAgainListener(
                new PlayAgainListener()
        );

        // Start first round
        model.startNewRound();

        updateView();

        view.showFeedbackInfo(
                "Game started! Make your guess."
        );

        view.focusGuessInput();
    }

    // Update GUI
    private void updateView() {

        GameModel.Difficulty difficulty =
                model.getCurrentDifficulty();

        view.updateStatusText(
                difficulty.range,
                model.getCurrentAttempts(),
                difficulty.maxAttempts
        );
    }

    // Guess button listener
    private class GuessListener
            implements ActionListener {

        @Override
        public void actionPerformed(
                ActionEvent e) {

            String input =
                    view.getGuessInput();

            // Empty input
            if (input.isEmpty()) {

                view.showFeedbackWarning(
                        "Please enter a number."
                );

                return;
            }

            int guess;

            try {

                guess = Integer.parseInt(input);

            } catch (NumberFormatException ex) {

                view.showFeedbackError(
                        "Please enter a valid number."
                );

                view.clearGuessInput();

                return;
            }

            // Check guess
            GameModel.GuessResult result =
                    model.checkGuess(guess);

            // Invalid range
            if (result ==
                    GameModel.GuessResult.INVALID_RANGE) {

                view.showFeedbackError(
                        "Please enter a number between 1 and "
                                + model.getCurrentDifficulty().range
                );

                view.clearGuessInput();

                return;
            }

            // Valid guess → increase attempts
            model.incrementAttempts();

            // Correct answer
            if (result ==
                    GameModel.GuessResult.CORRECT) {

                view.showFeedbackSuccess(
                        "🎉 Correct! You guessed the number!"
                );

                view.appendHistory(
                        "Round "
                                + model.getRoundCount()
                                + ": WON in "
                                + model.getCurrentAttempts()
                                + " attempts.\n"
                );

                view.setGameControlsEnabled(false);

            }

            // Guess is too high
            else if (result ==
                    GameModel.GuessResult.TOO_HIGH) {

                view.showFeedbackWarning(
                        "Too high! Try a smaller number."
                );

            }

            // Guess is too low
            else if (result ==
                    GameModel.GuessResult.TOO_LOW) {

                view.showFeedbackInfo(
                        "Too low! Try a larger number."
                );
            }

            updateView();

            // Game over
            if (result !=
                    GameModel.GuessResult.CORRECT
                    && model.isGameOver()) {

                view.showFeedbackError(
                        "Game Over! The number was "
                                + model.getRandomNumber()
                );

                view.appendHistory(
                        "Round "
                                + model.getRoundCount()
                                + ": LOST. Number was "
                                + model.getRandomNumber()
                                + ".\n"
                );

                view.setGameControlsEnabled(false);
            }

            view.clearGuessInput();
            view.focusGuessInput();
        }
    }

    // Difficulty listener
    private class DifficultyListener
            implements ActionListener {

        @Override
        public void actionPerformed(
                ActionEvent e) {

            int index =
                    view.getSelectedDifficultyIndex();

            if (index == 0) {

                model.setDifficulty(
                        GameModel.Difficulty.EASY
                );

            } else if (index == 1) {

                model.setDifficulty(
                        GameModel.Difficulty.MEDIUM
                );

            } else {

                model.setDifficulty(
                        GameModel.Difficulty.HARD
                );
            }

            model.startNewRound();

            view.setGameControlsEnabled(true);

            view.clearGuessInput();

            view.showFeedbackInfo(
                    "Difficulty changed. New round started!"
            );

            updateView();

            view.focusGuessInput();
        }
    }

    // Play again listener
    private class PlayAgainListener
            implements ActionListener {

        @Override
        public void actionPerformed(
                ActionEvent e) {

            model.advanceToNextRound();

            view.setGameControlsEnabled(true);

            view.clearGuessInput();

            view.showFeedbackInfo(
                    "New round started! Good luck!"
            );

            updateView();

            view.focusGuessInput();
        }
    }
}