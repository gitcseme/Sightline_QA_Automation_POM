package configsAndTestData;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

	public class ReadXML {
		static String filepath="C:\\Users\\smangal\\Documents\\XML.xml";
		static DocumentBuilder builder;

	  public static void main(String argv[]) throws Exception {

		  //Get Document Builder
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      builder = factory.newDocumentBuilder();
	       
	      //Build Document
	      Document document = builder.parse(new File(filepath));
	       
	      //Normalize the XML Structure; It's just too important !!
	      document.getDocumentElement().normalize();
	       
	      //Here comes the root node
	      Element root = document.getDocumentElement();
	      System.out.println(root.getNodeName());
	       
	      //Get all employees
	      NodeList nList = document.getElementsByTagName("kvp");
	      NodeList nList1 = document.getElementsByTagName("annotation");
	      System.out.println("============================");
	      System.out.println("Number of elements with tag name annotation : " + nList1.getLength());
	       
	      visitChildNodes(nList);
	   }
	 
	   //This function is called recursively
	   private static void visitChildNodes(NodeList nList)
	   {
	      for (int temp = 0; temp < nList.getLength(); temp++)
	      {
	         Node node = nList.item(temp);
	         if (node.getNodeType() == Node.ELEMENT_NODE)
	         {
	            System.out.println("Node Name = " + node.getNodeName() + "; Value = " + node.getTextContent());
	            //Check all attributes
	            if (node.hasAttributes()) {
	               // get attributes names and values
	               NamedNodeMap nodeMap = node.getAttributes();
	               for (int i = 0; i < nodeMap.getLength(); i++)
	               {
	                   Node tempNode = nodeMap.item(i);
	                   System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
	               }
	               if (node.hasChildNodes()) {
	                  //We got more childs; Let's visit them as well
	                  visitChildNodes(node.getChildNodes());
	               }
	           }
	         }
	      
	      }
		}
	   
	}
	