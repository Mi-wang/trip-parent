package cn.wolfcode.controller;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.StrategyContent;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.exception.AuthException;
import cn.wolfcode.query.StrategyQuery;
import cn.wolfcode.service.IStrategyService;
import cn.wolfcode.service.impl.UserTokenService;
import cn.wolfcode.utils.OSSUtils;
import cn.wolfcode.vo.R;
import cn.wolfcode.vo.ArticleStatVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public R<?> query(StrategyQuery qo) {
        Page<Strategy> page = strategyService.queryPage(qo);
        return R.ok(page);
    }

    @GetMapping("/content")
    public R<?> content(Long id) {
        StrategyContent content = strategyService.getContent(id);
        return R.ok(content);
    }

    @GetMapping("/viewnnumTop3")
    public R<?> viewnnumTop3(Long destId) {
        List<Strategy> top3 = strategyService.viewnnumTop3(destId);
        return R.ok(top3);
    }

    @GetMapping("/detail")
    public R<?> detail(Long id) {
        return R.ok(strategyService.getById(id));
    }

    @GetMapping("/isUserFavor")
    public R<Boolean> isUserFavor(Long sid, HttpServletRequest req) {
        UserInfo user = userTokenService.getLoginUser(req);
        if (user == null) {
            throw new AuthException("用户尚未登录");
        }
        Boolean ret = strategyService.isFavor(sid, user.getId());
        return R.ok(ret);
    }

    @PostMapping("/favornumIncr")
    public R<ArticleStatVo> favornumIncr(Long sid, HttpServletRequest req) {
        UserInfo user = userTokenService.getLoginUser(req);
        if (user == null) {
            throw new AuthException("用户尚未登录");
        }
        return strategyService.favornumIncr(sid, user.getId());
    }

    @PostMapping("/thumbnumIncr")
    public R<ArticleStatVo> thumbnumIncr(Long sid, HttpServletRequest req) {
        UserInfo user = userTokenService.getLoginUser(req);
        if (user == null) {
            throw new AuthException("用户尚未登录");
        }
        return strategyService.thumbnumIncr(sid, user.getId());
    }

    @PostMapping("/save")
    public R<?> save(Strategy Strategy) {
        strategyService.save(Strategy);
        return R.ok();
    }

    @PostMapping("/veiwnumIncr")
    public R<?> veiwnumIncr(Long sid) {
        ArticleStatVo vo = strategyService.veiwnumIncr(sid);
        return R.ok(vo);
    }

    @PostMapping("/uploadImg")
    public R<?> uploadImg(MultipartFile upload) {

        // 图片的原始名字
        String originalFilename = upload.getOriginalFilename();

        // 文件上传操作
        String url = OSSUtils.uploadFile("images", upload);

        // {code: 200, msg: "ok"}
        return R.ok()
                // {code: 200, msg: "ok", fileName: "xxx"}
                .put("fileName", originalFilename)
                // {code: 200, msg: "ok", fileName: "xxx", uploaded: 1}
                .put("uploaded", 1)
                // {code: 200, msg: "ok", fileName: "xxx", uploaded: 1, url: url}
                .put("url", url);
    }

    @PostMapping("/update")
    public R<?> update(Strategy Strategy) {
        strategyService.updateById(Strategy);
        return R.ok();
    }

    @PostMapping("/delete/{id}")
    public R<?> delete(@PathVariable Long id) {
        if (id != null) {
            strategyService.removeById(id);
        }
        return R.ok();
    }

    @GetMapping("/list")
    public R<?> list() {
        return R.ok(strategyService.list());
    }

    @GetMapping("/findByDestId")
    R<List<Strategy>> findByDestId(@RequestParam Long destId) {
        return R.ok(strategyService.findByDestId(destId));
    }
}
