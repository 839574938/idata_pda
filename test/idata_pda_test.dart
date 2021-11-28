import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:idata_pda/idata_pda.dart';

void main() {
  const MethodChannel channel = MethodChannel('idata_pda');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await IdataPda.platformVersion, '42');
  });
}
