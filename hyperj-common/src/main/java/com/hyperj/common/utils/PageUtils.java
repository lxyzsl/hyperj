package com.hyperj.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageUtils {

    public static <PO, VO> PageInfo<VO> PO2VO(PageInfo<PO> pageInfoPo, List<VO> listVo) {

        // 创建Page对象，实际上是一个ArrayList类型的集合
        Page<VO> page = new Page<>(pageInfoPo.getPageNum(), pageInfoPo.getPageSize());
        page.setTotal(pageInfoPo.getTotal());
        PageInfo<VO> voPageInfo = new PageInfo<>(page);
        voPageInfo.setList(listVo);

        return  voPageInfo;
    }


}
