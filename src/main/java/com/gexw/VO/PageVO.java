package com.gexw.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Long totalPage;
    private Long allRow;
    private T data;

}
