package chess;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessGameGUI {

  private ChessBoard board;
  private JButton[][] buttons;
  private boolean whiteTurn;
  private JFrame frame;

  public ChessGameGUI() {
    board = new ChessBoard();
    buttons = new JButton[8][8];
    whiteTurn = true;
    createAndShowGUI();
  }

  private void createAndShowGUI() {
    frame = new JFrame("Chess Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel(new GridLayout(8, 8));
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        JButton button = new JButton();
        button.setOpaque(true);
        button.setBackground((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
        final int fromX = x;
        final int fromY = y;
        button.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            handleMove(fromX, fromY);
          }
        });
        buttons[y][x] = button;
        panel.add(button);
      }
    }

    updateBoard();

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }

  private void handleMove(int fromX, int fromY) {
    if (board.getPiece(fromY, fromX) == null || board.getPiece(fromY, fromX).isWhite() != whiteTurn) {
      return; // Can't move empty squares or opponent's pieces
    }
    // Set the "to" square to be selected when the player clicks on a "from" square
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        final int toX = x;
        final int toY = y;
        buttons[y][x].addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              board.movePiece(fromX, fromY, toX, toY, whiteTurn);
            } catch (IllegalMoveException ex) {
              return;
            }
            whiteTurn = !whiteTurn; // Alternate turns
            updateBoard();
          }
        });
      }
    }
  }

  private void updateBoard() {
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {ChessPiece piece = board.getPiece(y, x);
        if (piece == null) {
          buttons[y][x].setText("");
        } else {
          buttons[y][x].setText(piece.getSymbol());
        }
      }
    }
  }

  public static void main(String[] args) {
    new ChessGameGUI();
  }
}


