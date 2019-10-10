package cn.xjk.leave.school.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author XuJiakai
 * @ClassName: PageResponse
 * @Description: Obj使用ObjectRestResponse,list使用TableResultResponse
 * @date 2018/12/24 17:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    Integer pageSize;
    Integer pageIndex;
    Long total;
    List<T> rows;
    private String scrollId;

    PageResponse(long total, List<T> rows) {
        this.rows = rows;
        this.total = total;
    }
}
