package cn.wolfcode.controller;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.StrategyContent;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.exception.AuthException;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.query.StrategyQuery;
import cn.wolfcode.redis.key.ArticleRedisPrefix;
import cn.wolfcode.service.IStrategyService;
import cn.wolfcode.service.impl.UserTokenService;
import cn.wolfcode.utils.OSSUtils;
import cn.wolfcode.vo.AjaxResult;
import cn.wolfcode.vo.ArticleStatVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    @Autowired
    private UserTokenService userTokenService;
    @GetMapping("/query")
    public AjaxResult<?> query(StrategyQuery qo) {
        Page<Strategy> page = strategyService.queryPage(qo);
        return AjaxResult.success(page);
    }

    @GetMapping("/content")
    public AjaxResult<?> content(Long id) {
        StrategyContent content = strategyService.getContent(id);
        return AjaxResult.success(content);
    }

    @GetMapping("/viewnnumTop3")
    public AjaxResult<?> viewnnumTop3(Long destId) {
        List<Strategy> top3 = strategyService.viewnnumTop3(destId);
        return AjaxResult.success(top3);
    }

    @GetMapping("/detail")
    public AjaxResult<?> detail(Long id) {
        return AjaxResult.success(strategyService.getById(id));
    }

    @GetMapping("/isUserFavor")
    public AjaxResult<Boolean> isUserFavor(Long sid, HttpServletRequest req) {
        UserInfo user = userTokenService.getLoginUser(req);
        if (user == null) {
            throw new AuthException("用户尚未登录");
        }
        Boolean ret = strategyService.isFavor(sid, user.getId());
        return AjaxResult.success(ret);
    }

    @PostMapping("/favornumIncr")
    public AjaxResult<ArticleStatVo> favornumIncr(Long sid, HttpServletRequest req) {
        UserInfo user = userTokenService.getLoginUser(req);
        if (user == null) {
            throw new AuthException("用户尚未登录");
        }
        return strategyService.favornumIncr(sid, user.getId());
    }
    @PostMapping("/save")
    public AjaxResult<?> save(Strategy Strategy) {
        strategyService.save(Strategy);
        return AjaxResult.success();
    }

    @PostMapping("/veiwnumIncr")
    public AjaxResult<?> veiwnumIncr(Long sid) {
        ArticleStatVo vo = strategyService.veiwnumIncr(sid);
        return AjaxResult.success(vo);
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
