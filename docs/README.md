### 프로그램 소개
주문을 받아서 이벤트 적용 내역을 출력하는 프로그램입니다.

### 핵심 기능
적용 가능한 이벤트 해택 내역 계산

### 주요 포인트
- MVC 패턴을 Controller, Service, Repository 구조로 변형하여 역할 분리
- 할인 이벤트와 증정 이벤트를 인터페이스와 추상클래스로 분리와 동시에 공통 로직 통합
- 여러 종류의 이벤트를 Enum과 함수형 인터페이스로 관리
- Enum과 EnumMap으로 불필요 클래스 제거 및 통합 관리
- Dto를 활용한 View와 Domain 의존성 분리

### 런타임 의존관계도
![asso](https://github.com/Arachneee/java-christmas-6-Arachneee/assets/66822642/e94eb329-5991-41f3-9e21-23978a98377e)

### UML
![uml](https://github.com/Arachneee/java-christmas-6-Arachneee/assets/66822642/6e8ee17f-081f-4833-91af-e6277994e644)

### 클래스 역할
<div align="center">
<table>
    <tr>
        <th align="center">Package</th>
        <th align="center">Class</th>
        <th align="center">Description</th>
    </tr>
    <tr>
        <td rowspan="1"><b>config</b></td>
        <td><b>PlannerConfig</b></td>
        <td>의존관계 설정</td>
    </tr>
<tr><td colspan="3"></td></tr>
    <tr>
        <td rowspan="2"><b>controller</b></td>
        <td><b>OrderController</b></td>
        <td>주문 입출력(View), 주문(Service) 연결</td>
    </tr>
    <tr>
        <td><b>OrderConverter</b></td>
        <td>View 입력값 Order 인스턴스 변수로 변환</td>
    </tr>
    <tr><td colspan="3"></td></tr>
    <tr>
        <td rowspan="2"><b>domain</b><br>&rAarr; order</td>
        <td><b>Order</b></td>
        <td>주문 일자와 메뉴 수량을 갖는 클래스</td>
    </tr>
    <tr>
        <td><b>Day</b></td>
        <td>12월의 일자 원시타입 포장 클래스</td>
    </tr>
    <tr>
        <td rowspan="5"><b>domain</b><br>&rAarr; order<br>&rAarr; constant</td>
        <td><b>Category</b></td>
        <td>메뉴의 카테고리 Enum</td>
    </tr>
    <tr>
        <td><b>DayOfWeek</b></td>
        <td>요일 Enum</td>
    </tr>
    <tr>
        <td><b>December</b></td>
        <td>12월 Enum</td>
    </tr>
    <tr>
        <td><b>Menu</b></td>
        <td>메뉴 Enum</td>
    </tr>
    <tr>
        <td><b>Week</b></td>
        <td>평일/주말 Enum</td>
    </tr>
    <tr>
        <td rowspan="3"><b>domain</b><br>&rAarr; event</td>
        <td><b>Event</b></td>
        <td>이벤트 해택 계산 인터페이스</td>
    </tr>
    <tr>
        <td><b>EventRepository</b></td>
        <td>이벤트 해택 결과 저장 추상 클래스</td>
    </tr>
    <tr>
        <td><b>Badge</b></td>
        <td>배지 Enum</td>
    </tr>
    <tr>
        <td rowspan="2"><b>domain</b><br>&rAarr; event<br>&rAarr; discount</td>
        <td><b>DiscountEventType</b></td>
        <td>Event 인터페이스 구현, 할인 이벤트 통합 관리 Enum</td>
    </tr>
    <tr>
        <td><b>DiscountRepository</b></td>
        <td>EventRepository 구현, 할인 이벤트 결과 EnumMap 저장</td>
    </tr>
    <tr>
        <td rowspan="2"><b>domain</b><br>&rAarr; event<br>&rAarr; gift</td>
        <td><b>GiftEventType</b></td>
        <td>Event 인터페이스 구현, 증정 이벤트 통합 관리 Enum</td>
    </tr>
    <tr>
        <td><b>GiftRepository</b></td>
        <td>EventRepository 구현, 증정 이벤트 결과 EnumMap 저장</td>
    </tr>
    <tr><td colspan="3"></td></tr>
    <tr>
        <td rowspan="1"><b>service</b><br>&rAarr; order</td>
        <td><b>OrderService</b></td>
        <td>주문 생성 및 주문 결과 요약</td>
    </tr>
    <tr>
        <td rowspan="4"><b>service</b><br>&rAarr; event</td>
        <td><b>EventDetailService</b></td>
        <td>이벤트 적용 상세 결과 요약</td>
    </tr>
    <tr>
        <td><b>EventService</b></td>
        <td>이벤트 해택 결과 계산 추상 클래스</td>
    </tr>
    <tr>
        <td><b>DiscountService</b></td>
        <td>EventService 구현, 할인 해택 적용</td>
    </tr>
    <tr>
        <td><b>GiftService</b></td>
        <td>EventService 구현, 증정 해택 적용</td>
    </tr>
    <tr><td colspan="3"></td></tr>
    <tr>
        <td rowspan="2"><b>exception</b></td>
        <td><b>OrderException</b></td>
        <td>주문 생성 예외 발생</td>
    </tr>
    <tr>
        <td><b>ErrorMessage</b></td>
        <td>예외 메시지 Enum</td>
    </tr>
    <tr><td colspan="3"></td></tr>
    <tr>
        <td rowspan="6"><b>response</b></td>
        <td><b>OrderSummaryResponse</b></td>
        <td>주문 결과 요약 DTO</td>
    </tr>
    <tr>
        <td><b>OrderResponse</b></td>
        <td>주문 항목 DTO</td>
    </tr>
    <tr>
        <td><b>MenuCountResponse</b></td>
        <td>주문 메뉴 수량 DTO</td>
    </tr>
    <tr>
        <td><b>EventDetailResponse</b></td>
        <td>이벤트 적용 상세 내역 DTO</td>
    </tr>
    <tr>
        <td><b>EventResponse</b></td>
        <td>이벤트 적용 내역 DTO</td>
    </tr>
    <tr>
        <td><b>GiftMenuResponse</b></td>
        <td>증정 메뉴 수량 DTO</td>
    </tr>
    <tr><td colspan="3"></td></tr>
    <tr>
        <td rowspan="4"><b>view</b></td>
        <td><b>InputView</b></td>
        <td>입력 요청 View</td>
    </tr>
    <tr>
        <td><b>OutputView</b></td>
        <td>결과 출력 View</td>
    </tr>
    <tr>
        <td><b>Reader</b></td>
        <td>입력 인터페이스</td>
    </tr>
    <tr>
        <td><b>Writer</b></td>
        <td>출력 인터페이스</td>
    </tr>
    <tr>
        <td rowspan="2"><b>view</b><br>&rAarr; io</td>
        <td><b>ConsoleReader</b></td>
        <td>콘솔 입력</td>
    </tr>
    <tr>
        <td><b>ConsoleWriter</b></td>
        <td>콘솔 출력</td>
    </tr>
    

</table>
</div>

<br>

### 구현 기능 목록
#### Controller
- #### OrderController
- [x] 입력 오류시 반복 요청
- #### OrderConverter
- [x] 입력 날짜 숫자 검증
- [x] 입력 날짜 숫자 변환
- [x] 중복 메뉴 검증
- [x] 개수 숫자 검증
- [x] 개수 숫자 변환
- [x] 메뉴 개수 포맷 검증
#### Domail
- #### Day
- [x] 입력 날짜 범위 검증 (1 ~ 31)
- [x] 날짜 차이 계산
- [x] 날짜 대소비교 기능
- #### Order
- [x] 메뉴 개수 범위 검증 (1이상)
- [x] 총 주문 메뉴 개수 검증 (20이하)
- [x] 음료만 주문 검증
- [x] 카테고리별 수량 계산
- [x] 할인 전 총주문 금액 계산
- #### Menu
- [x] 메뉴 유무 검증
- #### Week
- [x] 평일/주말 구분
- [x] 평일/주말 할인 카테고리 선택
- #### DayOfWeek
- [x] 요일 구분
- #### December
- [x] 크리스마스 확인
- [x] 12월 날짜 확인
- #### DiscountEventType
- [x] 전체 할인 적용 가능 판별
- [x] 전체 할인 이벤트 적용하기
- [x] 총주문 금액 10,000 이상 확인
- [x] 크리스마스 디데이 할인 적용 여부 확인
- [x] 크리스마스 디데이 할인 금액 계산
- [x] 평일 할인 적용 금액 계산
- [x] 주말 할인 적용 금액 계산
- [x] 특별할인 적용 금액 계산
- [x] 특별할인 적용 여부 확인
- #### GiftEventType
- [x] 전체 증정 이벤트 적용 가능 판별
- [x] 전체 할인 이벤트 적용하기
- [x] 증정 이벤트 적용 여부 확인
- [x] 증정 이벤트 적용 금액 계산
- #### DiscountRepository
- [x] 할인 이벤트 결과 저장
- [x] 할인 이벤트 전체 이익 계산
- #### GiftRepository
- [x] 증정 이벤트 결과 저장
- [x] 증정 이벤트 전체 이익 계산
- [x] 증정 메뉴 항목, 수량 계산
- #### Badge
- [x] 12월 이벤트 배지 등급 계산
#### Service
- #### OrderService
- [x] 주문 내역, 이벤트 적용 내역 요약하기
- #### EventDetailService
- [x] 이벤트 적용 내역 계신
- [x] 총해택 금액 계산
- [x] 할인 후 예상 결제 금액 계산
- #### DiscountService
- [x] 전체 할인 이벤트 적용하기
- #### GiftService
- [x] 전체 증정 이벤트 적용하기
#### View
- #### InputView
- [x] 방문 날짜 입력 받기
- [x] 메뉴와 개수 입력 받기
- #### OutputView
- [x] Hello 헤더 출력
- [x] 증정 메뉴 출력
- [x] 이벤트 혜택 미리보기 출력
- [x] 주문 메뉴 출력
- [x] 할인 전 총주문 금액 출력
- [x] 해택 내역 출력
- [x] 총해택 금액 출력
- [x] 할인 후 예상 결제 금액 출력
- [x] 12월 이벤트 배지 출력