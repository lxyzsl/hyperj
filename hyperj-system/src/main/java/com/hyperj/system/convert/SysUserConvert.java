package com.hyperj.system.convert;

import com.hyperj.system.bean.po.SysUserPo;
import com.hyperj.system.bean.vo.SysUserVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * sysUser相关Pojo之间的转化
 */
// @Mapper()这里加上componentModel = "spring"表示该接口自动注入spring的IOC容器中，
// 实质就是给生成的类加了@Component注解，后续可以通过@Autowired装配，
// 省去了 SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);
@Mapper( componentModel="spring" )
public interface SysUserConvert {

    /**
     * sysUserPo --> sysUserVo
     */
    SysUserVo sysUserVo(SysUserPo sysUserPo);

    List<SysUserVo> sysUserVoList(List<SysUserPo> sysUserPo);
}
