import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ReJSudoku extends JFrame {
    private final JTextField[][] cells = new JTextField[9][9];
    private final int[][] solution = new int[9][9];
    private final int[][] puzzle = new int[9][9];
    private final JLabel statusLabel = new JLabel("Solve the Sudoku!");
    private final JLabel themeLabel = new JLabel("Current Theme: Light");
    private boolean isLightTheme = true;

    public ReJSudoku() {
        setTitle("Ultimate Sudoku");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                gridPanel.add(cells[i][j]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        themeLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusPanel.add(statusLabel);
        statusPanel.add(themeLabel);
        add(statusPanel, BorderLayout.NORTH);

        
        JPanel controlPanel = new JPanel();
        JButton generateButton = new JButton("Generate Puzzle");
        generateButton.addActionListener(e -> generatePuzzle());
        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(e -> validateSolution());
        JButton themeSwitchButton = new JButton("Switch Theme");
        themeSwitchButton.addActionListener(e -> toggleTheme());
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(e -> solvePuzzle());

        controlPanel.add(generateButton);
        controlPanel.add(validateButton);
        controlPanel.add(themeSwitchButton);
        controlPanel.add(solveButton);
        add(controlPanel, BorderLayout.SOUTH);

        generatePuzzle(); 
    }

    private void generatePuzzle() {
        
        clearGrid();

        
        generateSolution();

        
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            System.arraycopy(solution[i], 0, puzzle[i], 0, 9);
        }
        for (int i = 0; i < 40; i++) { 
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            puzzle[row][col] = 0;
        }

        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(puzzle[i][j]));
                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(isLightTheme ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                } else {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                    cells[i][j].setBackground(isLightTheme ? Color.WHITE : Color.DARK_GRAY);
                }
            }
        }

        statusLabel.setText("Solve the Sudoku!");
    }

    private void clearGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText("");
                cells[i][j].setEditable(true);
                cells[i][j].setBackground(isLightTheme ? Color.WHITE : Color.DARK_GRAY);
            }
        }
    }

    private void generateSolution() {
        
        int num = 1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solution[i][j] = num;
                num = (num % 9) + 1;
            }
        }
    }

    private void validateSolution() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText();
                if (text.isEmpty() || !text.equals(String.valueOf(solution[i][j]))) {
                    statusLabel.setText("Incorrect! Keep trying.");
                    return;
                }
            }
        }
        statusLabel.setText("Congratulations! You solved the Sudoku!");
    }

    private void solvePuzzle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText(String.valueOf(solution[i][j]));
                cells[i][j].setEditable(false);
                cells[i][j].setBackground(isLightTheme ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            }
        }
        statusLabel.setText("Here's the solution!");
    }

    private void toggleTheme() {
        isLightTheme = !isLightTheme;
        Color bgColor = isLightTheme ? Color.WHITE : Color.DARK_GRAY;
        Color textColor = isLightTheme ? Color.BLACK : Color.WHITE;

        getContentPane().setBackground(bgColor);
        statusLabel.setForeground(textColor);
        themeLabel.setForeground(textColor);
        themeLabel.setText("Current Theme: " + (isLightTheme ? "Light" : "Dark"));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setBackground(puzzle[i][j] == 0 ? bgColor : (isLightTheme ? Color.LIGHT_GRAY : Color.DARK_GRAY));
                cells[i][j].setForeground(textColor);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReJSudoku game = new ReJSudoku();
            game.setVisible(true);
        });
    }
}
