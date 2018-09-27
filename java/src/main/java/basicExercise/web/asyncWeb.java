package basicExercise.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Callable;


/**
 * springmvc 的异步调用
 * Created by jinyan.cao on 2017/6/9.
 */
@Controller
public class asyncWeb<T> {

    @RequestMapping(value = "/asynctask", method = RequestMethod.GET)
    public DeferredResult<ModelAndView> asyncTask1() throws Exception {
        DeferredResult<ModelAndView> deferredResult = new DeferredResult<>();
        Callable<ModelAndView> view = ModelAndView::new;
         deferredResult.setResult(view.call());
        return deferredResult;
    }

    @RequestMapping(value = "/longtimetask", method = RequestMethod.GET)
    public WebAsyncTask<ModelAndView> longTimeTask2() {
        Callable<ModelAndView> callable = () -> {
            ModelAndView mav = new ModelAndView("longtimetask");
            mav.addObject("result", "执行成功");
            return mav;
        };
        return new WebAsyncTask<ModelAndView>(callable);
    }

    /**
     * 对超时的处理
     *
     * @return
     */


    @RequestMapping(value = "/longtimetask", method = RequestMethod.GET)
    public WebAsyncTask<ModelAndView> longTimeTask3() {
        Callable<ModelAndView> callable = () -> {
            Thread.sleep(3000); //假设是一些长时间任务
            ModelAndView mav = new ModelAndView("longtimetask");
            mav.addObject("result", "执行成功");
            System.out.println("执行成功 thread id is : " + Thread.currentThread().getId());
            return mav;
        };
        WebAsyncTask asyncTask = new WebAsyncTask(2000, callable);
        asyncTask.onTimeout(
                () -> {
                    ModelAndView mav = new ModelAndView("longtimetask");
                    mav.addObject("result", "执行超时");
                    System.out.println("执行超时 thread id is ：" + Thread.currentThread().getId());
                    return mav;
                }
        );
        return new WebAsyncTask<>(3000, callable);
    }

    /**
     * 对超时的处理
     *
     * @return
     */
    @RequestMapping(value = "/asynctask", method = RequestMethod.GET)
    public DeferredResult<ModelAndView> asyncTask4() {
        DeferredResult<ModelAndView> deferredResult = new DeferredResult<>(2000L);//添加超时时间
        deferredResult.setResult(new ModelAndView());// TODO: 2017/6/9 这里可以添加回调函数做处理
        deferredResult.onTimeout(() -> {
            ModelAndView mav = new ModelAndView("remotecalltask");
            mav.addObject("result", "异步调用执行超时");
            deferredResult.setResult(mav);
        });
        return deferredResult;
    }



}
