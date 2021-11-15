package com.genesislabs.video.controller;

import com.genesislabs.common.DataResponse;
import com.genesislabs.common.DataResponsePattern;
import com.genesislabs.video.entity.FileUploadEntity;
import com.genesislabs.video.service.VideoDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping(value = "/data/video/")
@RestController
@RequiredArgsConstructor
public class VideoDataController extends DataResponsePattern {

    @Autowired
    private VideoDataService videoDataService;

    @PostMapping(value="upload-file")
        public DataResponse uploadFile(
            MultipartFile videoFile
    ) throws IOException {
        if(!videoFile.isEmpty()) {
            boolean procTf = videoDataService.fileUplad(videoFile);
            if(!procTf) 
                return super.mvcReponseFail("파일업로드에 실패했습니다.\n관리자에게 문의해주세요.");
                
            return super.mvcReponseSuccess("파일업로드에 성공했습니다.");
        } else
            return super.mvcReponseFail("요청 파일이 올바르지 않습니다.");
    }

    @GetMapping("file-list")
    public DataResponse fileList() {
        List<FileUploadEntity> videoListData =  videoDataService.findFileList();
        if(ObjectUtils.isEmpty(videoListData))
            return super.mvcReponseFail("데이터 조회에 실패했습니다.\n관리자에게 문의해주세요.");
        else
            return super.mvcReponseSuccess(videoListData);
    }

    @GetMapping("play-video/{vf_idx}")
    public StreamingResponseBody playVideo(
        @PathVariable int vf_idx
    ) throws FileNotFoundException {
        return videoDataService.playVideo(vf_idx);
    }
}