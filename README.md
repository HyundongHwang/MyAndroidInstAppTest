<!-- TOC -->

- [MyAndroidInstAppTest](#myandroidinstapptest)
    - [프로젝트 개요](#프로젝트-개요)
    - [playstore url](#playstore-url)
    - [`InstanceApp` 이란?](#instanceapp-이란)
    - [`InstanceApp` 장점](#instanceapp-장점)
    - [`InstanceApp` 단점, 제약사항](#instanceapp-단점-제약사항)
    - [사전요구사항](#사전요구사항)
    - [의견](#의견)
    - [모든 빌드 파일 보기](#모든-빌드-파일-보기)
    - [모든 빌드 결과 살펴보기](#모든-빌드-결과-살펴보기)
    - [상세 개발 과정](#상세-개발-과정)
        - [프로젝트 생성](#프로젝트-생성)
        - [base 모듈 에 MyBaseActivity 추가](#base-모듈-에-mybaseactivity-추가)
        - [hello 모듈 에 MainActivity 추가](#hello-모듈-에-mainactivity-추가)
        - [base, hello모듈에 http, https 딥링크 인텐트 필터 연결](#base-hello모듈에-http-https-딥링크-인텐트-필터-연결)
        - [각 모듈을 모두 코드사이닝](#각-모듈을-모두-코드사이닝)
        - [base-release.apk와 hello-release.apk를 instantapp-release.zip 으로 압축](#base-releaseapk와-hello-releaseapk를-instantapp-releasezip-으로-압축)
        - [playstore에 app-release.apk 업로드](#playstore에-app-releaseapk-업로드)
        - [playstore에 instantapp-release.zip 업로드](#playstore에-instantapp-releasezip-업로드)
    - [테스트 과정](#테스트-과정)
        - [Galaxy S7 단말에서 `http://hhd.com/` `http://hhd.com/main` 으로 접근하여 InstantApp 실행](#galaxy-s7-단말에서-httphhdcom-httphhdcommain-으로-접근하여-instantapp-실행)

<!-- /TOC -->

# MyAndroidInstAppTest

## 프로젝트 개요
- 구글IO2017에서 `InstanceApp` 이란 솔루션을 발표함.
    - 소개동영상
        - https://www.youtube.com/watch?v=cosqlfqrpFA
        - https://www.youtube.com/watch?v=9Jg1D07NgeI
    - 개발자페이지
        - https://developer.android.com/topic/instant-apps/index.html
- 이를 분석하기 위해 샘플프로젝트를 만들었다.

## playstore url
- https://play.google.com/store/apps/details?id=com.hhd.myinstapp

## `InstanceApp` 이란?
- 명시적인 앱설치 없이 웹페이지 띄우듯 바로 앱의 작은 기능만 사용할 수 있는 솔루션
- 예를들면 웹서핑 하다가 `https://hhdmarket.com/prodId/1234` 를 클릭하면 해당 웹페이지로 이동하는 것이 아니라 대응되는 `hhdmarket앱`의 `prodId/1234`에 해당하는 작은모듈이 다운로드 되어 실행되는 원리
- 여기서 작은모듈이라 함은 `hhdmarket-base.apk` + `hhdmarket-prod-detail.apk` = `hhdmarket-prod-detail-instapp.zip` 으로 묶인 zip파일을 말한다.
- `InstanceApp` 은 다운로드후 암시적인 설치가 이뤄지며 폰이 재부팅 될때까지 캐시가 유지된다.
    - 하지만 앱업데이트나 시스템 자원부족인 경우 PlayStore가 캐시를 삭제할 수 있다.

## `InstanceApp` 장점
- 앱설치하는 과정이 거의 약간 느린 웹페이지를 방문하는 정도로만 허들이 낮아진다.
    - 기존에 브랜드가 약해서 사용자에게 감히 앱설치를 권할수 없었던 사업 시나리오들에 희소식!!!
- 앱의 많은 기능을 다수의 모듈로 분리한다면 앱설치가 있는 시나리오라도 처음에는 메인모듈을 설치하고, 나중에 기능모듈을 `InstanceApp`으로 받는 시나리오도 가능하다.
- 앱을 만들고 PlayStore에 파일을 올리는 것까지는 동일하므로, 개발자는 앱의 요소들을 모듈로 분해하고 딥링크를 거는 작업만 추가하면 된다.

## `InstanceApp` 단점, 제약사항
- (아래 사항은 현재 `InstanceApp`이 Preview란 점을 고려하면 근미래에 일부 개선될 것이다.)
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
- `InstanceApp` 는 초기단계인 기술이라 아직 깊이 개입하여 개발하는데 실익이 적을듯
    - 특히 디바이스, 최소실행 OS버전 제약이 크다.
- 하지만 점차 환경이 개선될 것이라 믿기(?) 때문에 브랜드가 약하여 앱설치를 유도하기 어려웠던 개발사인 경우는 개선되어 가는 과정을 모니터링 해둘 필요가 있을것.

## 모든 빌드 파일 보기

```powershell
PS C:\project\170523_MyAndroidInstAppTest> ls *.apk, *.zip -Recurse -Force | select FullName, Length

FullName                                                                         Length
--------                                                                         ------
C:\project\170523_MyAndroidInstAppTest\app\build\outputs\apk\debug\app-debug.apk 169261
C:\project\170523_MyAndroidInstAppTest\app\feature\release\base-release.apk      159809
C:\project\170523_MyAndroidInstAppTest\app\feature\release\Hello-release.apk       4898
C:\project\170523_MyAndroidInstAppTest\app\release\app-release.apk               161741
C:\project\170523_MyAndroidInstAppTest\instantapp-release.zip                    156205
```

## 모든 빌드 결과 살펴보기
- app-debug.apk
    - 모든 모듈을 통합해서 빌드한 결과
    - 일반적인 안드로이드 앱과 다를바 없다.
- instantapp-release.zip 
    - 인스턴스앱 빌드
    - base-release.apk, hello-release.apk 로 구성되어 있으며 playstore에 이렇게 묶어서 배포해야 함.
- base-release.apk
    - MyAppActivity 가 포함된 기본 모듈
    - `https://hhd.com/app` 으로 오픈 가능
- hello-release.apk
    - HelloActivity 가 포함된 기능 모듈
    - `https://hhd.com/hello` 으로 오픈 가능

## 상세 개발 과정

### 프로젝트 생성
- `Include Android Instant App Support` 체크

### base 모듈 에 MyBaseActivity 추가
- \base\src\main\AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.hhd.myinstapp">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <activity android:name=".MyBaseActivity">
            <intent-filter android:order="1"
                android:autoVerify="true">
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.BROWSABLE" />
                <category android:name="android.intent.category.DEFAULT" />

                <data
                    android:host="hhd.com"
                    android:pathPattern="/"
                    android:scheme="https" />

                <data
                    android:host="hhd.com"
                    android:pathPattern="/"
                    android:scheme="http" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

### hello 모듈 에 MainActivity 추가
- \Hello\src\main\AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.hhd.myinstapp.Hello">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter
                android:autoVerify="true"
                android:order="1">
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.BROWSABLE" />
                <category android:name="android.intent.category.DEFAULT" />

                <data
                    android:host="hhd.com"
                    android:pathPattern="/main"
                    android:scheme="https" />

                <data
                    android:host="hhd.com"
                    android:pathPattern="/main"
                    android:scheme="http" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

### base, hello모듈에 http, https 딥링크 인텐트 필터 연결

### 각 모듈을 모두 코드사이닝

### base-release.apk와 hello-release.apk를 instantapp-release.zip 으로 압축

```powershell
PS C:\project\170523_MyAndroidInstAppTest> zip C:\project\170523_MyAndroidInstAppTest\app\feature\release\base-release.apk, C:\project\170523_MyAndroidInstAppTest\app\feature\release\Hello-release.apk -DestinationPath instantapp-release.zip -Force
```

### playstore에 app-release.apk 업로드

### playstore에 instantapp-release.zip 업로드

## 테스트 과정

### Galaxy S7 단말에서 `http://hhd.com/` `http://hhd.com/main` 으로 접근하여 InstantApp 실행