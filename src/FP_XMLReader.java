
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FP_XMLReader {

    // static EmployeeArrayDB employDB = new EmployeeArrayDB();
    public static void main(String argv[]) {
        try {
            File file = new File("c:/MyJava/FoodParking/taipeiParkingInfo_20150811.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("PARK");

            String driverName = "com.mysql.jdbc.Driver";
            String connString = "jdbc:mysql://localhost:3306/foodparking?"
                    + "user=root&password=1234";
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(connString);
            PreparedStatement pstmt = conn.prepareStatement("Insert Into parkinfo "
                    + "(ID, AREA, NAME, SUMMARY, ADDRESS, TEL, PAYEX, "
                    + "SERVICETIME, TW97X, TW97Y, TOTALCAR, AVAILABLE) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element Elmnt1 = (Element) fstNode;

                    NodeList idElmntLst = Elmnt1.getElementsByTagName("ID");
                    Element idElmnt = (Element) idElmntLst.item(0);
                    NodeList id = idElmnt.getChildNodes();
                    //pstmt.setObject(1, idElmnt);
                    pstmt.setString(1, ((Node) id.item(0)).getNodeValue());
                    System.out.println("id : " + ((Node) id.item(0)).getNodeValue());

                    Element Elmnt2 = (Element) fstNode;
                    NodeList AREAElmntLst = Elmnt2.getElementsByTagName("AREA");
                    Element AREAElmnt = (Element) AREAElmntLst.item(0);
                    NodeList AREAE = AREAElmnt.getChildNodes();
                    pstmt.setString(2, ((Node) AREAE.item(0)).getNodeValue());

                    Element Elmnt3 = (Element) fstNode;
                    NodeList NAMEElmntLst = Elmnt3.getElementsByTagName("NAME");
                    Element NAMEElmnt = (Element) NAMEElmntLst.item(0);
                    NodeList NAME = NAMEElmnt.getChildNodes();
                    pstmt.setString(3, ((Node) NAME.item(0)).getNodeValue());

                    Element Elmnt4 = (Element) fstNode;
                    NodeList SUMMARYElmntLst = Elmnt4.getElementsByTagName("SUMMARY");
                    Element SUMMARYElmnt = (Element) SUMMARYElmntLst.item(0);
                    NodeList SUMMARY = SUMMARYElmnt.getChildNodes();
                    pstmt.setString(4, ((Node) SUMMARY.item(0)).getNodeValue());

                    Element Elmnt5 = (Element) fstNode;
                    NodeList ADDRElmntLst = Elmnt5.getElementsByTagName("ADDRESS");
                    Element ADDRElmnt = (Element) ADDRElmntLst.item(0);
                    NodeList ADDR = ADDRElmnt.getChildNodes();
                    pstmt.setString(5, ((Node) ADDR.item(0)).getNodeValue());

                    Element Elmnt6 = (Element) fstNode;
                    NodeList TELElmntLst = Elmnt6.getElementsByTagName("TEL");
                    Element TELElmnt = (Element) TELElmntLst.item(0);
                    NodeList TEL = TELElmnt.getChildNodes();
                    pstmt.setString(6, ((Node) TEL.item(0)).getNodeValue());

                    Element Elmnt7 = (Element) fstNode;
                    NodeList PAYElmntLst = Elmnt7.getElementsByTagName("PAYEX");
                    Element PAYElmnt = (Element) PAYElmntLst.item(0);
                    NodeList PAY = PAYElmnt.getChildNodes();
                    pstmt.setString(7, ((Node) PAY.item(0)).getNodeValue());

                    Element Elmnt8 = (Element) fstNode;
                    NodeList TIMEElmntLst = Elmnt8.getElementsByTagName("SERVICETIME");
                    Element TIMEElmnt = (Element) TIMEElmntLst.item(0);
                    NodeList TIME = TIMEElmnt.getChildNodes();
                    pstmt.setString(8, ((Node) TIME.item(0)).getNodeValue());

                    Element Elmnt9 = (Element) fstNode;
                    NodeList XElmntLst = Elmnt9.getElementsByTagName("TW97X");
                    Element XElmnt = (Element) XElmntLst.item(0);
                    NodeList X = XElmnt.getChildNodes();
                    pstmt.setString(9, ((Node) X.item(0)).getNodeValue());

                    Element Elmnt10 = (Element) fstNode;
                    NodeList YElmntLst = Elmnt10.getElementsByTagName("TW97Y");
                    Element YElmnt = (Element) YElmntLst.item(0);
                    NodeList Y = YElmnt.getChildNodes();
                    pstmt.setString(10, ((Node) Y.item(0)).getNodeValue());

                    Element Elmnt11 = (Element) fstNode;
                    NodeList TOTALElmntLst = Elmnt11.getElementsByTagName("TOTALCAR");
                    Element TOTALElmnt = (Element) TOTALElmntLst.item(0);
                    NodeList TOTAL = TOTALElmnt.getChildNodes();
                    pstmt.setString(11, ((Node) TOTAL.item(0)).getNodeValue());

                    pstmt.setInt(12, 0);
                    pstmt.executeUpdate();
                }

            }
            System.out.println("總計完成讀取之停車場資料筆數:" + nodeLst.getLength());
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
