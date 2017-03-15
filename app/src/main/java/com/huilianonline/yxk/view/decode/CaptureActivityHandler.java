package com.huilianonline.yxk.view.decode;

import com.huilianonline.yxk.R;
import com.huilianonline.yxk.view.camera.CameraManager;
import com.huilianonline.yxk.activity.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * This class handles all the messaging which comprises the state machine for capture.
 */
public final class CaptureActivityHandler extends Handler {

  private static final String TAG = CaptureActivityHandler.class.getSimpleName();

  private final CaptureActivity activity;
  private final DecodeThread decodeThread;
  private State state;

  private enum State 
  {
    PREVIEW,
    SUCCESS,
    DONE
  }

  public CaptureActivityHandler(CaptureActivity activity) 
  {
    this.activity = activity;
    decodeThread = new DecodeThread(activity);
    decodeThread.start();
    state = State.SUCCESS;
    CameraManager.get().startPreview();
    restartPreviewAndDecode();
  }

  @Override
  public void handleMessage(Message message) 
  {
    switch (message.what) {
      case R.id.auto_focus:
        if (state == State.PREVIEW) 
        {
          CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
        }
        break;
      case R.id.restart_preview:
        Log.d(TAG, "Got restart preview message");
        restartPreviewAndDecode();
        break;
      case R.id.decode_succeeded:
    	String strResult=(String) message.obj;
        Log.d(TAG, "Got decode succeeded message:"+strResult);
        state = State.SUCCESS;
        activity.handleDecode(strResult);
        /***********************************************************************/
        break;
      case R.id.decode_failed:
        state = State.PREVIEW;
        CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
        break;
      case R.id.return_scan_result:
        Log.d(TAG, "Got return scan result message");
        activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
        activity.finish();
        break;
    
    }
  }

  public void quitSynchronously() 
  {
    state = State.DONE;
    CameraManager.get().stopPreview();
    Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
    quit.sendToTarget();
    try 
    {
      decodeThread.join();
    } catch (InterruptedException e) {
      // continue
    }
    removeMessages(R.id.decode_succeeded);
    removeMessages(R.id.decode_failed);
  }

  private void restartPreviewAndDecode() 
  {
    if (state == State.SUCCESS) 
    {
      state = State.PREVIEW;
      CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
      CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
      activity.drawViewfinder();
    }
  }

}
