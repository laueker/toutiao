package com.nowcoder.toutiao.service;

import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    public int addNews(News news) {
        newsDAO.addNews(news);
        return news.getId();
    }

   public News getById(int newsId) {
       return newsDAO.getById(newsId);    }

//    public String saveImage(MultipartFile file) throws IOException {
//        //xxx._=adfa.jpg
//        int dotPos = file.getOriginalFilename().lastIndexOf(".");//或得文件的扩展名
//        if (dotPos < 0) {
//            return null;
//        }
//        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();//去掉点的后缀名
//        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
//            return null;
//        }
//
//        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;//设置新的文件名
//        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
//                StandardCopyOption.REPLACE_EXISTING);
//        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
//    }

    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }
}


