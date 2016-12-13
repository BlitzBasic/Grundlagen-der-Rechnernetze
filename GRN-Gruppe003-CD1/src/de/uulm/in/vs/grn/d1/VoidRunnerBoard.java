package de.uulm.in.vs.grn.d1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.Timer;

public class VoidRunnerBoard extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final int DOT_SIZE = 21;
    private final int DELAY = 300;

    private boolean[][] board;

    protected int boardWidth;
    protected int boardHeight;

    private int x;
    private int y;

    private HashMap<UUID, int[]> playerMap;
    private HashMap<UUID, Color> colorMap;

    private int direction = KeyEvent.VK_LEFT;

    private boolean inGame = true;

    private Timer timer;

    private UUID uuid;

    private ServerConnection gameServer;

    public VoidRunnerBoard(InetSocketAddress serverEndpoint) throws IOException {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        this.gameServer = new ServerConnectionImpl(this, serverEndpoint);

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        initGame();
    }

    private void initGame() throws IOException {
        uuid = UUID.randomUUID();

        playerMap = new HashMap<>();
        colorMap = new HashMap<>();

        board = gameServer.initializeGame(uuid);

        boardWidth = DOT_SIZE * board.length - 1;
        boardHeight = DOT_SIZE * board[0].length - 1;

        // start main game timer
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw tiles
        g.setColor(Color.GRAY);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (!board[i][j]) {
                    g.fillRect(i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE - 1, DOT_SIZE - 1);
                }
            }
        }

        // draw player avatars
        for (Map.Entry<UUID, int[]> entry : playerMap.entrySet()) {
            if (entry.getKey().equals(uuid))
                continue;

            g.setColor(colorMap.get(entry.getKey()));
            int[] xy = entry.getValue();
            g.fillOval(xy[0] * DOT_SIZE, xy[1] * DOT_SIZE, DOT_SIZE - 1, DOT_SIZE - 1);
        }

        // draw own player avatar
        if (inGame) {
            g.setColor(Color.CYAN);
            g.fillOval(x * DOT_SIZE, y * DOT_SIZE, DOT_SIZE - 1, DOT_SIZE - 1);
        } else {
            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        String msg2 = "(press ESC to exit)";
        Font font = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (boardWidth - metrics.stringWidth(msg)) / 2, boardHeight / 2 - metrics.getHeight());
        g.drawString(msg2, (boardWidth - metrics.stringWidth(msg2)) / 2, boardHeight / 2 + metrics.getHeight());
    }

    public void setInitialPosition(int x, int y) {
        if (board != null)
            throw new IllegalStateException("initial position cannot be set after the game has started");
        this.x = x;
        this.y = y;
    }

    public void handleUpdate(UUID id, boolean alive, int x, int y) {
        if (playerMap.containsKey(id)) {
            if (alive) {
                // update player
                int[] playerCoords = playerMap.get(id);
                playerCoords[0] = x;
                playerCoords[1] = y;

                board[x][y] = true;
            } else {
                // remove player
                playerMap.remove(id);

                // check if we just lost the game
                if (id.equals(uuid)) {
                    inGame = false;
                }
            }
        } else {
            if (alive) {
                // create player
                playerMap.put(id, new int[] { x, y });
                colorMap.put(id, new Color(id.hashCode()));
            } else {
                // ignore update
            }
        }
    }

    // handle one move step per game tick
    private void move() {
        switch (direction) {
        case KeyEvent.VK_LEFT:
            x -= 1;
            break;
        case KeyEvent.VK_RIGHT:
            x += 1;
            break;
        case KeyEvent.VK_UP:
            y -= 1;
            break;
        case KeyEvent.VK_DOWN:
            y += 1;
            break;
        }

        // send an update packet to the server
        gameServer.sendUpdate(uuid, x, y);
    }

    @Override
    // executed every game tick
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                direction = e.getKeyCode();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            }
        }
    }

    interface ServerConnection {
        boolean[][] initializeGame(UUID uuid);

        void sendUpdate(UUID uuid, int x, int y);
    }

}
