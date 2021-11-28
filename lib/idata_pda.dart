import 'dart:async';

import 'package:flutter/services.dart';

class IdataPda {
  static const MethodChannel _channel = const MethodChannel('idata_pda');

  /// 获取安卓版本
  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// 隐藏软键盘
  static Future closeSoftKeyboard() async {
    await _channel.invokeMethod('closeSoftKeyboard');
  }

  // /// 开启软键盘
  // static Future openSoftKeyboard() async {
  //   await _channel.invokeMethod('closeSoftKeyboard');
  // }
}
