# MusicAnimLine

在使用网易云音乐的时候，偶然发现iOS版<网易云音乐>的垂直线条动画view，觉得挺好看，很有动感，所以将其模仿到了Android上。
## 预览
iOS网易云 原版  
![](https://github.com/limuyang2/MusicAnimLine/blob/master/gif/NeteaseIOS.jpg)  
模仿实现效果  
![](https://github.com/limuyang2/MusicAnimLine/blob/master/gif/pic.gif)  

## 获取
先在 build.gradle 的 repositories 添加：  
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

再在dependencies添加：  
```gradle
dependencies {
	implementation 'com.github.limuyang2:MusicAnimLine:0.1'
}
```
## 使用
```xml
<top.limuyang2.musicanimlineview.MusicAnimLineView
	android:id="@+id/lineView"
	android:layout_width="150dp"
    android:layout_height="150dp"
    app:isAutoPlay="false"
    app:line_color="#22b1f9"
    app:line_width="3dp" />
```
## 属性说明

| 属性             | 说明               |
| ---------------- | ------------------ |
| app:line_width   | 竖线宽度（单位DP） |
| app:line_color   | 竖线颜色           |
| app:animDuration | 动画时长           |
| app:isAutoPlay   | 是否自动播放       |
| 方法 startAnim() | 播放动画           |

## License
```
2018 limuyang
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```