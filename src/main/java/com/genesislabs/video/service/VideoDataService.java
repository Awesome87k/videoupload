package com.genesislabs.video.service;

import com.genesislabs.config.security.CookieComponent;
import com.genesislabs.config.security.JwtComponent;
import com.genesislabs.video.entity.FileUploadEntity;
import com.genesislabs.video.entity.UserEntity;
import com.genesislabs.video.repository.VideoInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 회원정보를 조회하는 UserDetailsService를 재정의 합니다.
 */
@Log4j2
@Service
public class VideoDataService {

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    @Autowired
    private HttpServletRequest reqeuest;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private VideoInfoRepository videoInfoRepository;

    @Autowired
    private CookieComponent cookieComponent;


    public boolean fileUplad(MultipartFile _videoFile) throws IOException {
        Cookie refreshCookie = cookieComponent.getCookie(reqeuest, JwtComponent.REFRESH_TOKEN_NAME);
        String refeshToken = refreshCookie.getValue();
        UserEntity userEntity = userDetailsService.loadUserinfoInfoByToken(refeshToken);
        String fileName = _videoFile.getOriginalFilename();
        File uploadFile = new File(filePath + fileName);

        if(uploadFile.exists()) {
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            fileName = _videoFile.getOriginalFilename().substring(0, fileName.lastIndexOf("."))
                    + System.currentTimeMillis()
                    + "." + ext;
        }
        _videoFile.transferTo(new File(fileName));

        FileUploadEntity fileUploadEntity = FileUploadEntity.builder()
                .vu_idx(userEntity.getVu_idx())
                .vf_path(filePath)
                .vf_filename(fileName)
                .vf_filesize(_videoFile.getSize())
                .build();
        boolean insTf = videoInfoRepository.addUploadVideoInfo(fileUploadEntity);
        return insTf;
    }

    private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }
}