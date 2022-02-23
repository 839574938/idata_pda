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

  // 设置PDA默认状态
  // 0：不附加内容
  // 1：附加回车键
  // 2：附加 TAB 键
  // 3：附加换行符(\n)
  static Future enableAddKeyValue({int? flag}) async {
    Map<String, Object> map = {"flag": flag ?? 0};
    await _channel.invokeMethod('enableAddKeyValue', map);
  }

  // /// 开启软键盘
  // static Future openSoftKeyboard() async {
  //   await _channel.invokeMethod('closeSoftKeyboard');
  // }
}
