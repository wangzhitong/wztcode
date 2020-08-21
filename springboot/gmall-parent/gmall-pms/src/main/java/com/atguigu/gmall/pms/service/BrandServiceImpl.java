package com.atguigu.gmall.pms.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.Brand;
import com.atguigu.gmall.pms.mapper.BrandMapper;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2020-08-11
 */
@Slf4j
@Service
@Component
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    BrandMapper brandMapper;

    @Override
    public PageInfoVo brandPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        QueryWrapper<Brand> query = null;
        if (!StringUtils.isEmpty(keyword)){
            query = new QueryWrapper<Brand>().like("name",keyword);
        }

        IPage<Brand> iPage = brandMapper.selectPage(new Page<Brand>(pageNum.longValue(), pageSize.longValue()), query);
        PageInfoVo pageInfoVo = new PageInfoVo(iPage.getTotal(),iPage.getPages(),pageSize.longValue(),iPage.getRecords(),iPage.getCurrent());
        return pageInfoVo;
    }
}
