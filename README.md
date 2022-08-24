项目参考：git https://github.com/happylishang/AntiFakerAndroidChecker.git
判断条件主要有三个
1、需要授权  <uses-permission android:name="android.permission.READ_PHONE_STATE" /> 
   获取电话状态
   
2、通过adb 命令获取系统架构

3、通过c获取系统架构

添加so文件有两种方法

    // 如果so文件是放到src/main 里面需要把这整个注释了去
    //如果so文件是放到app/libs 目录下就需要打开这个
    sourceSets {
        main {
            //jniLibs.srcDirs = ['libs']
            //jniLibs.srcDirs = ['libs']
            //jniLibs.srcDirs 'libs'
        }
    }