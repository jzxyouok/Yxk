
package com.huilianonline.yxk.view.decode;

import java.util.concurrent.CountDownLatch;

import com.huilianonline.yxk.activity.CaptureActivity;

import android.os.Handler;
import android.os.Looper;

final class DecodeThread extends Thread {

  private final CaptureActivity activity;
  private Handler handler;
  private final CountDownLatch handlerInitLatch;

  DecodeThread(CaptureActivity activity) 
  {
    this.activity = activity;
    handlerInitLatch = new CountDownLatch(1);
  }

  Handler getHandler() {
    try 
    {
      handlerInitLatch.await();
    } catch (InterruptedException ie) {
      // continue?
    }
    return handler;
  }

  @Override
  public void run() {
    Looper.prepare();
    handler = new DecodeHandler(activity);
    handlerInitLatch.countDown();
    Looper.loop();
  }

}
