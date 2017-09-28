package cn.nsyr.hello.thread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author ZhouSs
 * @Mail: zhoushengshuai@ufenqi.com
 * @date:2017/3/23 下午2:41
 * @version: 1.0
 **/

@Controller
@RequestMapping("/risk/api")
public class ApiController {

    private @Value("/home/deploy/candidates.txt")
    String path;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);


    @RequestMapping("/doLock")
    @ResponseBody
    public Object doLock() {

        Long userId = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String lnContent = null;
            while ((lnContent = reader.readLine()) != null) {
//                log.info("#doLock# Scanned line:{}", lnContent);
                userId = Long.parseLong(lnContent);
                // TODO SOMETHING

            }
        } catch (Exception cause) {
//            log.error("#doLock# Error occured, userId:{} ", userId, cause);
        }

        return "Congratulations, all operations had been done well!";
    }

    @RequestMapping("/doPush")
    @ResponseBody
    public String doPush() {

        String lnContent = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            while ((lnContent = reader.readLine()) != null) {
                if (StringUtils.isEmpty(lnContent))
                    break;
                final String line = lnContent;
                final Long userId = Long.parseLong(lnContent);
//                log.info("#doPush# Scanned line:{} ", userId);
                executorService.execute(new Runnable() {
                    public void run() {
                      // TODO
                    }
                });
                Thread.sleep(500);
            }
        } catch (Exception cause) {
//            log.error("#doPush# Error occured, userId:{} ", lnContent, cause);
        }

        return "Congratulations, all operations had been done well!";
    }
}
