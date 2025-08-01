package com.gexw.DTO;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List; /**
 * 分页数据结构
 * @param <T> 数据类型
 */
@Data
public class PageData<T> {
    private List<T> data;   // 当前页数据
    private long pageNum;      // 当前页
    private long pageSize;        // 每页条数
    private long allRow;       // 总记录数
    private long totalPage;       // 总页数

    // 从MP的Page对象转换
    public static <T> PageData<T> from(Page<T> page) {
        PageData<T> data = new PageData<>();
        data.setData(page.getRecords());
        data.setPageNum(page.getCurrent());
        data.setPageSize(page.getSize());
        data.setAllRow(page.getTotal());
        data.setTotalPage(page.getPages());
        return data;
    }


}
