package main.java.jasper1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class MainClass {
	static final Logger logger = Logger.getLogger(MainClass.class);

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		logger.info("This is logger info");
		HashMap hm =null;
		
		try {
			System.out.println("Start...");
			//get jasper Report
			
			String jrxmlFileName ="C:\\Users\\crm0172\\JaspersoftWorkspace\\MyReports\\Task1.jrxml";
			String jasperlFileName ="C:\\Users\\crm0172\\JaspersoftWorkspace\\MyReports\\Task1.jasper";
			String pdfFileName = "C:\\Users\\crm0172\\JaspersoftWorkspace\\MyReports\\Task1.pdf";
			
			JasperCompileManager.compileReportToFile(jrxmlFileName,jasperlFileName);
			
			String dbUrl = "jdbc:oracle:thin:@192.168.238.205:1521:aquariustest";
			 String dbDriver = "oracle.jdbc.driver.OracleDriver";
			 String dbUname = "TRAINING";
			 String dbPwd = "TRAINING";
			 Class.forName(dbDriver);
			 Connection connection = DriverManager.getConnection(dbUrl, dbUname, dbPwd);
			 
			 JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperlFileName, hm, connection);
			
			 JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);
			 
			 System.out.print("Done exporting !");
		}catch(Exception e) {
			System.out.print("Exceptiion" + e);	
		}
}
}
