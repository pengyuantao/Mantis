### Mantis概述([github地址](https://github.com/pengyuantao/Mantis))
*****
1. 前言
2015年是MVP设计模式的爆发年，可以说是出现了百家争鸣的局面，例如：[TheMVP](https://github.com/kymjs/TheMVP)、[Beam](https://github.com/Jude95/Beam)等等MVP的框架，2017年Google推出了一个新的应用开发框架[指南](https://developer.android.com/topic/libraries/architecture/guide.html)，看完之后如醍醐灌顶，这个的架构思想太好了。于是疯狂的在github上找类似的框架，发现这样的[MinimalistWeather](https://github.com/BaronZ88/MinimalistWeather)项目，经过研究，这就是我想要的mvp架构，只是作者提供了一个标准，和一些简单的实现，并不能提高开发的效率，于是我想基于这样的MVP结构，开发一款快速开发，又能在代码中直接规范开发者代码的框架——Mantis。

![framework_minimalist_weather.png](http://upload-images.jianshu.io/upload_images/1460021-d511001582e3d424.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2. 架构的简要说明
* Contract：View和Presenter进行数据交互的协议，协议中没有定义的方法，View和Presenter只能调用协议中定义的方法。
* Activity: 负责管理View和Presenter的管理类
* Responsitory:专门为Presenter提供各种的数据（Http/Db/SharePreference）

3. 细节决定是否好用
这样的框架是好了，关键我们开发的时候还是需要写很多的多的代码，针对一些通用的代码和功能，进行了总结。
* 通用的提示页面（空页面、网络错误页面，运行错误页面）。
* 通用的数据页面、列表页面、下拉刷新及上拉加载页面。
* 统一以及规定接口，提高团队开发效率。
* RxLifecycle对Presenter生命周期的管理。
4. 开源框架的整合
* Rxjava
* Retrofit
* OkHttp
* GreenDao
* RetroLambda
* android-Ultra-Pull-To-Refresh
* BRVAH
* ButterKnife
### Mantis的使用
****
1. Mantis的初始化
```
//建议在Application的中进行Mantis的初始化操作，各种Builder详见Demo中
    Mantis.getInstance().init(this);
    //设置显示EmptyView，ErrorView，NetErrorView的Builder
    Mantis.getInstance().setHintConfigBuilder(getDefaultHintConfigBuilder());
    //设置页面刷新相关的配置
    Mantis.getInstance().setRefreshConfigBuilder(getDefaultRefreshBuilder());
    //设置列表页面的相关的配置
    Mantis.getInstance().setListConfigBuilder(getDefaultListBuilder());
```
2. 根据需求定义协议
目前有3大内置协议，每一个协议都有MantisXXXFragment和MantisXXXPresent与之对应，使用该协议的Fragment或Present必须继承于对应的MantisXXXFragment或MantisXXXPresent。

|协议名称|使用范围|
|:---|:----|
|MantisDataContract|适用于单一数据的界面，例如：商品详情，只是需要一个详情数据|
|MantisRefreshContract|适合非列表页面的刷新协议|
|MantisListContract|集成于MantisRefreshContract协议，自带就有刷新的功能|
|MantisBaseContract|如果不需要刷新或者列表的功能，那么你完全可以集成这个协议，自己进行定义|
````
/**
 * 
 *加入使用的是MantisDataContract协议
 */
public interface TestDataContract{
    interface View extends MantisDataContract.View<Present,String>{
        void toast (String msg);
    }
    interface Present extends MantisDataContract.Present<View,String>{
        void login (String account, String password);
        void logout ();
    }
}

/**
* Fragment （View）的定义，继承TestDataContract.View接口，传入对应的泛型
* 使用BindPresent注解指定这个Fragment需要进行引用的Present具体的实现类
*/
@BindPresent(value = TestDataPresent.class)
public class TestDataFragment extends MantisDataFragment<TestDataContract.Present, String> implements TestDataContract.View{}

/**
 * Present的定义，继承TestDataContract.Present接口，传入对应的泛型
 * 
 */
public class TestDataPresent extends MantisDataPresent<TestDataContract.View, String> implements TestDataContract.Present {}

//在View中可以通过getPresent()方法获取Present实例，从而调用Present在协议中提供的api，在Present中通过getView()方法获取View的实例，从而调用View在协议中提供的api。
````
3. Present绑定生命周期防止内存的泄露
我是将RxLifecycle绑定生命周期的方法移植到Present里面，用法和RxLifecycle一样。
```
Observable.<String>create(subscriber -> subscriber.onNext("登录成功  账号：" + account + "  密码：" + password))
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                //如果是Rxjava通过下面的用法进行绑定
                .compose(bindUntilEvent(PresentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            publishData(s);
                            Log.i(TAG, "login: 执行onNext");
                        }
                        , throwable -> {
                            publishError(throwable);
                            Log.i(TAG, "login: 执行onError");
                        }
                        , () -> {
                            Log.i(TAG, "login: 执行onComplete");
                        });
```    
4. Present中空指提示View的显示和隐藏
通过getViewDeletegate()方法获取提示view的代理对象
```
  @Override
    public void reload() {
        reloadIndex++;
        Log.i(TAG, "reload: index [ " + reloadIndex + " ]");
        if ( reloadIndex == 1) {
            createCommonObservable("", 1).subscribe(s -> getViewDelegate().showNetworkErrorView());
            Log.i(TAG, "reload:显示网络异常View");
        } else if ( reloadIndex == 2) {
            createCommonObservable("", 1) .subscribe(s -> getViewDelegate().showErrorView("十分的抱歉", "由于攻城狮GG的失误，导致程序的奔溃，截此图奖励9999999元"));
            Log.i(TAG, "reload: 显示程序错误View");
        }else {
            createCommonObservable("恭喜恭喜，终于显示ContentView了，来自不易啊!", 1).subscribe(s -> publishData(s), throwable -> publishError(throwable));
            Log.i(TAG, "reload: 显示内容View");
            reloadIndex = 0;
        }
    }
```
5. 个性化定义刷新和列表
还记得咱们再Application中定义的各种Builder嘛，这个时候总算用到了，哈哈，很多时候我们的刷新和提示不见的所有的页面都是一样的，那么就可以通过你重新MantisXXXFragment的onCreateView方法，进行个性化的设置。
```
@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder hintBuilder, RefreshConfig.Builder refreshBuilder) {
        refreshBuilder.autoRefresh(false);
        return inflater.inflate(R.layout.fragment_test_refresh, container, false);
    }
```
