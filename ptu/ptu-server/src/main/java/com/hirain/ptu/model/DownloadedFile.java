package com.hirain.ptu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_downloaded_file")
public class DownloadedFile implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "JDBC")
  private Integer id;

  private String fileName;

  public DownloadedFile() {}

  public DownloadedFile(String fileName) {
    this.fileName = fileName;
  }
}
