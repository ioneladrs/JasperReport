package jasperReport;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class mainClass {
	
	private Logger logger = Logger.getLogger(mainClass.class);
	
	public static void main(String[] args) throws IOException, JRException,  ClassNotFoundException, SQLException {
		//To initialize the log4j system properly
		BasicConfigurator.configure();
		/**
		* A test program that is working, showing null values, but exporting the jrxml file to pdf
		*/
		/*
		//compile jrxml
		 JasperReport  jasperReport = JasperCompileManager.compileReport("C:\\Users\\crm0172\\JaspersoftWorkspace\\MyReports\\Task2.jrxml");
		 
		 //Paramteres to report
		 Map<String, Object> parameters = new HashMap<String, Object>();
	
		 JRDataSource dataSource = new JREmptyDataSource();
		 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
	               parameters, dataSource);
		// Make sure the output directory exists.
	       File outDir = new File("C:\\Users\\crm0172\\Documents\\Jasper\\jasperReport\\src\\main\\resources\\jasperoutput");
	       outDir.mkdirs();
	 
	       // Export to PDF.
	       JasperExportManager.exportReportToPdfFile(jasperPrint,
	               "C:\\\\Users\\\\crm0172\\\\Documents\\\\Jasper\\\\jasperReport\\\\src\\\\main\\\\resources\\\\jasperoutput\\\\StyledTextReport.pdf");
	        
	       System.out.println("Done!");
	       */
	     
		/**
		 * The working example with JRMapCollectionDataSource
		 *  
		 */
	
		
		String reportSrcFile = "C:\\Users\\crm0172\\JaspersoftWorkspace\\MyReports\\Task2.jrxml";
		
		//Compile jrxml file into jasper
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
		
		//Connect to the database
		Connection conn=null;
		
		try { 
			Class.forName("oracle.jdbc.OracleDriver");
		    conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.238.205:1521:aquariustest", "TRAINING", "TRAINING");
		}catch (ClassNotFoundException e) {
		    	 
		    	  System.out.println("Could not find the database driver " + e.getMessage());
		    	    } catch (SQLException e) {
		    	 
		    	  System.out.println("Could not connect to the database " + e.getMessage());
		    	    }
		//Retrieve data from dataBase
		   try {
		    Statement statement = conn.createStatement();
		ResultSet results = statement.executeQuery("SELECT country, name, data from specialdate where to_char(data, 'YYYY') = 2017 ORDER BY data ");
		
	//Populating the report's fields with data from Database	
	 List<Map<String,?>> maps = new ArrayList<Map<String, ?>> ();
	 //Map<String,Object> map = new HashMap<String, Object>();
	while (results.next()) {
			String data = results.getString("country");
			Map<String,Object> map = new HashMap<String, Object>();
            map.put("COUNTRY", data);
            data = results.getString("name");
            map.put("NAME", data);
            Date datastmp = results.getTimestamp("data");
            map.put("DATA", datastmp);
            maps.add(map);    		  
		}
		JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);
		
		//Filling the report with data
        JasperPrint print = JasperFillManager.fillReport(jasperReport, new HashMap(), dataSource);
		
		//Showing the report 
		JasperViewer.viewReport(print, false);
		}
		catch (SQLException e) {
			e.printStackTrace();
		
		}
		
		
		/**
		 * example with JRBeanCollectionDataSource
		 */
		/*
		String reportSrcFile = "C:\\Users\\crm0172\\JaspersoftWorkspace\\MyReports\\Task2.jrxml";
		
		//Compile jrxml file into jasper
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
		
		//Connect to the database
		Connection conn=null;
		
		try { 
			Class.forName("oracle.jdbc.OracleDriver");
		    conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.238.205:1521:aquariustest", "TRAINING", "TRAINING");
		}catch (ClassNotFoundException e) {
		    	 
		    	  System.out.println("Could not find the database driver " + e.getMessage());
		    	    } catch (SQLException e) {
		    	 
		    	  System.out.println("Could not connect to the database " + e.getMessage());
		    	    }
		//Retrieve data from dataBase
		   try {
		    Statement statement = conn.createStatement();
		ResultSet results = statement.executeQuery("SELECT country, name, data from specialdate where to_char(data, 'YYYY') = 2017 ORDER BY data ");
		
	//Populating the report's fields with data from Database	
	 List<Map<String,?>> maps = new ArrayList<Map<String, ?>> ();
	 //Map<String,Object> map = new HashMap<String, Object>();
	while (results.next()) {
			String data = results.getString("country");
			Map<String,Object> map = new HashMap<String, Object>();
         map.put("COUNTRY", data);
         data = results.getString("name");
         map.put("NAME", data);
         Date datastmp = results.getTimestamp("data");
         map.put("DATA", datastmp);
         maps.add(map);    		  
         }
		JRBeanCollectionDataSource jRBeanDataSource = new JRBeanCollectionDataSource(maps);
		
		//Filling the report with data
        JasperPrint print = JasperFillManager.fillReport(jasperReport, new HashMap(), jRBeanDataSource);
		
		//Showing the report 
		JasperViewer.viewReport(print, false);
		   }catch (SQLException e) {
				e.printStackTrace();
			
		}
		*/
		
		/*
		 * Working example with JRResultSetDataSource
		 */

		/*
		String reportSrcFile = "C:\\Users\\crm0172\\JaspersoftWorkspace\\MyReports\\Task2.jrxml";
		
		//Compile jrxml file into jasper
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
		
		//Connect to the database
		Connection conn=null;
		
		try { 
			Class.forName("oracle.jdbc.OracleDriver");
		    conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.238.205:1521:aquariustest", "TRAINING", "TRAINING");
		}catch (ClassNotFoundException e) {
		    	 
		    	  System.out.println("Could not find the database driver " + e.getMessage());
		    	    } catch (SQLException e) {
		    	 
		    	  System.out.println("Could not connect to the database " + e.getMessage());
		    	    }
		//Retrieve data from dataBase
		   try {
		    Statement statement = conn.createStatement();
		ResultSet results = statement.executeQuery("SELECT country, name, data from specialdate where to_char(data, 'YYYY') = 2017 ORDER BY data ");
		
		JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(results);
		
		//Filling the report with data
        JasperPrint print = JasperFillManager.fillReport(jasperReport, new HashMap(), resultSetDataSource);
		
		//Showing the report 
		JasperViewer.viewReport(print, false);
		   }catch (SQLException e) {
				e.printStackTrace();
			
		}
		*/
	}	
}