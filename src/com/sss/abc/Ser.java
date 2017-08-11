package com.sss.abc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Ser extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Ser() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.sendRedirect("http://192.168.2.168:8056/WXpay/wechat/aa");
		//request.getRequestDispatcher("http://192.168.2.168:8056/WXpay/wechat/aa").forward(request, response);
		
		try {

			// 解压Book1.xlsx
			ZipFile xlsxFile = new ZipFile(new File("D:\\1_work_record\\yidaigou\\1.xlsx"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			// 先读取sharedStrings.xml这个文件备用
			ZipEntry sharedStringXML = xlsxFile
					.getEntry("xl/sharedStrings.xml");
			InputStream sharedStringXMLIS = xlsxFile
					.getInputStream(sharedStringXML);
			Document sharedString = (Document) dbf.newDocumentBuilder().parse(
					sharedStringXMLIS);
			NodeList str = ((org.w3c.dom.Document) sharedString)
					.getElementsByTagName("t");
			String sharedStrings[] = new String[str.getLength()];
			for (int n = 0; n < str.getLength(); n++) {
				Element element = (Element) str.item(n);
				// System.out.println(element.getTextContent());
				sharedStrings[n] = element.getTextContent();
			}
			// 找到解压文件夹里的workbook.xml,此文件中包含了这张工作表中有几个sheet
			ZipEntry workbookXML = xlsxFile.getEntry("xl/workbook.xml");
			InputStream workbookXMLIS = xlsxFile.getInputStream(workbookXML);
			Document doc = dbf.newDocumentBuilder().parse(workbookXMLIS);
			// 获取一共有几个sheet
			NodeList nl = doc.getElementsByTagName("sheet");

			for (int i = 0; i < nl.getLength(); i++) {
				Element element = (Element) nl.item(i);// 将node转化为element，用来得到每个节点的属性
				//System.out.println(element.getAttribute("name"));// 输出sheet节点的name属性的值
				// 接着就要到解压文件夹里找到对应的name值的xml文件，比如在workbook.xml中有<sheet
				// name="Sheet1" sheetId="1" r:id="rId1" /> 节点
				// 那么就可以在解压文件夹里的xl/worksheets下找到sheet1.xml,这个xml文件夹里就是包含的表格的内容
				ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/sheet"
						+ element.getAttribute("sheetId").toLowerCase()
						+ ".xml");
				InputStream sheetXMLIS = xlsxFile.getInputStream(sheetXML);
				Document sheetdoc = dbf.newDocumentBuilder().parse(sheetXMLIS);
				NodeList rowdata = sheetdoc.getElementsByTagName("row");
				for (int j = 0; j < rowdata.getLength(); j++) {
					// 得到每个行
					// 行的格式：
					Element row = (Element) rowdata.item(j);
					// 根据行得到每个行中的列
					NodeList columndata = row.getElementsByTagName("c");
					for (int k = 0; k < columndata.getLength(); k++) {
						Element column = (Element) columndata.item(k);
						NodeList values = column.getElementsByTagName("v");
						Element value = (Element) values.item(0);
						if (column.getAttribute("t") != null
								& column.getAttribute("t").equals("s")) {
							// 如果是共享字符串则在sharedstring.xml里查找该列的值
							System.out.print(sharedStrings[Integer
									.parseInt(value.getTextContent())]);
							
							String all = sharedStrings[Integer
														.parseInt(value.getTextContent())];
							
							String alls [] = all.split("http:");
							
							Tools.shenchen(alls[0], "http:"+alls[1]);
						} else {
							System.out.print(value.getTextContent() + "");
						}
					}
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		  

		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
