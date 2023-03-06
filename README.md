# Hari_onlyRemovebg

한경대학교 문제해결프로젝트2 2학년2학기 프로젝트 결과물입니다.

배경 제거 소스 코드입니다. 코틀린으로 구현되어있습니다. 
 
시도했던 방법들

1. removeBg openAPI 사용
- 배경제거 API를 사용하여 호출 후 반환값을 이용해 구현하려고 했지만 외부 서버와 의 통신이 불가능한 안드로이드 스튜디오 특성상 실패하였다.
2. 자바로 된 배경 제거 소스 코드 사용
- 구글링을 통해 찾아보았지만 적절히 사용하지 못하여 실패하였다.
3. 자이선(Jython)을 이용한 파이선 배경 제거 소스 이용
- 안드로이드 스튜디오는 호환되지 않아 실패하였다.
4. 자바와 코틀린 호환성을 이용한 코틀린 코드의 메소드 불러오기
- 자바와 코틀린은 서로 호환성이 좋아 intent를 이용해 값 송신-수신이 원활한 특징이 있다. 구조 또한 비슷하고 문법이 조금 다를 뿐이기 때문에 이를 이용하려 했지만 코 틀린 언어에 대한 문법 이해가 부족하였고, 자바에서 코틀린 메소드를 불러오는데 실 패하였다.
5. 기존 자바코드를 모두 코틀린으로 변환하여 구현
- 4의 방법에서 자바 코드에서 코틀린 메소드를 불러오는데 실패하여 기존 코드를 전부 코틀린으로 변환하였다. 크게 문법이 다른 것이 없어 변경하는데 큰 어려움을 겪지 않았고 기능 또한 정상 작동하여 이 방법을 선택해 성공하였다.



문제점
 1. 원하는 사진을 입력 후 배경 삭제 버튼을 누르면 배경제거가 되는 것처럼 보이나, 다른 프로그램에 붙여넣기 시도하였을 시 배경이 검정색으로 되어있다.
 2. 수정된 사진의 크기가 배경제거가 수행되면서 원본크기와 다르게 된다.
 3. Canvas 클래스를 이용하여 ui상에서 합쳐져있는 상태에서 저장을 시도하였으나 배경이 지워진 사진이 저장된다.
 
개선할 점 
 1. 사물 이외의 배경은 완전히 제거 되게 할것.
 2. 원본크기를 유지할 수 있도록 할 것.
 3. 배경 변경 기능을 추가할 것.
 4. Java 또는 파이썬으로 재작성하여 개선 할 것.
