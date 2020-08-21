package com.atguigu.gmall.vo;

import com.atguigu.gmall.pms.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInfoVo implements Serializable {

    @ApiModelProperty("总记录数")
    private Long total;
    @ApiModelProperty("总页码")
    private Long totalPage;
    @ApiModelProperty("每页显示的记录数")
    private Long pageSize;
    @ApiModelProperty("分页查出的数据")
    private List<? extends Object> list;
    @ApiModelProperty("当前的页码")
    private Long pageNum;

}
