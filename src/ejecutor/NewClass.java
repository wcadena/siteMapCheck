/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecutor;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 *
 * @author wcadena
 */
public class NewClass {
    public static void main(String args[]) throws MalformedURLException, IOException {
        //File temp = File.createTempFile("temp-file-name", ".tmp");
        
        File myDir = new File("src\\sitemaptemp1");
        if (!myDir.exists()) {
            if (myDir.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        /*
        WebSitemapGenerator wsg = new WebSitemapGenerator("http://tatoo.local/ec/", myDir);
        wsg.addUrl("http://tatoo.local/ec/home"); // repeat multiple times
        wsg.write();
        /**/
        /*
        WebSitemapGenerator wsg = new WebSitemapGenerator("http://tatoo.local/ec/", myDir);
        for (int i = 0; i < 60000; i++) {
            wsg.addUrl("http://tatoo.local/ec/" + i + ".html");
        }
        wsg.write();
        wsg.writeSitemapsWithIndex(); // generate the sitemap_index.xml
        */
        WebSitemapGenerator wsg = WebSitemapGenerator.builder("http://tatoo.local/ec/", myDir)
                .autoValidate(true).build(); // validate the sitemap after writing
        wsg.addUrl("http://tatoo.local/ec/home");
        wsg.write();
    }
}
