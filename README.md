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





#### 3. PDA设置附加按键

```dart
// 说明：将指定按键键值附加到扫描结果后。
// 输入参数：flag，参数值，附加值类型。支持的值如下：
// 不传入默认为 0
// 0：不附加内容
// 1：附加回车键
// 2：附加 TAB 键
// 3：附加换行符(\n)
// 返回值：空

await IdataPda.enableAddKeyValue(flag: 0);
```





参考文献

​	1. PDA红外扫描（[flutter插件开发,监听原生广播返回数据 - 简书 (jianshu.com)](https://www.jianshu.com/p/46c55eb9ad12)）

