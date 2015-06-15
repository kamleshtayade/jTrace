/**
 * 
 */
package com.spring.app.jms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spring.app.web.rest.BomheaderResource;

/**
 * @author ktayade
 *
 */
public class QueueToPostGre {
	
	private final Logger log = LoggerFactory.getLogger(QueueToPostGre.class);
	
	ResultSet rs = null;
	public Statement stat = null;
	String[] tokens = null;
	
	/*public static void main(String args[]){
		
		String str = "21 [11433588733714, ppattnai-ws01, 21u, Pun, 67321, ppattnai, suh, 3, 12:30, 12:45, 123, 32]";
		Connection  conn = DBConnPostGre.getConnection();
		//Connection  conn = DBConnPostGre.getConnection();
	    // QueueToPostGre queueToPostGre = new QueueToPostGre();
		insertToPostGreMs(conn, str);
		
	}*/

	public void insertToPostGreMs(Connection  conn, TextMessage message){
		
		log.debug("Inside insertToPostGreMs ");
		
		
		try {
			tokens = message.getText().toString().split(",|\\[|\\]", -1);
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tokens[14] = tokens[14]=="N"?"FALSE":"TRUE";
		tokens[16] = tokens[16]=="N"?"FALSE":"TRUE";
		
		for(int i=0;i<tokens.length;i++){
			log.debug("tokens" +i+ " --> " +tokens[i]);
			
		}
		
		
		log.debug(message.toString());
		
		try {
			stat = conn.createStatement();
			String plSql = "INSERT INTO domheader(id, is_auto, cycle_time, is_multi, panel_qty,"
					+ " opr, shiftsup, shift, shiftstart, shiftend, solder, machine_id, jmxid, consumable_id, tool_id) "
					+ " VALUES (nextval('domheader_id_seq'), "+tokens[14]+", "+tokens[15]+", "+tokens[16]+", "+tokens[17]+", '"+tokens[6]+"',"
					+ " '"+tokens[7]+"', "+tokens[8]+", now(), now(), '"+tokens[11]+"', '"+tokens[2]+"', "+tokens[1]+", '"+tokens[13]+"', '"+tokens[12]+"')";
			
			log.debug(plSql);
			stat.execute(plSql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertToPostGreCh(Connection  conn, TextMessage message){
		
		log.debug("Inside insertToPostGreCh ");
				
		try {
			log.debug(message.getText().toString());
			tokens = message.getText().toString().split(", |\\[|\\]", -1);
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Long headerId = verifyHeaderId(conn, tokens[2].toString());
				try {
					stat = conn.createStatement();
					String insertSQL = "INSERT INTO domline("+
							 "id, serialno, scantime, domheader_id)"+
							 "VALUES (nextval('itemsubcat_id_seq'), '"+tokens[0]+"', '"+tokens[1]+"', '"+headerId+"')";  
					
					log.debug("insertSQL  " + insertSQL);
					
					stat.execute(insertSQL);
			
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
	}
	public Long verifyHeaderId(Connection conn, String headerId){
		
		
		try {
			String query = "select ID from domheader where jmxid='"+headerId+"'";
			
			log.debug("Query " +query);
		    stat = conn.createStatement();
		    rs = stat.executeQuery(query);
		    if(rs.next()){
		    	return rs.getLong(1);
		    }else{
		    	return null;
		    }
		    	
		    //stat.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	    
	}

}
