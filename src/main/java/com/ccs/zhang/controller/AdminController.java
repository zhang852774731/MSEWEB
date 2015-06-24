package com.ccs.zhang.controller;

import com.ccs.zhang.domain.Show;
import com.ccs.zhang.util.MSEUtility;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.tree.DefaultDocument;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2015/6/23.
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/mseaddr")
    public String mseAddr (){
        return "mseaddr";
    }

    @RequestMapping(value = "/service")
    public String getMseAddr(@RequestParam(value = "mseaddr") String mseaddr,Model model){
        Document document = new DefaultDocument();
        try {
            document = MSEUtility.loadXMLFromUrl("http://" + mseaddr);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Node> list_directory = MSEUtility.GetValueFromDocumentByXPath(document,MSEUtility.XPATH_DIRECTORY);
        List<Node> list_profiles = MSEUtility.GetValueFromDocumentByXPath(document,MSEUtility.XPATH_PROFILES);
        String directoryPath = list_directory.get(0).getStringValue();
        String profilesPath = list_profiles.get(0).getStringValue();
        model.addAttribute("directory",directoryPath);
        model.addAttribute("profiles",profilesPath);
        return "service";
    }

    @RequestMapping(value = "/service/directory")
    public String directory(@RequestParam(value = "dirpath") String dir,Model model){
        Document document = new DefaultDocument();
        try{
            document = MSEUtility.loadXMLFromUrl(dir);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Node> list_shows = MSEUtility.GetValueFromDocumentByXPath(document,MSEUtility.XPATH_SHOWS);
        List<Node> list_playlists = MSEUtility.GetValueFromDocumentByXPath(document,MSEUtility.XPATH_PLAYLISTS);
        String showsPath = list_shows.get(0).getStringValue();
        String playlistsPath = list_playlists.get(0).getStringValue();
        model.addAttribute("showsPath",showsPath);
        model.addAttribute("playlistsPath",playlistsPath);
        return "directory";
    }

    @RequestMapping(value = "/service/profiles")
    public String profiles(@RequestParam(value = "profiles") String pro){
        return "";
    }

    @RequestMapping(value = "/service/directory/shows")
    public String shows(@RequestParam(value = "showspath") String shows,Model model){
        Document document = new DefaultDocument();
        try {
            document = MSEUtility.loadXMLFromUrl(shows);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Show> showList = new ArrayList<Show>();
        List<Node> titles = MSEUtility.GetValueFromDocumentByXPath(document,MSEUtility.XPATH_TITLE);
        for (int i=0;i<titles.size();i++){
            Show show = new Show();
            List<Node> showOrDir = MSEUtility.GetValueFromDocumentByXPath(document,"//atom:feed/atom:entry[atom:title[text()='"+titles.get(i).getStringValue()+"']]/atom:link[@rel='alternate']/@href");
            show.setTitle(titles.get(i).getStringValue());
            show.setHref(showOrDir.get(0).getStringValue());
            showList.add(show);
        }
        model.addAttribute("showList",showList);
        return "shows";
    }

    @RequestMapping(value = "/service/directory/shows/concret")
    public String showConcret(@RequestParam String showhref,Model model){
        Document document = new DefaultDocument();
        try {
            document = MSEUtility.loadXMLFromUrl(showhref);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Show> showConcretList = new ArrayList<Show>();
        List<Node> titles = MSEUtility.GetValueFromDocumentByXPath(document,MSEUtility.XPATH_TITLE);
        for (int i=0;i<titles.size();i++){
            Show show = new Show();
            List<Node> showConcret = MSEUtility.GetValueFromDocumentByXPath(document,"//atom:feed/atom:entry[atom:title[text()='"+titles.get(i).getStringValue()+"']]/atom:link[@rel='alternate']/@href");
            show.setTitle(titles.get(i).getStringValue());
            show.setHref(showConcret.get(0).getStringValue());
            showConcretList.add(show);
        }
        model.addAttribute("showConcretList",showConcretList);
        return "showsconcrete";
    }
}
