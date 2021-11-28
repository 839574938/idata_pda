# idata_pda

#### IData手持pda设备红外扫描监听 #仅限Android



# 如何使用

#### 1. 红外扫描 （目前支持： IData PDA安装过IScan Pro的型号）

​	第一种方式Mixin混入

```dart
// 第一步：混入Mixin
class _MyAppState extends State<MyApp> with PdaScanMixin<MyApp> {
  // 获取pda扫描后结果
  @override
  Future<void> pdaResultHandler(String code) async {}
}
```

​	第二种方式

```dart
// 在任何地方监听扫描数据
PdaScanUtil.instance.listen((String code) {});
```

​	销毁请使用

```dart
// 不需要的时候记得关闭它
PdaScanUtil.instance.cancel();
```





#### 2. 获取android版本

```dart
String? version = await IdataPda.platformVersio;
```





#### 3. 目前暂定为：焦点在的情况下，软键盘的隐藏





参考文献

​	1. PDA红外扫描（[flutter插件开发,监听原生广播返回数据 - 简书 (jianshu.com)](https://www.jianshu.com/p/46c55eb9ad12)）

