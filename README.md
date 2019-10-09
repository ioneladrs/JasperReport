# JasperReport
JasperReport is an open source Java reporting tool that can write to a variety of targets, such as screen (my project is doing just this), a printer, into PDF, HTML, Excel.

@ Prerequisites:

-Create a Maven project from your IDE.

-Add all dependincies to the .pom file as the project is using Maven (tool that can now be used for building and managing any Java-based projects)

@ For the dependincies, you can take a look at my pom files or just take the version from https://mvnrepository.com/

# The repo above is containing 3 Eclipse projects that are creating Jasper reports as per below:
* First task:

1. Draw report in Jasper Studio with one table and all report bands used (add static text in bands like Title, Page Header, Summary). Use TRAINING database as datasource. Do not use table, use Text Fields in Detail Band.

@ This task was performed using the JasperSoft - an application that is offering to you the interface to design your .jrxml file and after that you can import into your Java application. The second task is based on the design of this first task.

* The JasperReport folder

2.Fill report programmatically with same jrxml template and data from JRMapCollectionDataSource, JRBeanCollectionDataSource, JRResultSetDataSource. See Examples of data sources. 

* The JasperReport3 folder

3.Create DynamicReport with the same structure as in p.1, example is here 
https://dynamicreports.readthedocs.io/en/master/GettingStarted.html#step-1-start
https://dynamicreports.lbayer.com/examples/simplereport_step01/
https://dynamicreports.readthedocs.io/en/master/GettingStarted.html

4. Create crosstab report using DynamicReport like 
https://dynamicreports.lbayer.com/examples/crosstabreport/
https://dynamicreports.readthedocs.io/en/master/examples/crosstab/index.html




