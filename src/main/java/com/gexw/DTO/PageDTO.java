package com.gexw.DTO;

import lombok.Data;

@Data
public class PageDTO {

    private Integer pageNum=1;
    private Integer pageSize=5;
    private Integer total;

}
