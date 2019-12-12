package com.smart_home.s_home.data;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.smart_home.s_home.model.Board;

public class BoardRepository {
	
	private final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/s_home_db";
	private final String DATABASE_USER = "root";
	private final String DATABASE_PASSWORD = "Iulian0107.";
	
	public void deleteBoard(String serial){
		
		String queryDelete ="DELETE FROM commandboard where board_serial = ?";
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);PreparedStatement pstmt = conn.prepareStatement(queryDelete)) {

			pstmt.setString(1, serial);
			
			pstmt.execute();
			conn.commit();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>deleteBoard");
			e.printStackTrace();
		}
		
	}

	public Board saveBoard(Board board) {
		PreparedStatement pstmt = null;
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			String querySave = "INSERT INTO commandboard(board_name, board_serial, board_start, board_auto_start, board_contor, board_off) VALUES( ? , ? , ? , ? , ? , ? )";
			
			
			pstmt = conn.prepareStatement(querySave);
			pstmt.setString(1, board.getBoardName());
			pstmt.setString(2, board.getBoardSerial());
			pstmt.setDate(3, (Date) board.getBoardStart());
			pstmt.setInt(4, board.getBoardAutoStart());
			pstmt.setInt(5, board.getBoardContor());
			pstmt.setInt(6, board.getBoardOff());
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>saveBoard");
			e.printStackTrace();
		}
		return board;
	}
	
	private int getContorValue(String serial) {
		int contorValue = 0;
		StringBuilder queryContor = new StringBuilder("select board_contor from commandboard where board_serial = '")
				.append(serial)
				.append("' ");
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);Statement stmt = conn.createStatement();) {
			 
			ResultSet rs = stmt.executeQuery(String.valueOf(queryContor));
			
			contorValue = rs.getInt("board_contor");
			
			conn.commit();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>getContorValue");
			e.printStackTrace();
		}
		
		return contorValue;
	}
	
	//gaseste toate panourile de comanda ale unui user
	public List<Board> findBoards(int userId) {
		List<Board> list = new ArrayList<>();
		String queryBoards = "select * from commandboard join connections on connections.FK_BOARD_ID = commandboard.board_id and connections.FK_USER_ID= "+ userId;
		
		
		return list;
	}
}
