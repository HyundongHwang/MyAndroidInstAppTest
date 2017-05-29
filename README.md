<!-- TOC -->

- [MyAndroidInstAppTest](#myandroidinstapptest)
    - [프로젝트 개요](#프로젝트-개요)
    - [데모](#데모)
    - [`InstantApp` 이란?](#instantapp-이란)
    - [`InstantApp` 장점](#instantapp-장점)
    - [`InstantApp` 단점, 제약사항](#instantapp-단점-제약사항)
    - [사전요구사항](#사전요구사항)
    - [의견](#의견)
    - [개발순서](#개발순서)
        - [새 안드로이드 앱 프로젝트 + instant app support 생성](#새-안드로이드-앱-프로젝트--instant-app-support-생성)
        - [base 모듈 코딩](#base-모듈-코딩)
        - [myfeature 모듈 코딩](#myfeature-모듈-코딩)
        - [app 모듈 코딩](#app-모듈-코딩)
        - [instantapp 모듈 코딩](#instantapp-모듈-코딩)
        - [코드사이닝 빌드](#코드사이닝-빌드)
        - [instantapp.zip 준비](#instantappzip-준비)
        - [assetlinks 준비](#assetlinks-준비)
        - [playstore console 에 app, instantapp.zip 업로드](#playstore-console-에-app-instantappzip-업로드)
    - [참고자료](#참고자료)

<!-- /TOC -->

# MyAndroidInstAppTest

## 프로젝트 개요
- 구글IO2017에서 `InstantApp` 이란 솔루션을 발표함.
    - 소개동영상
        - https://www.youtube.com/watch?v=cosqlfqrpFA
        - https://www.youtube.com/watch?v=9Jg1D07NgeI
    - 개발자페이지
        - https://developer.android.com/topic/instant-apps/index.html
- 이를 분석하기 위해 샘플프로젝트를 만들었다.

## 데모
1. instantapp 기능이 지원되는 디바이스 준비
    - samsung galaxy s7, s8
    - google pixel
2. `` 실행
    - http://hhdandroidinstapptest.azurewebsites.net/ 실행
    - 하지만 원인은 모르지만 galaxy s7, s8에서 테스트 해 본결과 실행이 안됨. ㅠㅠ
- 참고
    - playstore url
        - https://play.google.com/store/apps/details?id=com.hhd.myinstapp3

## `InstantApp` 이란?
- 명시적인 앱설치 없이 웹페이지 띄우듯 바로 앱의 작은 기능만 사용할 수 있는 솔루션
- 예를들면 웹서핑 하다가 `https://hhdmarket.com/prodId/1234` 를 클릭하면 해당 웹페이지로 이동하는 것이 아니라 대응되는 `hhdmarket앱`의 `prodId/1234`에 해당하는 작은모듈이 다운로드 되어 실행되는 원리
- 여기서 작은모듈이라 함은 `hhdmarket-base.apk` + `hhdmarket-prod-detail.apk` = `hhdmarket-prod-detail-instapp.zip` 으로 묶인 zip파일을 말한다.
- `InstantApp` 은 다운로드후 암시적인 설치가 이뤄지며 폰이 재부팅 될때까지 캐시가 유지된다.
    - 하지만 앱업데이트나 시스템 자원부족인 경우 PlayStore가 캐시를 삭제할 수 있다.

## `InstantApp` 장점
- 앱설치하는 과정이 거의 약간 느린 웹페이지를 방문하는 정도로만 허들이 낮아진다.
    - 기존에 브랜드가 약해서 사용자에게 감히 앱설치를 권할수 없었던 사업 시나리오들에 희소식!!!
- 앱의 많은 기능을 다수의 모듈로 분리한다면 앱설치가 있는 시나리오라도 처음에는 메인모듈을 설치하고, 나중에 기능모듈을 `InstantApp`으로 받는 시나리오도 가능하다.
- 앱을 만들고 PlayStore에 파일을 올리는 것까지는 동일하므로, 개발자는 앱의 요소들을 모듈로 분해하고 딥링크를 거는 작업만 추가하면 된다.

## `InstantApp` 단점, 제약사항
- (아래 사항은 현재 `InstantApp`이 Preview란 점을 고려하면 근미래에 일부 개선될 것이다.)
- 모듈 zip 파일은 4Mb 를 넘지 못한다.
    - 추가하는 리소스와 종속성 선정에 신중하지 않으면 금새 용량초과 될듯.
- 푸시알림, 백그라운드 작업, 캘린더, 주소록, SMS수신후킹, SD카드 R/W, NFC 를 사용할 수 없음.
- 현재 사용가능한 디바이스가 제약되어 있고 최소지원OS 버전도 높다.
    - Nexus 5X, Nexus 6P, Pixel, Pixel XL, Galaxy S7
    - Android 6.0 or higher.

## 사전요구사항
- Android Studio 3.0 설치 
    - https://developer.android.com/studio/preview/index.html
    - 안정버전이 아닌 canary preview 를 설치해야 한다.
- SDK Manager 로 종속성 설치
    - Android 6.0 이상 SDK 설치
    - Android Build-Tools 26-rc2 이상 설치
    - Google Play Service 설치
    - Instance App Development SDJ 설치
- 디바이스 준비
    - Nexus 5X, Nexus 6P, Pixel, Pixel XL, Galaxy S7 running Android 6.0 or higher.

## 의견
- `InstantApp` 는 초기단계인 기술이라 아직 깊이 개입하여 개발하는데 실익이 적을듯
    - 특히 디바이스, 최소실행 OS버전 제약이 크다.
- 하지만 점차 환경이 개선될 것이라 믿기(?) 때문에 브랜드가 약하여 앱설치를 유도하기 어려웠던 개발사인 경우는 개선되어 가는 과정을 모니터링 해둘 필요가 있을것.


## 개발순서
### 새 안드로이드 앱 프로젝트 + instant app support 생성
- 앱이름은 `myinstapp3`
- 추가 기능 모듈이름은 `myfeature`
- 각 모듈들이 잘 생성 되었는지 확인 
    - http://s21.postimg.org/i98dwyrw7/screenshot_2017_05_29_at_00_46_08.png

| 모듈명 | 모듈형태 | 기능소개 | 내용 | 
| -- | -- | -- | -- |
| app | application | 메인어플리케이션 모듈| X |
| base | feature | 모든 instantapp의 기본기능 | BaseMainActivity |
| instantapp | instantapp | 인스턴트앱의 기본모듈 | X |
| myfeature | feature | 메인어플리케이션 | MyFeatureMainActivity | 

### base 모듈 코딩
- 모든 feature에 공통으로 포함되는 모듈
- 여기서는 BaseMainActivity 이라는 간단한 액티비티를 구성한다.

    - \base\src\main\java\com\hhd\myinstapp3\BaseMainActivity.java
    ```java
    public class BaseMainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_base_main);
        }
    }
    ```

- 이 BaseMainActivity 는 아래와 같은 http 인텐트 필터를 갖게 한다.
    - \base\src\main\AndroidManifest.xml
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.hhd.myinstapp3">
        <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme">
            <activity android:name=".BaseMainActivity">
                <intent-filter
                    android:autoVerify="true"
                    android:order="1">
                    <action android:name="android.intent.action.VIEW"/>

                    <category android:name="android.intent.category.BROWSABLE"/>
                    <category android:name="android.intent.category.DEFAULT"/>

                    <data android:host="hhdandroidinstapptest.azurewebsites.net"/>
                    <data android:pathPattern="/"/>
                    <data android:scheme="https"/>
                    <data android:scheme="http"/>
                </intent-filter>
                <intent-filter>
                    <action android:name="android.intent.action.MAIN"/>
                    <category android:name="android.intent.category.LAUNCHER"/>
                </intent-filter>
                <meta-data
                    android:name="default-url"
                    android:value="https://hhdandroidinstapptest.azurewebsites.net/"/>
            </activity>
        </application>
    </manifest>
    ```

- build.gradle 파일을 확인하여 feature, application 모듈을 선언한다.
    - \base\build.gradle
    ```java
    apply plugin: 'com.android.feature'

    android {
        compileSdkVersion 25
        buildToolsVersion "26.0.0 rc2"
        baseFeature true
        defaultConfig {
            minSdkVersion 23
            targetSdkVersion 25
            versionCode 2
            versionName "2.0"
            vectorDrawables.useSupportLibrary = true
        }
        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }
        }
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }

    dependencies {
        feature project(':myfeature')
        application project(":app")//new
        compile 'com.android.support:appcompat-v7:25.+'
        compile 'com.android.support.constraint:constraint-layout:1.0.2'
    }
    ```

### myfeature 모듈 코딩
- 여기서는 추가기능을 개발한다.
- MyFeatureMainActivity 이름의 액티비티 한개를 추가하기로 한다.
- myfeature이외의 추가기능을 여러개 포함하기 위해서는 같은방식으로 모듈을 여러개 추가하면 된다.
    - \myfeature\src\main\java\com\hhd\myinstapp3\myfeature\MyFeatureMainActivity.java
    ``` java
    public class MyFeatureMainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_feature_main);
        }
    }
    ```
- 마찬가지로 이 액티비티를 실행할 수 있는 http 인텐트필터를 추가한다.
    - \myfeature\src\main\AndroidManifest.xml
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.hhd.myinstapp3.myfeature">

        <application>
            <activity android:name=".MyFeatureMainActivity">
                <intent-filter
                    android:autoVerify="true"
                    android:order="1">
                    <action android:name="android.intent.action.VIEW"/>

                    <category android:name="android.intent.category.BROWSABLE"/>
                    <category android:name="android.intent.category.DEFAULT"/>

                    <data android:host="hhdandroidinstapptest.azurewebsites.net"/>
                    <data android:pathPattern="/"/>
                    <data android:scheme="https"/>
                    <data android:scheme="http"/>
                </intent-filter>
                <intent-filter>
                    <action android:name="android.intent.action.MAIN"/>
                    <category android:name="android.intent.category.LAUNCHER"/>
                </intent-filter>
            </activity>
        </application>

    </manifest>
    ```
- base 모듈에 종속성이 있음을 선언한다.
    - \myfeature\build.gradle
    ```java
    apply plugin: 'com.android.feature'

    android {
        compileSdkVersion 25
        buildToolsVersion "26.0.0 rc2"
        defaultConfig {
            minSdkVersion 23
            targetSdkVersion 25
            versionCode 2
            versionName "2.0"
        }
        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }
        }
    }

    dependencies {
        compile fileTree(dir: 'libs', include: ['*.jar'])
        api project(':base')
        compile 'com.android.support.constraint:constraint-layout:1.0.2'
    }
    ```

### app 모듈 코딩
- 기능 내용은 없이 application 임을 선언하기만 하면 된다.
    - app\src\main\AndroidManifest.xml
    ```xml
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.hhd.myinstapp3.app" />
    ```
    
    - app\build.gradle
    ```java
    apply plugin: 'com.android.application'

    android {
        compileSdkVersion 25
        buildToolsVersion "26.0.0 rc2"


        defaultConfig {
            applicationId "com.hhd.myinstapp3"
            minSdkVersion 23
            targetSdkVersion 25
            versionCode 2
            versionName "2.0"
            vectorDrawables.useSupportLibrary = true
        }
        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }
        }
    }

    dependencies {
        implementation project(':myfeature')
        //implementation project(':base')
    }
    ```

### instantapp 모듈 코딩
- 마찬가지로 기능내용없이 instantapp 이라고 선언해 주기만 하면 된다.
    - instantapp\build.gradle
    ```java
    apply plugin: 'com.android.instantapp'

    android {
        compileSdkVersion 25
        buildToolsVersion "26.0.0 rc2"
        defaultConfig {
            minSdkVersion 23
            targetSdkVersion 25
            versionCode 2
            versionName "2.0"
            vectorDrawables.useSupportLibrary = true
        }
        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }
        }
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }

    dependencies {
        api project(":base")
        implementation project(':myfeature')
    }
    ```

### 코드사이닝 빌드
- 아래와 같이 signed apk 를 생성한다.
    - 여기서는 편의상 debug.keystore 를 사용했다.
    - ![](http://s24.postimg.org/71ei2u8md/screenshot_2017_05_29_at_09_24_28.png)
- instantapp, app 두개의 모듈에 대해 build 하여 두개의 apk를 생성한다.
    ```powershell
    PS C:\project\170523_MyAndroidInstAppTest> ls *.apk -Recurse | select FullName
    FullName
    --------
    C:\project\170523_MyAndroidInstAppTest\feature\release\base-release.apk
    C:\project\170523_MyAndroidInstAppTest\feature\release\myfeature-release.apk
    C:\project\170523_MyAndroidInstAppTest\release\app-release.apk
    ```

### instantapp.zip 준비
- base-release.apk, myfeature-release.apk는 instantapp.zip으로 압축해서 playstore에 배포해야 한다.
    ```powershel
    PS C:\project\170523_MyAndroidInstAppTest> zip C:\project\170523_MyAndroidInstAppTest\feature\release\*.apk -Force -DestinationPath instantapp.zip
    PS C:\project\170523_MyAndroidInstAppTest> ls .\instantapp.zip
        디렉터리: C:\project\170523_MyAndroidInstAppTest
    Mode                LastWriteTime         Length Name
    ----                -------------         ------ ----
    -a----     2017-05-29   오전 9:31        1271887 instantapp.zip
    ```

### assetlinks 준비
- instantapp.zip을 playstore에 배포하게 되면 적용된 인텐트필터 http host와 같은이름의 웹서버에 사인된 인증서의 해시값을 이용해서 assetlinks.json을 띄워놓아야 한다.
    - asset links 의 소개 : https://developers.google.com/digital-asset-links/v1/getting-started
    - 내 인증서의 해시값 구하기
    ```powershell
    PS C:\project\170523_MyAndroidInstAppTest> keytool -list -keystore "C:\Users\hhd20\.android\debug.keystore" -v -keypass "android" -storepass "android"

    키 저장소 유형: JKS
    키 저장소 제공자: SUN

    키 저장소에 1개의 항목이 포함되어 있습니다.

    별칭 이름: androiddebugkey
    생성 날짜: 2017. 5. 21
    항목 유형: PrivateKeyEntry
    인증서 체인 길이: 1
    인증서[1]:
    소유자: C=US, O=Android, CN=Android Debug
    발행자: C=US, O=Android, CN=Android Debug
    일련 번호: 1
    적합한 시작 날짜: Sun May 21 23:29:18 KST 2017, 종료 날짜: Tue May 14 23:29:18 KST 2047
    인증서 지문:
            MD5: E8:25:2C:8F:59:E0:FC:FD:9C:E9:8B:F3:98:F2:5E:0C
            SHA1: A9:72:7D:FA:9C:AC:7B:11:9C:AA:21:93:32:72:6E:F1:AC:B4:71:BF
            SHA256: DD:DD:EE:E2:48:49:43:94:65:51:E6:F5:69:4F:44:1B:02:B9:02:35:C2:43:81:12:55:8D:36:0C:E1:43:CF:7F
            서명 알고리즘 이름: SHA1withRSA
            버전: 1


    *******************************************
    *******************************************
    ```
    - 준비된 모습
        - http://hhdandroidinstapptest.azurewebsites.net/assetlinks.json


### playstore console 에 app, instantapp.zip 업로드
- playstore console 을 이용하여 app-release.apk, instantapp.zip 을 업로드함.
    - ![](http://s30.postimg.org/wbs6nhurl/screenshot_2017_05_29_at_09_44_53.png)
    - https://play.google.com/store/apps/details?id=com.hhd.myinstapp3


## 참고자료

- 비슷한 시도...
    - https://willowtreeapps.com/ideas/an-introduction-to-android-instant-apps

- google IO 2017 instantapp 화면캡쳐

    http://s24.postimg.org/yvi379oo5/screenshot_2017_05_21_at_16_15_47.png
    http://s24.postimg.org/rslqvjn6t/screenshot_2017_05_21_at_21_12_23.png
    http://s24.postimg.org/vq90kya05/screenshot_2017_05_21_at_21_14_02.png
    http://s24.postimg.org/lhgjf4lyd/screenshot_2017_05_21_at_21_15_56.png
    http://s24.postimg.org/octmlzpyd/screenshot_2017_05_21_at_21_18_47.png
    http://s24.postimg.org/ursninwo5/screenshot_2017_05_21_at_21_19_28.png
    http://s24.postimg.org/5n1n58x7p/screenshot_2017_05_21_at_21_21_21.png
    http://s24.postimg.org/k7nbdthk5/screenshot_2017_05_21_at_21_22_26.png
    http://s24.postimg.org/ekqygcf1h/screenshot_2017_05_21_at_21_24_08.png
    http://s24.postimg.org/8xulivcit/screenshot_2017_05_21_at_21_24_40.png
    http://s24.postimg.org/ijo5z63ol/screenshot_2017_05_21_at_21_25_20.png
    http://s24.postimg.org/z8plv309x/screenshot_2017_05_21_at_21_25_47.png
    http://s24.postimg.org/fs4w8k55x/screenshot_2017_05_21_at_21_26_56.png
    http://s24.postimg.org/vrnjs417p/screenshot_2017_05_21_at_21_27_59.png
    http://s24.postimg.org/57uypz0o5/screenshot_2017_05_21_at_21_29_08.png
    http://s24.postimg.org/f6fxcga3p/screenshot_2017_05_21_at_21_32_30.png
    http://s24.postimg.org/ayl53p8o5/screenshot_2017_05_21_at_21_37_40.png
    http://s24.postimg.org/5okrq5dt1/screenshot_2017_05_21_at_21_39_51.png
    http://s24.postimg.org/u6cve1gdh/screenshot_2017_05_21_at_21_41_14.png
    http://s24.postimg.org/rqb206yat/screenshot_2017_05_21_at_21_42_18.png
    http://s24.postimg.org/enffgx82t/screenshot_2017_05_21_at_21_43_51.png
    http://s24.postimg.org/wer1vdnhh/screenshot_2017_05_21_at_21_53_01.png