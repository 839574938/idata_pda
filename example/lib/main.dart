import 'package:flutter/material.dart';
import 'package:idata_pda/idata_pda.dart';
import 'package:idata_pda/pda_scan_mixin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> with PdaScanMixin<MyApp> {
  String _code = '';
  String? _version = '';

  @override
  void initState() {
    super.initState();

    Future.delayed(Duration.zero).then((value) async {
      String? version = await IdataPda.platformVersion;
      setState(() {
        _version = version;
      });

      await IdataPda.closeSoftKeyboard();
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: AppBar(
          title: const Text('扫码例子'),
        ),
        body: Center(
          child: Column(
            children: [
              Text('_version：$_version'),
              Text('扫描到数据：$_code'),
              // TextField(),
              // MaterialButton(
              //   onPressed: () async {
              //     await IdataPda.closeSoftKeyboard();
              //   },
              //   child: Text('关闭键盘'),
              // ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  Future<void> pdaResultHandler(String code) async {
    /// 编写你的逻辑
    print('扫描到数据：$code');
    setState(() {
      _code = code;
    });
  }
}
