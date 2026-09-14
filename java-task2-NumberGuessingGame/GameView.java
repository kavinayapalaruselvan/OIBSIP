import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {

    private static final Color COLOR_INFO =
            new Color(0, 51, 153);

    private static final Color COLOR_SUCCESS =
            new Color(0, 128, 0);

    private static final Color COLOR_WARN =
            new Color(204, 102, 0);

    private static final Color COLOR_ERROR =
            new Color(204, 0, 0);

    private JComboBox<String> difficultyBox;
    private JLabel promptLabel;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JTextArea scoreHistoryArea;
    private JButton playAgainButton;

    public GameView() {

        setTitle("Number Guessing Game");
        setSize(450, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // TOP PANEL
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Select Difficulty: "));

        String[] difficulties = {
                "Easy (1-50, 10 attempts)",
                "Medium (1-100, 7 attempts)",
                "Hard (1-200, 5 attempts)"
        };

        difficultyBox = new JComboBox<>(difficulties);

        difficultyBox.setSelectedIndex(1);

        topPanel.add(difficultyBox);

        add(topPanel, BorderLayout.NORTH);

        // CENTER PANEL
        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(
                new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // Prompt
        promptLabel = new JLabel();

        promptLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        promptLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        // Guess field
        guessField = new JTextField(10);

        guessField.setMaximumSize(
                new Dimension(120, 30)
        );

        guessField.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        guessField.setHorizontalAlignment(
                JTextField.CENTER
        );

        // Guess button
        guessButton = new JButton("Submit Guess");

        guessButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // Feedback
        feedbackLabel = new JLabel();

        feedbackLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        feedbackLabel.setFont(
                new Font("Arial", Font.ITALIC, 14)
        );

        // Attempts
        attemptsLabel = new JLabel();

        attemptsLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // Play again
        playAgainButton =
                new JButton("Play Another Round");

        playAgainButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        playAgainButton.setVisible(false);

        // Add components
        centerPanel.add(promptLabel);

        centerPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 15)
                )
        );

        centerPanel.add(guessField);

        centerPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 10)
                )
        );

        centerPanel.add(guessButton);

        centerPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 20)
                )
        );

        centerPanel.add(feedbackLabel);

        centerPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 10)
                )
        );

        centerPanel.add(attemptsLabel);

        centerPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 15)
                )
        );

        centerPanel.add(playAgainButton);

        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM PANEL
        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        bottomPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Score History Summary"
                )
        );

        scoreHistoryArea =
                new JTextArea(6, 30);

        scoreHistoryArea.setEditable(false);

        bottomPanel.add(
                new JScrollPane(scoreHistoryArea),
                BorderLayout.CENTER
        );

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateStatusText(
            int range,
            int current,
            int max) {

        promptLabel.setText(
                "Guess a number between 1 and "
                        + range + ":"
        );

        attemptsLabel.setText(
                "Attempts: "
                        + current + " / " + max
        );
    }

    public void showFeedbackInfo(String msg) {
        setFeedback(msg, COLOR_INFO);
    }

    public void showFeedbackSuccess(String msg) {
        setFeedback(msg, COLOR_SUCCESS);
    }

    public void showFeedbackWarning(String msg) {
        setFeedback(msg, COLOR_WARN);
    }

    public void showFeedbackError(String msg) {
        setFeedback(msg, COLOR_ERROR);
    }

    private void setFeedback(
            String msg,
            Color color) {

        feedbackLabel.setText(msg);

        feedbackLabel.setForeground(color);
    }

    public String getGuessInput() {
        return guessField.getText().trim();
    }

    public void clearGuessInput() {
        guessField.setText("");
    }

    public void focusGuessInput() {
        guessField.requestFocus();
    }

    public int getSelectedDifficultyIndex() {
        return difficultyBox.getSelectedIndex();
    }

    public void appendHistory(String log) {
        scoreHistoryArea.append(log);
    }

    public void setGameControlsEnabled(
            boolean enabled) {

        guessField.setEnabled(enabled);

        guessButton.setEnabled(enabled);

        playAgainButton.setVisible(!enabled);
    }

    public void addGuessListener(ActionListener l) {

        guessButton.addActionListener(l);

        guessField.addActionListener(l);
    }

    public void addDifficultyListener(
            ActionListener l) {

        difficultyBox.addActionListener(l);
    }

    public void addPlayAgainListener(
            ActionListener l) {

        playAgainButton.addActionListener(l);
    }
}