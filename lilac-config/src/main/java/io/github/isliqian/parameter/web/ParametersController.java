package io.github.isliqian.parameter.web;


import io.github.isliqian.parameter.entity.ParametersBean;
import io.github.isliqian.parameter.service.ParametersService;
import io.github.isliqian.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/sys/parameter")
@Slf4j
public class ParametersController  {
    @Autowired
    private ParametersService parametersService;

    /**
     * 向参数配置模板页注入数据
     *
     * @param type  模板页参数的类型(系统参数，微信参数...)
     * @param model
     * @return
     * @author fengqiao
     */
    @RequestMapping("/{type}")
    public String typeList(@PathVariable("type") String type, ModelMap model, String types, String names) {
        List<Map> list2 = new ArrayList<>();
        String[] split = names.split(",");
        String[] split1 = types.split(",");

        try {
            for (int i = 0; i < split1.length; i++) {
                Map<String, String> map = new HashMap<>();
                map.put("type", split1[i]);
                map.put("name", URLDecoder.decode(split[i], "utf-8"));
                list2.add(map);
            }
        } catch (Exception e) {
            log.error("模板页顶部名称编码转化失败", e);
        }
        List<ParametersBean> list = parametersService.getPataByType(type);
        model.addAttribute("typeList", list2);
        model.addAttribute("data", list);
        model.addAttribute("types", types);
        model.addAttribute("names", URLEncoder.encode(names));
        model.addAttribute("type", type);

        return "/modules/params/parameterTemplate";
    }

    /**
     * 用户修改参数值的更新方法
     *
     * @return
     */
    @RequestMapping("/update")
    public String updateParameter(HttpServletRequest request) {
        //保存完毕需要跳转到上一步页面，需要保存type
        String type = request.getParameter("type");
        String types = request.getParameter("types");
        String names = request.getParameter("names");
        List<ParametersBean> list = parametersService.getPataByType(type);
        //循环页面展示的参数
        for (ParametersBean pb : list) {
            ParametersBean parametersBean = new ParametersBean();
            parametersBean.setBond(pb.getBond());
            //checkbox是同一个name多个值
            String[] values = request.getParameterValues(pb.getBond());
            //当多个值时进行拼接用逗号隔开
            if (values.length > 1) {
                String valuesString = "";
                for (String s : values) {
                    valuesString += s + ",";
                }
                //去掉最后一个逗号
                valuesString = valuesString.substring(0, valuesString.length() - 1);
                parametersBean.setValue(valuesString);
            } else {
                parametersBean.setValue(values[0]);
            }
            //若参数的 立即生效属性 为1则将修改加载到内存
            if ("1".equals(pb.getNowApply())) {
                parametersService.setPataByKey(pb.getBond(), request.getParameter(pb.getBond()));
            }
            //若参数的值发生了改变则记录修改人员和时间
            if (!request.getParameter(pb.getBond()).equals(pb.getValue())) {
                parametersBean.setUpdateDate(new Date());
            }
            //将本次循环的参数的修改更新到数据库
            parametersService.updateparaByKey(parametersBean);
        }

        return "redirect:" + type + "?types=" + types + "&names=" + names;
    }

    /**
     * 运维用户的列表页面数据
     *
     * @param model
     * @param label 根据label查
     * @param type  根据type查
     * @return
     */
    @RequestMapping("/")
    public String list(ModelMap model, String label, String type) {
        ParametersBean parametersBean = new ParametersBean();
//        if (StringUtils.isNotBlank(label)) {
//            parametersBean.setLabel(label);
//            model.addAttribute("bond", label);
//        }
//        if (StringUtils.isNotBlank(type)) {
//            parametersBean.setType(type);
//            model.addAttribute("type", type);
//        }
        List<ParametersBean> list = parametersService.findList(parametersBean);
        model.addAttribute("data", list);
        return "/modules/params/parameterTable";
    }

    /**
     * 去更新页面需加载数据
     *
     * @param id    根据id获取数据
     * @param model
     * @return
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id, ModelMap model) {
        ParametersBean parametersBean = parametersService.get(id);
        model.addAttribute("id", id);
        model.addAttribute("data", parametersBean);
        //添加和修改使用同一个页面，需传一个标识来区分本次请求是修改还是添加
        model.addAttribute("fg", "update");
        return "/modules/params/parameterUpdate";
    }

    /**
     * 运维修改参数的方法
     *
     * @return
     */
    @RequestMapping("updateFromList")
    public String update(ParametersBean parametersBean, RedirectAttributes redirectAttributes) {
        parametersBean.setUpdateDate(new Date());
        parametersService.updateParaById(parametersBean);
        return "redirect:list";
    }

    @RequestMapping("delete")
    public String delete(String id) {
        ParametersBean parametersBean = new ParametersBean();
        parametersBean.setUpdateDate(new Date());
        parametersBean.setId(id);
        parametersBean.setDelFlag("1");
        parametersService.updateParaById(parametersBean);
        return "redirect:list";
    }

    @RequestMapping("toAdd")
    public String toAdd(ModelMap model) {
        model.addAttribute("fg", "add");
        return "/modules/params/parameterUpdate";
    }

    @RequestMapping("add")
    public String add(ParametersBean parametersBean, RedirectAttributes redirectAttributes) {
        parametersBean.setUpdateDate(new Date());
        parametersBean.setCreateDate(new Date());
        parametersService.save(parametersBean);
        if ("1".equals(parametersBean.getNowApply())) {
            parametersService.setPataByKey(parametersBean.getBond(), parametersBean.getValue());
        }
        return "redirect:list";
    }


    /**
     * 批量修改参数排序
     */
    @RequestMapping(value = "updateSort")
    public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
        try {
            for (int i = 0; i < ids.length; i++) {
                ParametersBean bean = new ParametersBean(ids[i]);
                bean.setSort(sorts[i]);
                parametersService.updateSort(bean);
            }
        }catch (Exception e){
        }
        return "redirect:list";
    }


}
