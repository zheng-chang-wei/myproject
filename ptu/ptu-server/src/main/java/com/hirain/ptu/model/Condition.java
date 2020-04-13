package com.hirain.ptu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_condition")
public class Condition implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "JDBC")
  private Integer id;

  private String conditionName;

  private String expression;

  private String type;


}
