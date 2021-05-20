package org.example.demo.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldSetInputBO {
  private String n;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(n);
    return args;
  }
}
