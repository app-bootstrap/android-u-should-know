package com.gz.android_utils.concurrency.loop;

/**
 * created by Zhao Yue, at 22/9/16 - 9:08 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZThreadRunnable implements Runnable{
    private Runnable task;
    public GZThreadRunnable(Runnable task){
        this.task = task;
    }

    @Override
    public void run() {
        try{
            if(task != null)
                task.run();
        }catch(Exception exception){
//            TODO: implement logger logic
//            BBAppLogger.e(exception);
        }catch(Error error){
//            TODO: implement logger logic
//            BBAppLogger.e(error);
        }
        task = null;
    }
}