
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {

	/**
	 * @author conorprunty 13102311
	 */

	private static final long serialVersionUID = -3076576915669770713L;
	JLayeredPane layeredPane;
	JPanel chessBoard;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	int startX;
	int startY;
	int initialX;
	int initialY;
	JPanel panels;
	JLabel pieces;
	Colour nextMove;

	public enum Colour {
		WHITE, BLACK;
	}

	public ChessProject() {
		Dimension boardSize = new Dimension(600, 600);

		// Use a Layered Pane for this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		// Add a chess board to the Layered Pane
		chessBoard = new JPanel();
		nextMove = Colour.WHITE;
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout(new GridLayout(8, 8));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout());
			chessBoard.add(square);

			int row = (i / 8) % 2;
			if (row == 0)
				square.setBackground(i % 2 == 0 ? Color.white : Color.gray);
			else
				square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
		}

		// Setting up the Initial Chess board.
		for (int i = 8; i < 16; i++) {
			pieces = new JLabel(new ImageIcon("WhitePawn.png"));
			panels = (JPanel) chessBoard.getComponent(i);
			panels.add(pieces);
		}
		pieces = new JLabel(new ImageIcon("WhiteRook.png"));
		panels = (JPanel) chessBoard.getComponent(0);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
		panels = (JPanel) chessBoard.getComponent(1);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
		panels = (JPanel) chessBoard.getComponent(6);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
		panels = (JPanel) chessBoard.getComponent(2);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
		panels = (JPanel) chessBoard.getComponent(5);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKing.png"));
		panels = (JPanel) chessBoard.getComponent(3);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
		panels = (JPanel) chessBoard.getComponent(4);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteRook.png"));
		panels = (JPanel) chessBoard.getComponent(7);
		panels.add(pieces);
		for (int i = 48; i < 56; i++) {
			pieces = new JLabel(new ImageIcon("BlackPawn.png"));
			panels = (JPanel) chessBoard.getComponent(i);
			panels.add(pieces);
		}
		pieces = new JLabel(new ImageIcon("BlackRook.png"));
		panels = (JPanel) chessBoard.getComponent(56);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKnight.png"));
		panels = (JPanel) chessBoard.getComponent(57);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKnight.png"));
		panels = (JPanel) chessBoard.getComponent(62);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackBishup.png"));
		panels = (JPanel) chessBoard.getComponent(58);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackBishup.png"));
		panels = (JPanel) chessBoard.getComponent(61);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKing.png"));
		panels = (JPanel) chessBoard.getComponent(59);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackQueen.png"));
		panels = (JPanel) chessBoard.getComponent(60);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackRook.png"));
		panels = (JPanel) chessBoard.getComponent(63);
		panels.add(pieces);
	}

	/*
	 * This method checks if there is a piece present on a particular square.
	 */
	private Boolean piecePresent(int x, int y) {
		Component c = chessBoard.findComponentAt(x, y);
		if (c instanceof JPanel) {
			return false;
		} else {
			return true;
		}
	}

	// this checks if there is a King in a location so that the other King
	// cannot be within 1 square
	private Boolean checkKingAtLocation(int x, int y) {
		try {
			Component c1 = chessBoard.findComponentAt(x, y);
			if (c1 instanceof JPanel)
				return false;
			JLabel awaitingPiece = (JLabel) c1;
			String tmp1 = awaitingPiece.getIcon().toString();
			return tmp1.contains("King");
		} catch (Exception e) {
			return false;
		}
	}

	// this is to check if there is a King in the location of where the piece is
	// taken
	private Boolean checkIfGameOver(int newX, int newY) {
		Boolean oponent = false;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel) c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if (((tmp1.contains("King")))) {
			oponent = true;
		} else {
			oponent = false;
		}
		return oponent;
	}

	/*
	 * This is a method to check if a piece is a Black piece.
	 */
	private Boolean checkWhiteOponent(int newX, int newY) {
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel) c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if (((tmp1.contains("Black")))) {
			oponent = true;
		} else {
			oponent = false;
		}
		return oponent;
	}

	private Boolean checkBlackOponent(int newX, int newY) {
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel) c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if (((tmp1.contains("White")))) {
			oponent = true;
		} else {
			oponent = false;
		}
		return oponent;
	}

	/*
	 * This method is called when we press the Mouse. So we need to find out
	 * what piece we have selected. We may also not have selected a piece!
	 */

	public void mousePressed(MouseEvent e) {
		chessPiece = null;
		Component c = chessBoard.findComponentAt(e.getX(), e.getY());
		if (c instanceof JPanel)
			return;

		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel) c;
		initialX = e.getX();
		initialY = e.getY();
		startX = (e.getX() / 75);
		startY = (e.getY() / 75);
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}

	public void mouseDragged(MouseEvent me) {
		if (chessPiece == null)
			return;

		chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	}

	/*
	 * This method is used when the Mouse is released...we need to make sure the
	 * move was valid before putting the piece back on the board.
	 */
	public void mouseReleased(MouseEvent e) {
		if (chessPiece == null)
			return;

		chessPiece.setVisible(false);
		Boolean success = false;
		Component c = chessBoard.findComponentAt(e.getX(), e.getY());
		String tmp = chessPiece.getIcon().toString();
		String pieceName = tmp.substring(0, (tmp.length() - 4));
		Boolean validMove = false;
		int landingX = (e.getX() / 75);
		int landingY = (e.getY() / 75);

		if (pieceName.contains("King")) {
			if ((landingX < 0) || (landingX > 7) || (landingY < 0) || (landingY > 7)) {
				validMove = false;
			} else {
				if ((checkKingAtLocation(e.getX() - 75, e.getY() + 75))
						|| (checkKingAtLocation(e.getX() - 75, e.getY()))
						|| (checkKingAtLocation(e.getX() - 75, e.getY() - 75))
						|| (checkKingAtLocation(e.getX(), e.getY() - 75))
						|| (checkKingAtLocation(e.getX() + 75, e.getY() - 75))
						|| (checkKingAtLocation(e.getX() + 75, e.getY()))
						|| (checkKingAtLocation(e.getX() + 75, e.getY() + 75))
						|| (checkKingAtLocation(e.getX(), e.getY() + 75))) {
					validMove = false;
				} else {
					if ((((startX - landingX) == 1) && ((startY - landingY) == 1))
							|| (((startX - landingX) == 1) && ((startY - landingY) == 0))
							|| (((startX - landingX) == 1) && ((landingY - startY) == 1))
							|| (((startX - landingX) == 0) && ((startY - landingY) == 1))
							|| (((startX - landingX) == 0) && ((landingY - startY) == 1))
							|| (((landingX - startX) == 1) && ((startY - landingY) == 1))
							|| (((landingX - startX) == 1) && ((startY - landingY) == 0))
							|| (((landingX - startX) == 1) && ((landingY - startY) == 1))) {
						if (piecePresent(e.getX(), e.getY())) {
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
								} else {
									validMove = false;
								}
							} else if (pieceName.contains("Black")) {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
								} else {
									validMove = false;
								}
							}

						} else {
							validMove = true;
						}
					} else {
						validMove = false;
					}
				}
			}
		}

		else if (pieceName.contains("Queen")) {
			Boolean inTheWay = false;
			int distance = Math.abs(startX - landingX);
			if ((landingX < 0) || (landingX > 7) || (landingY < 0) || (landingY > 7)) {
				validMove = false;
			} else {
				if ((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0)
						|| ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) {

					if (Math.abs(startX - landingX) != 0) {
						int xMovement = Math.abs(startX - landingX);
						if (startX - landingX > 0) {
							for (int i = 0; i < xMovement; i++) {
								if (piecePresent(initialX - (i * 75), e.getY())) {
									inTheWay = true;
									break;
								} else {
									inTheWay = false;
								}
							}
						} else {
							for (int i = 0; i < xMovement; i++) {
								if (piecePresent(initialX + (i * 75), e.getY())) {
									inTheWay = true;
									break;
								} else {
									inTheWay = false;
								}
							}
						}
					} else {
						int yMovement = Math.abs(startY - landingY);
						if (startY - landingY > 0) {
							for (int i = 0; i < yMovement; i++) {
								if (piecePresent(e.getX(), initialY - (i * 75))) {
									inTheWay = true;
									break;
								} else {
									inTheWay = false;
								}
							}
						} else {
							for (int i = 0; i < yMovement; i++) {
								if (piecePresent(e.getX(), initialY + (i * 75))) {
									inTheWay = true;
									break;
								} else {
									inTheWay = false;
								}
							}
						}
					}

					if (inTheWay) {
						validMove = false;
					} else {
						if (piecePresent(e.getX(), e.getY())) {
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
									if (checkIfGameOver(e.getX(), e.getY()) && checkWhiteOponent(e.getX(), e.getY())) {
										JOptionPane.showMessageDialog(null, "Game Over - White Wins!!");
										System.exit(1);
									}
								} else {
									validMove = false;
								}
							} else {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
									if (checkIfGameOver(e.getX(), e.getY()) && checkBlackOponent(e.getX(), e.getY())) {
										JOptionPane.showMessageDialog(null, "Game Over - Black Wins!!");
										System.exit(1);
									}
								} else {
									validMove = false;
								}
							}
						} else {
							validMove = true;
						}
					}

				} else if ((Math.abs(startX - landingX) == Math.abs(startY - landingY))) {
					if ((startX - landingX < 0) && (startY - landingY < 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) {
								inTheWay = true;
							}
						}
					} else if ((startX - landingX < 0) && (startY - landingY > 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
								inTheWay = true;
							}
						}
					} else if ((startX - landingX > 0) && (startY - landingY > 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX - (i * 75)), (initialY - (i * 75)))) {
								inTheWay = true;
							}
						}
					} else if ((startX - landingX > 0) && (startY - landingY < 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
								inTheWay = true;
							}
						}
					}

					if (inTheWay) {
						validMove = false;
					} else {
						if (piecePresent(e.getX(), e.getY())) {
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
									if (checkIfGameOver(e.getX(), e.getY()) && checkWhiteOponent(e.getX(), e.getY())) {
										JOptionPane.showMessageDialog(null, "Game Over - White Wins!!");
										System.exit(1);
									}
								} else {
									validMove = false;
								}
							} else {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
									if (checkIfGameOver(e.getX(), e.getY()) && checkBlackOponent(e.getX(), e.getY())) {
										JOptionPane.showMessageDialog(null, "Game Over - Black Wins!!");
										System.exit(1);
									}
								} else {
									validMove = false;
								}
							}
						} else {
							validMove = true;
						}
					}
				}

				else {
					validMove = false;
				}
			}
		}

		else if (pieceName.contains("Rook")) {
			Boolean inTheWay = false;
			if ((landingX < 0) || (landingX > 7) || (landingY < 0) || (landingY > 7)) {
				validMove = false;
			} else {
				if ((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0)
						|| ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) {

					if (Math.abs(startX - landingX) != 0) {
						int xMovement = Math.abs(startX - landingX);
						if (startX - landingX > 0) {
							for (int i = 0; i < xMovement; i++) {
								if (piecePresent(initialX - (i * 75), e.getY())) {
									inTheWay = true;
									break;
								} else {
									inTheWay = false;
								}
							}
						} else {
							for (int i = 0; i < xMovement; i++) {
								if (piecePresent(initialX + (i * 75), e.getY())) {
									inTheWay = true;
									break;
								} else {
									inTheWay = false;
								}
							}
						}
					} else {
						int yMovement = Math.abs(startY - landingY);
						if (startY - landingY > 0) {
							for (int i = 0; i < yMovement; i++) {
								if (piecePresent(e.getX(), initialY - (i * 75))) {
									inTheWay = true;
									break;
								} else {
									inTheWay = false;
								}
							}
						} else {
							for (int i = 0; i < yMovement; i++) {
								if (piecePresent(e.getX(), initialY + (i * 75))) {
									inTheWay = true;
									break;
								} else {
									inTheWay = false;
								}
							}
						}
					}

					if (inTheWay) {
						validMove = false;
					} else {
						if (piecePresent(e.getX(), e.getY())) {
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
									if (checkIfGameOver(e.getX(), e.getY()) && checkWhiteOponent(e.getX(), e.getY())) {
										JOptionPane.showMessageDialog(null, "Game Over - White Wins!!");
										System.exit(1);
									}
								} else {
									validMove = false;
								}
							} else {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
									if (checkIfGameOver(e.getX(), e.getY()) && checkBlackOponent(e.getX(), e.getY())) {
										JOptionPane.showMessageDialog(null, "Game Over - Black Wins!!");
										System.exit(1);
									}
								} else {
									validMove = false;
								}
							}
						} else {
							validMove = true;
						}
					}

				} else {
					validMove = false;
				}
			}
		}

		else if (pieceName.contains("Bishup")) {
			// validMove = true;
			Boolean inTheWay = false;
			int distance = Math.abs(startX - landingX);
			if ((landingX < 0) || (landingX > 7) || (landingY < 0) || (landingY > 7)) {
				validMove = false;
			} else {
				validMove = true;
				if ((Math.abs(startX - landingX) == Math.abs(startY - landingY))) {
					if ((startX - landingX < 0) && (startY - landingY < 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) {
								inTheWay = true;
							}
						}
					} else if ((startX - landingX < 0) && (startY - landingY > 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
								inTheWay = true;
							}
						}
					} else if ((startX - landingX > 0) && (startY - landingY > 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX - (i * 75)), (initialY - (i * 75)))) {
								inTheWay = true;
							}
						}
					} else if ((startX - landingX > 0) && (startY - landingY < 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
								inTheWay = true;
							}
						}
					}

					if (inTheWay) {
						validMove = false;
					} else {
						if (piecePresent(e.getX(), e.getY())) {
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
									if (checkIfGameOver(e.getX(), e.getY()) && checkWhiteOponent(e.getX(), e.getY())) {
										JOptionPane.showMessageDialog(null, "Game Over - White Wins!!");
										System.exit(1);
									}
								} else {
									validMove = false;
								}
							} else {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
									if (checkIfGameOver(e.getX(), e.getY()) && checkBlackOponent(e.getX(), e.getY())) {
										JOptionPane.showMessageDialog(null, "Game Over - Black Wins!!");
										System.exit(1);
									}
								} else {
									validMove = false;
								}
							}
						} else {
							validMove = true;
						}
					}
				} else {
					validMove = false;
				}
			}
		}

		else if (pieceName.contains("Knight")) {
			if ((landingX < 0) || (landingX > 7) || (landingY < 0) || (landingY > 7)) {
				validMove = false;
			} else {
				if ((landingX == startX + 1) && (landingY == startY + 2)
						|| (landingX == startX - 1) && (landingY == startY + 2)
						|| (landingX == startX + 2) && (landingY == startY + 1)
						|| (landingX == startX - 2) && (landingY == startY + 1)
						|| (landingX == startX + 1) && (landingY == startY - 2)
						|| (landingX == startX - 1) && (landingY == startY - 2)
						|| (landingX == startX + 2) && (landingY == startY - 1)
						|| (landingX == startX - 2) && (landingY == startY - 1)) {
					if (piecePresent(e.getX(), e.getY())) {
						if (pieceName.contains("White")) {
							if (checkWhiteOponent(e.getX(), e.getY())) {
								validMove = true;
								if (checkIfGameOver(e.getX(), e.getY()) && checkWhiteOponent(e.getX(), e.getY())) {
									JOptionPane.showMessageDialog(null, "Game Over - White Wins!!");
									System.exit(1);
								}

							} else {
								validMove = false;
							}

						} else {
							if (checkBlackOponent(e.getX(), e.getY())) {
								validMove = true;
								if (checkIfGameOver(e.getX(), e.getY()) && checkBlackOponent(e.getX(), e.getY())) {
									JOptionPane.showMessageDialog(null, "Game Over - Black Wins!!");
									System.exit(1);
								}
							} else {
								validMove = false;
							}
						}

					} else {
						validMove = true;
					}
				} else {
					validMove = false;
				}
			}
		}

		else if (pieceName.equals("BlackPawn")) {

			if ((landingX < 0) || (landingX > 7) || (landingY < 0) || (landingY > 7)) {
				validMove = false;
			} else if (startY == 6) {
				if ((startX == landingX) && (((startY - landingY) == 1 || (startY - landingY) == 2))) {
					if (!piecePresent(e.getX(), e.getY())) {
						validMove = true;
					} else {
						validMove = false;
					}
				} else if (((startY - landingY) == 1) && (((startX - landingX) == 1 || (landingX - startX) == 1))) {
					if (piecePresent(e.getX(), e.getY())) {
						validMove = true;
					} else {
						validMove = false;
					}
				} else {
					validMove = false;
				}
			} else if ((Math.abs(startX - landingX) == 1) && ((startY - landingY) == 1)) {
				if (piecePresent(e.getX(), e.getY())) {
					if (checkBlackOponent(e.getX(), e.getY())) {
						validMove = true;
						if (checkIfGameOver(e.getX(), e.getY()) && checkBlackOponent(e.getX(), e.getY())) {
							JOptionPane.showMessageDialog(null, "Game Over - Black Wins!!");
							System.exit(1);
						}
						if ((e.getY() / 75) == 0) {
							success = true;
						}
					} else {
						validMove = false;
					}
				} else {
					validMove = false;
				}
			} else {
				if ((startX == landingX) && (((startY - landingY) == 1))) {
					if (!piecePresent(e.getX(), e.getY())) {
						validMove = true;
					} else {
						validMove = false;
					}

				} else {
					validMove = false;
				}
			}

		}

		else if (pieceName.equals("WhitePawn")) {

			if ((landingX < 0) || (landingX > 7) || (landingY < 0) || (landingY > 7)) {
				validMove = false;
			} else if (startY == 1) {
				if ((startX == landingX) && (((landingY - startY) == 1) || (landingY - startY) == 2)) {
					if ((!piecePresent(e.getX(), (e.getY())))) {
						validMove = true;
					} else {
						validMove = false;
					}
				} else if (((landingY - startY) == 1) && (((startX - landingX) == 1 || (landingX - startX) == 1))) {
					if (piecePresent(e.getX(), e.getY())) {
						validMove = true;
					} else {
						validMove = false;
					}
				} else {
					validMove = false;
				}
			} else if ((Math.abs(landingX - startX) == 1) && ((landingY - startY) == 1)) {
				if (piecePresent(e.getX(), e.getY())) {
					if (checkWhiteOponent(e.getX(), e.getY())) {
						validMove = true;
						if (checkIfGameOver(e.getX(), e.getY()) && checkWhiteOponent(e.getX(), e.getY())) {
							JOptionPane.showMessageDialog(null, "Game Over - White Wins!!");
							System.exit(1);
						}
						if ((e.getY() / 75) == 7) {
							success = true;
						}
					} else {
						validMove = false;
					}
				} else {
					validMove = false;
				}
			} else {
				if ((startX == landingX) && (((landingY - startY) == 1))) {
					if (!piecePresent(e.getX(), e.getY())) {
						validMove = true;
					} else {
						validMove = false;
					}

				} else {
					validMove = false;
				}
			}
		}

		if (validMove && ((pieceName.contains("White") && nextMove == Colour.BLACK)
				|| (pieceName.contains("Black") && nextMove == Colour.WHITE))) {
			validMove = false;
			System.out.println("It's " + nextMove + "'s turn!");
		}

		if (!validMove) {
			int location = 0;
			if (startY == 0) {
				location = startX;
			} else {
				location = (startY * 8) + startX;
			}
			String pieceLocation = pieceName + ".png";
			pieces = new JLabel(new ImageIcon(pieceLocation));
			panels = (JPanel) chessBoard.getComponent(location);
			panels.add(pieces);
		} else {
			int xMovement = Math.abs((e.getX() / 75) - startX);
			int yMovement = Math.abs((e.getY() / 75) - startY);
			System.out.println("----------------------------------------------");
			System.out.println("The piece that is being moved is : " + pieceName);
			System.out.println("The starting coordinates are : " + "( " + startX + "," + startY + ")");
			System.out.println("The xMovement is : " + xMovement);
			System.out.println("The yMovement is : " + yMovement);
			System.out.println("The landing coordinates are : " + "( " + landingX + "," + landingY + ")");
			System.out.println("----------------------------------------------");
			if (success) {
				if (pieceName.equals("WhitePawn")) {
					int location = 56 + (e.getX() / 75);
					if (c instanceof JLabel) {
						Container parent = c.getParent();
						parent.remove(0);
						pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
						parent = (JPanel) chessBoard.getComponent(location);
						parent.add(pieces);
					} else {
						Container parent = (Container) c;
						pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
						parent = (JPanel) chessBoard.getComponent(location);
						parent.add(pieces);
					}
				} else if (pieceName.equals("BlackPawn")) {
					int location = 0 + (e.getX() / 75);
					if (c instanceof JLabel) {
						Container parent = c.getParent();
						parent.remove(0);
						pieces = new JLabel(new ImageIcon("BlackQueen.png"));
						parent = (JPanel) chessBoard.getComponent(location);
						parent.add(pieces);
					} else {
						Container parent = (Container) c;
						pieces = new JLabel(new ImageIcon("BlackQueen.png"));
						parent = (JPanel) chessBoard.getComponent(location);
						parent.add(pieces);
					}
				}

			} else {
				if (c instanceof JLabel) {
					Container parent = c.getParent();
					parent.remove(0);
					parent.add(chessPiece);
				} else {
					Container parent = (Container) c;
					parent.add(chessPiece);
				}
				chessPiece.setVisible(true);
			}
			if (nextMove == Colour.WHITE) {
				nextMove = Colour.BLACK;
			} else {
				nextMove = Colour.WHITE;
			}
		}
	}

	/*
	 * Main method that gets the ball moving.
	 */
	public static void main(String[] args) {
		JFrame frame = new ChessProject();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
