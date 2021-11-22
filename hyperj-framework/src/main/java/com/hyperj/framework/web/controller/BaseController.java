package com.hyperj.framework.web.controller;

import cn.hutool.core.lang.Validator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyperj.common.enums.HttpStatusEnum;
import com.hyperj.common.utils.PageUtils;
import com.hyperj.common.utils.sql.SqlUtil;
import com.hyperj.framework.web.page.PageDomain;
import com.hyperj.framework.web.page.TableDataInfo;
import com.hyperj.framework.web.page.TableSupport;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class BaseController {

    /**
     * 设置请求分页数据
     */
    protected void startPage(){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if(Validator.isNotNull(pageNum) && Validator.isNotNull(pageSize)){
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum,pageSize,orderBy);
        }
    }

    /**
     * 响应请求分页数据(VO转换)
     */
    protected <PO,VO> TableDataInfo<VO> getDataTable(List<PO> listPo,List<VO> listVo){
        // 根据原始的PO数据生成分页对象
        PageInfo<PO> pageInfoPo = new PageInfo<>(listPo);
        // 通过PageUtils将pageInfoPo对象转化成前端需要的pageInfoVo对象
        PageInfo<VO> pageInfoVo =  PageUtils.PO2VO(pageInfoPo,listVo);
        // 将pageInfoVo对象封装成响应数据返回给前端
        TableDataInfo<VO> respData = new TableDataInfo<>();
        respData.setCode(HttpStatusEnum.SUCCESS.getResultCode());
        respData.setMsg("查询成功");
        return respData.setData(pageInfoVo.getList())
                .setTotal(pageInfoVo.getTotal())
                .setCurrent(pageInfoVo.getPageNum())
                .setPageSize(pageInfoVo.getPageSize());
    }

    /**
     * 响应请求分页数据
     */
    protected <PO> TableDataInfo<PO> getDataTable(List<PO> listPo){
        PageInfo<PO> pageInfoPo = new PageInfo<>(listPo);
        TableDataInfo<PO> respData = new TableDataInfo<>();
        respData.setCode(HttpStatusEnum.SUCCESS.getResultCode());
        respData.setMsg("查询成功");
        return respData.setData(pageInfoPo.getList())
                .setTotal(pageInfoPo.getTotal())
                .setCurrent(pageInfoPo.getPageNum())
                .setPageSize(pageInfoPo.getPageSize());
    }
}
