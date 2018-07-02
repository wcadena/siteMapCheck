/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecutor;

/**
 *
 * @author wcadena
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadXMLFile {

    public static void main(String argv[]) {
        String file = "";
        int pilasproceso=1;
        if (argv.length == 2) {
            file = (argv[0]);
            try {
                pilasproceso = Integer.valueOf(argv[1]);
            } catch (NumberFormatException e) {
                System.err.println("error en convertir numero,Cargar la direccion del sitemap.xml y luego el numero de pilas");
                return;
            }
        } else {
            System.err.println("Cargar la direccion del sitemap.xml y luego el numero de pilas");
            return;
        }
        try {
            URL url_inter = new URL(file);
            String uniqueID_file = UUID.randomUUID().toString()+".xml";
            File file_dow = new File(uniqueID_file);
            BufferedInputStream in = new BufferedInputStream(url_inter.openStream());
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file_dow));
            int bit = -1;
            while ((bit = in.read()) != -1) {
                out.write(bit);
            }
            in.close();
            out.close();
            
            
            System.out.println("-->"+url_inter+";"+uniqueID_file);
            File fXmlFile = new File(uniqueID_file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("url");

            System.out.println("----------------------------");
            
            int cantidadHilos = pilasproceso; //cantidad de hilos en el pool
            ExecutorService executor = Executors.newFixedThreadPool(cantidadHilos);
            int lineaLectura=0;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                lineaLectura++;
                Node nNode = nList.item(temp);

                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    String url = eElement.getElementsByTagName("loc").item(0).getTextContent();
                    String lastmod = eElement.getElementsByTagName("lastmod").item(0).getTextContent();
                    //String changefreq = eElement.getElementsByTagName("changefreq").item(0).getTextContent();
                    String changefreq="";
                    //String priority = eElement.getElementsByTagName("priority").item(0).getTextContent();
                    String priority="";
                    Verifica verifica = new Verifica(url, lastmod, changefreq, priority,lineaLectura);
                    //verifica.start( );
                    ////////////////////////////////////////////////////////////////////
                    
                    
                        executor.execute(verifica);
                    
                    
                    ////////////////////////////////////////////////////////////////////

                }
            }
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean doesURLExist(URL url) throws IOException {
        // We want to check the current URL
        HttpURLConnection.setFollowRedirects(false);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // We don't need to get data
        httpURLConnection.setRequestMethod("HEAD");

        // Some websites don't like programmatic access so pretend to be a browser
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
        int responseCode = httpURLConnection.getResponseCode();

        // We only accept response code 200
        return responseCode == HttpURLConnection.HTTP_OK;
    }

}
