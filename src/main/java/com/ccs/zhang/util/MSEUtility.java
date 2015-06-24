package com.ccs.zhang.util;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang on 2015/6/23.
 */
public class MSEUtility {

    public static final String XPATH_DIRECTORY="//app:service/app:workspace/app:collection[app:categories/atom:category[@term='directory']]/@href ";
    public static final String XPATH_PROFILES="//app:service/app:workspace/app:collection[app:categories/atom:category[@term='profile']]/@href ";
    public static final String XPATH_PLAYLISTS="//atom:feed/atom:entry[atom:category[@term='directory'] and atom:title[text()='playlists']]/atom:link[@rel='alternate']/@href";
    public static final String XPATH_SHOWS="//atom:feed/atom:entry[atom:category[@term='directory'] and atom:title[text()='shows']]/atom:link[@rel='alternate']/@href";
    public static final String XPATH_TITLE="//atom:feed/atom:entry/atom:title";
    public static Document loadXMLFromUrl(String url) throws Exception{
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(url);
        return document;
    }

    public static List<Node> GetValueFromDocumentByXPath(Document document,String xPath){
        Map<String,String> namespace = new HashMap<String, String>();
        namespace.put("atom","http://www.w3.org/2005/Atom");
        namespace.put("app","http://www.w3.org/2007/app");
        namespace.put("vdf","http://www.vizrt.com/types");
        namespace.put("viz","http://www.vizrt.com/atom");
        namespace.put("vaext","http://www.vizrt.com/atom-ext");
        XPath documentXPath = document.createXPath(xPath);
        documentXPath.setNamespaceURIs(namespace);
        List<Node> list = documentXPath.selectNodes(document);
        return list;
    }
}
