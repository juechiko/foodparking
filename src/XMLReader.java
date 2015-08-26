
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

    public static void main(String argv[]) {
        try {
            File file = new File("c:/MyJava/FoodParking/MyXMLFile.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("employee");
            System.out.println("Information of all employees");
            
            String driverName = "com.mysql.jdbc.Driver";
            String connString = "jdbc:mysql://localhost:3306/mydb?"
                    + "user=root&password=1234";
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(connString);
            PreparedStatement pstmt = conn.prepareStatement("Insert Into employee (firstname, lastname) values (?,?)");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;

                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("firstname");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();
                    System.out.println("First Name : " + ((Node) fstNm.item(0)).getNodeValue());
                    pstmt.setString(1, ((Node) fstNm.item(0)).getNodeValue());

                    NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("lastname");
                    Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
                    NodeList lstNm = lstNmElmnt.getChildNodes();
                    System.out.println("Last Name : " + ((Node) lstNm.item(0)).getNodeValue());
                    pstmt.setString(2, ((Node) lstNm.item(0)).getNodeValue());
                    pstmt.executeUpdate();
                }
            }
            System.out.println("總計完成讀取之員工資料筆數:" + nodeLst.getLength());
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
