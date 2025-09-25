# 1일차 개발일지

## 목표
- 앱 화면 레이아웃을 구성했습니다.
- ConstraintLayout을 활용하여 UI 요소를 효율적으로 배치하는 연습을 했습니다.
- 이미지뷰와 텍스트뷰를 정렬하고 사용자 상호작용에 따라 텍스트 색상을 변경하는 기능을 구현했습니다.

## 진행 내용
- UI 레이아웃 구성: XML 파일을 사용하여 앱의 메인 화면 UI를 설계하고 구현했습니다. ‘오늘 하루는 어땠나요?’와 같은 질문과 설명 텍스트를 배치하고 5가지 감정 상태를 나타내는 이미지와 해당 텍스트를 수직으로 정렬했습니다.

- ConstraintLayout 활용
    - Guideline: 상단에 head_guideline과 content_guideline을 설정하여 전체적인 UI 요소의 위치를 고정했습니다. 이는 화면 크기가 달라져도 일관된 상단 여백을 유지하는 데 도움이 됩니다.
    - 뷰 간 제약: 각 **ImageView**와 **TextView**는 바로 위의 뷰에 연결되도록 제약을 주었습니다. 

- 이벤트 핸들링:
    - 클릭 리스너: 각 감정 **ImageView**에 setOnClickListener를 적용하여 사용자가 이미지를 클릭했을 때 이벤트를 감지하도록 했습니다.
    - 색상 변경 로직: addChangeColorEvent라는 함수를 만들어서 클릭된 이미지에 해당하는 **TextView**의 색상을 **Color.parseColor()**를 이용해 변경하도록 구현했습니다. 

- Toast 메시지: 사용자가 이미지를 클릭하면 Toast 메시지(“색상을 변경했습니다.”)가 잠시 나타나도록 구현했습니다. 

# 트러블 슈팅

## 이슈
addChangeColorEvent 함수를 MainActivity 클래스 외부에 선언했을 때 Context 관련 오류가 발생합니다.

## 문제
Android에서 UI를 변경하는 것과 같은 작업은 **Context**에 의존합니다. Context는 현재 앱이 어떤 환경에서 실행되고 있는지에 대한 정보를 담고 있습니다. 함수가 MainActivity 클래스 밖에 있으면 함수 내부의 this 키워드가 Context를 참조하지 못해 **findViewById**와 같은 메서드를 호출할 때 오류가 발생합니다.

## 해결
함수를 MainActivity 클래스 내부의 멤버 함수로 만듭니다. 이렇게 하면 함수가 MainActivity의 Context에 접근할 수 있게 되어 오류가 해결됩니다.