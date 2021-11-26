package com.uploadservice.video.controller;

import com.uploadservice.common.DataResponse;
import com.uploadservice.common.DataResponsePattern;
import com.uploadservice.common.enums.FailMessage;
import com.uploadservice.video.entity.FileUploadEntity;
import com.uploadservice.video.service.VideoDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping(value = "/data/video")
@RestController
@RequiredArgsConstructor
public class VideoDataController extends DataResponsePattern {

    private VideoDataService videoDataService;

    @Autowired
    public void setVideoDataService(VideoDataService _videoDataService) {
        this.videoDataService = _videoDataService;
    }

    @PostMapping
    public DataResponse uploadFile(
            MultipartFile videoFile
    ) throws IOException {
        if(!videoFile.isEmpty()) {
            boolean procTf = videoDataService.fileUplad(videoFile);
            if(!procTf) 
                return super.mvcReponseFail(FailMessage.FILE_UPLOAD_FAIL.getMessage());
                
            return super.mvcReponseSuccess("파일업로드에 성공했습니다.");
        } else
            return super.mvcReponseFail(FailMessage.FILE_UPLOAD_EMPTY_FAIL.getMessage());
    }

    @GetMapping
    public DataResponse fileList() {
        List<FileUploadEntity> videoListData =  videoDataService.findFileList();
        return super.mvcReponseSuccess(videoListData);
    }

    @GetMapping("/play-video/{vf_idx}")
    public StreamingResponseBody playVideo(
        @PathVariable int vf_idx
    ) throws FileNotFoundException {
        return videoDataService.playVideo(vf_idx);
    }
}