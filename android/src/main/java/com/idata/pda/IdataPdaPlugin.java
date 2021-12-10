package com.idata.pda;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.IScanListener;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.example.iscandemo.iScanInterface;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * IdataPdaPlugin
 */
public class IdataPdaPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  private final static String TAG = "IdataPdaTag ------> ";
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  // flutter 通道
  private EventChannel eventChannel;

  /// 定义flutter通道文字
  private static final String CHARGING_CHANNEL = "com.idata.pda/scan";

  // 接口实例
  private iScanInterface miScanInterface;

  // eventChannel 返回成功和失败的事件接受器
  private EventChannel.EventSink eventSink;

  // active
  private Activity activity;

  // context
  private Context applicationContext;

  //定义数据回调接口
  final IScanListener scanResltListener = new IScanListener() {
    /**
     * @param data 条码数据
     * @param type 条码类型
     * @param decodeTime 解码耗时
     * @param keyDownTime 按键时间
     * @param imagePath 图片路径，注意：默认图片输出为关闭状态；
     *                                  如果需要条码图片需要打开图片输出选项。
     */
    @Override
    public void onScanResults(final String data, final int type, long decodeTime, long keyDownTime, String imagePath) {
      Map map = new HashMap<>();
      map.put("data", data);
      map.put("type", type);
      map.put("decodeTime", decodeTime);
      map.put("keyDownTime", keyDownTime);
      map.put("imagePath", imagePath);
      Log.d(TAG, map.toString());

      Handler handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
        @Override
        public void run() {
          eventSink.success(data);
        }
      });
    }
  };


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "idata_pda");
    channel.setMethodCallHandler(this);

    // 开启flutter通道
    eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), CHARGING_CHANNEL);
    eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        eventSink = events;

        try {
          //初始化接口对象
          miScanInterface = new iScanInterface(applicationContext);

          //注册数据回调接口
          miScanInterface.registerScan(scanResltListener);

        } catch (Throwable t) {
          t.printStackTrace();
          eventSink.error("0", "监听失败", "");
        }
      }

      @Override
      public void onCancel(Object arguments) {
        eventSink = null;
        //应用关闭后注销回调
        miScanInterface.unregisterScan(scanResltListener);
      }
    });

    // 设置context
    applicationContext = flutterPluginBinding.getApplicationContext();

  }


  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "getPlatformVersion":
        // 返回安卓版本
        result.success("Android " + android.os.Build.VERSION.RELEASE);
        break;
      case "closeSoftKeyboard":
        // 关闭Android键盘
        // 在需要打开的Activity取消禁用软键盘

        View view = activity.getCurrentFocus();
        if (view != null) {
          InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
          inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        Log.d(TAG, "closeSoftKeyboard");
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }
}
