<div float="left">
  <img src="https://github.com/GDSC-StreetReview/GDSC_Challenge_BE/assets/74559561/73eba101-7ed9-4249-b645-2d69fbd6edfc" width="100" height="100"/>
  <h1 float="right">GDSC Solution Challenge Backend Team</h1>
</div>

jenkins Test5


**SuiteRoom**

| 이름 | 속성 | 설명 | 선택 | 비고 |
| --- | --- | --- | --- | --- |
| suiteRoomId | Long | PK |  |  |
| title | String | 스터디 제목 |  | 50자 이하 [100Byte] |
| content | String | 스터디 내용 |  |  |
| subject | String | 스터디 주제 |  | studyCategory 선택 |
| recruitmentDeadLine | Timestamp | 스터디 모집마감일 |  |  |
| studyDeadLine | Timestamp | 스터디 종료일 |  | 모집마감일 초과 |
| recruitmentLimit | Integer | 모집 인원 |  | 2명 이상 |
| depositAmount | Integer | 보증 금액  |  | 10,000원 이상 |
| minAttendanceRate | Integer | 최소 출석률 |  | 80% 이상 |
| minMissionCompleteRate | Integer | 최소 미션 달성률 |  | 80% 이상 |
| isPublic | Boolean | 공개여부 | O |  |
| isOpen | Boolean | 개설여부 |  | 방장이 돈을 내야 다른 사용자의 스터디 그룹 목록에 올라옵니다. |
| password | Integer | 비공개 비밀번호 | O | 숫자 4자리 |
| channelLink | String | 오픈카톡방 링크 |  |  |
| studyMethod | String | 스터디 형식 |  | StudyMethodCategory 선택 |
| studyLocation | String | 스터디 위치 | O | LocationCategory 선택 |
| contractAddress | String | 스마트컨트랙트 PK |  |  |
