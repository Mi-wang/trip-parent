package cn.wolfcode.controller;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IStrategyService;
import cn.wolfcode.utils.OSSUtils;
import cn.wolfcode.vo.AjaxResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:42
 */
@RestController
@RequestMapping("/strategies")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;

    @GetMapping("/query")
    public AjaxResult<?> query(BaseQuery qo) {
        Page<Strategy> page = strategyService.queryPage(qo);
        return AjaxResult.success(page);
    }

    @GetMapping("/detail")
    public AjaxResult<?> detail(Long id) {
        return AjaxResult.success(strategyService.getById(id));
    }

    @PostMapping("/save")
    public AjaxResult<?> save(Strategy Strategy) {
        strategyService.save(Strategy);
        return AjaxResult.success();
    }

    @PostMapping("/uploadImg")
    public AjaxResult<?> uploadImg(MultipartFile upload) {

        // 图片的原始名字
        String originalFilename = upload.getOriginalFilename();

        // 文件上传操作
        String url = OSSUtils.uploadFile("images", upload);

        // {code: 200, msg: "success"}
        return AjaxResult.success()
                // {code: 200, msg: "success", fileName: "xxx"}
                .put("fileName", originalFilename)
                // {code: 200, msg: "success", fileName: "xxx", uploaded: 1}
                .put("uploaded", 1)
                // {code: 200, msg: "success", fileName: "xxx", uploaded: 1, url: url}
                .put("url", url);
    }

    @PostMapping("/update")
    public AjaxResult<?> update(Strategy Strategy) {
        strategyService.updateById(Strategy);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{id}")
    public AjaxResult<?> delete(@PathVariable Long id) {
        if (id != null) {
            strategyService.removeById(id);
        }
        return AjaxResult.success();
    }

}
