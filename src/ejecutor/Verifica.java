/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecutor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wcadena
 */
public class Verifica implements Runnable {

    private String url;
    private String lastmod;
    private String changefreq;
    private String priority;
    private int lineaLectura;

    public Verifica(String url, String lastmod, String changefreq, String priority,int lineaLectura) {
        this.url = url;
        this.lastmod = lastmod;
        this.changefreq = changefreq;
        this.priority = priority;
        this.lineaLectura = lineaLectura;
    }

    @Override
    public void run() {
        //System.out.println("Hilo: "+this.url);
        System.out.println(".");
        try {
            String url = this.url;

            URL domain = new URL(url);
            boolean exis= ReadXMLFile.doesURLExist(domain);
            //boolean exis = true;
            if (!exis) {
                /*
                System.out.println("loc : " + url);
                System.out.println("lastmod : " + eElement.getElementsByTagName("lastmod").item(0).getTextContent());
                System.out.println("changefreq : " + eElement.getElementsByTagName("changefreq").item(0).getTextContent());
                System.out.println("priority : " + eElement.getElementsByTagName("priority").item(0).getTextContent());
                System.out.println("Existe : " + (exis ? "Existe" : "No Existe"));
                 */
                System.out.print("Elemento:" + lineaLectura + "|");
                System.out.print("" + url.trim() + "|");
                System.out.print("" + this.lastmod.trim() + "|");
                System.out.print("" + this.changefreq.trim() + "|");
                System.out.print("" + this.priority.trim() + "|");
                System.out.print("" + (exis ? "Existe" : "No Existe") + "|");
                System.out.println("");

            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Verifica.class.getName()).log(Level.SEVERE, null, "Test: "+this.url+"Error"+ex);
        } catch (IOException ex) {
            Logger.getLogger(Verifica.class.getName()).log(Level.SEVERE, null, "Test: "+this.url+"Error"+ex);
        }

    }

}
