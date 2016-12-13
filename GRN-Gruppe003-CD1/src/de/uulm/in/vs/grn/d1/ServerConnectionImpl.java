package de.uulm.in.vs.grn.d1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.UUID;

public class ServerConnectionImpl implements VoidRunnerBoard.ServerConnection {

	private VoidRunnerBoard voidRunnerBoard;
	private InetSocketAddress serverEndpoint;
	private DatagramSocket clientSocket;
	private UpdateHandler updateHandler;

	public ServerConnectionImpl(VoidRunnerBoard voidRunnerBoard, InetSocketAddress serverEndpoint) throws IOException {
		this.voidRunnerBoard = voidRunnerBoard;
		this.serverEndpoint = serverEndpoint;

		clientSocket = new DatagramSocket(); // TODO: DAFUQ? enter port?
												// broadcast everywhere? WOW!
												// Much intelligent.

		updateHandler = new UpdateHandler(voidRunnerBoard, clientSocket);
	}

	@Override
	public boolean[][] initializeGame(UUID uuid) {

		long leastSig = uuid.getLeastSignificantBits();
		long mostSig = uuid.getMostSignificantBits();

		ByteBuffer outBuffer = ByteBuffer.allocate(24);

		outBuffer.putLong(mostSig);
		outBuffer.putLong(leastSig);
		for (int i = 0; i < 8; i++)
			outBuffer.put((byte) -1);

		byte[] payload = outBuffer.array();

		DatagramPacket outPacket = new DatagramPacket(payload, payload.length, serverEndpoint); // TODO:
																								// Port??

		byte[] receiveData = new byte[4096];

		try {
			clientSocket.send(outPacket);
			DatagramPacket incomingPacket = new DatagramPacket(receiveData, receiveData.length);

			clientSocket.receive(incomingPacket);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ByteBuffer buffer = ByteBuffer.wrap(receiveData);

		System.out.println("Initiation:");

		int posX = buffer.getInt();
		int posY = buffer.getInt();
		int boardWidth = buffer.getInt();
		int boardHeight = buffer.getInt();

		System.out.println("posX: " + posX + " posY: " + posY + " Width: " + boardWidth + " Height: " + boardHeight);

		boolean[] flatBoard = new boolean[boardHeight * boardWidth];

		for (int a = 0; a < boardHeight * boardWidth; a++) {
			byte buf = buffer.get();
			for (int b = 7; b >= 0; b--) {
				flatBoard[a] = ((1 & (buf >>> b)) == 1); // parse bit^
			}
		}

		System.out.println(Arrays.toString(flatBoard));

		boolean[][] board = new boolean[boardWidth][boardHeight];

		for (int x = 0; x < boardWidth; x++) {
			for (int y = 0; y < boardHeight; y++) {
				board[x][y] = flatBoard[y + (x * boardHeight)];
			}
		}

		// boolean[][] board = new boolean[boardWidth][boardHeight];
		// int counterX = 0;
		// int counterY = 0;
		//
		// for (int r = 0; r < (boardWidth * boardHeight) / 32; r++) {
		// int bitmapInt = buffer.getInt();
		// String bitmap = Integer.toBinaryString(bitmapInt);
		//
		// int leadingZeros = 32-bitmap.length();
		//
		// for(int cnt = 0; cnt < leadingZeros; cnt++)
		// bitmap = "0" + bitmap;
		//
		// System.out.println(bitmap.length());
		//
		// for (int k = 0; k < 32; k++) {
		//
		// System.out.print(counterX +" : "+counterY+ " | ");
		// if (counterX > boardWidth) {
		//
		// break;
		// }
		// /*if (k < leadingZeros) {
		// board[counterX][counterY] = false;
		// } else {*/
		//
		// board[counterX][counterY] = bitmap.charAt(k) == '1' ? true : false;
		// //}
		// counterY = (counterY + 1) % boardHeight;
		// if (counterY == 0){
		// System.out.println();
		// counterX++;}
		// }
		//
		// }

		System.out.println();
		/*
		 * for (boolean[] bs : board) { for(boolean b:bs)
		 * System.out.print(b?1:0); System.out.println(); }
		 */

		for (int a = 0; a < board[0].length; a++) {
			for (int b = 0; b < board.length; b++) {
				System.out.print(board[b][a] ? 1 : 0);
			}
			System.out.println();
		}

		voidRunnerBoard.setInitialPosition(posX, posY);
		updateHandler.start();
		return board;

		// voidRunnerBoard.setInitialPosition(5, 5);
		// updateHandler.start();
		// return new boolean[32][32];

		// TODO send an initial packet to the server and receive the initial
		// player position and board dimensions
	}

	@Override
	public void sendUpdate(UUID uuid, int x, int y) {

		long leastSig = uuid.getLeastSignificantBits();
		long mostSig = uuid.getMostSignificantBits();

		ByteBuffer outBuffer = ByteBuffer.allocate(24);

		outBuffer.putLong(mostSig);
		outBuffer.putLong(leastSig);
		outBuffer.putInt(x);
		outBuffer.putInt(y);

		byte[] payload = outBuffer.array();

		DatagramPacket outPacket = new DatagramPacket(payload, payload.length, serverEndpoint);
		try {
			clientSocket.send(outPacket);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO remove the following line and actually send an UDP packet
		// containing the update to the server
		// voidRunnerBoard.handleUpdate(uuid, !(x < 0 || y < 0 || x >= 32 || y>=
		// 32), x, y);
	}

}
