import 'dart:async';

import 'package:flutter/material.dart';
import './pda_utils.dart';

/// 商米扫描设备混入
mixin PdaScanMixin<T extends StatefulWidget> on State<T> {
  late StreamSubscription streamSubscription;

  @override
  void initState() {
    super.initState();

    /// 开始监听流
    streamSubscription = PdaScanUtil.instance.start().listen((event) {
      if (event != null) {
        pdaResultHandler(event.toString());
      }
    });
  }

  ///
  /// 当红外扫描头扫描到数据的时候执行回调
  ///
  /// 可以在这里处理相关逻辑
  ///
  Future<void> pdaResultHandler(String result);

  @override
  void dispose() {
    super.dispose();

    /// 关闭监听流，防止内存泄漏
    streamSubscription.cancel();
  }
}