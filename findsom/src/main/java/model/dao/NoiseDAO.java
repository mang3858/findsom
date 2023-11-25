package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoiseDAO {
	//방의 신고 개수, 순위, 신고
	private JDBCUtil jdbcUtil = null;
	
	public NoiseDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
	}
	
	// 내 방의 신고 개수
	public int myNoise(String roomInfo) throws SQLException {
		StringBuilder query = new StringBuilder();
        query.append("SELECT count ");
        query.append("FROM noiseinfo ");
        query.append("WHERE roomInfo = ? ");
    	jdbcUtil.setSqlAndParameters(query.toString(), new Object[]{roomInfo});	
    	 
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {		// 검색 결과 존재
				int myRoomCnt = -1;
				myRoomCnt = rs.getInt("count");
				return myRoomCnt;
			}
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 등 해제
		}
		return -1;
	}

	// 소음 순위
	public List<String> noiseRank() throws SQLException {
		StringBuilder query = new StringBuilder();
	    query.append("SELECT roominfo, count ");
	    query.append("FROM noiseinfo ");
	    query.append("ORDER BY count ");
	    query.append("WHERE ROWNUM < 3");
	    	 
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			List<String> ranking = new ArrayList<String>();
			while (rs.next()) {		// 검색 결과 존재
				ranking.add(rs.getString("roominfo"));	
			}
			return ranking;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 등 해제
		}
		return null;
	}

	// 소음 신고
	// 원래 있는 방에 대해서만
	public int noiseReport(String roomInfo) throws SQLException {
		StringBuilder query = new StringBuilder();
	    query.append("UPDATE noiseinfo SET count = ");
	    query.append("(SELECT count FROM noiseinfo WHERE roominfo = ?) + 1");
	    query.append("WHERE roominfo = ?");
	    jdbcUtil.setSqlAndParameters(query.toString(), new Object[]{roomInfo, roomInfo});
	    	 
		try {
			int result = jdbcUtil.executeUpdate();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 등 해제
		}
		return -1;
	}


}
