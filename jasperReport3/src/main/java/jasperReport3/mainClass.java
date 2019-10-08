package jasperReport3;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.BasicConfigurator;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;

public class mainClass {
	/**
	 * The working 3th example
	 * Can be designed better the UI, but the functionality is good.
	 * 
	 */

	public mainClass(){
		build();
	}
	
	private void build() {
		
		//Connect to the database
		Connection conn=null;
		
		try { 
			Class.forName("oracle.jdbc.OracleDriver");
		    conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.238.205:1521:aquariustest", "TRAINING", "TRAINING");
		    }
		catch (ClassNotFoundException e) {
			System.out.println("Could not find the database driver " + e.getMessage());
			}
		catch (SQLException e) {
			System.out.println("Could not connect to the database " + e.getMessage());
			}
		
		//Style the report
		
		StyleBuilder boldStyle         = stl.style().bold();
		StyleBuilder boldCenteredStyle = stl.style(boldStyle)
		                                   .setHorizontalAlignment(HorizontalAlignment.CENTER);

		StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
		                                   .setBorder(stl.pen1Point())
		                                   //.setBackgroundColor(Color.LIGHT_GRAY)
		                                   ;
		StyleBuilder titleStyle = stl.style(boldCenteredStyle)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(15);
		
					
		try {
			
			
			report().
			columns(
					col.column("Country", "country", type.stringType()).setStyle(columnTitleStyle).setTitleStyle(Templates.columnTitleStyle),
					col.column("Name","name", type.stringType()).setStyle(columnTitleStyle).setTitleStyle(Templates.columnTitleStyle),
					col.column("Date", "data", type.dateYearToSecondType()).setStyle(columnTitleStyle).setTitleStyle(Templates.columnTitleStyle))
			.title( cmp.horizontalList()
					 .add(
							 cmp.text("Holidays").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),  
							 cmp.image("C:\\Users\\crm0172\\Documents\\Jasper\\jasperReport3\\src\\main\\resources\\Logo.jpg").
							   setFixedDimension(250, 60).setHorizontalAlignment(HorizontalAlignment.RIGHT)
							   )
							 .newRow()
							 .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10))).
			pageFooter(cmp.pageXofY()).
			setDataSource("SELECT country, name, data from specialdate where to_char(data, 'YYYY') = 2017 ORDER BY country ", 
					conn).
			show();
					}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The 4th exercise has the logical part functioning
	 * The UI has to be designed better
	 * There are missing months when, there is no holiday neither in IT neither in MD
	 * Should be fixed to show it.
	 * 
	 * NB! For the 5th exercise, just add .setShowColumnTitle(showColumnTitle) on the commented line 
	 */
	/*
	public mainClass() {
		build();
	}
	private void build() {
		
		StyleBuilder titleStyle              = stl.style(Templates.boldCenteredStyle)
				   .setVerticalAlignment(VerticalAlignment.MIDDLE)
				   .setFontSize(15).setBorder(stl.pen1Point());

		//Connect to the database
		Connection conn=null;
		
		try { 
			Class.forName("oracle.jdbc.OracleDriver");
		    conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.238.205:1521:aquariustest", "TRAINING", "TRAINING");
		    }
		catch (ClassNotFoundException e) {
			System.out.println("Could not find the database driver " + e.getMessage());
			}
		catch (SQLException e) {
			System.out.println("Could not connect to the database " + e.getMessage());
			}
		
		CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("Country", String.class)
				.setHeaderStyle(Templates.columnTitleStyle)
				;
		
		CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup("Month", String.class)
				.setHeaderStyle(Templates.columnTitleStyle)
				;
						
		
		CrosstabBuilder crosstab = ctab.crosstab()
				.headerCell(cmp.text("Country/MONTH").setStyle(Templates.boldCenteredStyle).setStyle(Templates.columnTitleStyle))
				.rowGroups(rowGroup.setTotalHeaderStyle(Templates.columnTitleStyle))//.setShowTotal(false))// .setShowTotal(false) To hide the total of days
				.columnGroups(columnGroup.setTotalHeaderStyle(Templates.columnTitleStyle))//.setShowTotal(false))
				.setCellHeight(20)
				.setCellWidth(50)
				.measures(
						ctab.measure("month", Integer.class, Calculation.COUNT).setStyle(Templates.columnStyle)
						);
		
		
		try {
		report()
		.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
		.title(cmp.horizontalList()
		 .add(
				 cmp.text("Holidays").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),  
				 cmp.image("C:\\Users\\crm0172\\Documents\\Jasper\\jasperReport3\\src\\main\\resources\\Logo.jpg").
				   setFixedDimension(250, 60).setHorizontalAlignment(HorizontalAlignment.RIGHT)
				   )
				 .newRow()
				.add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
				.summary(crosstab)
				.pageFooter(cmp.pageXofY())
			    .setDataSource("SELECT ID, COUNTRY, TO_CHAR(DATA,'mm') AS month " + 
			    		"FROM specialdate " + 
			    		"where to_char(data, 'YYYY') = '2017' " + 
			    		"ORDER BY month", conn)
			    .show();
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	*/

	public static void main(String[] args) throws IOException, JRException,  ClassNotFoundException, SQLException {
	
		BasicConfigurator.configure();
		new mainClass();
	}
	
}
