package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.model.DownloadedFile;
import org.apache.ibatis.annotations.Delete;

public interface DownloadedFileMapper extends CommonMapper<DownloadedFile> {
    @Delete("delete from t_downloaded_file")
    void clearDownloadedFiles();
}
