package com.ss.pdfdownload.controller;

import com.ss.pdfdownload.pojo.Zzgg;
import com.ss.pdfdownload.utils.Msg;
import com.ss.pdfdownload.utils.PdfDownload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ss
 */
@Controller
public class PdfDownloadController {

    @RequestMapping("/show")
    public ModelAndView show() {
        ModelAndView mav = new ModelAndView("pd");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/pd/{id}")
    public Msg pd(@PathVariable("id") String id) {
        PdfDownload pdfDownload = new PdfDownload();
        try {
            ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
            List<Zzgg> zzggListlist = pdfDownload.getzxgg(id);
            for (Zzgg list : zzggListlist) {
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                String announcementId = list.getAnnouncementId();
                String announceTime = sdf2.format(new Date(Long.parseLong(list.getAnnouncementTime())));
                String urlStr = "http://www.cninfo.com.cn/new/announcement/download?bulletinId=" + announcementId + "&announceTime=" + announceTime;
                PdfDownload.downLoadByUrl(urlStr, "" + list.getSecName() + "-" + announceTime + ".pdf", "/Users/ss/Documents/test/pdf");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Msg.success();
    }
}
