import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

/// pda扫描库
class PdaScanUtil {
  PdaScanUtil._();

  factory PdaScanUtil() => PdaScanUtil._();

  static PdaScanUtil get instance => PdaScanUtil._();

  EventChannel eventChannel = EventChannel('com.idata.pda/scan');

  StreamSubscription? streamSubscription;
  Stream? stream;

  Stream start() {
    stream ??= eventChannel.receiveBroadcastStream();
    return stream!;
  }

  /// 监听扫码数据
  void listen(ValueChanged<String> codeHandle) {
    streamSubscription = start().listen((event) {
      if (event != null) {
        codeHandle.call(event.toString());
      }
    });
  }

  /// 关闭监听
  void cancel() {
    streamSubscription?.cancel();
  }
}
