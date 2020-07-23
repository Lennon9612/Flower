# FlowerClassificationApp


요약
-------------
TensorFlow를 이용하여 170종류의 꽃 클래스가 학습된 딥러닝 모델을
Android Studio를 이용하여 만든 어플리케이션에 주입하여 모바일 디바이스에서 사용할 수 있게끔 만든 결과물

사용자는 꽃 이미지를 불러온 후 해당 꽃의 이름을 찾는 버튼을 누르면
어플리케이션은 해당 꽃의 이름을 학습된 모델에서 찾아 출력해주고
부가기능으로 해당 이미지를 인터넷의 Unsplash 사이트에서 검색해 볼 수 있도록 API를 이용하여 지원



흐름도
-------------
<img width = "500" src= "https://user-images.githubusercontent.com/62198891/88307530-06e0f680-cd47-11ea-9eae-162b9cb81221.png">


개발환경
--------------
OS - Windows
Program Tool - Android Studio
Language - Java


모델
--------------
<img width = "500" src = "https://user-images.githubusercontent.com/62198891/88308688-4d832080-cd48-11ea-85eb-c5e15589b40b.png">


MobileNet_V3라는 학습된 모델을 사용하여 170 종류로 다시 전이학습 시킴
학습 Epoch 는 1200회
정확도는 79.64%



APP UI
--------------


<img width = "500" src = "https://user-images.githubusercontent.com/62198891/88310419-9d62e700-cd4a-11ea-9003-713030a8a5cd.png">


- 접근 권한 설정


앱을 실행 할 시 사용자의 Gallery와 Camera에 접근할 수 있는 접근 권한 요청
만약 접근 권한을 확인 하지 않을 시엔 어플리케이션이 종료

- 메인 레이아웃 


메인 레이아웃 구성
Gallery, URL, Camera 버튼 - 이미지 로드버튼
ImageView - 불러온 이미지를 출력
Find this flower - 불러온 이미지 분석 버튼
Search On Unsplash - Unpslash API 접근 버튼
Gallery(버튼) - 사용자의 스마트폰에 저장되어 있는 사진 로드
URL(버튼) - 인터넷 이미지 URL에서 이미지 로드
Camera 버튼은 사용자가 직접 카메라 어플리케이션 이용하여 찍은 사진을 불러오는 버튼이다. 


<img width = "500" src = "https://user-images.githubusercontent.com/62198891/88310539-bf5c6980-cd4a-11ea-8244-cd7fcf7edb19.png">


- 이미지 로드 결과 (그림 4)


MainActivity에서 Gallery, URL, Camera 버튼을 이용하여 이미지를 불러왔을 시 메인화면 가운데에 불러온 이미지 출력



- 이미지 분석 결과 (그림 5)


메인화면에서 이미지를 불러온 후 Find this Flower 버튼을 누를 시
SubActivity로 이동 후 불러온 이미지를 보여주며, 해당 이미지를 분석한 꽃 이름을 결과물로 출력하게 된다.
SubActivity에는 두가지 버튼이 존재하는데 Search Another Image 버튼을 누를 시 이전의 MainActivity,
즉 메인화면으로 돌아가 다른 이미지를 검색 할 수 있게 된다. Search this image on unsplash 버튼을 누를 시 Unplash API 인 
PhotoPickerActivity에서 이미지 분석된 결과물에 대한 검색 결과들을 출력하게 된다. 
위 예시에서는 Search this image on unsplash 버튼을 누를 시 그림과 같이 Unsplash에서 common dandelion을 검색한 결과들을 보여준다.

